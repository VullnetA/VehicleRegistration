package com.example.vehicleRSdemo.Controller;

import com.example.vehicleRSdemo.Pojo.*;
import com.example.vehicleRSdemo.Service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VehicleController {
    final
    VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/AllVehicles")
    public List<Vehicle> getAll() {
        return vehicleService.findAll();
    }

    @GetMapping("/VehicleById/{id}")
    public Vehicle getOneById(@PathVariable Integer id) {
        return vehicleService.findById(id);
    }

    @DeleteMapping("/DeleteVehicle/{id}")
    public void delete(@PathVariable Integer id) {
        vehicleService.delete(id);
    }

    @PostMapping("/RegisterVehicle")
    public Vehicle create(@RequestBody RequestVehicle register) {

        String Manufacturer = register.getManufacturer();
        String Model = register.getModel();
        Integer Year = register.getYear();
        Category Category = register.getCategory();
        Transmission Transmission = register.getTransmission();
        Integer Power = register.getPower();
        Fuel Fuel = register.getFuel();
        String LicensePlate = register.getLicensePlate();
        Integer OwnerId = register.getOwnerId();

        return vehicleService.create(Manufacturer, Model,
                Year, Category, Transmission, Power,
                Fuel, LicensePlate, OwnerId);
    }

    @PutMapping("/EditVehicle/{id}")
    public Vehicle edit(@PathVariable Integer id,
                                @RequestBody RequestVehicle input) {
        String licensePlate = input.getLicensePlate();
        Integer ownerId = input.getOwnerId();

    return vehicleService.edit(id, licensePlate, ownerId);
    }

    @GetMapping("/VehiclesForOwner/{id}")
    public List<Vehicle> findAllByOwner(@PathVariable Integer id) {
        return vehicleService.findAllByOwner(id);
    }

    @GetMapping("/VehiclesByYear/{year}")
    public List<Vehicle> findVehicleByYear(@PathVariable Integer year) {
        return vehicleService.findByYear(year);
    }

    @GetMapping("/VehiclesByPower/{power}")
    public List<Vehicle> findVehicleWithMorePower(@PathVariable Integer power) {
        return vehicleService.findByHorsepower(power);
    }

    @GetMapping("/VehiclesByFuel/{fuel}")
    public List<Vehicle> findVehicleByFuel(@PathVariable String fuel) {
        return vehicleService.findByFuelType(fuel);
    }

    @GetMapping("/VehiclesByBrand/{manufacturer}")
    public List<Vehicle> findVehicleByBrand(@PathVariable String manufacturer) {
        return vehicleService.findVehicleByBrand(manufacturer);
    }

    @GetMapping("/CheckRegistration/{id}")
    public ResponseEntity<String> checkRegistration(@PathVariable Integer id) {
        boolean isRegistered = vehicleService.checkRegistration(id);
        return ResponseEntity.ok(isRegistered ? "Registered" : "Not Registered");
    }

    @GetMapping("/FindByLicensePlate/{licenseplate}")
    Vehicle findByLicensePlate(@PathVariable String licenseplate) {
        return vehicleService.findByLicensePlate(licenseplate);
    }

    @GetMapping("/CountByBrand/{manufacturer}")
    public long countByBrand(@PathVariable String manufacturer) {
        return vehicleService.countByBrand(manufacturer);
    }

    @GetMapping("/CountByTransmission/{transmission}")
    public long countTransmission(@PathVariable String transmission) {
        return vehicleService.countTransmission(transmission);
    }

    @GetMapping("/CountRegistered")
    public long countRegistered() {
        return vehicleService.countRegistered();
    }

    @GetMapping("/CountUnregistered")
    public long countUnregistered() {
        return vehicleService.countUnregistered();
    }
}
