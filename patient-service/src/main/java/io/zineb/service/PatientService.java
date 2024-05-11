package io.zineb.service;

import io.zineb.controller.PatientDto;
import io.zineb.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    List<Patient> findPatientByFirstnameOrLastname(String query);

    List<Patient> getAll();

    Optional<Patient> findPatientById(long id);

    Patient createPatient(PatientDto patient);

    Patient updatePatient(long id, PatientDto patient);

}