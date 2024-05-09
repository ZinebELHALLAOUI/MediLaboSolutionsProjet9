package io.zineb.service;

import io.zineb.model.Patient;
import io.zineb.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public List<Patient> findPatientByFirstnameOrLastname(String query) {
        return patientRepository.findPatientsByFirstnameOrLastname(query);
    }

    public Optional<Patient> getPatientById(long id) {
        return patientRepository.getPatientById(id);
    }

    public void createPatient(Patient patient) {
        patientRepository.createPatient(patient);
    }

    public void updatePatient(long id, Patient patient) {
        patientRepository.updatePatient(id, patient);
    }

    public List<Patient> getAll(){
        return patientRepository.getAll();
    }
}
