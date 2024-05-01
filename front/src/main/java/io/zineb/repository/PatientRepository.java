package io.zineb.repository;

import io.zineb.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository {
    List<Patient> findPatientsByFirstnameOrLastname(String query);

    Optional<Patient> getPatientById(long id);

    void createPatient(Patient patient);

    void updatePatient(Patient patient);

    List<Patient> getAll();

}
