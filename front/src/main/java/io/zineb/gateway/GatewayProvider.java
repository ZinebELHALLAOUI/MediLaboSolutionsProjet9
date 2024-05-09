package io.zineb.gateway;

import io.zineb.model.Note;
import io.zineb.model.Patient;
import io.zineb.model.Risk;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "gateway", url = "${gateway.url}")
public interface GatewayProvider {

    @GetMapping("patients/search")
    List<Patient> patientsSearch(@RequestParam String query);

    @GetMapping("patients/{id}")
    Patient getPatient(@PathVariable long id);

    @GetMapping("patients")
    List<Patient> getAllPatients();

    @PostMapping("patients")
    Patient createPatient(Patient patient);

    @PutMapping("patients/{id}")
    Patient updatePatient(@PathVariable long id, Patient patient);

    @GetMapping("notes/query")
    List<Note> getNotes(@RequestParam long patId);

    @PostMapping("notes")
    Note addNote(Note note);

    @GetMapping("risks")
    Risk getRiskDiabetesForPatient(@RequestParam long patId);
}

