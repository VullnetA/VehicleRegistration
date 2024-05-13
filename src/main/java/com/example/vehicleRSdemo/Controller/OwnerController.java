package com.example.vehicleRSdemo.Controller;

import com.example.vehicleRSdemo.Pojo.*;
import com.example.vehicleRSdemo.Service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Owner> findOwnerByVehicle(@RequestParam String manufacturer, @RequestParam String model) {
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

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleEntityNotFoundException(EntityNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleRuntimeException(RuntimeException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        return errorResponse;
    }
}
