package io.zineb.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "gateway", url = "http://localhost:8080/")
public interface GatewayProvider {

    @GetMapping("patients/search")
    Patient patientSearch(@RequestParam String query);

    @GetMapping("patients/{id}")
    Patient getPatient(@PathVariable long id);

    @GetMapping("patients")
    Patient getAllPatient();

    @PostMapping("patients")
    Patient createPatient(Patient patient);

    @PutMapping("patients")
    Patient updatePatient(Patient patient);

    @GetMapping("notes/query")
    List<Note> getNotes(@RequestParam long patId);

    @PostMapping("notes")
    Note addNote(Note note);
}

