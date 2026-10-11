package com.duoc.veterinaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "medical_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El diagnóstico es obligatorio")
    @Column(nullable = false, length = 1000)
    private String diagnosis;

    @NotBlank(message = "El tratamiento es obligatorio")
    @Column(nullable = false, length = 1000)
    private String treatment;

    @Column(nullable = false)
    private LocalDate date;

    // Relación OneToOne con Appointment
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    private Appointment appointment;
}