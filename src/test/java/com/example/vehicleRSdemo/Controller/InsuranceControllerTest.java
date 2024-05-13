package com.example.vehicleRSdemo.Controller;

import com.example.vehicleRSdemo.Pojo.Insurance;
import com.example.vehicleRSdemo.Pojo.InsuranceCompany;
import com.example.vehicleRSdemo.Pojo.MakeInsurance;
import com.example.vehicleRSdemo.Service.InsuranceService;
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

import static com.example.vehicleRSdemo.Pojo.InsuranceCompany.AKTIVA;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class InsuranceControllerTest {

    @Mock
    private InsuranceService insuranceService;

    @InjectMocks
    private InsuranceController insuranceController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(insuranceController).build();
    }

    // FIND ALL TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testGetAllWithMultipleInsurances() throws Exception {
        // Arrange
        Insurance insurance1 = new Insurance();
        insurance1.setId(1);
        insurance1.setInsuranceFee(2000);
        Insurance insurance2 = new Insurance();
        insurance2.setId(2);
        insurance2.setInsuranceFee(2000);
        List<Insurance> insurances = Arrays.asList(insurance1, insurance2);

        when(insuranceService.findAll()).thenReturn(insurances);

        // Act & Assert
        mockMvc.perform(get("/AllInsurances"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].insuranceFee", is(2000.0)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].insuranceFee", is(2000.0)));
    }

    @Test
    void testGetAllWithNoInsurances() throws Exception {
        // Arrange
        when(insuranceService.findAll()).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/AllInsurances"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // FIND BY ID TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testGetOneByIdWithValidId() throws Exception {
        // Arrange
        Integer insuranceId = 1;
        Insurance insurance = new Insurance();
        insurance.setId(insuranceId);
        insurance.setInsuranceFee(2000);

        when(insuranceService.findOneById(insuranceId)).thenReturn(insurance);

        // Act & Assert
        mockMvc.perform(get("/InsuranceById/{id}", insuranceId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(insuranceId)))
                .andExpect(jsonPath("$.insuranceFee", is(2000.0)));
    }

    @Test
    void testGetOneByIdWithInvalidId() throws Exception {
        // Arrange
        Integer insuranceId = 99;

        when(insuranceService.findOneById(insuranceId)).thenThrow(new EntityNotFoundException("Insurance not found"));

        // Act & Assert
        mockMvc.perform(get("/InsuranceById/{id}", insuranceId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Insurance not found")));
    }

    // CANCEL INSURANCE TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testDeleteWithValidId() throws Exception {
        // Arrange
        Integer insuranceId = 1;
        doNothing().when(insuranceService).delete(insuranceId);

        // Act & Assert
        mockMvc.perform(delete("/CancelInsurance/{id}", insuranceId))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteWithInvalidId() throws Exception {
        // Arrange
        Integer insuranceId = 99;
        doThrow(new EntityNotFoundException("Insurance not found")).when(insuranceService).delete(insuranceId);

        // Act & Assert
        mockMvc.perform(delete("/CancelInsurance/{id}", insuranceId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Insurance not found")));
    }

    @Test
    void testDeleteWithException() throws Exception {
        // Arrange
        Integer insuranceId = 1;
        doThrow(new RuntimeException("Database error")).when(insuranceService).delete(insuranceId);

        // Act & Assert
        mockMvc.perform(delete("/CancelInsurance/{id}", insuranceId))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Database error")));
    }

    // ADD INSURANCE TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testCreateWithValidInputs() throws Exception {
        // Arrange
        MakeInsurance makeInsurance = new MakeInsurance();
        makeInsurance.setInsuranceCompany(InsuranceCompany.AKTIVA);
        makeInsurance.setVehicleId(1);

        Insurance insurance = new Insurance();
        insurance.setId(1);
        insurance.setInsuranceFee(2000);
        insurance.setInsuranceCompany(InsuranceCompany.AKTIVA);

        when(insuranceService.create(any(InsuranceCompany.class), any(Integer.class))).thenReturn(insurance);

        // Act & Assert
        mockMvc.perform(post("/AddInsurance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"insuranceCompany\":\"AKTIVA\",\"vehicleId\":1}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.insuranceCompany", is("AKTIVA")));
    }

    @Test
    void testCreateWithException() throws Exception {
        // Arrange
        MakeInsurance makeInsurance = new MakeInsurance();
        makeInsurance.setInsuranceCompany(InsuranceCompany.AKTIVA);
        makeInsurance.setVehicleId(1);

        when(insuranceService.create(any(InsuranceCompany.class), any(Integer.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        mockMvc.perform(post("/AddInsurance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"insuranceCompany\":\"AKTIVA\",\"vehicleId\":1}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Database error")));
    }

    // COUNT INSURANCE TESTS //
    ///////////////////////////////////////////////////
    @Test
    void testCountInsuranceWithMultipleInsurances() throws Exception {
        // Arrange
        long count = 10;
        when(insuranceService.countInsurance()).thenReturn(count);

        // Act & Assert
        mockMvc.perform(get("/CountInsurance"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(String.valueOf(count)));
    }

    @Test
    void testCountInsuranceWithNoInsurances() throws Exception {
        // Arrange
        long count = 0;
        when(insuranceService.countInsurance()).thenReturn(count);

        // Act & Assert
        mockMvc.perform(get("/CountInsurance"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(String.valueOf(count)));
    }
}
