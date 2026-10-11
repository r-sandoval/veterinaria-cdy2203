package com.duoc.veterinaria.service;

import com.duoc.veterinaria.model.Invoice;
import com.duoc.veterinaria.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    public Optional<Invoice> getInvoiceByAppointment(Long appointmentId) {
        return invoiceRepository.findByAppointmentId(appointmentId);
    }

    public List<Invoice> getInvoicesByPaid(Boolean paid) {
        return invoiceRepository.findByPaid(paid);
    }

    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }
}