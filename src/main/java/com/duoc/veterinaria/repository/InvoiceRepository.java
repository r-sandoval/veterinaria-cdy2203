package com.duoc.veterinaria.repository;

import com.duoc.veterinaria.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByAppointmentId(Long appointmentId);
    List<Invoice> findByPaid(Boolean paid);
}