package io.zineb.controller.dto;

import io.zineb.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Patient.Gender gender;
    private String address;
    private String phoneNumber;
    private String riskDiabetes;

    public static PatientDto toDto(Patient patient) {
        return new PatientDto(
                patient.id(),
                patient.firstName(),
                patient.lastName(),
                patient.dateOfBirth(),
                patient.gender(),
                patient.address(),
                patient.phoneNumber(),
                null
        );
    }
}
