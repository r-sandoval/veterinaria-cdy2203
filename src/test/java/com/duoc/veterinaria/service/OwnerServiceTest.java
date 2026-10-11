package com.duoc.veterinaria.service;

import com.duoc.veterinaria.model.Owner;
import com.duoc.veterinaria.repository.OwnerRepository;
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
class OwnerServiceTest {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerService ownerService;

    @Test
    void testGetAllOwners() {
        Owner owner1 = new Owner();
        owner1.setId(1L);
        owner1.setName("Juan Pérez");

        Owner owner2 = new Owner();
        owner2.setId(2L);
        owner2.setName("Ana Gómez");

        when(ownerRepository.findAll()).thenReturn(Arrays.asList(owner1, owner2));

        List<Owner> result = ownerService.getAllOwners();

        assertEquals(2, result.size());
        assertEquals("Juan Pérez", result.get(0).getName());
        verify(ownerRepository, times(1)).findAll();
    }

    @Test
    void testGetOwnerById() {
        Owner owner = new Owner();
        owner.setId(1L);
        owner.setName("Juan Pérez");

        when(ownerRepository.findById(1L)).thenReturn(Optional.of(owner));

        Optional<Owner> result = ownerService.getOwnerById(1L);

        assertTrue(result.isPresent());
        assertEquals("Juan Pérez", result.get().getName());
        verify(ownerRepository, times(1)).findById(1L);
    }

    @Test
    void testGetOwnerByIdNotFound() {
        when(ownerRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Owner> result = ownerService.getOwnerById(999L);

        assertFalse(result.isPresent());
        verify(ownerRepository, times(1)).findById(999L);
    }

    @Test
    void testSaveOwner() {
        Owner owner = new Owner();
        owner.setName("Juan Pérez");

        when(ownerRepository.save(owner)).thenReturn(owner);

        Owner result = ownerService.saveOwner(owner);

        assertNotNull(result);
        assertEquals("Juan Pérez", result.getName());
        verify(ownerRepository, times(1)).save(owner);
    }

    @Test
    void testDeleteOwner() {
        ownerService.deleteOwner(1L);

        verify(ownerRepository, times(1)).deleteById(1L);
    }
}