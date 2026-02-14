package com.chn.exam.customer.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chn.exam.common.dto.PagedResponseDTO;
import com.chn.exam.common.mapper.PageMapper;
import com.chn.exam.customer.dto.*;
import com.chn.exam.customer.exception.*;
import com.chn.exam.customer.mapper.CustomerMapper;
import com.chn.exam.customer.model.Customer;
import com.chn.exam.customer.repository.CustomerRepository;
import com.chn.exam.customer.specification.CustomerSpecs;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional(readOnly = true)
    public PagedResponseDTO<CustomerResponseDTO> getCustomers(GetCustomersRequestDTO request, Pageable pageable) {
        Specification<Customer> specification = CustomerSpecs.searchTermInAttributes(
            request.searchTerm()
        );
        Page<Customer> customersPage = customerRepository.findAll(specification, pageable);
        Page<CustomerResponseDTO> customersDtoPage = customersPage.map(customerMapper::toResponseDto);
        return PageMapper.toPagedResponse(customersDtoPage);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDTO getCustomerById(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> CustomerNotFoundException.forId(id));
        return customerMapper.toResponseDto(customer);
    }

    @Override
    public CustomerResponseDTO createCustomer(CreateCustomerRequestDTO request) {
        if (customerRepository.existsByIdentificationNumber(request.identificationNumber())) {
            throw DuplicateCustomerException.forIdentificationNumber(request.identificationNumber());
        }
        if (customerRepository.existsByPhone(request.phone())) {
            throw DuplicateCustomerException.forPhone(request.phone());
        }
        if (request.email() != null && customerRepository.existsByEmail(request.email())) {
            throw DuplicateCustomerException.forEmail(request.email());
        }
        Customer customer = customerMapper.toEntity(request);
        customer = customerRepository.save(customer);
        return customerMapper.toResponseDto(customer);
    }

    @Override
    public CustomerResponseDTO updateCustomer(UUID id, UpdateCustomerRequestDTO request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> CustomerNotFoundException.forId(id));
        
        if(request.identificationNumber() != null && customerRepository.existsByIdentificationNumberAndIdNot(request.identificationNumber(), id)) {
            throw DuplicateCustomerException.forIdentificationNumber(request.identificationNumber());
        }
        if (request.email() != null && customerRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw DuplicateCustomerException.forEmail(request.email());
        }
        if (request.phone() != null && customerRepository.existsByPhoneAndIdNot(request.phone(), id)) {
            throw DuplicateCustomerException.forPhone(request.phone());
        }
        customerMapper.updateEntityFromDto(request, customer);
        customer = customerRepository.save(customer);
        return customerMapper.toResponseDto(customer);
    }

    @Override
    public void deleteCustomer(UUID id) {
        if (!customerRepository.existsById(id)) {
            throw CustomerNotFoundException.forId(id);
        }
        customerRepository.deleteById(id);
    }

}
