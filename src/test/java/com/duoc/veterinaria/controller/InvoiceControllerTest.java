package com.duoc.veterinaria.controller;

import com.duoc.veterinaria.model.Invoice;
import com.duoc.veterinaria.service.InvoiceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InvoiceController.class)
@AutoConfigureMockMvc(addFilters = false)
class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InvoiceService invoiceService;

    @Test
    @WithMockUser
    void testGetAllInvoices() throws Exception {
        Invoice i1 = new Invoice();
        i1.setId(1L);
        i1.setAmount(50000.0);
        i1.setPaid(false);
        i1.setDate(LocalDate.now());

        when(invoiceService.getAllInvoices()).thenReturn(Arrays.asList(i1));

        mockMvc.perform(get("/api/invoices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].amount").value(50000.0));
    }

    @Test
    @WithMockUser
    void testGetInvoiceById() throws Exception {
        Invoice i = new Invoice();
        i.setId(1L);
        i.setAmount(50000.0);
        i.setPaid(false);

        when(invoiceService.getInvoiceById(1L)).thenReturn(Optional.of(i));

        mockMvc.perform(get("/api/invoices/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(50000.0));
    }

    @Test
    @WithMockUser
    void testGetInvoiceByIdNotFound() throws Exception {
        when(invoiceService.getInvoiceById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/invoices/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testGetInvoiceByAppointment() throws Exception {
        Invoice i = new Invoice();
        i.setId(1L);
        i.setAmount(50000.0);

        when(invoiceService.getInvoiceByAppointment(1L)).thenReturn(Optional.of(i));

        mockMvc.perform(get("/api/invoices/appointment/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testGetInvoicesByPaid() throws Exception {
        Invoice i = new Invoice();
        i.setId(1L);
        i.setPaid(true);

        when(invoiceService.getInvoicesByPaid(true)).thenReturn(Arrays.asList(i));

        mockMvc.perform(get("/api/invoices/paid/true"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testCreateInvoice() throws Exception {
        Invoice i = new Invoice();
        i.setId(1L);
        i.setAmount(50000.0);

        when(invoiceService.saveInvoice(any(Invoice.class))).thenReturn(i);

        mockMvc.perform(post("/api/invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":50000.0,\"paid\":false}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testUpdateInvoice() throws Exception {
        Invoice i = new Invoice();
        i.setId(1L);
        i.setAmount(50000.0);

        when(invoiceService.getInvoiceById(1L)).thenReturn(Optional.of(i));
        when(invoiceService.saveInvoice(any(Invoice.class))).thenReturn(i);

        mockMvc.perform(put("/api/invoices/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":60000.0,\"paid\":true}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testUpdateInvoiceNotFound() throws Exception {
        when(invoiceService.getInvoiceById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/invoices/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":1000.0,\"paid\":false}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testDeleteInvoice() throws Exception {
        mockMvc.perform(delete("/api/invoices/1"))
                .andExpect(status().isNoContent());
    }
}
