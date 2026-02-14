package com.chn.exam.loan.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chn.exam.common.dto.PagedResponseDTO;
import com.chn.exam.common.mapper.PageMapper;
import com.chn.exam.customer.exception.CustomerNotFoundException;
import com.chn.exam.customer.model.Customer;
import com.chn.exam.customer.repository.CustomerRepository;
import com.chn.exam.loan.dto.GetLoansRequestDTO;
import com.chn.exam.loan.dto.LoanResponseDTO;
import com.chn.exam.loan.dto.LoanStatusHistoryResponse;
import com.chn.exam.loan.dto.ReviewLoanApplicationRequestDTO;
import com.chn.exam.loan.dto.SubmitLoanApplicationRequestDTO;
import com.chn.exam.loan.exception.InvalidLoanStatusTransitionException;
import com.chn.exam.loan.exception.LoanNotFoundException;
import com.chn.exam.loan.mapper.LoanMapper;
import com.chn.exam.loan.mapper.LoanStatusHistoryMapper;
import com.chn.exam.loan.model.Loan;
import com.chn.exam.loan.model.LoanPaymentStatus;
import com.chn.exam.loan.model.LoanStatus;
import com.chn.exam.loan.model.LoanStatusHistory;
import com.chn.exam.loan.repository.LoanRepository;
import com.chn.exam.loan.repository.LoanStatusHistoryRepository;
import com.chn.exam.loan.specification.LoanSpecs;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {
    private static final BigDecimal DEFAULT_ANNUAL_INTEREST_RATE = new BigDecimal("5.00");
    private static final BigDecimal MONTHS_IN_YEAR = BigDecimal.valueOf(12);
    private static final BigDecimal PERCENT = BigDecimal.valueOf(100);

    private final LoanRepository loanRepository;
    private final LoanStatusHistoryRepository loanStatusHistoryRepository;
    private final CustomerRepository customerRepository;
    private final LoanMapper loanMapper;
    private final LoanStatusHistoryMapper loanStatusHistoryMapper;

    @Override
    @Transactional(readOnly = true)
    public LoanResponseDTO getLoanById(UUID loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> LoanNotFoundException.forId(loanId));
        return loanMapper.toResponseDto(loan);
    }

    @Override
    public LoanResponseDTO submitLoanApplication(UUID customerId, SubmitLoanApplicationRequestDTO request) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> CustomerNotFoundException.forId(customerId));

        Loan loan = loanMapper.toEntity(request);
        loan.setCustomer(customer);
        loan.setLoanDate(loan.getLoanDate() == null ? LocalDateTime.now() : loan.getLoanDate());
        loan.setAnnualInterestRate(resolveAnnualInterestRate(loan.getAnnualInterestRate()));
        loan.setStatus(LoanStatus.IN_PROCESS);

        Loan savedLoan = loanRepository.save(loan);
        saveLoanStatusHistory(savedLoan, savedLoan.getStatus(), savedLoan.getPurpose());

        return loanMapper.toResponseDto(savedLoan);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponseDTO<LoanResponseDTO> getLoanApplicationsByCustomer(UUID customerId, GetLoansRequestDTO request, Pageable pageable) {
        if (!customerRepository.existsById(customerId)) {
            throw CustomerNotFoundException.forId(customerId);
        }
        Specification<Loan> specification = Specification.where(LoanSpecs.customerIdEquals(customerId))
                .and(LoanSpecs.statusEquals(request.status()));

        Page<Loan> loansPage = loanRepository.findAll(specification, pageable);
        Page<LoanResponseDTO> loansDtoPage = loansPage.map(loanMapper::toResponseDto);

        return PageMapper.toPagedResponse(loansDtoPage);
    }

    @Override
    public LoanResponseDTO approveLoanApplication(UUID loanId, ReviewLoanApplicationRequestDTO request) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> LoanNotFoundException.forId(loanId));

        if (loan.getStatus() != LoanStatus.IN_PROCESS) {
            throw InvalidLoanStatusTransitionException.forAction("aprobar", loan.getStatus());
        }

        BigDecimal annualInterestRate = resolveAnnualInterestRate(loan.getAnnualInterestRate());
        BigDecimal totalPayable = calculateTotalPayable(loan.getAmount(), annualInterestRate, loan.getTermMonths());

        loan.setAnnualInterestRate(annualInterestRate);
        loan.setTotalPayable(totalPayable);
        loan.setOutstandingBalance(totalPayable);
        loan.setPaymentStatus(LoanPaymentStatus.UNPAID);
        loan.setStatus(LoanStatus.APPROVED);

        Loan updatedLoan = loanRepository.save(loan);
        saveLoanStatusHistory(updatedLoan, updatedLoan.getStatus(), request.notes());

        return loanMapper.toResponseDto(updatedLoan);
    }

    @Override
    public LoanResponseDTO rejectLoanApplication(UUID loanId, ReviewLoanApplicationRequestDTO request) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> LoanNotFoundException.forId(loanId));

        if (loan.getStatus() != LoanStatus.IN_PROCESS) {
            throw InvalidLoanStatusTransitionException.forAction("rechazar", loan.getStatus());
        }

        loan.setStatus(LoanStatus.REJECTED);
        Loan updatedLoan = loanRepository.save(loan);
        saveLoanStatusHistory(updatedLoan, updatedLoan.getStatus(), request.notes());

        return loanMapper.toResponseDto(updatedLoan);
    }

    private void saveLoanStatusHistory(Loan loan, LoanStatus status, String notes) {
        LoanStatusHistory loanStatusHistory = LoanStatusHistory.builder()
                .loan(loan)
                .status(status)
                .notes(notes)
                .build();
        loanStatusHistoryRepository.save(loanStatusHistory);
    }

    @Override
    public PagedResponseDTO<LoanStatusHistoryResponse> getLoanStatusHistory(UUID loanId, Pageable pageable) {
        if (!loanRepository.existsById(loanId)) {
            throw LoanNotFoundException.forId(loanId);
        }
        Page<LoanStatusHistory> historyPage = loanStatusHistoryRepository.findByLoanId(loanId, pageable);
        Page<LoanStatusHistoryResponse> historyDtoPage = historyPage.map(loanStatusHistoryMapper::toResponseDto);
        return PageMapper.toPagedResponse(historyDtoPage);
    }

    private BigDecimal resolveAnnualInterestRate(BigDecimal annualInterestRate) {
        return annualInterestRate == null ? DEFAULT_ANNUAL_INTEREST_RATE : annualInterestRate;
    }

    private BigDecimal calculateTotalPayable(BigDecimal amount, BigDecimal annualInterestRate, Integer termMonths) {
        BigDecimal termInYears = BigDecimal.valueOf(termMonths)
                .divide(MONTHS_IN_YEAR, 10, RoundingMode.HALF_UP);

        BigDecimal totalInterest = amount
                .multiply(annualInterestRate)
                .multiply(termInYears)
                .divide(PERCENT, 10, RoundingMode.HALF_UP);

        return amount.add(totalInterest).setScale(2, RoundingMode.HALF_UP);
    }
}
