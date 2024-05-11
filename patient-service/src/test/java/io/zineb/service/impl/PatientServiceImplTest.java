package io.zineb.service.impl;

import io.zineb.controller.PatientDto;
import io.zineb.model.Gender;
import io.zineb.model.Patient;
import io.zineb.repository.GenderRepository;
import io.zineb.repository.PatientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private GenderRepository genderRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    @Test
    void testFindPatientByFirstnameOrLastname() {
        // Given
        String query = "John";
        Patient patient1 = new Patient(1L, "John", "Doe", LocalDate.of(1980, 1, 1), null, "Address 1", "123456789");
        Patient patient2 = new Patient(2L, "Jane", "Doe", LocalDate.of(1985, 5, 5), null, "Address 2", "987654321");
        when(patientRepository.findAllByFirstNameOrLastName(query, query)).thenReturn(Arrays.asList(patient1, patient2));

        // When
        List<Patient> patients = patientService.findPatientByFirstnameOrLastname(query);

        // Then
        assertEquals(2, patients.size());
        assertEquals("John", patients.get(0).getFirstName());
        assertEquals("Doe", patients.get(0).getLastName());
        assertEquals("Jane", patients.get(1).getFirstName());
        assertEquals("Doe", patients.get(1).getLastName());
    }

    @Test
    void testGetAll() {
        // Given
        Patient patient1 = new Patient(1L, "John", "Doe", LocalDate.of(1980, 1, 1), null, "Address 1", "123456789");
        Patient patient2 = new Patient(2L, "Jane", "Doe", LocalDate.of(1985, 5, 5), null, "Address 2", "987654321");
        when(patientRepository.findAll()).thenReturn(Arrays.asList(patient1, patient2));

        // When
        List<Patient> patients = patientService.getAll();

        // Then
        assertEquals(2, patients.size());
        assertEquals("John", patients.get(0).getFirstName());
        assertEquals("Doe", patients.get(0).getLastName());
        assertEquals("Jane", patients.get(1).getFirstName());
        assertEquals("Doe", patients.get(1).getLastName());
    }

    @Test
    void testFindPatientById() {
        // Given
        long id = 1L;
        Patient patient = new Patient(1L, "John", "Doe", LocalDate.of(1980, 1, 1), null, "Address 1", "123456789");
        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));

        // When
        Optional<Patient> optionalPatient = patientService.findPatientById(id);

        // Then
        assertTrue(optionalPatient.isPresent());
        assertEquals("John", optionalPatient.get().getFirstName());
        assertEquals("Doe", optionalPatient.get().getLastName());
    }

    @Test
    void testCreatePatient() {
        // Given
        PatientDto patientDto = new PatientDto(null, "John", "Doe", LocalDate.of(1980, 1, 1), PatientDto.Gender.M, "Address 1", "123456789");
        when(genderRepository.findByName("M")).thenReturn(Optional.of(new Gender(1,"M")));
        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Patient createdPatient = patientService.createPatient(patientDto);

        // Then
        assertEquals("John", createdPatient.getFirstName());
        assertEquals("Doe", createdPatient.getLastName());
        assertEquals(LocalDate.of(1980, 1, 1), createdPatient.getDateOfBirth());
        assertEquals("Address 1", createdPatient.getAddress());
        assertEquals("123456789", createdPatient.getPhoneNumber());
        assertEquals("M", createdPatient.getGender().getName());
    }

    @Test
    void testUpdatePatient() {
        // Given
        long id = 1L;
        PatientDto patientDto = new PatientDto(null, "John", "Doe", LocalDate.of(1980, 1, 1), PatientDto.Gender.M, "Address 1", "123456789");
        when(patientRepository.findById(id)).thenReturn(Optional.of(new Patient()));
        when(genderRepository.findByName("M")).thenReturn(Optional.of(new Gender(1,"M")));
        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Patient updatedPatient = patientService.updatePatient(id, patientDto);

        // Then
        assertEquals(id, updatedPatient.getId());
        assertEquals("John", updatedPatient.getFirstName());
        assertEquals("Doe", updatedPatient.getLastName());
        assertEquals(LocalDate.of(1980, 1, 1), updatedPatient.getDateOfBirth());
        assertEquals("Address 1", updatedPatient.getAddress());
        assertEquals("123456789", updatedPatient.getPhoneNumber());
        assertEquals("M", updatedPatient.getGender().getName());
    }
}
