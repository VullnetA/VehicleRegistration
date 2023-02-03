package com.example.vehicleRSdemo.Pojo;

import javax.persistence.*;
import java.time.LocalDate;

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

    public Vehicle(Integer id, String manufacturer, String model,
                   Integer year, Category category, Transmission transmission,
                   Integer power, Fuel fuel, String licensePlate, Owner owner,
                   LocalDate dateRegistered, LocalDate expirationDate) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.year = year;
        this.category = category;
        this.transmission = transmission;
        this.power = power;
        this.fuel = fuel;
        this.licensePlate = licensePlate;
        this.owner = owner;
        this.dateRegistered = dateRegistered;
        this.expirationDate = expirationDate;
    }

    public Vehicle() {
    }

    public Integer getVehicleId() {
        return id;
    }

    public void setVehicleId(Integer vehicleId) {
        this.id = vehicleId;
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

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public LocalDate getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(LocalDate dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public LocalDate getExpirationDate() { return expirationDate; }

    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }

    @Override
    public String toString() {
        return "Vehicle{" +
                "Vehicle ID=" + id +
                ", Manufacturer='" + manufacturer + '\'' +
                ", Model='" + model + '\'' +
                ", Production Year=" + year +
                ", Category=" + category +
                ", Transmission=" + transmission +
                ", Horsepower=" + power + "HP" +
                ", Fuel='" + fuel + '\'' +
                ", License Plate='" + licensePlate + '\'' +
                ", Owner=" + owner +
                ", Registration Date=" + dateRegistered +
                ", Registration Expires=" + expirationDate +
                '}';
    }
}
