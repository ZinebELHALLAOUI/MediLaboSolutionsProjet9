package io.zineb.provider;

import io.zineb.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "patient-service", url = "${patient-service.url}")
public interface PatientProvider {

    @GetMapping("patients/{id}")
    Patient getPatient(@PathVariable long id);

}
