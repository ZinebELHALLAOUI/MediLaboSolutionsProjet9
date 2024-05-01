package io.zineb.service.impl;

import io.zineb.service.PatientService;
import io.zineb.service.exception.DuplicatedEntityException;
import io.zineb.service.exception.NotFoundEntityException;
import io.zineb.model.Patient;
import io.zineb.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    private PatientService patientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        patientService = new PatientServiceImpl(patientRepository);
    }

    @Test
    public void findPatient_WhenPatientExists_ReturnsOptionalOfPatient() {
        // Given
        Patient patient = new Patient();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        when(patientRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.of(patient));

        // When
        Optional<Patient> result = patientService.findPatient("John", "Doe");

        // Then
        assertThat(result).isPresent().contains(patient);
    }

    @Test
    public void findPatient_WhenPatientDoesNotExist_ReturnsEmptyOptional() {
        // Given
        when(patientRepository.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.empty());

        // When
        Optional<Patient> result = patientService.findPatient("Jane", "Smith");

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    public void savePatient_WhenPatientDoesNotExist_ReturnsSavedPatient() {
        // Given
        Patient patient = new Patient();
        patient.setFirstName("Jane");
        patient.setLastName("Smith");
        when(patientRepository.findByFirstNameAndLastName("Jane", "Smith")).thenReturn(Optional.empty());
        when(patientRepository.save(patient)).thenReturn(patient);

        // When
        Patient result = patientService.createPatient(patient);

        // Then
        assertThat(result).isEqualTo(patient);
        verify(patientRepository, times(1)).findByFirstNameAndLastName("Jane", "Smith");
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    public void savePatient_WhenPatientExists_ThrowsDuplicatedEntityException() {
        // Given
        Patient patient = new Patient();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        when(patientRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.of(patient));

        // Then
        assertThatExceptionOfType(DuplicatedEntityException.class)
                .isThrownBy(() -> patientService.createPatient(patient));
    }

    @Test
    public void updatePatient_WhenPatientExists_ReturnsUpdatedPatient() {
        // Given
        Patient existingPatient = new Patient();
        existingPatient.setFirstName("John");
        existingPatient.setLastName("Doe");

        Patient updatedPatient = new Patient();
        updatedPatient.setFirstName("John");
        updatedPatient.setLastName("Doe");
        updatedPatient.setAddress("Updated Address");

        when(patientRepository.findByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.of(existingPatient));
        when(patientRepository.save(updatedPatient)).thenReturn(updatedPatient);

        // When
        Patient result = patientService.updatePatient(updatedPatient);

        // Then
        assertThat(result).isEqualTo(updatedPatient);
        verify(patientRepository, times(1)).findByFirstNameAndLastName("John", "Doe");
        verify(patientRepository, times(1)).save(updatedPatient);
    }

    @Test
    public void updatePatient_WhenPatientDoesNotExist_ThrowsNotFoundEntityException() {
        // Given
        Patient patient = new Patient();
        patient.setFirstName("Jane");
        patient.setLastName("Smith");
        when(patientRepository.findByFirstNameAndLastName("Jane", "Smith")).thenReturn(Optional.empty());

        // Then
        assertThatExceptionOfType(NotFoundEntityException.class)
                .isThrownBy(() -> patientService.updatePatient(patient));
    }
}
