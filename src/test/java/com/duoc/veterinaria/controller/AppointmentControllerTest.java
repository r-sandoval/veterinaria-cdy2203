package com.duoc.veterinaria.controller;

import com.duoc.veterinaria.model.Appointment;
import com.duoc.veterinaria.model.Patient;
import com.duoc.veterinaria.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppointmentController.class)
@AutoConfigureMockMvc(addFilters = false)
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AppointmentService appointmentService;

    @Test
    @WithMockUser
    void testGetAllAppointments() throws Exception {
        Appointment a1 = new Appointment();
        a1.setId(1L);
        a1.setReason("Vacunación");
        a1.setVeterinarian("Dr. Smith");
        a1.setDateTime(LocalDateTime.now());

        Appointment a2 = new Appointment();
        a2.setId(2L);
        a2.setReason("Control");
        a2.setVeterinarian("Dra. Johnson");
        a2.setDateTime(LocalDateTime.now());

        when(appointmentService.getAllAppointments()).thenReturn(Arrays.asList(a1, a2));

        mockMvc.perform(get("/api/appointments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].reason").value("Vacunación"))
                .andExpect(jsonPath("$[1].reason").value("Control"));
    }

    @Test
    @WithMockUser
    void testGetAppointmentById() throws Exception {
        Appointment a = new Appointment();
        a.setId(1L);
        a.setReason("Vacunación");
        a.setVeterinarian("Dr. Smith");

        when(appointmentService.getAppointmentById(1L)).thenReturn(Optional.of(a));

        mockMvc.perform(get("/api/appointments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reason").value("Vacunación"))
                .andExpect(jsonPath("$.veterinarian").value("Dr. Smith"));
    }

    @Test
    @WithMockUser
    void testGetAppointmentByIdNotFound() throws Exception {
        when(appointmentService.getAppointmentById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/appointments/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testGetAppointmentsByPatient() throws Exception {
        Appointment a = new Appointment();
        a.setId(1L);
        a.setReason("Vacunación");

        when(appointmentService.getAppointmentsByPatient(1L)).thenReturn(Arrays.asList(a));

        mockMvc.perform(get("/api/appointments/patient/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    @WithMockUser
    void testCreateAppointment() throws Exception {
        Appointment a = new Appointment();
        a.setId(1L);
        a.setReason("Vacunación");
        a.setVeterinarian("Dr. Smith");

        when(appointmentService.saveAppointment(any(Appointment.class))).thenReturn(a);

        mockMvc.perform(post("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reason\":\"Vacunación\",\"veterinarian\":\"Dr. Smith\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reason").value("Vacunación"));
    }

    @Test
    @WithMockUser
    void testUpdateAppointment() throws Exception {
        Appointment a = new Appointment();
        a.setId(1L);
        a.setReason("Vacunación");
        a.setVeterinarian("Dr. Smith");

        when(appointmentService.getAppointmentById(1L)).thenReturn(Optional.of(a));
        when(appointmentService.saveAppointment(any(Appointment.class))).thenReturn(a);

        mockMvc.perform(put("/api/appointments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reason\":\"Vacunación actualizada\",\"veterinarian\":\"Dr. Smith\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testUpdateAppointmentNotFound() throws Exception {
        when(appointmentService.getAppointmentById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/appointments/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reason\":\"Test\",\"veterinarian\":\"Test\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testDeleteAppointment() throws Exception {
        mockMvc.perform(delete("/api/appointments/1"))
                .andExpect(status().isNoContent());
    }
}
