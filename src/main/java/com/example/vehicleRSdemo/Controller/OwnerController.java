package com.example.vehicleRSdemo.Controller;

import com.example.vehicleRSdemo.Pojo.*;
import com.example.vehicleRSdemo.Service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class OwnerController {
    @Autowired
    OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/owners")
    public List<Owner> getAll() { return ownerService.findAll(); }

    @GetMapping("/owners/{id}")
    public Owner getOneById(@PathVariable Integer id) { return ownerService.findOneById(id); }

    @GetMapping("/findbycar")
    public List<Owner> findOwnerByVehicle(@RequestBody FindOwnerByCar car) {
        String manufacturer = car.getManufacturer();
        String model = car.getModel();
        return ownerService.findOwnerByVehicle(manufacturer, model);
    }

    @PostMapping("/input")
    public Owner createOwner(@RequestBody InputOwner input) {
        String firstName = input.getFirstName();
        String lastName = input.getLastName();
        LocalDate dateOfBirth = input.getDateOfBirth();
        String placeOfBirth = input.getPlaceOfBirth();
        Gender gender = input.getGender();
        LocalDate licenseIssueDate = input.getLicenseIssueDate();

        return ownerService.createOwner(firstName,lastName,dateOfBirth,placeOfBirth,gender,licenseIssueDate);
    }

    @DeleteMapping("/remove/{id}")
    public void delete(@PathVariable Integer id) {ownerService.delete(id);}

    @GetMapping("/owners/{city}")
    public List<Owner> findByCity(@PathVariable String placeofbirth) { return ownerService.findByCity(placeofbirth); }

}
