package com.example.vehicleRSdemo.Service;

import com.example.vehicleRSdemo.Pojo.*;
import com.example.vehicleRSdemo.Repository.InsuranceRepository;
import com.example.vehicleRSdemo.Repository.OwnerRepository;
import com.example.vehicleRSdemo.Repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultInsuranceService implements InsuranceService{
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private InsuranceRepository insuranceRepository;

    @Override
    public List<Insurance> findAll() {
        List<Insurance> allInsurances = new ArrayList<>();
        Iterable<Insurance> insurances = insuranceRepository.findAll();
        for (Insurance insurance: insurances){
            allInsurances.add(insurance);
        }
        return allInsurances;
    }

    @Override
    public Insurance findOneById(Integer id) {
        return insuranceRepository.findInsuranceById(id);
    }

    @Override
    public Insurance create(InsuranceCompany insuranceCompany, Integer vehicleId,
                            LocalDate dateRegistered, LocalDate expirationDate) {
        Vehicle vehicle = vehicleRepository.findVehicleById(vehicleId);
        Insurance insurance = new Insurance();
        insurance.setInsuranceCompany(insuranceCompany);

        float fee = 0;

        Category category = vehicle.getCategory();

        switch(category) {
            case Motorcycle:
                fee = fee + 1000;
                break;
            case Hatchback:
                fee = fee + 2000;
                break;
            case Coupe:
                fee = fee + 2500;
                break;
            case Sedan:
                fee = fee + 3000;
                break;
            case Van:
                fee = fee + 4000;
                break;
            case SUV:
                fee = fee + 4500;
                break;
            case Truck:
                fee = fee + 6000;
                break;
            case Bus:
                fee = fee + 6500;
        }

        Integer horsepower = vehicle.getPower();

        if(horsepower<100){
            fee = fee + 500;
        } else if (horsepower<200 && horsepower>100) {
            fee = fee + 700;
        } else if (horsepower<300 && horsepower>200) {
            fee = fee + 800;
        } else if (horsepower<400 && horsepower>300) {
            fee = fee + 900;
        } else if (horsepower>400) {
            fee = fee + 1000;
        }

        insurance.setInsuranceFee(fee);
        insurance.setVehicle(vehicle);
        insurance.setDateRegistered(dateRegistered);
        insurance.setExpirationDate(expirationDate);
        return insuranceRepository.save(insurance);
    }

    @Override
    public void delete(Integer id) {
        insuranceRepository.deleteById(id);
    }

    @Override
    public List<Owner> findInsuranceByVehicle() {
        return null;
    }

    @Override
    public long countInsurance() {
        return insuranceRepository.countInsurance();
    }

}
