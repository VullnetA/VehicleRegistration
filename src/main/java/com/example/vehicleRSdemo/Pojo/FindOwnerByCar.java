package com.example.vehicleRSdemo.Pojo;

public class FindOwnerByCar {

    private String manufacturer;
    private String model;

    public FindOwnerByCar(String manufacturer, String model) {
        this.manufacturer = manufacturer;
        this.model = model;
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
}
