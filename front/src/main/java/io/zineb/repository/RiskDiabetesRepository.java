package io.zineb.repository;

import io.zineb.model.Risk;

import java.util.Optional;

public interface RiskDiabetesRepository {
    Optional<Risk> getRiskByPatient(long patientId);
}
