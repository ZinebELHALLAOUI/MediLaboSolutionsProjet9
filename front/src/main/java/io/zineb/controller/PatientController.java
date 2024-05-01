package io.zineb.controller;

import io.zineb.controller.request.SearchPatientRequest;
import io.zineb.gateway.GatewayProvider;
import io.zineb.gateway.Note;
import io.zineb.gateway.Patient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Controller
@RequestMapping("/patients")
@AllArgsConstructor

public class PatientController {

    private final GatewayProvider gatewayProvider;

    @GetMapping
    public String getPatientsPage(Model model) {
        model.addAttribute("searchPatientRequest", new SearchPatientRequest(null));
        return "patients_pages";
    }

    @GetMapping("/search")
    public String searchPatient(@RequestParam("query") String query, Model model) {
        Patient patient = gatewayProvider.patientSearch(query);
        model.addAttribute("patient", patient);
        model.addAttribute("searchPatientRequest", new SearchPatientRequest(query));
        return "patients_pages";
    }

    @GetMapping("/add-form")
    public String addForm(Patient patient, Model model) {
        model.addAttribute("operation", "add");
        return "add_edit_patient_page";
    }

    @GetMapping("/edit-form/{id}")
    public String editForm(Model model, @PathVariable("id") long id) {
        model.addAttribute("operation", "edit");
        Patient patient = gatewayProvider.getPatient(id);
        model.addAttribute("patient", patient);
        return "add_edit_patient_page";
    }

    @GetMapping("/{id}/notes")
    public String notesPage(Model model, @PathVariable("id") long id) {
        List<Note> notes = gatewayProvider.getNotes(id);
        List<Note> rawNotes = notes.stream().map(note -> new Note(
                note.patId(),
                note.patient(),
                HtmlUtils.htmlEscape(note.note()).replaceAll("\r\n", "<br>")
        )).toList();
        model.addAttribute("notes", rawNotes);
        model.addAttribute("patientId", id);
        return "notes_page";
    }

    @GetMapping("/{patientId}/add-note")
    public String addNotePage(Model model, @PathVariable("patientId") long patientId, Note note) {
        List<Note> notes = gatewayProvider.getNotes(patientId);
        model.addAttribute("notes", notes);
        model.addAttribute("patientId", patientId);
        return "add_note_page";
    }

    @PostMapping
    public String createPatient(Patient patient) {
        gatewayProvider.createPatient(patient);
        return "redirect:/patients";
    }

    @PostMapping("edit")
    public String updatePatient(Patient patient) {
        gatewayProvider.updatePatient(patient);
        return "redirect:/patients";
    }

    @PostMapping("/{patientId}/notes")
    public String addNote(@PathVariable("patientId") long patientId, @ModelAttribute Note note) {
        final Patient patient = gatewayProvider.getPatient(patientId);
        Note noteToAdd = new Note(patientId, patient.lastName(), note.note());
        gatewayProvider.addNote(noteToAdd);
        return "redirect:/patients/" + patientId + "/notes";
    }

}
