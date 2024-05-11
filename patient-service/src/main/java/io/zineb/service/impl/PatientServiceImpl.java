package io.zineb.service.impl;

import io.zineb.controller.PatientDto;
import io.zineb.model.Patient;
import io.zineb.repository.GenderRepository;
import io.zineb.repository.PatientRepository;
import io.zineb.service.PatientService;
import io.zineb.service.exception.NotFoundEntityException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;
    private GenderRepository genderRepository;

    @Override
    public List<Patient> findPatientByFirstnameOrLastname(String query) {
        return patientRepository.findAllByFirstNameOrLastName(query, query);
    }

    @Override
    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    @Override
    public Optional<Patient> findPatientById(long id) {
        return patientRepository.findById(id);
    }

    @Override
    public Patient createPatient(PatientDto patient) {
        Assert.isNull(patient.id(), () -> "Patient id should be null for create");
        Patient newPatient = new Patient(
                null,
                patient.firstName(),
                patient.lastName(),
                patient.dateOfBirth(),
                genderRepository.findByName(patient.gender().name()).get(),
                patient.address(),
                patient.phoneNumber()
        );
        return patientRepository.save(newPatient);
    }

    @Override
    public Patient updatePatient(long id, PatientDto patient) {
        if (findPatientById(id).isEmpty()) {
            throw new NotFoundEntityException(String.format("Patient %s not found", patient.id()));
        }

        Patient patientToUpdate = new Patient(
                id,
                patient.firstName(),
                patient.lastName(),
                patient.dateOfBirth(),
                genderRepository.findByName(patient.gender().name()).get(),
                patient.address(),
                patient.phoneNumber()
        );
        return patientRepository.save(patientToUpdate);
    }

}