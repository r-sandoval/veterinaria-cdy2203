package com.duoc.veterinaria.service;

import com.duoc.veterinaria.model.Appointment;
import com.duoc.veterinaria.repository.AppointmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @Test
    void testGetAllAppointments() {
        Appointment a1 = new Appointment();
        a1.setId(1L);
        a1.setReason("Vacunación");

        Appointment a2 = new Appointment();
        a2.setId(2L);
        a2.setReason("Control");

        when(appointmentRepository.findAll()).thenReturn(Arrays.asList(a1, a2));

        List<Appointment> result = appointmentService.getAllAppointments();

        assertEquals(2, result.size());
        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    void testGetAppointmentById() {
        Appointment a = new Appointment();
        a.setId(1L);
        a.setReason("Vacunación");

        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(a));

        Optional<Appointment> result = appointmentService.getAppointmentById(1L);

        assertTrue(result.isPresent());
        assertEquals("Vacunación", result.get().getReason());
        verify(appointmentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAppointmentsByPatient() {
        Appointment a = new Appointment();
        a.setId(1L);

        when(appointmentRepository.findByPatientId(1L)).thenReturn(Arrays.asList(a));

        List<Appointment> result = appointmentService.getAppointmentsByPatient(1L);

        assertEquals(1, result.size());
        verify(appointmentRepository, times(1)).findByPatientId(1L);
    }

    @Test
    void testSaveAppointment() {
        Appointment a = new Appointment();
        a.setReason("Vacunación");

        when(appointmentRepository.save(a)).thenReturn(a);

        Appointment result = appointmentService.saveAppointment(a);

        assertNotNull(result);
        assertEquals("Vacunación", result.getReason());
        verify(appointmentRepository, times(1)).save(a);
    }

    @Test
    void testDeleteAppointment() {
        appointmentService.deleteAppointment(1L);
        verify(appointmentRepository, times(1)).deleteById(1L);
    }
}