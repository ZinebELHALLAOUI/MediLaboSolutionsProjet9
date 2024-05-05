package io.zineb.service;

import io.zineb.model.Note;
import io.zineb.model.Patient;
import io.zineb.model.Patient.Gender;
import io.zineb.repository.NoteRepository;
import io.zineb.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RiskDiabetesCalculatorService {

    private final NoteRepository noteRepository;
    private final PatientRepository patientRepository;

    public Risk calculateRiskForPatient(long patientId) {
        Optional<Patient> patientOpt = patientRepository.getPatientById(patientId);
        List<Note> notes = noteRepository.findNotesByPatient(patientId);

        if (patientOpt.isEmpty() || notes.isEmpty()) {
            return Risk.None;
        }

        Patient patient = patientOpt.get();
        int triggerCount = countTriggers(notes);
        boolean isOver30 = patient.dateOfBirth().plusYears(30).isBefore(LocalDate.now());

        if (triggerCount < 2) {
            return Risk.None;
        } else if (triggerCount <= 5 && isOver30) {
            return Risk.Borderline;
        } else if (triggerCount >= 3 && triggerCount <= 7 && patient.gender() == Gender.M) {
            return Risk.InDanger;
        } else if (triggerCount >= 4 && triggerCount <= 7 && patient.gender() == Gender.F) {
            return Risk.InDanger;
        } else if (triggerCount >= 6 && isOver30) {
            return Risk.EarlyOnset;
        } else {
            return Risk.InDanger;
        }
    }

    private int countTriggers(List<Note> notes) {
        int count = 0;
        for (Note note : notes) {
            if (note.note().contains("Hémoglobine A1C") || note.note().contains("Microalbumine")
                    || note.note().contains("Taille") || note.note().contains("Poids")
                    || note.note().contains("Fumeur") || note.note().contains("Fumeuse")
                    || note.note().contains("Anormal") || note.note().contains("Cholestérol")
                    || note.note().contains("Vertiges") || note.note().contains("Rechute")
                    || note.note().contains("Réaction") || note.note().contains("Anticorps")) {
                count++;
            }
        }
        return count;
    }
}
