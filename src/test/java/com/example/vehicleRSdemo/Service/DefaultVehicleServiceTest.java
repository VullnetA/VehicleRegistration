package com.example.vehicleRSdemo.Service;

import com.example.vehicleRSdemo.Pojo.*;
import com.example.vehicleRSdemo.Repository.OwnerRepository;
import com.example.vehicleRSdemo.Repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DefaultVehicleServiceTest {

    private VehicleRepository vehicleRepository;
    private OwnerRepository ownerRepository;
    private DefaultVehicleService vehicleService;

    @BeforeEach
    public void setup() {
        vehicleRepository = mock(VehicleRepository.class);
        ownerRepository = mock(OwnerRepository.class);
        vehicleService = new DefaultVehicleService(vehicleRepository, ownerRepository);
    }

    // FIND ALL TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testFindAllWithMultipleVehicles() {
        // Arrange
        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();
        List<Vehicle> vehicleIterable = Arrays.asList(vehicle1, vehicle2);
        when(vehicleRepository.findAll()).thenReturn(vehicleIterable);

        // Act
        List<Vehicle> result = vehicleService.findAll();

        // Assert
        verify(vehicleRepository).findAll();
        assertEquals(2, result.size());
        assertEquals(vehicle1, result.get(0));
        assertEquals(vehicle2, result.get(1));
    }

    @Test
    public void testFindAllWithNoVehicles() {
        // Arrange
        when(vehicleRepository.findAll()).thenReturn(List.of());

        // Act
        List<Vehicle> result = vehicleService.findAll();

        //Assert
        verify(vehicleRepository).findAll();
        assertEquals(0, result.size());
    }

    @Test
    public void testFindAllWithOneVehicle() {
        // Arrange
        Vehicle vehicle1 = new Vehicle();
        List<Vehicle> vehicleIterable = List.of(vehicle1);
        when(vehicleRepository.findAll()).thenReturn(vehicleIterable);

        // Act
        List<Vehicle> result = vehicleService.findAll();

        // Assert
        verify(vehicleRepository).findAll();
        assertEquals(1, result.size());
        assertEquals(vehicle1, result.get(0));
    }

    // FIND BY ID TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testFindByIdWithExistingId() {
        // Arrange
        Integer vehicleId = 1;
        Vehicle expectedVehicle = new Vehicle();
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(expectedVehicle));

        // Act
        Vehicle result = vehicleService.findById(vehicleId);

        // Assert
        verify(vehicleRepository).findById(vehicleId);
        assertEquals(expectedVehicle, result);
    }

    @Test
    public void testFindByIdWithNonExistingId() {
        // Arrange
        Integer vehicleId = 99;
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            vehicleService.findById(vehicleId);
        });
    }

    // FIND ALL BY OWNER TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testFindAllByOwnerWithValidId() {
        // Arrange
        Integer ownerId = 1;
        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);
        when(vehicleRepository.findAllByOwner(ownerId)).thenReturn(vehicles);

        // Act
        List<Vehicle> result = vehicleService.findAllByOwner(ownerId);

        // Assert
        verify(vehicleRepository).findAllByOwner(ownerId);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(vehicles));
    }

    @Test
    public void testFindAllByOwnerWithNoVehicles() {
        // Arrange
        Integer ownerId = 2;
        when(vehicleRepository.findAllByOwner(ownerId)).thenReturn(List.of());

        // Act
        List<Vehicle> result = vehicleService.findAllByOwner(ownerId);

        // Assert
        verify(vehicleRepository).findAllByOwner(ownerId);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindAllByOwnerWithNullId() {
        // Arrange
        Integer ownerId = null;

        // Act
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            vehicleService.findAllByOwner(ownerId);
        });

        // Assert
        assertEquals("Owner ID cannot be null", thrown.getMessage());
    }

    // FIND BY YEAR TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testFindByYearWithValidYear() {
        // Arrange
        Integer year = 2020;
        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);
        when(vehicleRepository.findByYear(year)).thenReturn(vehicles);

        // Act
        List<Vehicle> result = vehicleService.findByYear(year);

        // Assert
        verify(vehicleRepository).findByYear(year);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(vehicles));
    }

    @Test
    public void testFindByYearWithNoVehicles() {
        // Arrange
        Integer year = 1990;
        when(vehicleRepository.findByYear(year)).thenReturn(List.of());

        // Act
        List<Vehicle> result = vehicleService.findByYear(year);

        // Assert
        verify(vehicleRepository).findByYear(year);
        assertTrue(result.isEmpty());
    }

    // FIND BY HORSEPOWER TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testFindByHorsepowerWithValidPower() {
        // Arrange
        Integer horsepower = 150;
        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);
        when(vehicleRepository.findByHorsepower(horsepower)).thenReturn(vehicles);

        // Act
        List<Vehicle> result = vehicleService.findByHorsepower(horsepower);

        // Assert
        verify(vehicleRepository).findByHorsepower(horsepower);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(vehicles));
    }

    @Test
    public void testFindByHorsepowerWithNoVehicles() {
        // Arrange
        Integer horsepower = 300;
        when(vehicleRepository.findByHorsepower(horsepower)).thenReturn(List.of());

        // Act
        List<Vehicle> result = vehicleService.findByHorsepower(horsepower);

        // Assert
        verify(vehicleRepository).findByHorsepower(horsepower);
        assertTrue(result.isEmpty());
    }

    // FIND VEHICLE BY BRAND TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testFindVehicleByBrandWithValidBrand() {
        // Arrange
        String manufacturer = "Toyota";
        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);
        when(vehicleRepository.findVehicleByBrand(manufacturer)).thenReturn(vehicles);

        // Act
        List<Vehicle> result = vehicleService.findVehicleByBrand(manufacturer);

        // Assert
        verify(vehicleRepository).findVehicleByBrand(manufacturer);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(vehicles));
    }

    @Test
    public void testFindVehicleByBrandWithNoVehicles() {
        // Arrange
        String manufacturer = "SomeRareBrand";
        when(vehicleRepository.findVehicleByBrand(manufacturer)).thenReturn(List.of());

        // Act
        List<Vehicle> result = vehicleService.findVehicleByBrand(manufacturer);

        // Assert
        verify(vehicleRepository).findVehicleByBrand(manufacturer);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindVehicleByBrandWithNullBrand() {
        // Arrange
        String manufacturer = null;
        when(vehicleRepository.findVehicleByBrand(manufacturer)).thenReturn(List.of());

        // Act
        List<Vehicle> result = vehicleService.findVehicleByBrand(manufacturer);

        // Assert
        assertTrue(result.isEmpty());
    }

    // FIND BY FUEL TYPE TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testFindByFuelTypeWithValidFuelType() {
        // Arrange
        String fuelType = "Diesel";
        Vehicle vehicle1 = new Vehicle();
        Vehicle vehicle2 = new Vehicle();
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);
        when(vehicleRepository.findByFuelType(fuelType)).thenReturn(vehicles);

        // Act
        List<Vehicle> result = vehicleService.findByFuelType(fuelType);

        // Assert
        verify(vehicleRepository).findByFuelType(fuelType);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(vehicles));
    }

    @Test
    public void testFindByFuelTypeWithNoVehicles() {
        // Arrange
        String fuelType = "Petrol";
        when(vehicleRepository.findByFuelType(fuelType)).thenReturn(List.of());

        // Act
        List<Vehicle> result = vehicleService.findByFuelType(fuelType);

        // Assert
        verify(vehicleRepository).findByFuelType(fuelType);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindByFuelTypeWithNullFuelType() {
        // Arrange
        String fuelType = null;
        when(vehicleRepository.findByFuelType(fuelType)).thenReturn(List.of());

        // Act
        List<Vehicle> result = vehicleService.findByFuelType(fuelType);

        // Assert
        assertTrue(result.isEmpty());
    }

    // CREATE VEHICLE TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testCreateVehicleWithValidInputs() {
        // Arrange
        Integer ownerId = 1;
        Owner owner = new Owner();
        owner.setLicenseIssueDate(LocalDate.of(2010, 1, 1));

        when(ownerRepository.findById(ownerId)).thenReturn(Optional.of(owner));

        Vehicle vehicle = new Vehicle();
        vehicle.setManufacturer("Toyota");
        vehicle.setModel("Corolla");
        vehicle.setYear(2020);
        vehicle.setCategory(Category.Sedan);
        vehicle.setTransmission(Transmission.Automatic);
        vehicle.setPower(150);
        vehicle.setFuel(Fuel.Petrol);
        vehicle.setLicensePlate("XYZ123");
        vehicle.setOwner(owner);
        vehicle.setDateRegistered(LocalDate.now());
        vehicle.setExpirationDate(LocalDate.now().plusYears(1));

        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        // Act
        Vehicle createdVehicle = vehicleService.create("Toyota", "Corolla", 2020, Category.Sedan, Transmission.Automatic, 150, Fuel.Petrol, "XYZ123", ownerId);

        // Assert
        verify(ownerRepository).findById(ownerId);
        verify(vehicleRepository).save(any(Vehicle.class));
        assertEquals("Toyota", createdVehicle.getManufacturer());
    }

    @Test
    public void testCreateVehicleOwnerNotFound() {
        // Arrange
        Integer ownerId = 99;
        when(ownerRepository.findById(ownerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            vehicleService.create("Toyota", "Corolla", 2020, Category.Sedan, Transmission.Automatic, 150, Fuel.Petrol, "XYZ123", ownerId);
        });
    }

    @Test
    public void testCreateVehicleOwnerWithoutLicense() {
        // Arrange
        Integer ownerId = 1;
        Owner owner = new Owner();
        owner.setLicenseIssueDate(null);

        // Act
        when(ownerRepository.findById(ownerId)).thenReturn(Optional.of(owner));

        // Assert
        assertThrows(IllegalStateException.class, () -> {
            vehicleService.create("Toyota", "Corolla", 2020, Category.Sedan, Transmission.Automatic, 150, Fuel.Petrol, "XYZ123", ownerId);
        });
    }

    // DELETE BY ID TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testDeleteWithValidId() {
        // Arrange
        Integer vehicleId = 1;
        doNothing().when(vehicleRepository).deleteById(vehicleId);

        // Act
        vehicleService.delete(vehicleId);

        // Assert
        verify(vehicleRepository).deleteById(vehicleId);
    }

    @Test
    public void testDeleteWithNonExistentId() {
        // Arrange
        Integer vehicleId = 99;
        doNothing().when(vehicleRepository).deleteById(vehicleId);

        // Act
        vehicleService.delete(vehicleId);

        // Assert
        verify(vehicleRepository).deleteById(vehicleId);
    }

    @Test
    public void testDeleteHandlingException() {
        // Arrange
        Integer vehicleId = 1;
        doThrow(new RuntimeException("Database error")).when(vehicleRepository).deleteById(vehicleId);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> vehicleService.delete(vehicleId));
        verify(vehicleRepository).deleteById(vehicleId);
    }

    // EDIT VEHICLE TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testEditSuccessful() {
        // Arrange
        Integer vehicleId = 1, ownerId = 1;
        Vehicle vehicle = new Vehicle();
        Owner owner = new Owner();

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        when(ownerRepository.findById(ownerId)).thenReturn(Optional.of(owner));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        // Act
        Vehicle result = vehicleService.edit(vehicleId, "NEWPLATE123", ownerId);

        assertEquals("NEWPLATE123", result.getLicensePlate());
        assertEquals(owner, result.getOwner());
        assertEquals(LocalDate.now(), result.getDateRegistered());
        assertEquals(LocalDate.now().plusYears(1), result.getExpirationDate());

        // Assert
        verify(vehicleRepository).save(vehicle);
    }

    @Test
    public void testEditVehicleNotFound() {
        // Arrange
        Integer vehicleId = 99;
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> vehicleService.edit(vehicleId, "NEWPLATE123", 1));
    }

    @Test
    public void testEditOwnerNotFound() {
        // Arrange
        Integer vehicleId = 1, ownerId = 99;
        Vehicle vehicle = new Vehicle();
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        when(ownerRepository.findById(ownerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> vehicleService.edit(vehicleId, "NEWPLATE123", ownerId));
    }

    // COUNT BY BRAND TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testCountByBrandWithExistingBrand() {
        // Arrange
        String manufacturer = "Toyota";
        long expectedCount = 5;
        when(vehicleRepository.countByBrand(manufacturer)).thenReturn(expectedCount);

        // Act
        long result = vehicleService.countByBrand(manufacturer);

        // Assert
        verify(vehicleRepository).countByBrand(manufacturer);
        assertEquals(expectedCount, result);
    }

    @Test
    public void testCountByBrandWithNonExistentBrand() {
        // Arrange
        String manufacturer = "UnknownBrand";
        when(vehicleRepository.countByBrand(manufacturer)).thenReturn(0L);

        // Act
        long result = vehicleService.countByBrand(manufacturer);

        // Assert
        verify(vehicleRepository).countByBrand(manufacturer);
        assertEquals(0, result);
    }

    @Test
    public void testCountByBrandWithNullBrand() {
        // Arrange
        when(vehicleRepository.countByBrand(null)).thenReturn(0L); // Adjust this based on actual handling

        // Act
        long result = vehicleService.countByBrand(null);

        // Assert
        assertEquals(0, result);
    }

    // COUNT UNREGISTERED TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testCountUnregisteredWithUnregisteredVehicles() {
        // Arrange
        long expectedCount = 3;
        when(vehicleRepository.countUnregistered()).thenReturn(expectedCount);

        // Act
        long result = vehicleService.countUnregistered();

        // Assert
        verify(vehicleRepository).countUnregistered();
        assertEquals(expectedCount, result);
    }

    @Test
    public void testCountUnregisteredWithNoUnregisteredVehicles() {
        // Arrange
        when(vehicleRepository.countUnregistered()).thenReturn(0L);

        // Act
        long result = vehicleService.countUnregistered();

        // Assert
        verify(vehicleRepository).countUnregistered();
        assertEquals(0, result);
    }

    // COUNT REGISTERED TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testCountRegisteredWithRegisteredVehicles() {
        // Arrange
        long expectedCount = 5;
        when(vehicleRepository.countRegistered()).thenReturn(expectedCount);

        // Act
        long result = vehicleService.countRegistered();

        // Assert
        verify(vehicleRepository).countRegistered();
        assertEquals(expectedCount, result);
    }

    @Test
    public void testCountRegisteredWithNoRegisteredVehicles() {
        // Arrange
        when(vehicleRepository.countRegistered()).thenReturn(0L);

        // Act
        long result = vehicleService.countRegistered();

        // Assert
        verify(vehicleRepository).countRegistered();
        assertEquals(0, result);
    }


    // CHECK REGISTRATION TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testCheckRegistrationWithRegisteredVehicle() {
        // Arrange
        Integer vehicleId = 1;
        when(vehicleRepository.checkRegistration(vehicleId)).thenReturn(true);

        // Act
        boolean isRegistered = vehicleService.checkRegistration(vehicleId);

        // Assert
        verify(vehicleRepository).checkRegistration(vehicleId);
        assertTrue(isRegistered);
    }

    @Test
    public void testCheckRegistrationWithUnregisteredVehicle() {
        // Arrange
        Integer vehicleId = 2;
        when(vehicleRepository.checkRegistration(vehicleId)).thenReturn(false);

        // Act
        boolean isRegistered = vehicleService.checkRegistration(vehicleId);

        // Assert
        verify(vehicleRepository).checkRegistration(vehicleId);
        assertFalse(isRegistered);
    }

    @Test
    public void testCheckRegistrationWithNonExistentVehicle() {
        // Arrange
        Integer vehicleId = 99;
        when(vehicleRepository.checkRegistration(vehicleId)).thenReturn(false); //

        // Act
        boolean isRegistered = vehicleService.checkRegistration(vehicleId);

        // Assert
        verify(vehicleRepository).checkRegistration(vehicleId);
        assertFalse(isRegistered);
    }

    // COUNT TRANSMISSION TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testCountTransmissionWithValidType() {
        // Arrange
        String transmission = "Automatic";
        long expectedCount = 5;
        when(vehicleRepository.countTransmission(transmission)).thenReturn(expectedCount);

        // Act
        long result = vehicleService.countTransmission(transmission);

        // Assert
        verify(vehicleRepository).countTransmission(transmission);
        assertEquals(expectedCount, result);
    }

    @Test
    public void testCountTransmissionWithNoVehicles() {
        // Arrange
        String transmission = "Manual";
        when(vehicleRepository.countTransmission(transmission)).thenReturn(0L);

        // Act
        long result = vehicleService.countTransmission(transmission);

        // Assert
        verify(vehicleRepository).countTransmission(transmission);
        assertEquals(0, result);
    }

    @Test
    public void testCountTransmissionWithNullType() {
        // Arrange
        when(vehicleRepository.countTransmission(null)).thenReturn(0L);

        // Act
        long result = vehicleService.countTransmission(null);

        // Assert
        assertEquals(0, result);
    }

    // FIND BY LICENSE PLATE TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testFindByLicensePlateWithExistingPlate() {
        // Arrange
        String licensePlate = "ABC123";
        Vehicle expectedVehicle = new Vehicle();
        when(vehicleRepository.findByLicensePlate(licensePlate)).thenReturn(expectedVehicle);

        // Act
        Vehicle result = vehicleService.findByLicensePlate(licensePlate);

        // Assert
        verify(vehicleRepository).findByLicensePlate(licensePlate);
        assertEquals(expectedVehicle, result);
    }

    @Test
    public void testFindByLicensePlateWithNonExistentPlate() {
        // Arrange
        String licensePlate = "XYZ789";
        when(vehicleRepository.findByLicensePlate(licensePlate)).thenReturn(null);

        // Act
        Vehicle result = vehicleService.findByLicensePlate(licensePlate);

        // Assert
        verify(vehicleRepository).findByLicensePlate(licensePlate);
        assertNull(result);
    }

    @Test
    public void testFindByLicensePlateWithNullPlate() {
        // Arrange
        when(vehicleRepository.findByLicensePlate(null)).thenReturn(null);

        // Act
        Vehicle result = vehicleService.findByLicensePlate(null);

        // Assert
        assertNull(result);
    }
}