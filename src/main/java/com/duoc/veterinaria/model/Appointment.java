package com.duoc.veterinaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha y hora son obligatorias")
    @Column(nullable = false)
    private LocalDateTime dateTime;

    @NotBlank(message = "El motivo es obligatorio")
    @Column(nullable = false)
    private String reason;

    @NotBlank(message = "El nombre del veterinario es obligatorio")
    @Column(nullable = false)
    private String veterinarian;

    // Relación ManyToOne con Patient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
}