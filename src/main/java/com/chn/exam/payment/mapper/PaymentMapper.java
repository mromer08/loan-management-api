package com.chn.exam.payment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.chn.exam.common.config.mapstruct.MapStructConfig;
import com.chn.exam.payment.dto.LoanPaymentResponseDTO;
import com.chn.exam.payment.dto.RegisterLoanPaymentRequestDTO;
import com.chn.exam.payment.model.Payment;

@Mapper(config = MapStructConfig.class)
public interface PaymentMapper {
    @Mapping(target = "loanId", source = "loan.id")
    @Mapping(target = "customerId", source = "loan.customer.id")
    LoanPaymentResponseDTO toResponseDto(Payment payment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "loan", ignore = true)
    Payment toEntity(RegisterLoanPaymentRequestDTO request);
}
