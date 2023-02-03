package com.example.vehicleRSdemo.Pojo;

import java.time.LocalDate;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InsuranceCompany getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(InsuranceCompany insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    public float getInsuranceFee() {
        return insuranceFee;
    }

    public void setInsuranceFee(float insuranceFee) {
        this.insuranceFee = insuranceFee;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDate getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(LocalDate dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
