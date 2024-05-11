package com.example.vehicleRSdemo.Service;

import com.example.vehicleRSdemo.Pojo.*;
import com.example.vehicleRSdemo.Repository.InsuranceRepository;
import com.example.vehicleRSdemo.Repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DefaultInsuranceService implements InsuranceService {
    private final VehicleRepository vehicleRepository;
    private final InsuranceRepository insuranceRepository;

    private static final Map<Category, Float> CATEGORY_FEES = Map.of(
            Category.Motorcycle, 1000f,
            Category.Hatchback, 2000f,
            Category.Coupe, 2500f,
            Category.Sedan, 3000f,
            Category.Van, 4000f,
            Category.SUV, 4500f,
            Category.Truck, 6000f,
            Category.Bus, 6500f
    );

    private static final Map<Integer, Float> HORSEPOWER_FEES = Map.of(
            100, 500f,
            200, 700f,
            300, 800f,
            400, 900f,
            500, 1000f
    );

    @Autowired
    public DefaultInsuranceService(VehicleRepository vehicleRepository, InsuranceRepository insuranceRepository) {
        this.vehicleRepository = vehicleRepository;
        this.insuranceRepository = insuranceRepository;
    }

    @Override
    public List<Insurance> findAll() {
        return insuranceRepository.findAll();
    }

    @Override
    public Insurance findOneById(Integer id) {
        return insuranceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Insurance with ID " + id + " not found"));
    }

    @Override
    public Insurance create(InsuranceCompany insuranceCompany, Integer vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle with ID " + vehicleId + " not found"));
        Insurance insurance = new Insurance();
        insurance.setInsuranceCompany(insuranceCompany);
        insurance.setInsuranceFee(calculateFee(vehicle));
        insurance.setVehicle(vehicle);
        insurance.setDateRegistered(LocalDate.now());
        insurance.setExpirationDate(LocalDate.now().plusYears(1));

        return insuranceRepository.save(insurance);
    }

    @Override
    public void delete(Integer id) {
        insuranceRepository.deleteById(id);
    }

    @Override
    public long countInsurance() {
        return insuranceRepository.countInsurance();
    }

    private float calculateFee(Vehicle vehicle) {
        float baseFee = Optional.ofNullable(CATEGORY_FEES.get(vehicle.getCategory())).orElse(0f);
        float horsepowerFee = calculateHorsepowerFee(vehicle.getPower());
        return baseFee + horsepowerFee;
    }

    private float calculateHorsepowerFee(int horsepower) {
        return HORSEPOWER_FEES.entrySet().stream()
                .filter(entry -> horsepower >= entry.getKey())
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(0f);
    }
}
