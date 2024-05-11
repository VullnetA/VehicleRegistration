package com.example.vehicleRSdemo.Controller;

import com.example.vehicleRSdemo.Pojo.*;
import com.example.vehicleRSdemo.Service.InsuranceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InsuranceController {

    final
    InsuranceService insuranceService;

    public InsuranceController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    @GetMapping("/AllInsurances")
    public List<Insurance> getAll() {
        return insuranceService.findAll();
    }

    @GetMapping("/InsuranceById/{id}")
    public Insurance getOneById(@PathVariable Integer id) { return insuranceService.findOneById(id); }

    @DeleteMapping("/CancelInsurance/{id}")
    public void delete(@PathVariable Integer id) {insuranceService.delete(id);}

    @PostMapping("/AddInsurance")
    public Insurance create(@RequestBody MakeInsurance insurance) {
        InsuranceCompany insuranceCompany = insurance.getInsuranceCompany();
        Integer vehicle = insurance.getVehicleId();

        return insuranceService.create(insuranceCompany, vehicle);
    }

    @GetMapping("/CountInsurance")
    public long countInsurance() {
        return insuranceService.countInsurance();
    }
}