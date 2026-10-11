package com.duoc.veterinaria.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    void testOwnerGettersSetters() {
        Owner owner = new Owner();
        owner.setId(1L);
        owner.setName("Juan");
        owner.setPhone("123456789");
        owner.setEmail("juan@example.com");
        owner.setAddress("Calle 123");

        assertEquals(1L, owner.getId());
        assertEquals("Juan", owner.getName());
        assertEquals("123456789", owner.getPhone());
        assertEquals("juan@example.com", owner.getEmail());
        assertEquals("Calle 123", owner.getAddress());
    }

    @Test
    void testPatientGettersSetters() {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setName("Firulais");
        patient.setSpecies("Perro");
        patient.setBreed("Labrador");
        patient.setAge(5);

        assertEquals(1L, patient.getId());
        assertEquals("Firulais", patient.getName());
        assertEquals("Perro", patient.getSpecies());
        assertEquals("Labrador", patient.getBreed());
        assertEquals(5, patient.getAge());
    }

    @Test
    void testAppointmentGettersSetters() {
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setDateTime(LocalDateTime.now());
        appointment.setReason("Vacunación");
        appointment.setVeterinarian("Dr. Smith");

        assertEquals(1L, appointment.getId());
        assertNotNull(appointment.getDateTime());
        assertEquals("Vacunación", appointment.getReason());
        assertEquals("Dr. Smith", appointment.getVeterinarian());
    }

    @Test
    void testMedicalRecordGettersSetters() {
        MedicalRecord record = new MedicalRecord();
        record.setId(1L);
        record.setDiagnosis("Sano");
        record.setTreatment("Vacuna");
        record.setDate(LocalDate.now());

        assertEquals(1L, record.getId());
        assertEquals("Sano", record.getDiagnosis());
        assertEquals("Vacuna", record.getTreatment());
        assertNotNull(record.getDate());
    }

    @Test
    void testInvoiceGettersSetters() {
        Invoice invoice = new Invoice();
        invoice.setId(1L);
        invoice.setAmount(50000.0);
        invoice.setDate(LocalDate.now());
        invoice.setPaid(false);

        assertEquals(1L, invoice.getId());
        assertEquals(50000.0, invoice.getAmount());
        assertNotNull(invoice.getDate());
        assertFalse(invoice.getPaid());
    }

    @Test
    void testUserGettersSetters() {
        User user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setPassword("hashed");
        user.setRole("ADMIN");
        user.setEnabled(true);

        assertEquals(1L, user.getId());
        assertEquals("admin", user.getUsername());
        assertEquals("hashed", user.getPassword());
        assertEquals("ADMIN", user.getRole());
        assertTrue(user.getEnabled());
        assertTrue(user.isEnabled());
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertEquals(1, user.getAuthorities().size());
    }
}
