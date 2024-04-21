package io.zineb.controller;

import io.zineb.model.Patient;
import io.zineb.service.PatientService;
import io.zineb.service.exception.DuplicatedEntityException;
import io.zineb.service.exception.NotFoundEntityException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/patients")
@AllArgsConstructor
@Slf4j
public class PatientController {

    private final PatientService patientService;

    @GetMapping("query")
    public ResponseEntity<Patient> getPatient(@RequestParam String firstName, @RequestParam String lastName) {
        Optional<Patient> patient = patientService.findPatient(firstName, lastName);
        return patient.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@Validated @RequestBody Patient patient) {
        try {
            return ResponseEntity.ok(patientService.savePatient(patient));
        }catch (DuplicatedEntityException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<Patient> updatePatient(@Validated @RequestBody Patient patient) {
        try {
            Patient updatedPatient = patientService.updatePatient(patient);
            return ResponseEntity.ok(updatedPatient);
        } catch (NotFoundEntityException e) {
            log.error("Error on update patient", e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
