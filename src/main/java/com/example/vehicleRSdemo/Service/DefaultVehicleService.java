package com.example.vehicleRSdemo.Service;

import com.example.vehicleRSdemo.Pojo.*;
import com.example.vehicleRSdemo.Repository.OwnerRepository;
import com.example.vehicleRSdemo.Repository.VehicleRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultVehicleService implements VehicleService{
    private final VehicleRepository vehicleRepository;
    private final OwnerRepository ownerRepository;

    public DefaultVehicleService(VehicleRepository vehicleRepository, OwnerRepository ownerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.ownerRepository = ownerRepository;
    }

    @Override
    public List<Vehicle> findAll() {
        List<Vehicle> allVehicles = new ArrayList<>();
        Iterable<Vehicle> vehicles = vehicleRepository.findAll();
        for (Vehicle vehicle: vehicles){
            allVehicles.add(vehicle);
        }
        return allVehicles;
    }

    @Override
    public Vehicle findById(Integer id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle with ID " + id + " not found"));
    }

    @Override
    public List<Vehicle> findAllByOwner(Integer ownerId) {
        if (ownerId == null) {
            throw new IllegalArgumentException("Owner ID cannot be null");
        }
        return vehicleRepository.findAllByOwner(ownerId);
    }

    @Override
    public List<Vehicle> findByYear(Integer year) {
        return vehicleRepository.findByYear(year);
    }

    @Override
    public List<Vehicle> findByHorsepower(Integer power) {
        return vehicleRepository.findByHorsepower(power);
    }

    @Override
    public List<Vehicle> findVehicleByBrand(String manufacturer) {
        return vehicleRepository.findVehicleByBrand(manufacturer);
    }

    @Override
    public List<Vehicle> findByFuelType(String fuel) {
        return vehicleRepository.findByFuelType(fuel);
    }

    @Override
    public Vehicle create(String manufacturer, String model,
                                  Integer year, Category category, Transmission transmission,
                                  Integer power, Fuel fuel, String licensePlate, Integer ownerId) {

        Owner owner = ownerRepository.findById(ownerId).orElseThrow((() -> new EntityNotFoundException("Owner with ID " + ownerId + " not found")));
        if (owner.getLicenseIssueDate()==null){
            throw new IllegalStateException("This owner does not have a drivers license to possess a vehicle");
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setManufacturer(manufacturer);
        vehicle.setModel(model);
        vehicle.setYear(year);
        vehicle.setCategory(category);
        vehicle.setTransmission(transmission);
        vehicle.setPower(power);
        vehicle.setFuel(fuel);
        vehicle.setLicensePlate(licensePlate);
        vehicle.setOwner(owner);
        vehicle.setDateRegistered(LocalDate.now());
        vehicle.setExpirationDate(LocalDate.now().plusYears(1));

        return vehicleRepository.save(vehicle);
    }

    @Override
    public void delete(Integer id) {
        vehicleRepository.deleteById(id);
    }

    @Override
    public Vehicle edit(Integer id, String licensePlate, Integer ownerId){
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle with ID " + id + " not found"));

        Owner owner = ownerRepository.findById(ownerId).orElseThrow((() -> new EntityNotFoundException("Owner with ID " + ownerId + " not found")));
        
        vehicle.setLicensePlate(licensePlate);
        vehicle.setOwner(owner);
        vehicle.setDateRegistered(LocalDate.now());
        vehicle.setExpirationDate(LocalDate.now().plusYears(1));
        
        return vehicleRepository.save(vehicle);
    }

    @Override
    public long countByBrand(String manufacturer) {
        return vehicleRepository.countByBrand(manufacturer);
    }

    @Override
    public long countUnregistered() {
        return vehicleRepository.countUnregistered();
    }

    @Override
    public long countRegistered() {
        return vehicleRepository.countRegistered();
    }

    @Override
    public boolean checkRegistration(Integer id) {
        return vehicleRepository.checkRegistration(id);
    }

    @Override
    public long countTransmission(String transmission) {
        return vehicleRepository.countTransmission(transmission);
    }

    @Override
    public Vehicle findByLicensePlate(String licensePlate) {
        return vehicleRepository.findByLicensePlate(licensePlate);
    }
}
