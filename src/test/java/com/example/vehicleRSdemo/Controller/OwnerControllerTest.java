package com.example.vehicleRSdemo.Controller;

import com.example.vehicleRSdemo.Pojo.Gender;
import com.example.vehicleRSdemo.Pojo.InputOwner;
import com.example.vehicleRSdemo.Pojo.Owner;
import com.example.vehicleRSdemo.Service.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OwnerControllerTest {

    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private OwnerController ownerController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    // FIND ALL TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testGetAllWithMultipleOwners() throws Exception {
        // Arrange
        Owner owner1 = new Owner();
        owner1.setOwnerId(1);
        owner1.setFirstName("John");
        Owner owner2 = new Owner();
        owner2.setOwnerId(2);
        owner2.setFirstName("Jane");
        List<Owner> owners = Arrays.asList(owner1, owner2);

        when(ownerService.findAll()).thenReturn(owners);

        // Act & Assert
        mockMvc.perform(get("/AllOwners"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].ownerId", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[1].ownerId", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Jane")));
    }

    @Test
    void testGetAllWithNoOwners() throws Exception {
        // Arrange
        when(ownerService.findAll()).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/AllOwners"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // FIND BY ID TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testGetOneByIdWithValidId() throws Exception {
        // Arrange
        Integer ownerId = 1;
        Owner owner = new Owner();
        owner.setOwnerId(ownerId);
        owner.setFirstName("John");

        when(ownerService.findById(ownerId)).thenReturn(owner);

        // Act & Assert
        mockMvc.perform(get("/OwnerById/{id}", ownerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ownerId", is(ownerId)))
                .andExpect(jsonPath("$.firstName", is("John")));
    }

    @Test
    void testGetOneByIdWithInvalidId() throws Exception {
        // Arrange
        Integer ownerId = 99;

        when(ownerService.findById(ownerId)).thenThrow(new EntityNotFoundException("Owner not found"));

        // Act & Assert
        mockMvc.perform(get("/OwnerById/{id}", ownerId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Owner not found")));
    }

    // REMOVE OWNER TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testDeleteWithValidId() throws Exception {
        // Arrange
        Integer ownerId = 1;
        doNothing().when(ownerService).delete(ownerId);

        // Act & Assert
        mockMvc.perform(delete("/RemoveOwner/{id}", ownerId))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteWithInvalidId() throws Exception {
        // Arrange
        Integer ownerId = 99;
        doThrow(new EntityNotFoundException("Owner not found")).when(ownerService).delete(ownerId);

        // Act & Assert
        mockMvc.perform(delete("/RemoveOwner/{id}", ownerId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Owner not found")));
    }

    @Test
    void testDeleteWithException() throws Exception {
        // Arrange
        Integer ownerId = 1;
        doThrow(new RuntimeException("Database error")).when(ownerService).delete(ownerId);

        // Act & Assert
        mockMvc.perform(delete("/RemoveOwner/{id}", ownerId))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Database error")));
    }

    // INPUT OWNER TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testCreateOwnerWithValidInputs() throws Exception {
        // Arrange
        InputOwner inputOwner = new InputOwner();
        inputOwner.setFirstName("John");
        inputOwner.setLastName("Doe");
        inputOwner.setDateOfBirth(LocalDate.of(1990, 1, 1));
        inputOwner.setPlaceOfBirth("New York");
        inputOwner.setGender(Gender.Male);
        inputOwner.setLicenseIssueDate(LocalDate.of(2010, 1, 1));

        Owner owner = new Owner();
        owner.setOwnerId(1);
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setDateOfBirth(LocalDate.of(1990, 1, 1));
        owner.setPlaceOfBirth("New York");
        owner.setGender(Gender.Male);
        owner.setLicenseIssueDate(LocalDate.of(2010, 1, 1));

        when(ownerService.createOwner(any(String.class), any(String.class), any(LocalDate.class), any(String.class), any(Gender.class), any(LocalDate.class))).thenReturn(owner);

        // Act & Assert
        mockMvc.perform(post("/InputOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"dateOfBirth\":\"1990-01-01\",\"placeOfBirth\":\"New York\",\"gender\":\"Male\",\"licenseIssueDate\":\"2010-01-01\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.ownerId", is(1)))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.placeOfBirth", is("New York")))
                .andExpect(jsonPath("$.gender", is("Male")));
    }

    @Test
    void testCreateOwnerWithException() throws Exception {
        // Arrange
        InputOwner inputOwner = new InputOwner();
        inputOwner.setFirstName("John");
        inputOwner.setLastName("Doe");
        inputOwner.setDateOfBirth(LocalDate.of(1990, 1, 1));
        inputOwner.setPlaceOfBirth("New York");
        inputOwner.setGender(Gender.Male);
        inputOwner.setLicenseIssueDate(LocalDate.of(2010, 1, 1));

        when(ownerService.createOwner(any(String.class), any(String.class), any(LocalDate.class), any(String.class), any(Gender.class), any(LocalDate.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        mockMvc.perform(post("/InputOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"dateOfBirth\":\"1990-01-01\",\"placeOfBirth\":\"New York\",\"gender\":\"Male\",\"licenseIssueDate\":\"2010-01-01\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Database error")));
    }

    // FIND OWNER BY VEHICLE TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testFindOwnerByVehicleWithValidVehicle() throws Exception {
        // Arrange
        String manufacturer = "Toyota";
        String model = "Corolla";

        Owner owner1 = new Owner();
        owner1.setOwnerId(1);
        owner1.setFirstName("John");
        Owner owner2 = new Owner();
        owner2.setOwnerId(2);
        owner2.setFirstName("Jane");
        List<Owner> owners = Arrays.asList(owner1, owner2);

        when(ownerService.findOwnerByVehicle(manufacturer, model)).thenReturn(owners);

        // Act & Assert
        mockMvc.perform(get("/OwnersByCar")
                        .param("manufacturer", manufacturer)
                        .param("model", model)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].ownerId", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[1].ownerId", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Jane")));
    }

    @Test
    void testFindOwnerByVehicleWithNoOwners() throws Exception {
        // Arrange
        String manufacturer = "Toyota";
        String model = "NonExistentModel";

        when(ownerService.findOwnerByVehicle(manufacturer, model)).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/OwnersByCar")
                        .param("manufacturer", manufacturer)
                        .param("model", model)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void testFindOwnerByVehicleWithInvalidVehicle() throws Exception {
        // Arrange
        String manufacturer = "";
        String model = "";

        when(ownerService.findOwnerByVehicle(manufacturer, model)).thenThrow(new IllegalArgumentException("Invalid vehicle data"));

        // Act & Assert
        mockMvc.perform(get("/OwnersByCar")
                        .param("manufacturer", manufacturer)
                        .param("model", model)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Invalid vehicle data")));
    }

    // LICENSES BY CITY TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testLicensesByCityWithValidCity() throws Exception {
        // Arrange
        String placeOfBirth = "New York";
        long count = 10;

        when(ownerService.licensesByCity(placeOfBirth)).thenReturn(count);

        // Act & Assert
        mockMvc.perform(get("/LicensesByCity/{placeOfBirth}", placeOfBirth))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(String.valueOf(count)));
    }

    @Test
    void testLicensesByCityWithNoLicenses() throws Exception {
        // Arrange
        String placeOfBirth = "NonExistentCity";
        long count = 0;

        when(ownerService.licensesByCity(placeOfBirth)).thenReturn(count);

        // Act & Assert
        mockMvc.perform(get("/LicensesByCity/{placeOfBirth}", placeOfBirth))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(String.valueOf(count)));
    }

    // FIND BY CITY TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testFindByCityWithValidCity() throws Exception {
        // Arrange
        String placeOfBirth = "New York";

        Owner owner1 = new Owner();
        owner1.setOwnerId(1);
        owner1.setFirstName("John");
        Owner owner2 = new Owner();
        owner2.setOwnerId(2);
        owner2.setFirstName("Jane");
        List<Owner> owners = Arrays.asList(owner1, owner2);

        when(ownerService.findByCity(placeOfBirth)).thenReturn(owners);

        // Act & Assert
        mockMvc.perform(get("/OwnersByCity/{placeOfBirth}", placeOfBirth))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].ownerId", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[1].ownerId", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Jane")));
    }

    @Test
    void testFindByCityWithNoOwners() throws Exception {
        // Arrange
        String placeOfBirth = "NonExistentCity";

        when(ownerService.findByCity(placeOfBirth)).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/OwnersByCity/{placeOfBirth}", placeOfBirth))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }
}