package com.chn.exam.customer.controller;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chn.exam.common.dto.PagedResponseDTO;
import com.chn.exam.customer.dto.*;
import com.chn.exam.customer.service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<PagedResponseDTO<CustomerResponseDTO>> getCustomers(
        @RequestParam(required = false) String searchTerm,
        @PageableDefault Pageable pageable
    ){
        GetCustomersRequestDTO requestDTO = new GetCustomersRequestDTO(searchTerm);
        PagedResponseDTO<CustomerResponseDTO> customers = customerService.getCustomers(requestDTO, pageable);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(
        @PathVariable UUID id
    ){
        CustomerResponseDTO customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(
        @RequestBody @Valid CreateCustomerRequestDTO requestDTO
    ){
        CustomerResponseDTO customer = customerService.createCustomer(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(
        @PathVariable UUID id,
        @RequestBody @Valid UpdateCustomerRequestDTO requestDTO
    ){
        CustomerResponseDTO customer = customerService.updateCustomer(id, requestDTO);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(
        @PathVariable UUID id
    ){
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
