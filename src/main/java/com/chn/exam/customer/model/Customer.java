package com.chn.exam.customer.model;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.chn.exam.common.model.audit.Auditable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
public class Customer extends Auditable{
    @Id
    @UuidGenerator
    @Column(nullable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "identification_number", nullable = false, length = 13, unique = true)
    private String identificationNumber;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false, length = 150, unique = true)
    private String email;

    @Column(nullable = false, length = 8)
    private String phone;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
