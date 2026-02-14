package com.chn.exam.customer.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.chn.exam.customer.model.Customer;

public interface CustomerRepository extends
        JpaRepository<Customer, UUID>,
        JpaSpecificationExecutor<Customer> {
    boolean existsByIdentificationNumber(String identificationNumber);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByPhoneAndIdNot(String phone, UUID id);
    boolean existsByEmailAndIdNot(String email, UUID id);
    boolean existsByIdentificationNumberAndIdNot(String identificationNumber, UUID id);
}
