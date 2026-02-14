package com.chn.exam.customer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.chn.exam.common.config.mapstruct.MapStructConfig;
import com.chn.exam.customer.dto.*;
import com.chn.exam.customer.model.Customer;

@Mapper(config = MapStructConfig.class)
public interface CustomerMapper {
    CustomerResponseDTO toResponseDto(Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Customer toEntity(CreateCustomerRequestDTO request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "identificationNumber", ignore = true)
    void updateEntityFromDto(UpdateCustomerRequestDTO request, @MappingTarget Customer customer);
}
