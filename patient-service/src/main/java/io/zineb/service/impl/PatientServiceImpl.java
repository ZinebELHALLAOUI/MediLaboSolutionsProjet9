package io.zineb.service.impl;

import io.zineb.model.Patient;
import io.zineb.repository.PatientRepository;
import io.zineb.service.PatientService;
import io.zineb.service.exception.NotFoundEntityException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;

    @Override
    public List<Patient> findPatientByFirstnameOrLastname(String query) {
        return patientRepository.findAllByFirstNameOrLastName(query,query);
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
    public Patient createPatient(Patient patient) {
        patient.setId(null);
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(long id, Patient patient) {
        if (findPatientById(id).isEmpty()) {
            throw new NotFoundEntityException(String.format("Patient %s not found", patient.getId()));
        }
        patient.setId(id);
        return patientRepository.save(patient);
    }

}