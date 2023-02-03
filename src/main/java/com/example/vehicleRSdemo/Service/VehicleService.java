package com.example.vehicleRSdemo.Service;

import com.example.vehicleRSdemo.Pojo.*;

import java.time.LocalDate;
import java.util.List;

public interface VehicleService {
    List<Vehicle> findAll();

    Vehicle findOneById(Integer id);

    List<Vehicle> findAllByOwner(Integer ownerId);

    List<Vehicle> findByFuelType(String fuel);

    Vehicle create(String manufacturer, String model, Integer year,
                           Category category, Transmission transmission, Integer power,
                           Fuel fuel, String licensePlate, Integer ownerId, LocalDate dateRegistered, LocalDate expirationDate);

    void delete(Integer id);

    Vehicle edit(Integer id, String licensePlate, Integer ownerId, LocalDate dateRegistered, LocalDate expirationDate);

    List<Vehicle> findByYear(Integer year);

    List<Vehicle> findByHorsepower(Integer power);

    List<Vehicle> findVehicleByBrand(String manufacturer);

    long countByBrand(String manufacturer);

    long countUnregistered();

    long countRegistered();

    Vehicle checkRegistration(Integer id);

    long countTransmission(String transmission);

    Vehicle findByLicensePlate(String licenseplate);
}
