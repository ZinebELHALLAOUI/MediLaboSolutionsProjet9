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
    public ResponseEntity<List<Patient>> findPatientsByFirstnameOrLastname(@RequestParam String query) {
        List<Patient> patients = patientService.findPatientByFirstnameOrLastname(query);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable long id) {
        Optional<Patient> patient = patientService.findPatientById(id);
        return patient.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAll());
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@Validated @RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.createPatient(patient));
    }

    @PutMapping("{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable long id, @Validated @RequestBody Patient patient) {
        try {
            Patient updatedPatient = patientService.updatePatient(id, patient);
            return ResponseEntity.ok(updatedPatient);
        } catch (NotFoundEntityException e) {
            log.error("Error on update patient", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
