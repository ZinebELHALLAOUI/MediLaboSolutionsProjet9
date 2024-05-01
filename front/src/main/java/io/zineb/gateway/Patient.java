package io.zineb.gateway;

import java.time.LocalDate;

public record Patient(Long id, String firstName, String lastName, LocalDate dateOfBirth, Gender gender, String address,
               String phoneNumber) {
    enum Gender {
        F,M
    }
}