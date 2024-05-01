package io.zineb.controller;

import io.zineb.controller.dto.NoteDto;
import io.zineb.controller.dto.PatientDto;
import io.zineb.controller.dto.SearchPatientRequest;
import io.zineb.model.Note;
import io.zineb.model.Patient;
import io.zineb.service.NoteService;
import io.zineb.service.PatientService;
import io.zineb.service.RiskDiabetesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/patients")
@AllArgsConstructor

public class PatientController {

    private final PatientService patientService;
    private final NoteService noteService;
    private final RiskDiabetesService riskDiabetesService;

    @GetMapping
    public String getPatientsPage(Model model) {
        List<Patient> patients = patientService.getAll();
        model.addAttribute("patients", patients.stream().map(PatientDto::toDto).toList());
        model.addAttribute("searchPatientRequest", new SearchPatientRequest(null));
        return "patients_pages";
    }

    @GetMapping("/search")
    public String searchPatient(@RequestParam("query") String query, Model model) {
        List<Patient> patients;
        if (query == null || query.isBlank())
            patients = patientService.getAll();
        else
            patients = patientService.findPatientByFirstnameOrLastname(query);
        model.addAttribute("patients", patients.stream().map(PatientDto::toDto).toList());
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
        Optional<Patient> patient = patientService.getPatientById(id);
        PatientDto patientDto = patient.map(PatientDto::toDto).orElseThrow();
        model.addAttribute("patient", patientDto);
        return "add_edit_patient_page";
    }

    @GetMapping("/{id}/notes")
    public String notesPage(Model model, @PathVariable("id") long id) {
        List<Note> notes = noteService.findNotesByPatient(id);
        List<NoteDto> noteDtos = notes.stream().map(NoteDto::toDto).toList();
        model.addAttribute("notes", noteDtos);
        model.addAttribute("patientId", id);
        return "notes_page";
    }

    @GetMapping("/{patientId}/add-note")
    public String addNotePage(Model model, @PathVariable("patientId") long patientId, Note note) {
        model.addAttribute("patientId", patientId);
        return "add_note_page";
    }

    @PostMapping
    public String createPatient(Patient patient) {
        patientService.createPatient(patient);
        return "redirect:/patients";
    }

    @PostMapping("edit")
    public String updatePatient(Patient patient) {
        patientService.updatePatient(patient);
        return "redirect:/patients";
    }

    @PostMapping("/{patientId}/notes")
    public String addNote(@PathVariable("patientId") long patientId, @ModelAttribute Note note) {
        final Patient patient = patientService.getPatientById(patientId).get();
        Note noteToAdd = new Note(patientId, patient.lastName(), note.note());
        noteService.addNote(noteToAdd);
        return "redirect:/patients/" + patientId + "/notes";
    }

}
