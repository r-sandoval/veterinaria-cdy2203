package com.duoc.veterinaria.controller;

import com.duoc.veterinaria.model.MedicalRecord;
import com.duoc.veterinaria.service.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedicalRecordController.class)
@AutoConfigureMockMvc(addFilters = false)
class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MedicalRecordService medicalRecordService;

    @Test
    @WithMockUser
    void testGetAllMedicalRecords() throws Exception {
        MedicalRecord r1 = new MedicalRecord();
        r1.setId(1L);
        r1.setDiagnosis("Sano");
        r1.setTreatment("Vacuna");
        r1.setDate(LocalDate.now());

        when(medicalRecordService.getAllMedicalRecords()).thenReturn(Arrays.asList(r1));

        mockMvc.perform(get("/api/medical-records"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].diagnosis").value("Sano"));
    }

    @Test
    @WithMockUser
    void testGetMedicalRecordById() throws Exception {
        MedicalRecord r = new MedicalRecord();
        r.setId(1L);
        r.setDiagnosis("Sano");
        r.setTreatment("Vacuna");

        when(medicalRecordService.getMedicalRecordById(1L)).thenReturn(Optional.of(r));

        mockMvc.perform(get("/api/medical-records/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.diagnosis").value("Sano"));
    }

    @Test
    @WithMockUser
    void testGetMedicalRecordByIdNotFound() throws Exception {
        when(medicalRecordService.getMedicalRecordById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/medical-records/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testGetMedicalRecordByAppointment() throws Exception {
        MedicalRecord r = new MedicalRecord();
        r.setId(1L);
        r.setDiagnosis("Sano");

        when(medicalRecordService.getMedicalRecordByAppointment(1L)).thenReturn(Optional.of(r));

        mockMvc.perform(get("/api/medical-records/appointment/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testCreateMedicalRecord() throws Exception {
        MedicalRecord r = new MedicalRecord();
        r.setId(1L);
        r.setDiagnosis("Sano");
        r.setTreatment("Vacuna");

        when(medicalRecordService.saveMedicalRecord(any(MedicalRecord.class))).thenReturn(r);

        mockMvc.perform(post("/api/medical-records")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"diagnosis\":\"Sano\",\"treatment\":\"Vacuna\",\"date\":\"2026-10-11\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testUpdateMedicalRecord() throws Exception {
        MedicalRecord r = new MedicalRecord();
        r.setId(1L);
        r.setDiagnosis("Sano");

        when(medicalRecordService.getMedicalRecordById(1L)).thenReturn(Optional.of(r));
        when(medicalRecordService.saveMedicalRecord(any(MedicalRecord.class))).thenReturn(r);

        mockMvc.perform(put("/api/medical-records/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"diagnosis\":\"Sano actualizado\",\"treatment\":\"Vacuna\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testUpdateMedicalRecordNotFound() throws Exception {
        when(medicalRecordService.getMedicalRecordById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/medical-records/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"diagnosis\":\"Test\",\"treatment\":\"Test\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testDeleteMedicalRecord() throws Exception {
        mockMvc.perform(delete("/api/medical-records/1"))
                .andExpect(status().isNoContent());
    }
}
