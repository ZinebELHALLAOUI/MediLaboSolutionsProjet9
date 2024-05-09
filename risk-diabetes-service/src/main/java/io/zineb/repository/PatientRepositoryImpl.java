package io.zineb.repository;

import feign.FeignException;
import io.zineb.model.Patient;
import io.zineb.provider.PatientProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class PatientRepositoryImpl implements PatientRepository {

    private final PatientProvider patientProvider;

    @Override
    public Optional<Patient> getPatientById(long id) {
        try {
            Optional<Patient> patient = Optional.ofNullable(patientProvider.getPatient(id));
            return patient;
        } catch (FeignException.FeignClientException e) {
            if (e.status() == 404)
                return Optional.empty();
            else throw e;
        }
    }

}
