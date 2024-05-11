package com.example.vehicleRSdemo.Service;

import com.example.vehicleRSdemo.Pojo.Category;
import com.example.vehicleRSdemo.Pojo.Insurance;
import com.example.vehicleRSdemo.Pojo.InsuranceCompany;
import com.example.vehicleRSdemo.Pojo.Vehicle;
import com.example.vehicleRSdemo.Repository.InsuranceRepository;
import com.example.vehicleRSdemo.Repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DefaultInsuranceServiceTest {

    private InsuranceRepository insuranceRepository;

    private VehicleRepository vehicleRepository;
    private DefaultInsuranceService insuranceService;

    @BeforeEach
    public void setup() {
        insuranceRepository = mock(InsuranceRepository.class);
        vehicleRepository = mock(VehicleRepository.class);
        insuranceService = new DefaultInsuranceService(vehicleRepository, insuranceRepository);
    }

    // FIND ALL TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testFindAllWithMultipleInsurances() {
        // Arrange
        Insurance insurance1 = new Insurance();
        Insurance insurance2 = new Insurance();
        List<Insurance> insuranceList = Arrays.asList(insurance1, insurance2);
        when(insuranceRepository.findAll()).thenReturn(insuranceList);

        // Act
        List<Insurance> result = insuranceService.findAll();

        // Assert
        verify(insuranceRepository).findAll();
        assertEquals(2, result.size());
        assertEquals(insurance1, result.get(0));
        assertEquals(insurance2, result.get(1));
    }

    @Test
    public void testFindAllWithNoInsurances() {
        // Arrange
        when(insuranceRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Insurance> result = insuranceService.findAll();

        // Assert
        verify(insuranceRepository).findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindAllWithOneInsurance() {
        // Arrange
        Insurance insurance1 = new Insurance();
        List<Insurance> insuranceList = Collections.singletonList(insurance1);
        when(insuranceRepository.findAll()).thenReturn(insuranceList);

        // Act
        List<Insurance> result = insuranceService.findAll();

        // Assert
        verify(insuranceRepository).findAll();
        assertEquals(1, result.size());
        assertEquals(insurance1, result.get(0));
    }

    // FIND BY ID TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testFindOneByIdWithExistingId() {
        // Arrange
        Integer insuranceId = 1;
        Insurance expectedInsurance = new Insurance();
        when(insuranceRepository.findById(insuranceId)).thenReturn(Optional.of(expectedInsurance));

        // Act
        Insurance result = insuranceService.findOneById(insuranceId);

        // Assert
        verify(insuranceRepository).findById(insuranceId);
        assertEquals(expectedInsurance, result);
    }

    @Test
    public void testFindOneByIdWithNonExistingId() {
        // Arrange
        Integer insuranceId = 99;
        when(insuranceRepository.findById(insuranceId)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            insuranceService.findOneById(insuranceId);
        });

        // Verify
        verify(insuranceRepository).findById(insuranceId);
        assertEquals("Insurance with ID " + insuranceId + " not found", thrown.getMessage());
    }

    // DELETE BY ID TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testDeleteById() {
        // Arrange
        Integer insuranceId = 1;

        // Act
        insuranceService.delete(insuranceId);

        // Assert
        verify(insuranceRepository).deleteById(insuranceId);
    }

    // COUNT INSURANCE TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testCountInsurance() {
        // Arrange
        long expectedCount = 10L;
        when(insuranceRepository.countInsurance()).thenReturn(expectedCount);

        // Act
        long result = insuranceService.countInsurance();

        // Assert
        verify(insuranceRepository).countInsurance();
        assertEquals(expectedCount, result);
    }

    // CREATE INSURANCE TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void testCreateInsuranceWithVehicleFound() {
        // Arrange
        Integer vehicleId = 1;
        Vehicle vehicle = new Vehicle();
        vehicle.setCategory(Category.Sedan);
        vehicle.setPower(300);

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));
        when(insuranceRepository.save(any(Insurance.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Insurance createdInsurance = insuranceService.create(InsuranceCompany.AKTIVA, vehicleId);

        // Assert
        verify(vehicleRepository).findById(vehicleId);
        verify(insuranceRepository).save(any(Insurance.class));
        assertEquals(LocalDate.now(), createdInsurance.getDateRegistered());
        assertEquals(LocalDate.now().plusYears(1), createdInsurance.getExpirationDate());
        assertEquals(InsuranceCompany.AKTIVA, createdInsurance.getInsuranceCompany());
        assertEquals(vehicle, createdInsurance.getVehicle());
        // Check for correct fee calculation based on provided vehicle details
        assertEquals(3800f, createdInsurance.getInsuranceFee());
    }

    @Test
    public void testCreateInsuranceWithVehicleNotFound() {
        // Arrange
        Integer vehicleId = 99;
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            insuranceService.create(InsuranceCompany.AKTIVA, vehicleId);
        });
    }

    @Test
    public void testCalculateFee() {
        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setCategory(Category.SUV);
        vehicle.setPower(200);

        // Act
        float fee = insuranceService.calculateFee(vehicle);

        // Assert
        assertEquals(5200f, fee);
    }



    @Test
    public void testCalculateHorsepowerFee() {
        // Arrange
        int horsepower = 350;

        // Act
        float fee = insuranceService.calculateHorsepowerFee(horsepower);

        // Assert
        assertEquals(800f, fee);
    }
}