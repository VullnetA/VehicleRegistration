package com.example.vehicleRSdemo.Controller;

import com.example.vehicleRSdemo.Pojo.*;
import com.example.vehicleRSdemo.Service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VehicleControllerTest {
    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private VehicleController vehicleController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vehicleController).build();
    }

    // FIND ALL TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testGetAllWithMultipleVehicles() throws Exception {
        // Arrange
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(1);
        vehicle1.setManufacturer("Toyota");
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setId(2);
        vehicle2.setManufacturer("Honda");
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);

        when(vehicleService.findAll()).thenReturn(vehicles);

        // Act & Assert
        mockMvc.perform(get("/AllVehicles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].manufacturer", is("Toyota")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].manufacturer", is("Honda")));
    }

    @Test
    void testGetAllWithNoVehicles() throws Exception {
        // Arrange
        when(vehicleService.findAll()).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/AllVehicles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // FIND BY ID TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testGetOneByIdWithValidId() throws Exception {
        // Arrange
        Integer vehicleId = 1;
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleId);
        vehicle.setManufacturer("Toyota");

        when(vehicleService.findById(vehicleId)).thenReturn(vehicle);

        // Act & Assert
        mockMvc.perform(get("/VehicleById/{id}", vehicleId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(vehicleId)))
                .andExpect(jsonPath("$.manufacturer", is("Toyota")));
    }

    @Test
    void testGetOneByIdWithInvalidId() throws Exception {
        // Arrange
        Integer vehicleId = 99;

        when(vehicleService.findById(vehicleId)).thenThrow(new EntityNotFoundException("Vehicle not found"));

        // Act & Assert
        mockMvc.perform(get("/VehicleById/{id}", vehicleId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Vehicle not found")));
    }

    // DELETE BY ID TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testDeleteWithValidId() throws Exception {
        // Arrange
        Integer vehicleId = 1;
        doNothing().when(vehicleService).delete(vehicleId);

        // Act & Assert
        mockMvc.perform(delete("/DeleteVehicle/{id}", vehicleId))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteWithInvalidId() throws Exception {
        // Arrange
        Integer vehicleId = 99;
        doThrow(new EntityNotFoundException("Vehicle not found")).when(vehicleService).delete(vehicleId);

        // Act & Assert
        mockMvc.perform(delete("/DeleteVehicle/{id}", vehicleId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Vehicle not found")));
    }

    // REGISTER VEHICLE TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testCreateVehicleWithValidInputs() throws Exception {
        // Arrange
        Owner owner = new Owner();
        owner.setOwnerId(1);

        RequestVehicle requestVehicle = new RequestVehicle();
        requestVehicle.setManufacturer("Toyota");
        requestVehicle.setModel("Corolla");
        requestVehicle.setYear(2020);
        requestVehicle.setCategory(Category.Sedan);
        requestVehicle.setTransmission(Transmission.Automatic);
        requestVehicle.setPower(150);
        requestVehicle.setFuel(Fuel.Petrol);
        requestVehicle.setLicensePlate("XYZ123");
        requestVehicle.setOwnerId(1);

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

        when(vehicleService.create(any(String.class), any(String.class), any(Integer.class), any(Category.class),
                any(Transmission.class), any(Integer.class), any(Fuel.class), any(String.class), any(Integer.class)))
                .thenReturn(vehicle);

        // Act & Assert
        mockMvc.perform(post("/RegisterVehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"manufacturer\":\"Toyota\",\"model\":\"Corolla\",\"year\":2020,\"category\":\"Sedan\",\"transmission\":\"Automatic\",\"power\":150,\"fuel\":\"Petrol\",\"licensePlate\":\"XYZ123\",\"ownerId\":1}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.manufacturer", is("Toyota")))
                .andExpect(jsonPath("$.model", is("Corolla")))
                .andExpect(jsonPath("$.year", is(2020)))
                .andExpect(jsonPath("$.category", is("Sedan")))
                .andExpect(jsonPath("$.transmission", is("Automatic")))
                .andExpect(jsonPath("$.power", is(150)))
                .andExpect(jsonPath("$.fuel", is("Petrol")))
                .andExpect(jsonPath("$.licensePlate", is("XYZ123")));
    }

    @Test
    void testCreateVehicleOwnerNotFound() throws Exception {
        // Arrange
        when(vehicleService.create(any(String.class), any(String.class), any(Integer.class), any(Category.class),
                any(Transmission.class), any(Integer.class), any(Fuel.class), any(String.class), any(Integer.class)))
                .thenThrow(new EntityNotFoundException("Owner not found"));

        // Act & Assert
        mockMvc.perform(post("/RegisterVehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"manufacturer\":\"Toyota\",\"model\":\"Corolla\",\"year\":2020,\"category\":\"Sedan\",\"transmission\":\"Automatic\",\"power\":150,\"fuel\":\"Petrol\",\"licensePlate\":\"XYZ123\",\"ownerId\":99}"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Owner not found")));
    }

    @Test
    void testCreateVehicleOwnerWithoutLicense() throws Exception {
        // Arrange
        when(vehicleService.create(any(String.class), any(String.class), any(Integer.class), any(Category.class),
                any(Transmission.class), any(Integer.class), any(Fuel.class), any(String.class), any(Integer.class)))
                .thenThrow(new IllegalStateException("Owner does not have a valid license"));

        // Act & Assert
        mockMvc.perform(post("/RegisterVehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"manufacturer\":\"Toyota\",\"model\":\"Corolla\",\"year\":2020,\"category\":\"Sedan\",\"transmission\":\"Automatic\",\"power\":150,\"fuel\":\"Petrol\",\"licensePlate\":\"XYZ123\",\"ownerId\":1}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Owner does not have a valid license")));
    }

    // EDIT VEHICLE TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testEditVehicleWithValidInputs() throws Exception {
        // Arrange
        Owner owner = new Owner();
        owner.setOwnerId(1);

        RequestVehicle requestVehicle = new RequestVehicle();
        requestVehicle.setLicensePlate("NEWPLATE123");
        requestVehicle.setOwnerId(1);

        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate("NEWPLATE123");
        vehicle.setOwner(owner);

        when(vehicleService.edit(any(Integer.class), any(String.class), any(Integer.class)))
                .thenReturn(vehicle);

        // Act & Assert
        mockMvc.perform(put("/EditVehicle/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"licensePlate\":\"NEWPLATE123\",\"ownerId\":1}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.licensePlate", is("NEWPLATE123")));
    }

    @Test
    void testEditVehicleNotFound() throws Exception {
        // Arrange
        when(vehicleService.edit(any(Integer.class), any(String.class), any(Integer.class)))
                .thenThrow(new EntityNotFoundException("Vehicle not found"));

        // Act & Assert
        mockMvc.perform(put("/EditVehicle/{id}", 99)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"licensePlate\":\"NEWPLATE123\",\"ownerId\":1}"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Vehicle not found")));
    }

    @Test
    void testEditOwnerNotFound() throws Exception {
        // Arrange
        when(vehicleService.edit(any(Integer.class), any(String.class), any(Integer.class)))
                .thenThrow(new EntityNotFoundException("Owner not found"));

        // Act & Assert
        mockMvc.perform(put("/EditVehicle/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"licensePlate\":\"NEWPLATE123\",\"ownerId\":99}"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Owner not found")));
    }

    // VEHICLES FOR OWNERS TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testFindAllByOwnerWithValidId() throws Exception {
        // Arrange
        Integer ownerId = 1;
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(1);
        vehicle1.setManufacturer("Toyota");
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setId(2);
        vehicle2.setManufacturer("Honda");
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);

        when(vehicleService.findAllByOwner(ownerId)).thenReturn(vehicles);

        // Act & Assert
        mockMvc.perform(get("/VehiclesForOwner/{id}", ownerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].manufacturer", is("Toyota")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].manufacturer", is("Honda")));
    }

    @Test
    void testFindAllByOwnerWithNoVehicles() throws Exception {
        // Arrange
        Integer ownerId = 1;

        when(vehicleService.findAllByOwner(ownerId)).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/VehiclesForOwner/{id}", ownerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void testFindAllByOwnerWithInvalidId() throws Exception {
        // Arrange
        Integer ownerId = 99;

        when(vehicleService.findAllByOwner(ownerId)).thenThrow(new EntityNotFoundException("Owner not found"));

        // Act & Assert
        mockMvc.perform(get("/VehiclesForOwner/{id}", ownerId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Owner not found")));
    }

    // VEHICLES BY YEAR TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testFindVehicleByYearWithValidYear() throws Exception {
        // Arrange
        Integer year = 2020;
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(1);
        vehicle1.setManufacturer("Toyota");
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setId(2);
        vehicle2.setManufacturer("Honda");
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);

        when(vehicleService.findByYear(year)).thenReturn(vehicles);

        // Act & Assert
        mockMvc.perform(get("/VehiclesByYear/{year}", year))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].manufacturer", is("Toyota")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].manufacturer", is("Honda")));
    }

    @Test
    void testFindVehicleByYearWithNoVehicles() throws Exception {
        // Arrange
        Integer year = 1990;

        when(vehicleService.findByYear(year)).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/VehiclesByYear/{year}", year))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void testFindVehicleByYearWithInvalidYear() throws Exception {
        // Arrange
        Integer year = -1;

        when(vehicleService.findByYear(year)).thenThrow(new IllegalArgumentException("Invalid year"));

        // Act & Assert
        mockMvc.perform(get("/VehiclesByYear/{year}", year))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Invalid year")));
    }

    // VEHICLES BY POWER TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testFindVehicleWithMorePowerWithValidPower() throws Exception {
        // Arrange
        Integer power = 150;
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(1);
        vehicle1.setManufacturer("Toyota");
        vehicle1.setPower(150);
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setId(2);
        vehicle2.setManufacturer("Honda");
        vehicle2.setPower(200);
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);

        when(vehicleService.findByHorsepower(power)).thenReturn(vehicles);

        // Act & Assert
        mockMvc.perform(get("/VehiclesByPower/{power}", power))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].manufacturer", is("Toyota")))
                .andExpect(jsonPath("$[0].power", is(150)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].manufacturer", is("Honda")))
                .andExpect(jsonPath("$[1].power", is(200)));
    }

    @Test
    void testFindVehicleWithMorePowerWithNoVehicles() throws Exception {
        // Arrange
        Integer power = 300;

        when(vehicleService.findByHorsepower(power)).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/VehiclesByPower/{power}", power))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void testFindVehicleWithMorePowerWithInvalidPower() throws Exception {
        // Arrange
        Integer power = -1;

        when(vehicleService.findByHorsepower(power)).thenThrow(new IllegalArgumentException("Invalid power value"));

        // Act & Assert
        mockMvc.perform(get("/VehiclesByPower/{power}", power))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Invalid power value")));
    }

    // VEHICLES BY FUEL TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testFindVehicleByFuelWithValidFuel() throws Exception {
        // Arrange
        String fuel = "Diesel";
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(1);
        vehicle1.setManufacturer("Toyota");
        vehicle1.setFuel(Fuel.Diesel);
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setId(2);
        vehicle2.setManufacturer("Honda");
        vehicle2.setFuel(Fuel.Diesel);
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);

        when(vehicleService.findByFuelType(fuel)).thenReturn(vehicles);

        // Act & Assert
        mockMvc.perform(get("/VehiclesByFuel/{fuel}", fuel))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].manufacturer", is("Toyota")))
                .andExpect(jsonPath("$[0].fuel", is("Diesel")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].manufacturer", is("Honda")))
                .andExpect(jsonPath("$[1].fuel", is("Diesel")));
    }

    @Test
    void testFindVehicleByFuelWithNoVehicles() throws Exception {
        // Arrange
        String fuel = "Electric";

        when(vehicleService.findByFuelType(fuel)).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/VehiclesByFuel/{fuel}", fuel))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void testFindVehicleByFuelWithInvalidFuel() throws Exception {
        // Arrange
        String fuel = "InvalidFuelType";

        when(vehicleService.findByFuelType(fuel)).thenThrow(new IllegalArgumentException("Invalid fuel type"));

        // Act & Assert
        mockMvc.perform(get("/VehiclesByFuel/{fuel}", fuel))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Invalid fuel type")));
    }

    // VEHICLES BY BRAND TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testFindVehicleByBrandWithValidBrand() throws Exception {
        // Arrange
        String manufacturer = "Toyota";
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(1);
        vehicle1.setManufacturer(manufacturer);
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setId(2);
        vehicle2.setManufacturer(manufacturer);
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);

        when(vehicleService.findVehicleByBrand(manufacturer)).thenReturn(vehicles);

        // Act & Assert
        mockMvc.perform(get("/VehiclesByBrand/{manufacturer}", manufacturer))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].manufacturer", is("Toyota")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].manufacturer", is("Toyota")));
    }

    @Test
    void testFindVehicleByBrandWithNoVehicles() throws Exception {
        // Arrange
        String manufacturer = "NonExistentBrand";

        when(vehicleService.findVehicleByBrand(manufacturer)).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/VehiclesByBrand/{manufacturer}", manufacturer))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // CHECK REGISTRATION TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testCheckRegistrationWithRegisteredVehicle() throws Exception {
        // Arrange
        Integer vehicleId = 1;

        when(vehicleService.checkRegistration(vehicleId)).thenReturn(true);

        // Act & Assert
        mockMvc.perform(get("/CheckRegistration/{id}", vehicleId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(content().string("Registered"));
    }

    @Test
    void testCheckRegistrationWithUnregisteredVehicle() throws Exception {
        // Arrange
        Integer vehicleId = 2;

        when(vehicleService.checkRegistration(vehicleId)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(get("/CheckRegistration/{id}", vehicleId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(content().string("Not Registered"));
    }

    @Test
    void testCheckRegistrationWithInvalidId() throws Exception {
        // Arrange
        Integer vehicleId = 99;

        when(vehicleService.checkRegistration(vehicleId)).thenThrow(new EntityNotFoundException("Vehicle not found"));

        // Act & Assert
        mockMvc.perform(get("/CheckRegistration/{id}", vehicleId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Vehicle not found")));
    }

    // FIND BY LICENSE PLATE TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testFindByLicensePlateWithValidPlate() throws Exception {
        // Arrange
        String licensePlate = "ABC123";
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1);
        vehicle.setManufacturer("Toyota");
        vehicle.setLicensePlate(licensePlate);

        when(vehicleService.findByLicensePlate(licensePlate)).thenReturn(vehicle);

        // Act & Assert
        mockMvc.perform(get("/FindByLicensePlate/{licenseplate}", licensePlate))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.manufacturer", is("Toyota")))
                .andExpect(jsonPath("$.licensePlate", is(licensePlate)));
    }

    @Test
    void testFindByLicensePlateWithInvalidPlate() throws Exception {
        // Arrange
        String licensePlate = "XYZ789";

        when(vehicleService.findByLicensePlate(licensePlate)).thenThrow(new EntityNotFoundException("Vehicle not found"));

        // Act & Assert
        mockMvc.perform(get("/FindByLicensePlate/{licenseplate}", licensePlate))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Vehicle not found")));
    }

    // COUNT BY BRAND TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testCountByBrandWithValidBrand() throws Exception {
        // Arrange
        String manufacturer = "Toyota";
        long count = 5;

        when(vehicleService.countByBrand(manufacturer)).thenReturn(count);

        // Act & Assert
        mockMvc.perform(get("/CountByBrand/{manufacturer}", manufacturer))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(String.valueOf(count)));
    }

    @Test
    void testCountByBrandWithNoVehicles() throws Exception {
        // Arrange
        String manufacturer = "NonExistentBrand";
        long count = 0;

        when(vehicleService.countByBrand(manufacturer)).thenReturn(count);

        // Act & Assert
        mockMvc.perform(get("/CountByBrand/{manufacturer}", manufacturer))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(String.valueOf(count)));
    }

    // COUNT TRANSMISSION TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testCountTransmissionWithValidTransmission() throws Exception {
        // Arrange
        String transmission = "Automatic";
        long count = 5;

        when(vehicleService.countTransmission(transmission)).thenReturn(count);

        // Act & Assert
        mockMvc.perform(get("/CountByTransmission/{transmission}", transmission))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(String.valueOf(count)));
    }

    @Test
    void testCountTransmissionWithNoVehicles() throws Exception {
        // Arrange
        String transmission = "Manual";
        long count = 0;

        when(vehicleService.countTransmission(transmission)).thenReturn(count);

        // Act & Assert
        mockMvc.perform(get("/CountByTransmission/{transmission}", transmission))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(String.valueOf(count)));
    }

    @Test
    void testCountTransmissionWithInvalidTransmission() throws Exception {
        // Arrange
        String transmission = "InvalidTransmission";

        when(vehicleService.countTransmission(transmission)).thenThrow(new IllegalArgumentException("Invalid transmission"));

        // Act & Assert
        mockMvc.perform(get("/CountByTransmission/{transmission}", transmission))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Invalid transmission")));
    }

    // COUNT REGISTERED TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testCountRegisteredWithRegisteredVehicles() throws Exception {
        // Arrange
        long count = 10;

        when(vehicleService.countRegistered()).thenReturn(count);

        // Act & Assert
        mockMvc.perform(get("/CountRegistered"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(String.valueOf(count)));
    }

    @Test
    void testCountRegisteredWithNoRegisteredVehicles() throws Exception {
        // Arrange
        long count = 0;

        when(vehicleService.countRegistered()).thenReturn(count);

        // Act & Assert
        mockMvc.perform(get("/CountRegistered"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(String.valueOf(count)));
    }

    // COUNT UNREGISTERED TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testCountUnregisteredWithUnregisteredVehicles() throws Exception {
        // Arrange
        long count = 5;

        when(vehicleService.countUnregistered()).thenReturn(count);

        // Act & Assert
        mockMvc.perform(get("/CountUnregistered"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(String.valueOf(count)));
    }

    @Test
    void testCountUnregisteredWithNoUnregisteredVehicles() throws Exception {
        // Arrange
        long count = 0;

        when(vehicleService.countUnregistered()).thenReturn(count);

        // Act & Assert
        mockMvc.perform(get("/CountUnregistered"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(String.valueOf(count)));
    }
}

