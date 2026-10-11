package com.duoc.veterinaria.service;

import com.duoc.veterinaria.model.Invoice;
import com.duoc.veterinaria.repository.InvoiceRepository;
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
class InvoiceServiceTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private InvoiceService invoiceService;

    @Test
    void testGetAllInvoices() {
        Invoice i1 = new Invoice();
        i1.setId(1L);
        i1.setAmount(50000.0);

        Invoice i2 = new Invoice();
        i2.setId(2L);
        i2.setAmount(75000.0);

        when(invoiceRepository.findAll()).thenReturn(Arrays.asList(i1, i2));

        List<Invoice> result = invoiceService.getAllInvoices();

        assertEquals(2, result.size());
        verify(invoiceRepository, times(1)).findAll();
    }

    @Test
    void testGetInvoiceById() {
        Invoice i = new Invoice();
        i.setId(1L);
        i.setAmount(50000.0);

        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(i));

        Optional<Invoice> result = invoiceService.getInvoiceById(1L);

        assertTrue(result.isPresent());
        assertEquals(50000.0, result.get().getAmount());
        verify(invoiceRepository, times(1)).findById(1L);
    }

    @Test
    void testGetInvoiceByAppointment() {
        Invoice i = new Invoice();
        i.setId(1L);

        when(invoiceRepository.findByAppointmentId(1L)).thenReturn(Optional.of(i));

        Optional<Invoice> result = invoiceService.getInvoiceByAppointment(1L);

        assertTrue(result.isPresent());
        verify(invoiceRepository, times(1)).findByAppointmentId(1L);
    }

    @Test
    void testGetInvoicesByPaid() {
        Invoice i = new Invoice();
        i.setId(1L);
        i.setPaid(true);

        when(invoiceRepository.findByPaid(true)).thenReturn(Arrays.asList(i));

        List<Invoice> result = invoiceService.getInvoicesByPaid(true);

        assertEquals(1, result.size());
        verify(invoiceRepository, times(1)).findByPaid(true);
    }

    @Test
    void testSaveInvoice() {
        Invoice i = new Invoice();
        i.setAmount(50000.0);

        when(invoiceRepository.save(i)).thenReturn(i);

        Invoice result = invoiceService.saveInvoice(i);

        assertNotNull(result);
        assertEquals(50000.0, result.getAmount());
        verify(invoiceRepository, times(1)).save(i);
    }

    @Test
    void testDeleteInvoice() {
        invoiceService.deleteInvoice(1L);
        verify(invoiceRepository, times(1)).deleteById(1L);
    }
}