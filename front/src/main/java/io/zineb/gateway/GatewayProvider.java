package io.zineb.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "gateway", url = "http://localhost:8080/")
public interface GatewayProvider {

    @GetMapping("/patients/query")
    Patient getPatient(@RequestParam String firstName, @RequestParam String lastName);

    @PostMapping("/patients")
    Patient createPatient(Patient patient);

    @PutMapping("/patients")
    Patient updatePatient(Patient patient);
}

