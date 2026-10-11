package com.duoc.veterinaria.controller;

import com.duoc.veterinaria.model.Owner;
import com.duoc.veterinaria.service.OwnerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OwnerController.class)
@AutoConfigureMockMvc(addFilters = false)
class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OwnerService ownerService;

    @Test
    @WithMockUser
    void testGetAllOwners() throws Exception {
        Owner owner1 = new Owner();
        owner1.setId(1L);
        owner1.setName("Juan Pérez");
        owner1.setPhone("+56912345678");
        owner1.setEmail("juan@example.com");

        Owner owner2 = new Owner();
        owner2.setId(2L);
        owner2.setName("Ana Gómez");
        owner2.setPhone("+56987654321");
        owner2.setEmail("ana@example.com");

        when(ownerService.getAllOwners()).thenReturn(Arrays.asList(owner1, owner2));

        mockMvc.perform(get("/api/owners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Juan Pérez"))
                .andExpect(jsonPath("$[1].name").value("Ana Gómez"));
    }

    @Test
    @WithMockUser
    void testGetOwnerById() throws Exception {
        Owner owner = new Owner();
        owner.setId(1L);
        owner.setName("Juan Pérez");
        owner.setPhone("+56912345678");
        owner.setEmail("juan@example.com");

        when(ownerService.getOwnerById(1L)).thenReturn(Optional.of(owner));

        mockMvc.perform(get("/api/owners/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Juan Pérez"))
                .andExpect(jsonPath("$.email").value("juan@example.com"));
    }

    @Test
    @WithMockUser
    void testGetOwnerByIdNotFound() throws Exception {
        when(ownerService.getOwnerById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/owners/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testCreateOwner() throws Exception {
        Owner owner = new Owner();
        owner.setId(1L);
        owner.setName("Juan Pérez");
        owner.setPhone("+56912345678");
        owner.setEmail("juan@example.com");

        when(ownerService.saveOwner(any(Owner.class))).thenReturn(owner);

        mockMvc.perform(post("/api/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Juan Pérez\",\"phone\":\"+56912345678\",\"email\":\"juan@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Juan Pérez"));
    }

    @Test
    @WithMockUser
    void testUpdateOwner() throws Exception {
        Owner owner = new Owner();
        owner.setId(1L);
        owner.setName("Juan Pérez");

        when(ownerService.getOwnerById(1L)).thenReturn(Optional.of(owner));
        when(ownerService.saveOwner(any(Owner.class))).thenReturn(owner);

        mockMvc.perform(put("/api/owners/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Juan Actualizado\",\"phone\":\"+56912345678\",\"email\":\"juan@example.com\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testUpdateOwnerNotFound() throws Exception {
        when(ownerService.getOwnerById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/owners/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test\",\"phone\":\"123\",\"email\":\"t@t.com\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void testDeleteOwner() throws Exception {
        mockMvc.perform(delete("/api/owners/1"))
                .andExpect(status().isNoContent());
    }
}
