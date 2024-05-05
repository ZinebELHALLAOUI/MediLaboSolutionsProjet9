package io.zineb.service;

import io.zineb.model.Risk;
import io.zineb.repository.RiskDiabetesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RiskDiabetesService {

    private final RiskDiabetesRepository riskDiabetesRepository;

    public Optional<Risk> getRiskByPatient(long patientId) {
        return riskDiabetesRepository.getRiskByPatient(patientId);
    }

}
