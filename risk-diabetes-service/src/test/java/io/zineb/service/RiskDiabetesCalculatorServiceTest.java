package io.zineb.service;

import io.zineb.model.Note;
import io.zineb.model.Patient;
import io.zineb.repository.NoteRepository;
import io.zineb.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class RiskDiabetesCalculatorServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private RiskDiabetesCalculatorService riskDiabetesCalculatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCalculateRiskForPatient_NoPatient_NoNotes() {
        long patientId = 1L;
        when(patientRepository.getPatientById(patientId)).thenReturn(Optional.empty());
        when(noteRepository.findNotesByPatient(patientId)).thenReturn(new ArrayList<>());
        Risk risk = riskDiabetesCalculatorService.calculateRiskForPatient(patientId);
        assertEquals(Risk.None, risk);
    }

    @Test
    void testCalculateRiskForPatient_TriggerCountLessThan2() {
        long patientId = 1L;
        Patient patient = new Patient(patientId, "John", "Doe", LocalDate.of(1980, 1, 1), Patient.Gender.M, "Address", "123456789");
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(patientId, "John Doe", "Note without relevant trigger"));
        when(patientRepository.getPatientById(patientId)).thenReturn(Optional.of(patient));
        when(noteRepository.findNotesByPatient(patientId)).thenReturn(notes);
        Risk risk = riskDiabetesCalculatorService.calculateRiskForPatient(patientId);
        assertEquals(Risk.None, risk);
    }

    @Test
    void testCalculateRiskForPatient_TriggerCountBetween2And5_Over30() {
        long patientId = 1L;
        Patient patient = new Patient(patientId, "Jane", "Smith", LocalDate.of(1975, 1, 1), Patient.Gender.F, "Address", "987654321");
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(patientId, "Jane Smith", "Contains Hémoglobine A1C"));
        notes.add(new Note(patientId, "Jane Smith", "Contains Microalbumine"));
        when(patientRepository.getPatientById(patientId)).thenReturn(Optional.of(patient));
        when(noteRepository.findNotesByPatient(patientId)).thenReturn(notes);
        Risk risk = riskDiabetesCalculatorService.calculateRiskForPatient(patientId);
        assertEquals(Risk.Borderline, risk);
    }


    @Test
    void testCalculateRiskForPatient_InDanger_MaleUnder30() {
        long patientId = 1L;
        Patient patient = new Patient(patientId, "John", "Doe", LocalDate.of(1995, 1, 1), Patient.Gender.M, "Address", "123456789");
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(patientId, "John Doe", "Contains Hémoglobine A1C"));
        notes.add(new Note(patientId, "John Doe", "Contains Microalbumine"));
        notes.add(new Note(patientId, "John Doe", "Contains Taille"));
        when(patientRepository.getPatientById(patientId)).thenReturn(Optional.of(patient));
        when(noteRepository.findNotesByPatient(patientId)).thenReturn(notes);
        Risk risk = riskDiabetesCalculatorService.calculateRiskForPatient(patientId);
        assertEquals(Risk.InDanger, risk);
    }

    @Test
    void testCalculateRiskForPatient_InDanger_FemaleUnder30() {
        long patientId = 1L;
        Patient patient = new Patient(patientId, "Alice", "Johnson", LocalDate.of(1995, 1, 1), Patient.Gender.F, "Address", "456123789");
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(patientId, "Alice Johnson", "Contains Hémoglobine A1C"));
        notes.add(new Note(patientId, "Alice Johnson", "Contains Microalbumine"));
        notes.add(new Note(patientId, "Alice Johnson", "Contains Taille"));
        notes.add(new Note(patientId, "Alice Johnson", "Contains Poids"));
        when(patientRepository.getPatientById(patientId)).thenReturn(Optional.of(patient));
        when(noteRepository.findNotesByPatient(patientId)).thenReturn(notes);
        Risk risk = riskDiabetesCalculatorService.calculateRiskForPatient(patientId);
        assertEquals(Risk.InDanger, risk);
    }


    @Test
    void testCalculateRiskForPatient_InDanger_MaleOver30() {
        long patientId = 1L;
        Patient patient = new Patient(patientId, "John", "Doe", LocalDate.of(1980, 1, 1), Patient.Gender.M, "Address", "123456789");
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(patientId, "John Doe", "Contains Hémoglobine A1C"));
        notes.add(new Note(patientId, "John Doe", "Contains Microalbumine"));
        notes.add(new Note(patientId, "John Doe", "Contains Taille"));
        notes.add(new Note(patientId, "John Doe", "Contains Poids"));
        notes.add(new Note(patientId, "John Doe", "Contains Fumeur"));
        notes.add(new Note(patientId, "John Doe", "Contains Anormal"));
        notes.add(new Note(patientId, "John Doe", "Contains Cholestérol"));
        when(patientRepository.getPatientById(patientId)).thenReturn(Optional.of(patient));
        when(noteRepository.findNotesByPatient(patientId)).thenReturn(notes);
        Risk risk = riskDiabetesCalculatorService.calculateRiskForPatient(patientId);
        assertEquals(Risk.InDanger, risk);
    }

    @Test
    void testCalculateRiskForPatient_EarlyOnset_MaleOver30() {
        long patientId = 1L;
        Patient patient = new Patient(patientId, "John", "Doe", LocalDate.of(1980, 1, 1), Patient.Gender.M, "Address", "123456789");
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(patientId, "John Doe", "Contains Hémoglobine A1C"));
        notes.add(new Note(patientId, "John Doe", "Contains Microalbumine"));
        notes.add(new Note(patientId, "John Doe", "Contains Taille"));
        notes.add(new Note(patientId, "John Doe", "Contains Poids"));
        notes.add(new Note(patientId, "John Doe", "Contains Fumeur"));
        notes.add(new Note(patientId, "John Doe", "Contains Anormal"));
        notes.add(new Note(patientId, "John Doe", "Contains Cholestérol"));
        notes.add(new Note(patientId, "John Doe", "Contains Vertiges"));
        when(patientRepository.getPatientById(patientId)).thenReturn(Optional.of(patient));
        when(noteRepository.findNotesByPatient(patientId)).thenReturn(notes);
        Risk risk = riskDiabetesCalculatorService.calculateRiskForPatient(patientId);
        assertEquals(Risk.EarlyOnset, risk);
    }

    @Test
    void testCalculateRiskForPatient_EarlyOnset_FemaleOver30() {
        long patientId = 1L;
        Patient patient = new Patient(patientId, "Alice", "Johnson", LocalDate.of(1980, 1, 1), Patient.Gender.F, "Address", "456123789");
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(patientId, "Alice Johnson", "Contains Hémoglobine A1C"));
        notes.add(new Note(patientId, "Alice Johnson", "Contains Microalbumine"));
        notes.add(new Note(patientId, "Alice Johnson", "Contains Taille"));
        notes.add(new Note(patientId, "Alice Johnson", "Contains Poids"));
        notes.add(new Note(patientId, "Alice Johnson", "Contains Fumeur"));
        notes.add(new Note(patientId, "Alice Johnson", "Contains Anormal"));
        notes.add(new Note(patientId, "Alice Johnson", "Contains Cholestérol"));
        notes.add(new Note(patientId, "Alice Johnson", "Contains Vertiges"));
        notes.add(new Note(patientId, "Alice Johnson", "Contains Réaction"));
        when(patientRepository.getPatientById(patientId)).thenReturn(Optional.of(patient));
        when(noteRepository.findNotesByPatient(patientId)).thenReturn(notes);
        Risk risk = riskDiabetesCalculatorService.calculateRiskForPatient(patientId);
        assertEquals(Risk.EarlyOnset, risk);
    }


}

