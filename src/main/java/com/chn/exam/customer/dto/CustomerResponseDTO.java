package com.chn.exam.customer.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record CustomerResponseDTO(
    UUID id,
    String firstName,
    String lastName,
    String identificationNumber,
    LocalDate birthDate,
    String address,
    String email,
    String phone,
    Instant createdAt,
    Instant updatedAt
) {}
