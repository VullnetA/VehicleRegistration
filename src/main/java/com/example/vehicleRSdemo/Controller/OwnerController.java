package com.example.vehicleRSdemo.Controller;

import com.example.vehicleRSdemo.Pojo.*;
import com.example.vehicleRSdemo.Service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class OwnerController {

    OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/AllOwners")
    public List<Owner> getAll() {
        return ownerService.findAll();
    }

    @GetMapping("/OwnerById/{id}")
    public Owner getOneById(@PathVariable Integer id) {
        return ownerService.findById(id);
    }

    @DeleteMapping("/RemoveOwner/{id}")
    public void delete(@PathVariable Integer id) {
        ownerService.delete(id);
    }

    @PostMapping("/InputOwner")
    public Owner createOwner(@RequestBody InputOwner input) {
        String firstName = input.getFirstName();
        String lastName = input.getLastName();
        LocalDate dateOfBirth = input.getDateOfBirth();
        String placeOfBirth = input.getPlaceOfBirth();
        Gender gender = input.getGender();
        LocalDate licenseIssueDate = input.getLicenseIssueDate();

        return ownerService.createOwner(firstName,lastName,dateOfBirth,placeOfBirth,gender,licenseIssueDate);
    }

    @GetMapping("/OwnersByCar")
    public List<Owner> findOwnerByVehicle(@RequestBody RequestVehicle vehicle) {
        String manufacturer = vehicle.getManufacturer();
        String model = vehicle.getModel();
        return ownerService.findOwnerByVehicle(manufacturer, model);
    }

    @GetMapping("/LicensesByCity/{placeOfBirth}")
    public long licensesByCity(@PathVariable String placeOfBirth) {
        return ownerService.licensesByCity(placeOfBirth);
    }

    @GetMapping("/OwnersByCity/{placeOfBirth}")
    public List<Owner> findByCity(@PathVariable String placeOfBirth) {
        return ownerService.findByCity(placeOfBirth);
    }
}
