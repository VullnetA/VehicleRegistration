package com.example.vehicleRSdemo.Pojo;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "insurance")
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "insuranceid")
    private Integer id;
    @Column(name = "company")
    private InsuranceCompany insuranceCompany;
    @Column(name = "fee")
    private float insuranceFee;
    @OneToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    @Column(name = "dateregistered")
    private LocalDate dateRegistered;
    @Column(name = "expirationdate")
    private LocalDate expirationDate;


    public Insurance() {
    }

    public Insurance(Integer id, InsuranceCompany insuranceCompany, float insuranceFee,
                     Vehicle vehicle, LocalDate dateRegistered, LocalDate expirationDate) {
        this.id = id;
        this.insuranceCompany = insuranceCompany;
        this.insuranceFee = insuranceFee;
        this.vehicle = vehicle;
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

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
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

    @Override
    public String toString() {
        return "Insurance{" +
                "id=" + id +
                ", insuranceCompany=" + insuranceCompany +
                ", insuranceFee=" + insuranceFee +
                ", vehicle=" + vehicle +
                ", dateRegistered=" + dateRegistered +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
