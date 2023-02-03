package com.example.vehicleRSdemo.Controller;

import com.example.vehicleRSdemo.Pojo.*;
import com.example.vehicleRSdemo.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class VehicleController {
    @Autowired
    VehicleService vehicleService;

    @GetMapping("/vehicles")
    public List<Vehicle> getAll() { return vehicleService.findAll(); }

    @GetMapping("/vehicles/{id}")
    public Vehicle getOneById(@PathVariable Integer id) { return vehicleService.findOneById(id); }

    @PostMapping("/register")
    public Vehicle create(@RequestBody RegisterVehicle register) {

        String newManufacturer = register.getManufacturer();
        String newModel = register.getModel();
        Integer newYear = register.getYear();
        Category newCategory = register.getCategory();
        Transmission newTransmission = register.getTransmission();
        Integer newPower = register.getPower();
        Fuel newFuel = register.getFuel();
        String newLicensePlate = register.getLicensePlate();
        Integer newOwnerId = register.getOwnerId();
        LocalDate newDateRegistered = LocalDate.now();
        LocalDate newExpirationDate = newDateRegistered.plusYears( 1 );

        return vehicleService.create(newManufacturer, newModel,
                newYear, newCategory, newTransmission, newPower,
                newFuel, newLicensePlate, newOwnerId, newDateRegistered, newExpirationDate);
    }

    @PutMapping("/vehicles/{id}")
    public Vehicle edit(@PathVariable Integer id,
                                @RequestBody EditVehicle input) {
        String newLicensePlate = input.getLicensePlate();
        Integer newOwnerId = input.getOwnerId();
        LocalDate newDateRegistered = LocalDate.now();
        LocalDate newExpirationDate = newDateRegistered.plusYears( 1 );

    return vehicleService.edit(id, newLicensePlate, newOwnerId, newDateRegistered, newExpirationDate);
    }

    @GetMapping("/ownervehicles/{id}")
    public List<Vehicle> findAllByOwner(@PathVariable Integer id) { return vehicleService.findAllByOwner(id); }

    @GetMapping("/vehicleyear/{year}")
    public List<Vehicle> findVehicleByYear(@PathVariable Integer year) { return vehicleService.findByYear(year); }

    @GetMapping("/vehiclepower/{power}")
    public List<Vehicle> findVehicleWithMorePower(@PathVariable Integer power) { return vehicleService.findByHorsepower(power); }

    @GetMapping("/fuel/{fuel}")
    public List<Vehicle> findVehicleByFuel(@PathVariable String fuel) {
        return vehicleService.findByFuelType(fuel);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {vehicleService.delete(id);}

    @GetMapping("/brand/{manufacturer}")
    public List<Vehicle> findVehicleByBrand(@PathVariable String manufacturer) {
        return vehicleService.findVehicleByBrand(manufacturer); }

}
