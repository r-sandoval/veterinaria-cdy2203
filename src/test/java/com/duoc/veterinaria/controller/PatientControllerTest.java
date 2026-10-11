package com.duoc.veterinaria.controller;

import com.duoc.veterinaria.model.Owner;
import com.duoc.veterinaria.model.Patient;
import com.duoc.veterinaria.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
@AutoConfigureMockMvc(addFilters = false)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PatientService patientService;

    @Test
    @WithMockUser
    void testGetAllPatients() throws Exception {
        Owner owner = new Owner();
        owner.setId(1L);
        owner.setName("Juan Pérez");

        Patient patient1 = new Patient();
        patient1.setId(1L);
        patient1.setName("Firulais");
        patient1.setSpecies("Perro");
        patient1.setBreed("Labrador");
        patient1.setAge(5);
        patient1.setOwner(owner);

        Patient patient2 = new Patient();
        patient2.setId(2L);
        patient2.setName("Michi");
        patient2.setSpecies("Gato");
        patient2.setBreed("Siames");
        patient2.setAge(3);
        patient2.setOwner(owner);

        when(patientService.getAllPatients()).thenReturn(Arrays.asList(patient1, patient2));

        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Firulais"))
                .andExpect(jsonPath("$[1].name").value("Michi"));
    }

    @Test
    @WithMockUser
    void testGetPatientById() throws Exception {
        Owner owner = new Owner();
        owner.setId(1L);
        owner.setName("Juan Pérez");

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Firulais");
        patient.setSpecies("Perro");
        patient.setBreed("Labrador");
        patient.setAge(5);
        patient.setOwner(owner);

        when(patientService.getPatientById(1L)).thenReturn(Optional.of(patient));

        mockMvc.perform(get("/api/patients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Firulais"))
                .andExpect(jsonPath("$.species").value("Perro"));
    }

    @Test
    @WithMockUser
    void testGetPatientByIdNotFound() throws Exception {
        when(patientService.getPatientById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/patients/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testGetPatientsByOwner() throws Exception {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Firulais");

        when(patientService.getPatientsByOwner(1L)).thenReturn(Arrays.asList(patient));

        mockMvc.perform(get("/api/patients/owner/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Firulais"));
    }

    @Test
    @WithMockUser
    void testCreatePatient() throws Exception {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Firulais");
        patient.setSpecies("Perro");
        patient.setBreed("Labrador");
        patient.setAge(5);

        when(patientService.savePatient(any(Patient.class))).thenReturn(patient);

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Firulais\",\"species\":\"Perro\",\"breed\":\"Labrador\",\"age\":5}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Firulais"));
    }

    @Test
    @WithMockUser
    void testUpdatePatient() throws Exception {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Firulais");
        patient.setSpecies("Perro");

        when(patientService.getPatientById(1L)).thenReturn(Optional.of(patient));
        when(patientService.savePatient(any(Patient.class))).thenReturn(patient);

        mockMvc.perform(put("/api/patients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Firulais\",\"species\":\"Perro\",\"breed\":\"Labrador\",\"age\":6}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testUpdatePatientNotFound() throws Exception {
        when(patientService.getPatientById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/patients/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test\",\"species\":\"Test\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testDeletePatient() throws Exception {
        mockMvc.perform(delete("/api/patients/1"))
                .andExpect(status().isNoContent());
    }
}
