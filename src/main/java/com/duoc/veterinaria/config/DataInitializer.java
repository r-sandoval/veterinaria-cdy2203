package com.duoc.veterinaria.config;

import com.duoc.veterinaria.model.*;
import com.duoc.veterinaria.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Solo cargar datos si la base de datos está vacía
        if (userRepository.count() > 0) {
            return;
        }

        // Crear usuarios
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole("ADMIN");
        admin.setEnabled(true);
        userRepository.save(admin);

        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user123"));
        user.setRole("USER");
        user.setEnabled(true);
        userRepository.save(user);

        // Crear dueños
        Owner owner1 = new Owner();
        owner1.setName("Juan Pérez");
        owner1.setPhone("+56912345678");
        owner1.setEmail("juan@example.com");
        owner1.setAddress("Av. Providencia 123, Santiago");
        ownerRepository.save(owner1);

        Owner owner2 = new Owner();
        owner2.setName("Ana Gómez");
        owner2.setPhone("+56987654321");
        owner2.setEmail("ana@example.com");
        owner2.setAddress("Av. Las Condes 456, Santiago");
        ownerRepository.save(owner2);

        // Crear mascotas
        Patient patient1 = new Patient();
        patient1.setName("Firulais");
        patient1.setSpecies("Perro");
        patient1.setBreed("Labrador");
        patient1.setAge(5);
        patient1.setOwner(owner1);
        patientRepository.save(patient1);

        Patient patient2 = new Patient();
        patient2.setName("Michi");
        patient2.setSpecies("Gato");
        patient2.setBreed("Siames");
        patient2.setAge(3);
        patient2.setOwner(owner2);
        patientRepository.save(patient2);

        // Crear citas
        Appointment appointment1 = new Appointment();
        appointment1.setDateTime(LocalDateTime.now().plusDays(1));
        appointment1.setReason("Vacunación anual");
        appointment1.setVeterinarian("Dr. Smith");
        appointment1.setPatient(patient1);
        appointmentRepository.save(appointment1);

        Appointment appointment2 = new Appointment();
        appointment2.setDateTime(LocalDateTime.now().plusDays(2));
        appointment2.setReason("Control de rutina");
        appointment2.setVeterinarian("Dra. Johnson");
        appointment2.setPatient(patient2);
        appointmentRepository.save(appointment2);

        // Crear registros médicos
        MedicalRecord record1 = new MedicalRecord();
        record1.setDiagnosis("Mascota sana");
        record1.setTreatment("Vacuna aplicada");
        record1.setDate(LocalDate.now());
        record1.setAppointment(appointment1);
        medicalRecordRepository.save(record1);

        // Crear facturas
        Invoice invoice1 = new Invoice();
        invoice1.setAmount(50000.0);
        invoice1.setDate(LocalDate.now());
        invoice1.setPaid(false);
        invoice1.setAppointment(appointment1);
        invoiceRepository.save(invoice1);

        System.out.println(" Datos iniciales cargados correctamente");
        System.out.println("   Usuarios: admin/admin123 y user/user123");
    }
}