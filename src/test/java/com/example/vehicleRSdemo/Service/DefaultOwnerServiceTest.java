package com.example.vehicleRSdemo.Service;

import com.example.vehicleRSdemo.Pojo.Gender;
import com.example.vehicleRSdemo.Pojo.Owner;
import com.example.vehicleRSdemo.Repository.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DefaultOwnerServiceTest {

    private OwnerRepository ownerRepository;
    private DefaultOwnerService ownerService;

    @BeforeEach
    public void setup() {
        ownerRepository = mock(OwnerRepository.class);
        ownerService = new DefaultOwnerService(ownerRepository);
    }

    // FIND ALL TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testFindAllWithMultipleOwners() {
        // Arrange
        Owner owner1 = new Owner();
        Owner owner2 = new Owner();
        List<Owner> ownersIterable = Arrays.asList(owner1, owner2);
        when(ownerRepository.findAll()).thenReturn(ownersIterable);

        // Act
        List<Owner> result = ownerService.findAll();

        // Assert
        verify(ownerRepository).findAll();
        assertEquals(2, result.size());
        assertTrue(result.contains(owner1));
        assertTrue(result.contains(owner2));
    }

    @Test
    public void testFindAllWithNoOwners() {
        // Arrange
        when(ownerRepository.findAll()).thenReturn(List.of());

        // Act
        List<Owner> result = ownerService.findAll();

        // Assert
        verify(ownerRepository).findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindAllWithOneOwner() {
        // Arrange
        Owner owner1 = new Owner();
        List<Owner> ownersIterable = List.of(owner1);
        when(ownerRepository.findAll()).thenReturn(ownersIterable);

        // Act
        List<Owner> result = ownerService.findAll();

        // Assert
        verify(ownerRepository).findAll();
        assertEquals(1, result.size());
        assertEquals(owner1, result.get(0));
    }

    // FIND BY ID TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testFindByIdWithExistingId() {
        // Arrange
        Integer ownerId = 1;
        Owner expectedOwner = new Owner();
        when(ownerRepository.findById(ownerId)).thenReturn(Optional.of(expectedOwner));

        // Act
        Owner result = ownerService.findById(ownerId);

        // Assert
        verify(ownerRepository).findById(ownerId);
        assertEquals(expectedOwner, result);
    }

    @Test
    public void testFindByIdWithNonExistingId() {
        // Arrange
        Integer ownerId = 99;
        when(ownerRepository.findById(ownerId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            ownerService.findById(ownerId);
        });

        // Verify
        assertTrue(exception.getMessage().contains("Owner with ID " + ownerId + " not found"));
    }

    // INPUT OWNER TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testCreateOwnerSuccessfully() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        LocalDate dateOfBirth = LocalDate.now().minusYears(20);
        String placeOfBirth = "Hometown";
        Gender gender = Gender.Male;
        LocalDate licenseIssueDate = LocalDate.now().minusYears(2);
        Owner savedOwner = new Owner();
        when(ownerRepository.save(any(Owner.class))).thenReturn(savedOwner);

        // Act
        Owner result = ownerService.createOwner(firstName, lastName, dateOfBirth, placeOfBirth, gender, licenseIssueDate);

        // Assert
        verify(ownerRepository).save(any(Owner.class));
        assertNotNull(result);
    }

    @Test
    public void testCreateOwnerFailureDueToAge() {
        // Arrange
        String firstName = "Jane";
        String lastName = "Doe";
        LocalDate dateOfBirth = LocalDate.now().minusYears(17);
        String placeOfBirth = "Smallville";
        Gender gender = Gender.Female;
        LocalDate licenseIssueDate = LocalDate.now().minusYears(1);

        // Act & Assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> {
            ownerService.createOwner(firstName, lastName, dateOfBirth, placeOfBirth, gender, licenseIssueDate);
        });

        // Verify
        assertEquals("This person is too young to possess a vehicle", thrown.getMessage());
    }

    // DELETE BY ID TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testDeleteOwner() {
        // Arrange
        Integer ownerId = 1;

        // Act
        ownerService.delete(ownerId);

        // Assert
        verify(ownerRepository).deleteById(ownerId);
    }

    // FIND OWNER BY VEHICLE TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testFindOwnerByVehicleWithMultipleOwners() {
        // Arrange
        String manufacturer = "Toyota";
        String model = "Corolla";
        Owner owner1 = new Owner();
        Owner owner2 = new Owner();
        List<Owner> owners = List.of(owner1, owner2);
        when(ownerRepository.findOwnerByVehicle(manufacturer, model)).thenReturn(owners);

        // Act
        List<Owner> result = ownerService.findOwnerByVehicle(manufacturer, model);

        // Assert
        verify(ownerRepository).findOwnerByVehicle(manufacturer, model);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(owners));
    }

    @Test
    public void testFindOwnerByVehicleWithNoOwners() {
        // Arrange
        String manufacturer = "Tesla";
        String model = "Model 3";
        when(ownerRepository.findOwnerByVehicle(manufacturer, model)).thenReturn(List.of());

        // Act
        List<Owner> result = ownerService.findOwnerByVehicle(manufacturer, model);

        // Assert
        verify(ownerRepository).findOwnerByVehicle(manufacturer, model);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindOwnerByVehicleWithNullOrEmptyParameters() {
        // Arrange
        when(ownerRepository.findOwnerByVehicle(null, null)).thenThrow(new IllegalArgumentException("Parameters cannot be null"));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ownerService.findOwnerByVehicle(null, null);
        });

        // Verify
        assertEquals("Parameters cannot be null", exception.getMessage());
    }

    // LICENSES BY CITY TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testLicensesByCityWithMultipleLicenses() {
        // Arrange
        String placeOfBirth = "Chicago";
        long expectedCount = 5;
        when(ownerRepository.licensesByCity(placeOfBirth)).thenReturn(expectedCount);

        // Act
        long result = ownerService.licensesByCity(placeOfBirth);

        // Assert
        verify(ownerRepository).licensesByCity(placeOfBirth);
        assertEquals(expectedCount, result);
    }

    @Test
    public void testLicensesByCityWithNoLicenses() {
        // Arrange
        String placeOfBirth = "Springfield";
        when(ownerRepository.licensesByCity(placeOfBirth)).thenReturn(0L);

        // Act
        long result = ownerService.licensesByCity(placeOfBirth);

        // Assert
        verify(ownerRepository).licensesByCity(placeOfBirth);
        assertEquals(0, result);
    }

    @Test
    public void testLicensesByCityWithNullOrEmptyParameter() {
        // Arrange
        when(ownerRepository.licensesByCity(null)).thenThrow(new IllegalArgumentException("Place of birth cannot be null"));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ownerService.licensesByCity(null);
        });

        // Verify
        assertEquals("Place of birth cannot be null", exception.getMessage());
    }

    // FIND BY CITY TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testFindByCityWithMultipleOwners() {
        // Arrange
        String placeOfBirth = "New York";
        Owner owner1 = new Owner();
        Owner owner2 = new Owner();
        List<Owner> owners = List.of(owner1, owner2);
        when(ownerRepository.findByCity(placeOfBirth)).thenReturn(owners);

        // Act
        List<Owner> result = ownerService.findByCity(placeOfBirth);

        // Assert
        verify(ownerRepository).findByCity(placeOfBirth);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(owners));
    }

    @Test
    public void testFindByCityWithNoOwners() {
        // Arrange
        String placeOfBirth = "Los Angeles";
        when(ownerRepository.findByCity(placeOfBirth)).thenReturn(List.of());

        // Act
        List<Owner> result = ownerService.findByCity(placeOfBirth);

        // Assert
        verify(ownerRepository).findByCity(placeOfBirth);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindByCityWithNullOrEmptyParameter() {
        // Arrange
        when(ownerRepository.findByCity(null)).thenThrow(new IllegalArgumentException("City cannot be null"));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ownerService.findByCity(null);
        });

        // Verify
        assertEquals("City cannot be null", exception.getMessage());
    }
}