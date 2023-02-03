package com.example.vehicleRSdemo.Pojo;

import java.time.LocalDate;

public class RegisterVehicle {

    private Integer id;
    private String manufacturer;
    private String model;
    private Integer year;
    private Category category;
    private Transmission transmission;
    private Integer power;
    private Fuel fuel;
    private String licensePlate;
    private Integer ownerId;
    private LocalDate dateRegistered;
    private LocalDate expirationDate;

    public RegisterVehicle(Integer id, String manufacturer, String model,
                           Integer year, Category category, Transmission transmission,
                           Integer power, Fuel fuel, String licensePlate, Integer ownerId) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.year = year;
        this.category = category;
        this.transmission = transmission;
        this.power = power;
        this.fuel = fuel;
        this.licensePlate = licensePlate;
        this.ownerId = ownerId;
        this.dateRegistered = LocalDate.now();
    }

    public RegisterVehicle(Fuel fuel) {
        this.fuel = fuel;
    }

    public Integer getVehicleId() {
        return id;
    }

    public void setVehicleId(Integer id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public LocalDate getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered() {
        this.dateRegistered = dateRegistered;
    }
}
