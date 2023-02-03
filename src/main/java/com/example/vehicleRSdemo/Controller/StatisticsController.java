package com.example.vehicleRSdemo.Controller;

import com.example.vehicleRSdemo.Pojo.Vehicle;
import com.example.vehicleRSdemo.Service.InsuranceService;
import com.example.vehicleRSdemo.Service.OwnerService;
import com.example.vehicleRSdemo.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StatisticsController {
    @Autowired
    VehicleService vehicleService;
    @Autowired
    OwnerService ownerService;
    @Autowired
    InsuranceService insuranceService;

    @GetMapping("/countbrand/{manufacturer}")
    public long countByBrand(@PathVariable String manufacturer) {
        return vehicleService.countByBrand(manufacturer); }

    @GetMapping("/countunregistered")
    public long countUnregistered() {
        return vehicleService.countUnregistered(); }

    @GetMapping("/countregistered")
    public long countRegistered() {
        return vehicleService.countRegistered(); }

    @GetMapping("/checkregistration/{id}")
    public Vehicle checkRegistration(@PathVariable Integer id) {
        return vehicleService.checkRegistration(id);
    }

    @GetMapping("/licensebycity/{placeofbirth}")
    public long licensesByCity(@PathVariable String placeofbirth) {
        return ownerService.licensesByCity(placeofbirth);
    }

    @GetMapping("/countinsurance")
    public long countInsurance() {
        return insuranceService.countInsurance();
    }

    @GetMapping("/findplate/{licenseplate}")
    Vehicle findByLicensePlate(@PathVariable String licenseplate) {
        return vehicleService.findByLicensePlate(licenseplate);
    }

    @GetMapping("/counttransmission/{transmission}")
    public long countTransmission(@PathVariable String transmission) {
        return vehicleService.countTransmission(transmission);
    }
}
