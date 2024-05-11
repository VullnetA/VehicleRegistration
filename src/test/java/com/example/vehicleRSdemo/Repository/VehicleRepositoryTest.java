package com.example.vehicleRSdemo.Repository;

import com.example.vehicleRSdemo.Pojo.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class VehicleRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private VehicleRepository vehicleRepository;

    // FIND ALL TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void whenFindAll_singleVehiclePresent_thenReturnsSingleVehicle() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setManufacturer("Toyota");
        vehicle.setModel("Corolla");
        vehicle.setYear(2020);
        vehicle.setCategory(Category.Hatchback);
        vehicle.setTransmission(Transmission.Automatic);
        vehicle.setPower(120);
        vehicle.setFuel(Fuel.Petrol);
        vehicle.setLicensePlate("ABC123");
        vehicle.setDateRegistered(LocalDate.now());
        vehicle.setExpirationDate(LocalDate.now().plusYears(1));
        entityManager.persist(vehicle);
        entityManager.flush();

        // Act
        List<Vehicle> vehicles = vehicleRepository.findAll();

        // Assert
        assertThat(vehicles).hasSize(1);
        assertThat(vehicles.get(0).getManufacturer()).isEqualTo("Toyota");
    }

    @Test
    public void whenFindAll_noVehiclesPresent_thenReturnsEmptyList() {
        // Act
        List<Vehicle> vehicles = vehicleRepository.findAll();

        // Assert
        assertThat(vehicles).isEmpty();
    }

    @Test
    public void whenFindAll_multipleVehiclesPresent_thenReturnsAllVehicles() {
        // Arrange
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setManufacturer("Toyota");
        vehicle1.setModel("Corolla");
        vehicle1.setYear(2020);
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setManufacturer("Honda");
        vehicle2.setModel("Civic");
        vehicle2.setYear(2022);
        entityManager.persist(vehicle1);
        entityManager.persist(vehicle2);
        entityManager.flush();

        // Act
        List<Vehicle> vehicles = vehicleRepository.findAll();

        // Assert
        assertThat(vehicles).hasSize(2);
    }

    // FIND BY ID TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void whenFindById_withValidId_thenReturnsVehicle() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setManufacturer("Toyota");
        vehicle.setModel("Corolla");
        vehicle.setYear(2020);
        vehicle = entityManager.persistFlushFind(vehicle);

        // Act
        Optional<Vehicle> foundVehicle = vehicleRepository.findById(vehicle.getId());

        // Assert
        assertThat(foundVehicle).isPresent();
        assertThat(foundVehicle.get().getManufacturer()).isEqualTo("Toyota");
    }

    @Test
    public void whenFindById_withInvalidId_thenReturnsEmpty() {
        // Arrange
        entityManager.persistFlushFind(new Vehicle());

        // Act
        Optional<Vehicle> foundVehicle = vehicleRepository.findById(-99);

        // Assert
        assertThat(foundVehicle).isNotPresent();
    }

    // DELETE BY ID TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void whenDeleteById_withValidId_thenVehicleIsDeleted() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setManufacturer("Toyota");
        vehicle.setModel("Corolla");
        vehicle = entityManager.persistAndFlush(vehicle);
        vehicleRepository.deleteById(vehicle.getId());
        entityManager.flush();
        entityManager.clear();

        // Act
        Vehicle foundVehicle = entityManager.find(Vehicle.class, vehicle.getId());

        // Assert
        assertThat(foundVehicle).isNull();
    }

    @Test
    public void whenDeleteById_withInvalidId_thenExceptionThrown() {
        // Assert
        assertThrows(EmptyResultDataAccessException.class, () -> vehicleRepository.deleteById(-99));
    }

    // FIND ALL BY OWNER TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void whenFindAllByOwner_withValidOwnerId_thenReturnsVehicles() {
        // Arrange
        Owner owner = new Owner();
        entityManager.persist(owner);
        entityManager.flush();
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setManufacturer("Toyota");
        vehicle1.setModel("Corolla");
        vehicle1.setOwner(owner);
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setManufacturer("Honda");
        vehicle2.setModel("Civic");
        vehicle2.setOwner(owner);
        entityManager.persist(vehicle1);
        entityManager.persist(vehicle2);
        entityManager.flush();

        // Act
        List<Vehicle> vehicles = vehicleRepository.findAllByOwner(owner.getOwnerId());

        // Assert
        assertThat(vehicles).hasSize(2);
    }

    @Test
    public void whenFindAllByOwner_withInvalidOwnerId_thenReturnsEmptyList() {
        // Arrange
        Owner owner = new Owner();
        owner = entityManager.persistFlushFind(owner);
        Vehicle vehicle = new Vehicle();
        vehicle.setManufacturer("Toyota");
        vehicle.setModel("Corolla");
        vehicle.setOwner(owner);
        entityManager.flush();

        // Act
        List<Vehicle> vehicles = vehicleRepository.findAllByOwner(-99);

        // Assert
        assertThat(vehicles).isEmpty();
    }

    // FIND BY YEAR TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void whenFindByYear_withSpecificYear_thenReturnsVehicles() {
        // Arrange
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setManufacturer("Toyota");
        vehicle1.setModel("Corolla");
        vehicle1.setYear(2020);
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setManufacturer("Honda");
        vehicle2.setModel("Civic");
        vehicle2.setYear(2020);
        entityManager.persist(vehicle1);
        entityManager.persist(vehicle2);
        entityManager.flush();

        // Act
        List<Vehicle> vehicles = vehicleRepository.findByYear(2020);

        // Assert
        assertThat(vehicles).hasSize(2).extracting(Vehicle::getYear).containsOnly(2020);
    }

    @Test
    public void whenFindByYear_withYearNoVehicles_thenReturnsEmptyList() {
        // Act
        List<Vehicle> vehicles = vehicleRepository.findByYear(1999);

        // Assert
        assertThat(vehicles).isEmpty();
    }

    // FIND BY HORSEPOWER TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void whenFindByHorsepower_withValueAboveThreshold_thenReturnsVehicles() {
        // Arrange
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setManufacturer("Toyota");
        vehicle1.setModel("Corolla");
        vehicle1.setYear(2020);
        vehicle1.setPower(100);
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setManufacturer("Honda");
        vehicle2.setModel("Civic");
        vehicle2.setYear(2020);
        vehicle1.setPower(150);
        entityManager.persist(vehicle1);
        entityManager.persist(vehicle2);
        entityManager.flush();

        // Act
        List<Vehicle> vehicles = vehicleRepository.findByHorsepower(100);

        // Assert
        assertThat(vehicles).hasSize(1);
    }

    @Test
    public void whenFindByHorsepower_withNoVehiclesAboveThreshold_thenReturnsEmptyList() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setManufacturer("Toyota");
        vehicle.setModel("Corolla");
        vehicle.setYear(2020);
        vehicle.setPower(90);
        entityManager.persist(vehicle);
        entityManager.flush();

        // Act
        List<Vehicle> vehicles = vehicleRepository.findByHorsepower(100);

        // Assert
        assertThat(vehicles).isEmpty();
    }

    @Test
    public void whenFindByHorsepower_withVehiclesAtThreshold_thenExcludesVehicles() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setManufacturer("Toyota");
        vehicle.setModel("Corolla");
        vehicle.setYear(2020);
        vehicle.setPower(100);
        entityManager.persist(vehicle);
        entityManager.flush();

        // Act
        List<Vehicle> vehicles = vehicleRepository.findByHorsepower(100);

        // Assert
        assertThat(vehicles).isEmpty();
    }

    @Test
    public void whenFindByHorsepower_withNegativePowerValue_thenReturnsEmptyList() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setManufacturer("Toyota");
        vehicle.setModel("Corolla");
        vehicle.setYear(2020);
        vehicle.setPower(100);
        entityManager.persist(vehicle);
        entityManager.flush();

        // Act
        List<Vehicle> vehicles = vehicleRepository.findByHorsepower(-1);

        // Assert
        assertThat(vehicles).isEmpty();
    }

    // FIND BY FUEL TYPE TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void whenFindByFuelType_withValidFuel_thenReturnsVehicles() {
        // Arrange
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setFuel(Fuel.Petrol);
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setFuel(Fuel.Petrol);
        entityManager.persist(vehicle1);
        entityManager.persist(vehicle2);
        entityManager.flush();

        // Act
        List<Vehicle> vehicles = vehicleRepository.findByFuelType("Petrol");

        // Assert
        assertThat(vehicles).hasSize(2);
    }

    @Test
    public void whenFindByFuelType_withNoMatch_thenReturnsEmptyList() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setFuel(Fuel.Diesel);
        entityManager.persist(vehicle);
        entityManager.flush();

        // Act
        List<Vehicle> vehicles = vehicleRepository.findByFuelType("Petrol");

        // Assert
        assertThat(vehicles).isEmpty();
    }

    @Test
    public void whenFindByFuelType_withCaseSensitivity_thenDependsOnConfig() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setFuel(Fuel.Petrol);
        entityManager.persist(vehicle);
        entityManager.flush();

        // Act
        List<Vehicle> vehicles = vehicleRepository.findByFuelType("petrol");

        // Assert
        assertThat(vehicles).isEmpty();
    }

    @Test
    public void whenFindByFuelType_withEmptyString_thenReturnsEmptyList() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setFuel(Fuel.Petrol);
        entityManager.persist(vehicle);
        entityManager.flush();

        // Act
        List<Vehicle> vehicles = vehicleRepository.findByFuelType("");

        // Assert
        assertThat(vehicles).isEmpty();
    }

    // FIND BY VEHICLE BRAND TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void whenFindVehicleByBrand_withValidManufacturer_thenReturnsVehicles() {
        // Arrange
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setManufacturer("Toyota");
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setManufacturer("Toyota");
        entityManager.persist(vehicle1);
        entityManager.persist(vehicle2);
        entityManager.flush();

        // Act
        List<Vehicle> vehicles = vehicleRepository.findVehicleByBrand("Toyota");

        // Assert
        assertThat(vehicles).hasSize(2);
    }

    @Test
    public void whenFindVehicleByBrand_withNoMatch_thenReturnsEmptyList() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setManufacturer("Ford");
        entityManager.persist(vehicle);
        entityManager.flush();

        // Act
        List<Vehicle> vehicles = vehicleRepository.findVehicleByBrand("Toyota");

        // Assert
        assertThat(vehicles).isEmpty();
    }

    @Test
    public void whenFindVehicleByBrand_withCaseSensitivity_thenDependsOnConfig() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setManufacturer("toyota");
        entityManager.persist(vehicle);
        entityManager.flush();

        // Act
        List<Vehicle> vehicles = vehicleRepository.findVehicleByBrand("Toyota");

        // Assert
        assertThat(vehicles).isEmpty();
    }

    @Test
    public void whenFindVehicleByBrand_withEmptyString_thenReturnsEmptyList() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setManufacturer("Toyota");
        entityManager.persist(vehicle);
        entityManager.flush();

        // Act
        List<Vehicle> vehicles = vehicleRepository.findVehicleByBrand("");

        // Assert
        assertThat(vehicles).isEmpty();
    }

    // COUNT BY VEHICLE BRAND TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void whenCountByBrand_withExistingManufacturer_thenReturnsCorrectCount() {
        // Arrange
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setManufacturer("Toyota");
        vehicle1.setModel("Corolla");
        vehicle1.setYear(2020);
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setManufacturer("Toyota");
        vehicle2.setModel("Yaris");
        vehicle2.setYear(2020);
        entityManager.persist(vehicle1);
        entityManager.persist(vehicle2);
        entityManager.flush();

        // Act
        long count = vehicleRepository.countByBrand("Toyota");

        // Assert
        assertThat(count).isEqualTo(2);
    }

    @Test
    public void whenCountByBrand_withNoMatch_thenReturnsZero() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setManufacturer("Ford");
        vehicle.setModel("Fiesta");
        vehicle.setYear(2020);
        entityManager.persist(vehicle);
        entityManager.flush();

        // Act
        long count = vehicleRepository.countByBrand("Toyota");

        // Assert
        assertThat(count).isZero();
    }

    @Test
    public void whenCountByBrand_withCaseSensitivity_thenDependsOnConfig() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setManufacturer("toyota");
        vehicle.setModel("Corolla");
        vehicle.setYear(2020);
        entityManager.persist(vehicle);
        entityManager.flush();

        // Act
        long count = vehicleRepository.countByBrand("Toyota");

        // Assert
        assertThat(count).isZero();
    }

    @Test
    public void whenCountByBrand_withEmptyString_thenReturnsZero() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setManufacturer("Toyota");
        vehicle.setModel("Corolla");
        vehicle.setYear(2020);
        entityManager.persist(vehicle);
        entityManager.flush();

        // Act
        long count = vehicleRepository.countByBrand("");

        // Assert
        assertThat(count).isZero();
    }

    // COUNT UNREGISTERED TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void whenCountUnregistered_withUnregisteredVehicles_thenReturnsCorrectCount() {
        // Arrange
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setManufacturer("Toyota");
        vehicle1.setModel("Corolla");
        vehicle1.setExpirationDate(LocalDate.now().minusDays(1));
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setManufacturer("Toyota");
        vehicle2.setModel("Corolla");
        vehicle2.setExpirationDate(LocalDate.now().minusDays(1));
        entityManager.persist(vehicle1);
        entityManager.persist(vehicle2);
        entityManager.flush();

        // Act
        long count = vehicleRepository.countUnregistered();

        // Assert
        assertThat(count).isEqualTo(2);
    }

    @Test
    public void whenCountUnregistered_withAllVehiclesRegistered_thenReturnsZero() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setManufacturer("Toyota");
        vehicle.setModel("Corolla");
        vehicle.setExpirationDate(LocalDate.now().plusDays(1));
        entityManager.persist(vehicle);
        entityManager.flush();

        // Act
        long count = vehicleRepository.countUnregistered();

        // Assert
        assertThat(count).isZero();
    }

    @Test
    public void whenCountUnregistered_withMixedRegistrationStatuses_thenReturnsOnlyUnregisteredCount() {
        // Arrange
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setManufacturer("Toyota");
        vehicle1.setModel("Corolla");
        vehicle1.setExpirationDate(LocalDate.now().minusDays(10));
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setManufacturer("Toyota");
        vehicle2.setModel("Corolla");
        vehicle2.setExpirationDate(LocalDate.now().plusDays(10));
        entityManager.persist(vehicle1);
        entityManager.persist(vehicle2);
        entityManager.flush();

        // Act
        long count = vehicleRepository.countUnregistered();

        // Assert
        assertThat(count).isEqualTo(1);
    }

    @Test
    public void whenCountUnregistered_onBoundaryCondition_thenExcludesVehiclesExpiringToday() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setManufacturer("Chevrolet");
        vehicle.setModel("Camaro");
        vehicle.setExpirationDate(LocalDate.now());
        entityManager.persist(vehicle);
        entityManager.flush();

        // Act
        long count = vehicleRepository.countUnregistered();

        // Assert
        assertThat(count).isZero();
    }

    // COUNT REGISTERED TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void whenCountRegistered_withRegisteredVehicles_thenReturnsCorrectCount() {
        // Arrange
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setManufacturer("Chevrolet");
        vehicle1.setModel("Camaro");
        vehicle1.setExpirationDate(LocalDate.now().plusDays(1));
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setManufacturer("Audi");
        vehicle2.setModel("A1");
        vehicle2.setExpirationDate(LocalDate.now().plusDays(10));
        entityManager.persist(vehicle1);
        entityManager.persist(vehicle2);
        entityManager.flush();

        // Act
        long count = vehicleRepository.countRegistered();

        // Assert
        assertThat(count).isEqualTo(2);
    }

    @Test
    public void whenCountRegistered_withNoRegisteredVehicles_thenReturnsZero() {
        // Arrange
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setManufacturer("Chevrolet");
        vehicle1.setModel("Camaro");
        vehicle1.setExpirationDate(LocalDate.now());
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setManufacturer("Audi");
        vehicle2.setModel("A1");
        vehicle2.setExpirationDate(LocalDate.now().minusDays(1));
        entityManager.persist(vehicle1);
        entityManager.persist(vehicle2);
        entityManager.flush();

        // Act
        long count = vehicleRepository.countRegistered();

        // Assert
        assertThat(count).isZero();
    }

    @Test
    public void whenCountRegistered_withMixedRegistrationStatuses_thenReturnsOnlyRegisteredCount() {
        // Arrange
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setManufacturer("Chevrolet");
        vehicle1.setModel("Camaro");
        vehicle1.setExpirationDate(LocalDate.now().minusDays(10));
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setManufacturer("Audi");
        vehicle2.setModel("A1");
        vehicle2.setExpirationDate(LocalDate.now().plusDays(10));
        entityManager.persist(vehicle1);
        entityManager.persist(vehicle2);
        entityManager.flush();

        // Act
        long count = vehicleRepository.countRegistered();

        // Assert
        assertThat(count).isEqualTo(1);
    }

    @Test
    public void whenCountRegistered_onBoundaryCondition_thenExcludesVehiclesExpiringToday() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setManufacturer("Chevrolet");
        vehicle.setModel("Camaro");
        vehicle.setExpirationDate(LocalDate.now());
        entityManager.persist(vehicle);
        entityManager.flush();

        // Act
        long count = vehicleRepository.countRegistered();

        // Assert
        assertThat(count).isZero();
    }

    // COUNT TRANSMISSION TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void whenCountTransmission_withExistingTransmission_thenReturnsCorrectCount() {
        // Arrange
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setManufacturer("Chevrolet");
        vehicle1.setModel("Camaro");
        vehicle1.setTransmission(Transmission.Automatic);
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setManufacturer("Audi");
        vehicle2.setModel("A1");
        vehicle2.setTransmission(Transmission.Automatic);
        entityManager.persist(vehicle1);
        entityManager.persist(vehicle2);
        entityManager.flush();

        // Act
        long count = vehicleRepository.countTransmission("Automatic");

        // Assert
        assertThat(count).isEqualTo(2);
    }

    @Test
    public void whenCountTransmission_withNoMatch_thenReturnsZero() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setManufacturer("Chevrolet");
        vehicle.setModel("Camaro");
        vehicle.setTransmission(Transmission.Manual);
        entityManager.persist(vehicle);
        entityManager.flush();

        // Act
        long count = vehicleRepository.countTransmission("Automatic");

        // Assert
        assertThat(count).isZero();
    }

    // FIND BY LICENSE PLATE TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void whenFindByLicensePlate_withValidPlate_thenReturnsVehicle() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setManufacturer("Chevrolet");
        vehicle.setModel("Camaro");
        vehicle.setLicensePlate("ABC123");
        entityManager.persist(vehicle);
        entityManager.flush();

        // Act
        Vehicle foundVehicle = vehicleRepository.findByLicensePlate("ABC123");

        // Assert
        assertThat(foundVehicle).isNotNull();
        assertThat(foundVehicle.getLicensePlate()).isEqualTo("ABC123");
    }

    @Test
    public void whenFindByLicensePlate_withNoMatch_thenReturnsNull() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setManufacturer("Chevrolet");
        vehicle.setModel("Camaro");
        vehicle.setLicensePlate("ABC123");
        entityManager.persist(vehicle);
        entityManager.flush();

        // Act
        Vehicle foundVehicle = vehicleRepository.findByLicensePlate("XYZ789");

        // Assert
        assertThat(foundVehicle).isNull();
    }

    @Test
    public void whenFindByLicensePlate_withEmptyString_thenReturnsNullOrVehicle() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate("");
        entityManager.persist(vehicle);
        entityManager.flush();

        // Act
        Vehicle foundVehicle = vehicleRepository.findByLicensePlate("ABC123");

        // Assert
        assertThat(foundVehicle).isNull();
    }
}


