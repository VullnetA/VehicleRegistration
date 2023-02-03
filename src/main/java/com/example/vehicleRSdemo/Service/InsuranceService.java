package com.example.vehicleRSdemo.Service;

import com.example.vehicleRSdemo.Pojo.*;

import java.time.LocalDate;
import java.util.List;

public interface InsuranceService {
    List<Insurance> findAll();

    Insurance findOneById(Integer id);
    Insurance create(InsuranceCompany insuranceCompany, Integer vehicleId,
                     LocalDate dateRegistered, LocalDate expirationDate);

    void delete(Integer id);

    List<Owner> findInsuranceByVehicle();

    long countInsurance();

}
