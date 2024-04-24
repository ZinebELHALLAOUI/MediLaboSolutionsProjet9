package io.zineb.controller;

import io.zineb.controller.request.SearchPatientRequest;
import io.zineb.gateway.GatewayProvider;
import io.zineb.gateway.Patient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patients")
@AllArgsConstructor

public class PatientController {

    private final GatewayProvider gatewayProvider;

    @GetMapping
    public String getPatientsPage(Model model) {
        model.addAttribute("searchPatientRequest", new SearchPatientRequest(null, null));
        return "patients_pages";
    }

    @GetMapping("/search")
    public String searchPatient(@RequestParam("firstName") String firstName,
                                @RequestParam("lastName") String lastName, Model model) {
        Patient patient = gatewayProvider.getPatient(firstName, lastName);
        model.addAttribute("patient", patient);
        model.addAttribute("searchPatientRequest", new SearchPatientRequest(firstName, lastName));
        return "patients_pages";
    }

    @GetMapping("/add-form")
    public String addForm(Patient patient, Model model) {
        model.addAttribute("operation", "add");
        return "add_edit_patient_page";
    }

    @GetMapping("/edit-form/{firstName}/{lastName}")
    public String editForm(Model model, @PathVariable("firstName") String firstName,
                           @PathVariable("lastName") String lastName) {
        model.addAttribute("operation", "edit");
        Patient patient = gatewayProvider.getPatient(firstName, lastName);
        model.addAttribute("patient", patient);
        return "add_edit_patient_page";
    }

    @PostMapping
    public String savePatient(Patient patient) {
        gatewayProvider.createPatient(patient);
        return "redirect:/patients";
    }

    @PostMapping("edit")
    public String updatePatient(Patient patient) {
        gatewayProvider.updatePatient(patient);
        return "redirect:/patients";
    }

}
