package com.example.vehicleRSdemo.Pojo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class MakeInsurance {
    private Integer id;
    private InsuranceCompany insuranceCompany;
    private float insuranceFee;
    private Integer vehicleId;
    private LocalDate dateRegistered;
    private LocalDate expirationDate;

    public MakeInsurance(Integer id, InsuranceCompany insuranceCompany, float insuranceFee,
                         Integer vehicleId, LocalDate dateRegistered, LocalDate expirationDate) {
        this.id = id;
        this.insuranceCompany = insuranceCompany;
        this.insuranceFee = insuranceFee;
        this.vehicleId = vehicleId;
        this.dateRegistered = dateRegistered;
        this.expirationDate = expirationDate;
    }
}
