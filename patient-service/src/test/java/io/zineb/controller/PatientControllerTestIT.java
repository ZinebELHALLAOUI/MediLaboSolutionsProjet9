package io.zineb.controller;

import io.zineb.model.Patient;
import io.zineb.service.PatientService;
import io.zineb.service.exception.DuplicatedEntityException;
import io.zineb.service.exception.NotFoundEntityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getPatient_WhenPatientExists_ReturnsOk() throws Exception {
        // Given
        Patient patient = new Patient();
        patient.setFirstName("John");
        patient.setLastName("Doe");
        when(patientService.findPatient("John", "Doe")).thenReturn(Optional.of(patient));

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.get("/query")
                        .param("firstName", "John")
                        .param("lastName", "Doe"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"));
    }

    @Test
    public void getPatient_WhenPatientDoesNotExist_ReturnsNotFound() throws Exception {
        // Given
        when(patientService.findPatient("Jane", "Smith")).thenReturn(Optional.empty());

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.get("/query")
                        .param("firstName", "Jane")
                        .param("lastName", "Smith"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void createPatient_WhenValidPatient_ReturnsCreated() throws Exception {
        // Given
        Patient patient = new Patient();
        patient.setFirstName("Jane");
        patient.setLastName("Smith");
        patient.setDateOfBirth(LocalDate.of(1980, 1, 1));

        when(patientService.savePatient(any())).thenReturn(patient);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Jane\",\"lastName\":\"Smith\",\"dateOfBirth\":\"1980-01-01\",\"gender\":\"F\",\"address\":\"123 Street\",\"phoneNumber\":\"1234567890\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Jane"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Smith"));
    }

    @Test
    public void createPatient_WhenDuplicatePatient_ReturnsBadRequest() throws Exception {
        // Given
        when(patientService.savePatient(any())).thenThrow(new DuplicatedEntityException("Duplicate patient"));

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Jane\",\"lastName\":\"Smith\",\"dateOfBirth\":\"1980-01-01\",\"gender\":\"F\",\"address\":\"123 Street\",\"phoneNumber\":\"1234567890\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void updatePatient_WhenValidPatient_ReturnsOk() throws Exception {
        // Given
        Patient patient = new Patient();
        patient.setId(1);
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setDateOfBirth(LocalDate.of(1980, 1, 1));

        when(patientService.updatePatient(any())).thenReturn(patient);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.put("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"dateOfBirth\":\"1980-01-01\",\"gender\":\"M\",\"address\":\"123 Street\",\"phoneNumber\":\"1234567890\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("M"));
    }

    @Test
    public void updatePatient_WhenNotFound_ReturnsNotFound() throws Exception {
        // Given
        when(patientService.updatePatient(any())).thenThrow(new NotFoundEntityException("Patient not found"));

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.put("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"dateOfBirth\":\"1980-01-01\",\"gender\":\"M\",\"address\":\"123 Street\",\"phoneNumber\":\"1234567890\"}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
