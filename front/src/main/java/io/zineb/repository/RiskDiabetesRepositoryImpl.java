package io.zineb.repository;

import feign.FeignException;
import io.zineb.gateway.GatewayProvider;
import io.zineb.model.Risk;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class RiskDiabetesRepositoryImpl implements RiskDiabetesRepository {

    private final GatewayProvider gatewayProvider;

    @Override
    public Optional<Risk> getRiskByPatient(long patientId) {
        try {
            Optional<Risk> risk = Optional.ofNullable(gatewayProvider.getRiskDiabetesForPatient(patientId));
            return risk;
        } catch (FeignException.FeignClientException e) {
            if (e.status() == 404)
                return Optional.empty();
            else throw e;
        }
    }
}
