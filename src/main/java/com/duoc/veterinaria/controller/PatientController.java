package com.duoc.veterinaria.controller;

import com.duoc.veterinaria.model.Patient;
import com.duoc.veterinaria.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/owner/{ownerId}")
    public List<Patient> getPatientsByOwner(@PathVariable Long ownerId) {
        return patientService.getPatientsByOwner(ownerId);
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return patientService.savePatient(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patientDetails) {
        return patientService.getPatientById(id)
                .map(patient -> {
                    patient.setName(patientDetails.getName());
                    patient.setSpecies(patientDetails.getSpecies());
                    patient.setBreed(patientDetails.getBreed());
                    patient.setAge(patientDetails.getAge());
                    patient.setOwner(patientDetails.getOwner());
                    return ResponseEntity.ok(patientService.savePatient(patient));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}