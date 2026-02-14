package com.chn.exam.customer.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateCustomerRequestDTO(
    @Size(min = 2, max = 50)
    String firstName,
    @Size(min = 2, max = 50)
    String lastName,
    @Pattern(regexp = "\\d{13}")
    String identificationNumber,
    @Size(max = 255)
    String address,
    @Email
    String email,
    @Pattern(regexp = "\\d{8}")
    String phone,
    @Past
    LocalDate birthDate
) {
}
