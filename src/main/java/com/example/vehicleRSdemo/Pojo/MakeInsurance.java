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
}
