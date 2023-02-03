package com.example.vehicleRSdemo.Service;

import com.example.vehicleRSdemo.Pojo.Gender;
import com.example.vehicleRSdemo.Pojo.Owner;
import com.example.vehicleRSdemo.Repository.OwnerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private DefaultOwnerService ownerService;

    @Test
    public void OwnerService_GetAllOwners_ReturnsOwner() {
        //Arrange
        Owner owner1 = new Owner(7, "Antigona", "Saniu",
                LocalDate.of(1998, 01, 01), "Skopje", Gender.Female, LocalDate.of(2018, 01, 01));

        Owner owner2 = new Owner(8,"Ola", "Berati",
                LocalDate.of(1998, 01, 01), "Skopje", Gender.Female,LocalDate.of(2018, 01, 01));

        //Act
        List<Owner> owners = new ArrayList<>();
        owners.add(owner1);
        owners.add(owner2);
        when(ownerRepository.findAll()).thenReturn(owners);
        ownerService = new DefaultOwnerService(ownerRepository);
        List<Owner> allOwners = ownerService.findAll();

        //Assert
        Assert.assertNotNull(allOwners);
        Assert.assertTrue("True",allOwners.contains(owner1));
    }


    @ParameterizedTest
    @ValueSource(strings = {"Gostivar","Tetovo","Skopje"})
    public void OwnerService_findOwnersByCity_ReturnsOwners(String city) {
        //Arrange
        Owner owner1 = new Owner(9, "Nikola", "Dimitrievski",
                LocalDate.of(1998, 01, 01), "Gostivar", Gender.Male, LocalDate.of(2017, 01, 01));

        Owner owner2 = new Owner(10,"Maria", "Bogata",
                LocalDate.of(1998, 01, 01), "Skopje", Gender.Female,LocalDate.of(2018, 01, 01));

        //Act
        List<Owner> owners = new ArrayList<>();
        owners.add(owner1);
        owners.add(owner2);
        Mockito.when(ownerRepository.findByCity(anyString())).thenReturn( owners.stream().filter(owner -> owner.getPlaceOfBirth()==(city)).collect(Collectors.toList()));
        ownerService = new DefaultOwnerService(ownerRepository);
        List<Owner> ownersByCity = ownerService.findByCity(city);

        //Assert
        Assert.assertNotNull(ownersByCity);
    }
}