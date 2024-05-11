package io.zineb.controller;

import io.zineb.model.Patient;
import io.zineb.service.PatientService;
import io.zineb.service.exception.NotFoundEntityException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patients")
@AllArgsConstructor
@Slf4j
public class PatientController {

    private final PatientService patientService;

    @GetMapping("search")
    public ResponseEntity<List<PatientDto>> findPatientsByFirstnameOrLastname(@RequestParam String query) {
        List<PatientDto> patients = patientService.findPatientByFirstnameOrLastname(query).stream().map(PatientDto::toDto).toList();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("{id}")
    public ResponseEntity<PatientDto> getPatient(@PathVariable long id) {
        Optional<Patient> patient = patientService.findPatientById(id);
        return patient.map(p -> ResponseEntity.ok(PatientDto.toDto(p))).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAll().stream().map(PatientDto::toDto).toList());
    }

    @PostMapping
    public ResponseEntity<PatientDto> createPatient(@Validated @RequestBody PatientDto patient) {
        return ResponseEntity.ok(PatientDto.toDto(patientService.createPatient(patient)));
    }

    @PutMapping("{id}")
    public ResponseEntity<PatientDto> updatePatient(@PathVariable long id, @Validated @RequestBody PatientDto patient) {
        try {
            Patient updatedPatient = patientService.updatePatient(id, patient);
            return ResponseEntity.ok(PatientDto.toDto(updatedPatient));
        } catch (NotFoundEntityException e) {
            log.error("Error on update patient", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
