package io.zineb.model;

import java.time.LocalDate;

public record Patient(Long id, String firstName, String lastName, LocalDate dateOfBirth, Gender gender, String address,
               String phoneNumber) {
    public enum Gender {
        F,M
    }
}