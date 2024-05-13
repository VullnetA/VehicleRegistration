package com.example.vehicleRSdemo.Service;

import com.example.vehicleRSdemo.Pojo.*;

import java.util.List;

public interface InsuranceService {
    List<Insurance> findAll();

    Insurance findOneById(Integer id);
    Insurance create(InsuranceCompany insuranceCompany, Integer vehicleId);

    void delete(Integer id);

    long countInsurance();

}
