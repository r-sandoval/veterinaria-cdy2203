package com.duoc.veterinaria.service;

import com.duoc.veterinaria.model.Patient;
import com.duoc.veterinaria.repository.PatientRepository;
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
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @Test
    void testGetAllPatients() {
        Patient p1 = new Patient();
        p1.setId(1L);
        p1.setName("Firulais");

        Patient p2 = new Patient();
        p2.setId(2L);
        p2.setName("Michi");

        when(patientRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Patient> result = patientService.getAllPatients();

        assertEquals(2, result.size());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void testGetPatientById() {
        Patient p = new Patient();
        p.setId(1L);
        p.setName("Firulais");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(p));

        Optional<Patient> result = patientService.getPatientById(1L);

        assertTrue(result.isPresent());
        assertEquals("Firulais", result.get().getName());
        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPatientsByOwner() {
        Patient p = new Patient();
        p.setId(1L);
        p.setName("Firulais");

        when(patientRepository.findByOwnerId(1L)).thenReturn(Arrays.asList(p));

        List<Patient> result = patientService.getPatientsByOwner(1L);

        assertEquals(1, result.size());
        verify(patientRepository, times(1)).findByOwnerId(1L);
    }

    @Test
    void testSavePatient() {
        Patient p = new Patient();
        p.setName("Firulais");

        when(patientRepository.save(p)).thenReturn(p);

        Patient result = patientService.savePatient(p);

        assertNotNull(result);
        assertEquals("Firulais", result.getName());
        verify(patientRepository, times(1)).save(p);
    }

    @Test
    void testDeletePatient() {
        patientService.deletePatient(1L);
        verify(patientRepository, times(1)).deleteById(1L);
    }
}