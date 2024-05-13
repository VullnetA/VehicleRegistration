package com.example.vehicleRSdemo.Pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicleid")
    private Integer id;
    @Column(name = "manufacturer")
    private String manufacturer;
    @Column(name = "model")
    private String model;
    @Column(name = "year")
    private Integer year;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private Transmission transmission;
    @Column(name = "power")
    private Integer power;
    @Enumerated(EnumType.STRING)
    private Fuel fuel;
    @Column(name = "licenseplate")
    private String licensePlate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Owner owner;
    @Column(name = "dateregistered")
    private LocalDate dateRegistered;
    @Column(name = "expirationdate")
    private LocalDate expirationDate;

    public Vehicle() {
    }
}
