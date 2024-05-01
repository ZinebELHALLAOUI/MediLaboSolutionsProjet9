package io.zineb.repository;

import io.zineb.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository {
    Optional<Patient> getPatientById(long id);
}
