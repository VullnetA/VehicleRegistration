package com.example.vehicleRSdemo.Pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
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
}
