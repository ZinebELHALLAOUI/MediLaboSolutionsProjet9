package io.zineb.controller;

import io.zineb.model.Patient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Arrays;


public record PatientDto(Long id, @NotBlank String firstName, @NotBlank String lastName, @NotNull LocalDate dateOfBirth,
                         @NotNull Gender gender,
                         String address,
                         String phoneNumber) {
    public enum Gender {
        F, M
    }

    public static PatientDto toDto(Patient patient) {
        return new PatientDto(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getDateOfBirth(),
                Arrays.stream(Gender.values()).filter(g -> g.name().equals(patient.getGender().getName())).findAny().orElseThrow(),
                patient.getAddress(),
                patient.getPhoneNumber()
        );
    }
}