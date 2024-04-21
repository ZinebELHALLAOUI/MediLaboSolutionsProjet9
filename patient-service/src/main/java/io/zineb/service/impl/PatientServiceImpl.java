package io.zineb.service.impl;

import io.zineb.model.Patient;
import io.zineb.repository.PatientRepository;
import io.zineb.service.PatientService;
import io.zineb.service.exception.DuplicatedEntityException;
import io.zineb.service.exception.NotFoundEntityException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;

    @Override
    public Optional<Patient> findPatient(String firstName, String lastName) {
        return patientRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Patient savePatient(Patient patient) {
        Optional<Patient> maybePatient = findPatient(patient.getFirstName(), patient.getLastName());
        if (maybePatient.isPresent())
            throw new DuplicatedEntityException(String.format("Patient %s %s already exist", patient.getFirstName(), patient.getLastName()));
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(Patient patient) {
        Optional<Patient> maybePatient = findPatient(patient.getFirstName(), patient.getLastName());
        if (maybePatient.isEmpty())
            throw new NotFoundEntityException(String.format("Patient %s %s not found", patient.getFirstName(), patient.getLastName()));
        patient.setId(maybePatient.get().getId());
        return patientRepository.save(patient);
    }

}