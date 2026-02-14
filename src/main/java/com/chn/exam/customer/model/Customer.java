package com.chn.exam.customer.model;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.chn.exam.common.model.audit.Auditable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Data
@EqualsAndHashCode(of = "identificationNumber", callSuper = false)
@NoArgsConstructor
public class Customer extends Auditable{
    @Id
    @UuidGenerator
    private UUID id;
    private String firstName;
    private String lastName;
    private String identificationNumber;
    private LocalDate birthDate;
    private String address;
    private String email;
    private String phone;
}
