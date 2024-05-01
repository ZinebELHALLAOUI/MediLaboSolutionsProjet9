package io.zineb.controller;

import io.zineb.service.Risk;
import io.zineb.service.RiskDiabetesCalculatorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("risks")
@AllArgsConstructor
public class RiskController {

    private final RiskDiabetesCalculatorService riskDiabetesCalculatorService;

    @GetMapping
    public ResponseEntity<Risk> getRiskForPatient(@RequestParam long patientId) {
        return ResponseEntity.ok(riskDiabetesCalculatorService.calculateRiskForPatient(patientId));
    }
}
