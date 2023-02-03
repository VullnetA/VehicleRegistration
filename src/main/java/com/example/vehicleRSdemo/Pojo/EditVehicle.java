package com.example.vehicleRSdemo.Pojo;

public class EditVehicle {
    private String licensePlate;
    private Integer ownerId;

    public EditVehicle() {
    }

    public EditVehicle(String licensePlate, Integer ownerId) {
        this.licensePlate = licensePlate;
        this.ownerId = ownerId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Integer getOwnerId() { return ownerId; }

    public void setOwnerId(Integer ownerId) { this.ownerId = ownerId;}

}
