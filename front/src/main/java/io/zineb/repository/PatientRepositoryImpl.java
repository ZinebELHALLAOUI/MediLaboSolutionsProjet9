package io.zineb.repository;

import feign.FeignException;
import io.zineb.gateway.GatewayProvider;
import io.zineb.model.Patient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PatientRepositoryImpl implements PatientRepository {

    private GatewayProvider gatewayProvider;

    @Override
    public List<Patient> findPatientsByFirstnameOrLastname(String query) {
        return gatewayProvider.patientsSearch(query);
    }

    @Override
    public Optional<Patient> getPatientById(long id) {
        try {
            Optional<Patient> patient = Optional.ofNullable(gatewayProvider.getPatient(id));
            return patient;
        } catch (FeignException.FeignClientException e) {
            if (e.status() == 404)
                return Optional.empty();
            else throw e;
        }
    }

    @Override
    public void createPatient(Patient patient) {
        gatewayProvider.createPatient(patient);
    }

    @Override
    public void updatePatient(long id, Patient patient) {
        gatewayProvider.updatePatient(id, patient);
    }

    @Override
    public List<Patient> getAll() {
        return gatewayProvider.getAllPatients();
    }
}
