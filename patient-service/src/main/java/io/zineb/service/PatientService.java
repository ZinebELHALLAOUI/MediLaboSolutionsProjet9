package io.zineb.service;

import io.zineb.model.Patient;

import java.util.Optional;

public interface PatientService {

    Optional<Patient> findPatient(String firstName, String lastName);

    Patient savePatient(Patient patient);

    Patient updatePatient(Patient patient);

}