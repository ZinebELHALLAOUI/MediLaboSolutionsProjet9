package io.zineb.repository;

import feign.FeignException;
import io.zineb.gateway.GatewayProvider;
import io.zineb.model.Patient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class PatientRepositoryImpl implements PatientRepository {

    private GatewayProvider gatewayProvider;

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

}
