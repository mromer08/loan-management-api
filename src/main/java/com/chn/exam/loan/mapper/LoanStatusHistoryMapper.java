package com.chn.exam.loan.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.chn.exam.common.config.mapstruct.MapStructConfig;
import com.chn.exam.loan.dto.LoanStatusHistoryResponse;
import com.chn.exam.loan.model.LoanStatusHistory;

@Mapper(config = MapStructConfig.class)
public interface LoanStatusHistoryMapper {
    @Mapping(target = "loanId", source = "loan.id")
    LoanStatusHistoryResponse toResponseDto(LoanStatusHistory history);
}
