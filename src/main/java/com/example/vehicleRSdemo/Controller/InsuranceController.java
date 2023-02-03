package com.example.vehicleRSdemo.Controller;

import com.example.vehicleRSdemo.Pojo.*;
import com.example.vehicleRSdemo.Service.InsuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class InsuranceController {

    @Autowired
    InsuranceService insuranceService;

    @GetMapping("/insurances")
    public List<Insurance> getAll() {
        return insuranceService.findAll();
    }

    @GetMapping("/insurance/{id}")
    public Insurance getOneById(@PathVariable Integer id) { return insuranceService.findOneById(id); }

    @PostMapping("/addinsurance")
    public Insurance create(@RequestBody MakeInsurance insurance) {
        InsuranceCompany addInsuranceCompany = insurance.getInsuranceCompany();
        Integer addVehicle = insurance.getVehicleId();
        LocalDate addDateRegistered = LocalDate.now();
        LocalDate addExpirationDate = addDateRegistered.plusYears( 1 );

        return insuranceService.create(addInsuranceCompany, addVehicle, addDateRegistered, addExpirationDate);
    }

    @DeleteMapping("/cancel/{id}")
    public void delete(@PathVariable Integer id) {insuranceService.delete(id);}

}