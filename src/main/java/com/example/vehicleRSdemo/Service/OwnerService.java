package com.example.vehicleRSdemo.Service;

import com.example.vehicleRSdemo.Pojo.*;

import java.time.LocalDate;
import java.util.List;

public interface OwnerService {
    List<Owner> findAll();

    Owner findById(Integer id);
    Owner createOwner(String firstName, String lastName, LocalDate dateOfBirth,
                 String placeOfBirth, Gender gender, LocalDate licenseIssueDate);

    void delete(Integer id);

    List<Owner> findOwnerByVehicle(String manufacturer, String model);

    long licensesByCity(String placeofbirth);

    List<Owner> findByCity(String placeofbirth);
}
