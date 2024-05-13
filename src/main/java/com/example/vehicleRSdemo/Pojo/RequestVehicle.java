package com.example.vehicleRSdemo.Pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestVehicle {
    private String manufacturer;
    private String model;
    private Integer year;
    private Category category;
    private Transmission transmission;
    private Integer power;
    private Fuel fuel;
    private String licensePlate;
    private Integer ownerId;
}
