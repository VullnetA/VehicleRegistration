package com.example.vehicleRSdemo.Service;

import com.example.vehicleRSdemo.Pojo.Gender;
import com.example.vehicleRSdemo.Pojo.Owner;
import com.example.vehicleRSdemo.Repository.OwnerRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultOwnerService implements OwnerService{

    private final OwnerRepository ownerRepository;

    public DefaultOwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public List<Owner> findAll() {
        List<Owner> allOwners = new ArrayList<>();
        Iterable<Owner> owners = ownerRepository.findAll();
        for (Owner owner: owners){
            allOwners.add(owner);
        }
        return allOwners;
    }

    @Override
    public Owner findById(Integer id) {
        return ownerRepository.findById(id).orElseThrow((() -> new EntityNotFoundException("Owner with ID " + id + " not found")));
    }

    @Override
    public Owner createOwner(String firstName, String lastName,
                        LocalDate dateOfBirth, String placeOfBirth, Gender gender, LocalDate licenseIssueDate) {
        long years;
        years = ChronoUnit.YEARS.between(dateOfBirth, LocalDate.now());
        if (years >= 18) {
            Owner owner = new Owner();
            owner.setFirstName(firstName);
            owner.setLastName(lastName);
            owner.setDateOfBirth(dateOfBirth);
            owner.setPlaceOfBirth(placeOfBirth);
            owner.setGender(gender);
            owner.setLicenseIssueDate(licenseIssueDate);

            return ownerRepository.save(owner);
        } else {
            throw new IllegalStateException("This person is too young to possess a vehicle");
        }
    }

    @Override
    public void delete(Integer id) {
        ownerRepository.deleteById(id);
    }

    @Override
    public List<Owner> findOwnerByVehicle(String manufacturer, String model){
        return ownerRepository.findOwnerByVehicle(manufacturer,model);
    }

    @Override
    public long licensesByCity(String placeOfBirth){
        return ownerRepository.licensesByCity(placeOfBirth);
    }

    @Override
    public List<Owner> findByCity(String placeOfBirth) {
        return ownerRepository.findByCity(placeOfBirth);
    }
}
