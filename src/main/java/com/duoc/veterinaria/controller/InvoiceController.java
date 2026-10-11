package com.duoc.veterinaria.controller;

import com.duoc.veterinaria.model.Invoice;
import com.duoc.veterinaria.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        return invoiceService.getInvoiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<Invoice> getInvoiceByAppointment(@PathVariable Long appointmentId) {
        return invoiceService.getInvoiceByAppointment(appointmentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/paid/{paid}")
    public List<Invoice> getInvoicesByPaid(@PathVariable Boolean paid) {
        return invoiceService.getInvoicesByPaid(paid);
    }

    @PostMapping
    public Invoice createInvoice(@RequestBody Invoice invoice) {
        return invoiceService.saveInvoice(invoice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id, @RequestBody Invoice details) {
        return invoiceService.getInvoiceById(id)
                .map(invoice -> {
                    invoice.setAmount(details.getAmount());
                    invoice.setDate(details.getDate());
                    invoice.setPaid(details.getPaid());
                    invoice.setAppointment(details.getAppointment());
                    return ResponseEntity.ok(invoiceService.saveInvoice(invoice));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
}