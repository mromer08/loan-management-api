package com.chn.exam.loan.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.chn.exam.common.config.mapstruct.MapStructConfig;
import com.chn.exam.loan.dto.LoanResponseDTO;
import com.chn.exam.loan.dto.SubmitLoanApplicationRequestDTO;
import com.chn.exam.loan.model.Loan;

@Mapper(config = MapStructConfig.class)
public interface LoanMapper {
    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "customerFullName", expression = "java(loan.getCustomer().getFullName())")
    LoanResponseDTO toResponseDto(Loan loan);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "totalPayable", ignore = true)
    @Mapping(target = "outstandingBalance", ignore = true)
    @Mapping(target = "paymentStatus", ignore = true)
    Loan toEntity(SubmitLoanApplicationRequestDTO request);
}
