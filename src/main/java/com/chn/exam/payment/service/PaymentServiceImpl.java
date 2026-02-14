package com.chn.exam.payment.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chn.exam.common.dto.PagedResponseDTO;
import com.chn.exam.common.mapper.PageMapper;
import com.chn.exam.loan.exception.LoanNotFoundException;
import com.chn.exam.loan.model.Loan;
import com.chn.exam.loan.model.LoanPaymentStatus;
import com.chn.exam.loan.model.LoanStatus;
import com.chn.exam.loan.repository.LoanRepository;
import com.chn.exam.payment.dto.LoanPaymentResponseDTO;
import com.chn.exam.payment.dto.RegisterLoanPaymentRequestDTO;
import com.chn.exam.payment.exception.InvalidLoanPaymentException;
import com.chn.exam.payment.mapper.PaymentMapper;
import com.chn.exam.payment.model.Payment;
import com.chn.exam.payment.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private static final BigDecimal ZERO = BigDecimal.ZERO.setScale(2);

    private final PaymentRepository paymentRepository;
    private final LoanRepository loanRepository;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional(readOnly = true)
    public PagedResponseDTO<LoanPaymentResponseDTO> getLoanPayments(UUID loanId, Pageable pageable) {
        if (!loanRepository.existsById(loanId)) {
            throw LoanNotFoundException.forId(loanId);
        }

        Page<Payment> paymentsPage = paymentRepository.findByLoanId(loanId, pageable);

        Page<LoanPaymentResponseDTO> paymentsDtoPage = paymentsPage.map(paymentMapper::toResponseDto);
        return PageMapper.toPagedResponse(paymentsDtoPage);
    }

    @Override
    public LoanPaymentResponseDTO registerLoanPayment(UUID loanId, RegisterLoanPaymentRequestDTO request) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> LoanNotFoundException.forId(loanId));

        if (loan.getStatus() != LoanStatus.APPROVED) {
            throw InvalidLoanPaymentException.forLoanStatus(loan.getStatus());
        }

        BigDecimal outstandingBalance = loan.getOutstandingBalance();
        if (outstandingBalance == null || outstandingBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw InvalidLoanPaymentException.invalidOutstandingBalance();
        }

        BigDecimal paymentAmount = request.amount().setScale(2);
        if (paymentAmount.compareTo(outstandingBalance) > 0) {
            throw InvalidLoanPaymentException.amountExceedsOutstanding(paymentAmount, outstandingBalance);
        }

        Payment payment = paymentMapper.toEntity(request);
        payment.setLoan(loan);
        payment.setPaymentDate(payment.getPaymentDate() == null ? LocalDateTime.now() : payment.getPaymentDate());
        Payment savedPayment = paymentRepository.save(payment);

        BigDecimal updatedOutstandingBalance = outstandingBalance.subtract(paymentAmount).setScale(2);
        loan.setOutstandingBalance(updatedOutstandingBalance);
        loan.setPaymentStatus(resolvePaymentStatus(updatedOutstandingBalance));
        loanRepository.save(loan);

        return paymentMapper.toResponseDto(savedPayment);
    }

    private LoanPaymentStatus resolvePaymentStatus(BigDecimal outstandingBalance) {
        return outstandingBalance.compareTo(ZERO) == 0
                ? LoanPaymentStatus.PAID
                : LoanPaymentStatus.PARTIALLY_PAID;
    }
}
