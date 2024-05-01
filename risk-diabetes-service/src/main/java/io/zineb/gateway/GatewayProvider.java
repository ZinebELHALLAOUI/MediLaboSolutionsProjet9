package io.zineb.gateway;

import io.zineb.model.Note;
import io.zineb.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "gateway", url = "http://localhost:8080/")
public interface GatewayProvider {

    @GetMapping("patients/{id}")
    Patient getPatient(@PathVariable long id);

    @GetMapping("patients")
    List<Patient> getAllPatients();

    @GetMapping("notes/query")
    List<Note> getNotes(@RequestParam long patId);

}

