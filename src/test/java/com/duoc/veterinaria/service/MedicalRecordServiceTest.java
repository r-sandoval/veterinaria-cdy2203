package com.duoc.veterinaria.service;

import com.duoc.veterinaria.model.MedicalRecord;
import com.duoc.veterinaria.repository.MedicalRecordRepository;
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
class MedicalRecordServiceTest {

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    @Test
    void testGetAllMedicalRecords() {
        MedicalRecord r1 = new MedicalRecord();
        r1.setId(1L);
        r1.setDiagnosis("Sano");

        MedicalRecord r2 = new MedicalRecord();
        r2.setId(2L);
        r2.setDiagnosis("Infección");

        when(medicalRecordRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<MedicalRecord> result = medicalRecordService.getAllMedicalRecords();

        assertEquals(2, result.size());
        verify(medicalRecordRepository, times(1)).findAll();
    }

    @Test
    void testGetMedicalRecordById() {
        MedicalRecord r = new MedicalRecord();
        r.setId(1L);
        r.setDiagnosis("Sano");

        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(r));

        Optional<MedicalRecord> result = medicalRecordService.getMedicalRecordById(1L);

        assertTrue(result.isPresent());
        assertEquals("Sano", result.get().getDiagnosis());
        verify(medicalRecordRepository, times(1)).findById(1L);
    }

    @Test
    void testGetMedicalRecordByAppointment() {
        MedicalRecord r = new MedicalRecord();
        r.setId(1L);

        when(medicalRecordRepository.findByAppointmentId(1L)).thenReturn(Optional.of(r));

        Optional<MedicalRecord> result = medicalRecordService.getMedicalRecordByAppointment(1L);

        assertTrue(result.isPresent());
        verify(medicalRecordRepository, times(1)).findByAppointmentId(1L);
    }

    @Test
    void testSaveMedicalRecord() {
        MedicalRecord r = new MedicalRecord();
        r.setDiagnosis("Sano");

        when(medicalRecordRepository.save(r)).thenReturn(r);

        MedicalRecord result = medicalRecordService.saveMedicalRecord(r);

        assertNotNull(result);
        assertEquals("Sano", result.getDiagnosis());
        verify(medicalRecordRepository, times(1)).save(r);
    }

    @Test
    void testDeleteMedicalRecord() {
        medicalRecordService.deleteMedicalRecord(1L);
        verify(medicalRecordRepository, times(1)).deleteById(1L);
    }
}