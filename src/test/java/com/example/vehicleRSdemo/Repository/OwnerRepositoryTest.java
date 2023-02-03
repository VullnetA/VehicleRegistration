package com.example.vehicleRSdemo.Repository;

import com.example.vehicleRSdemo.Pojo.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.Assert;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class OwnerRepositoryTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    public void OwnerRepository_SaveAll_ReturnSavedOwner(){
        //Arrange
        Owner owner = new Owner(1, "Armend", "Adili",
                LocalDate.of(2002, 01, 01), "Skopje", Gender.Male, LocalDate.of(2020, 01, 01));

        //Act
        Owner savedOwners = ownerRepository.save(owner);

        //Assert
        Assert.assertNotNull(savedOwners);
        Assert.assertEquals(Optional.of(savedOwners.getOwnerId()), Optional.of(1));
    };

    @Test
    public void OwnerRepository_GetAll_ReturnMoreThanOneOwner(){
        //Arrange
        Owner owner1 = new Owner(2, "Driton", "Jashari",
                LocalDate.of(2000, 01, 01), "Tetovo", Gender.Male, LocalDate.of(2018, 01, 01));
        Owner owner2 = new Owner(3, "Femi", "Meliku",
                LocalDate.of(1999, 01, 01), "Gostivar", Gender.Male, LocalDate.of(2018, 01, 01));

        //Act
        ownerRepository.save(owner1);
        ownerRepository.save(owner2);

        List<Owner> ownerList = ownerRepository.findAll();

        //Assert
        Assert.assertNotNull(ownerList);
        Assert.assertEquals(ownerList.isEmpty(), false);
    }

    @Test
    public void OwnerRepository_FindById_ReturnOwner(){
        //Arrange
        Owner owner1 = new Owner(4, "Mateo", "Maricic",
                LocalDate.of(2001, 01, 01), "Ohrid", Gender.Male, LocalDate.of(2019, 01, 01));

        //Act
        ownerRepository.save(owner1);
        Owner ownerList = ownerRepository.findById(owner1.getOwnerId()).get();

        //Assert
        Assert.assertNotNull(ownerList);
    }

    @Test
    public void OwnerRepository_FindByCity_ReturnOwnerNotNull(){
        //Arrange
        Owner owner1 = new Owner(5, "Petrit", "Shaqiri",
                LocalDate.of(2000, 01, 01), "Kumanovo", Gender.Male, LocalDate.of(2018, 01, 01));

        //Act
        ownerRepository.save(owner1);

        List<Owner> ownerList = ownerRepository.findByCity(owner1.getPlaceOfBirth());

        //Assert
        Assert.assertNotNull(ownerList);
    }

    @Test
    public void OwnerRepository_OwnerDelete_ReturnOwnerisEmpty(){
        //Arrange
        Owner owner1 = new Owner(6, "Ana", "Shtegu",
                LocalDate.of(2000, 01, 01), "Debar", Gender.Male, LocalDate.of(2018, 01, 01));

        //Act
        ownerRepository.save(owner1);
        ownerRepository.delete(owner1);
        Optional<Owner> ownerReturn = ownerRepository.findById(owner1.getOwnerId());

        //Assert
        Assert.assertNotNull(ownerReturn);
    }
}