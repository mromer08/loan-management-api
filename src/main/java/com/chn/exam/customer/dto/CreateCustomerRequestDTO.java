package com.chn.exam.customer.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateCustomerRequestDTO(
    @NotBlank
    @Size(min = 2, max = 50)
    String firstName,

    @NotBlank
    @Size(min = 2, max = 50)
    String lastName,

    @NotBlank
    @Pattern(regexp = "\\d{13}")
    String identificationNumber,
    
    @NotNull
    @Past
    LocalDate birthDate,

    @NotBlank
    @Size(max = 255)
    String address,

    @Email
    String email,

    @NotBlank
    @Pattern(regexp = "\\d{8}")
    String phone
) {}
