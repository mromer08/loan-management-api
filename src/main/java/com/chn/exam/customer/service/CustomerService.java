package com.chn.exam.customer.service;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.chn.exam.common.dto.PagedResponseDTO;
import com.chn.exam.customer.dto.*;

public interface CustomerService {
    PagedResponseDTO<CustomerResponseDTO> getCustomers(GetCustomersRequestDTO request, Pageable pageable);
    CustomerResponseDTO getCustomerById(UUID id);
    CustomerResponseDTO createCustomer(CreateCustomerRequestDTO request);
    CustomerResponseDTO updateCustomer(UUID id, UpdateCustomerRequestDTO request);
    void deleteCustomer(UUID id);
}
