package com.example.vehicleRSdemo.Repository;

import com.example.vehicleRSdemo.Pojo.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OwnerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OwnerRepository ownerRepository;

    // FIND ALL TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void whenFindAll_noOwnersPresent_thenReturnsEmptyList() {
        // Act
        List<Owner> owners = ownerRepository.findAll();

        // Assert
        assertThat(owners).isEmpty();
    }

    @Test
    public void whenFindAll_singleOwnerPresent_thenReturnsSingleOwner() {
        // Arrange
        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setLicenseIssueDate(LocalDate.now());
        entityManager.persist(owner);
        entityManager.flush();

        // Act
        List<Owner> owners = ownerRepository.findAll();

        // Assert
        assertThat(owners).hasSize(1);
        assertThat(owners.get(0).getFirstName()).isEqualTo("John");
    }

    @Test
    public void whenFindAll_multipleOwnersPresent_thenReturnsAllOwners() {
        // Arrange
        Owner owner1 = new Owner();
        owner1.setFirstName("John");
        owner1.setLastName("Doe");
        owner1.setLicenseIssueDate(LocalDate.now());
        Owner owner2 = new Owner();
        owner2.setFirstName("Jane");
        owner2.setLastName("Doe");
        owner2.setLicenseIssueDate(LocalDate.now().minusDays(1));
        entityManager.persist(owner1);
        entityManager.persist(owner2);
        entityManager.flush();

        // Act
        List<Owner> owners = ownerRepository.findAll();

        // Assert
        assertThat(owners).hasSize(2);
    }

    // FIND BY ID TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void whenFindById_OwnerExists_thenReturnsOwner() {
        // Arrange
        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setLicenseIssueDate(LocalDate.now());
        entityManager.persist(owner);
        entityManager.flush();
        Integer id = owner.getOwnerId();

        // Act
        Optional<Owner> foundOwner = ownerRepository.findById(id);

        // Assert
        assertThat(foundOwner.isPresent()).isTrue();
        assertThat(foundOwner.get().getOwnerId()).isEqualTo(id);
        assertThat(foundOwner.get().getFirstName()).isEqualTo("John");
        assertThat(foundOwner.get().getLastName()).isEqualTo("Doe");
    }

    @Test
    public void whenFindById_OwnerDoesNotExist_thenReturnsEmpty() {
        // Arrange
        Integer nonExistentId = 999;

        // Act
        Optional<Owner> foundOwner = ownerRepository.findById(nonExistentId);

        // Assert
        assertThat(foundOwner.isPresent()).isFalse();
    }

    // DELETE BY ID TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void whenDeleteById_ExistingOwner_thenOwnerIsDeleted() {
        // Arrange
        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setLicenseIssueDate(LocalDate.now());
        entityManager.persist(owner);
        entityManager.flush();
        Integer id = owner.getOwnerId();

        // Act
        ownerRepository.deleteById(id);
        entityManager.flush();

        // Assert
        Optional<Owner> deletedOwner = ownerRepository.findById(id);
        assertThat(deletedOwner.isPresent()).isFalse();
    }

    @Test
    public void whenDeleteById_NonExistentOwner_thenNoException() {
        assertThrows(EmptyResultDataAccessException.class, () -> ownerRepository.deleteById(-99));
    }

    // FIND OWNER BY VEHICLE TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void whenFindOwnerByVehicle_MatchingOwnersFound_thenReturnsOwners() {
        // Arrange
        Owner owner1 = new Owner();
        owner1.setFirstName("John");
        owner1.setLastName("Doe");
        entityManager.persist(owner1);

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setOwner(owner1);
        vehicle1.setManufacturer("Toyota");
        vehicle1.setModel("Corolla");
        entityManager.persist(vehicle1);

        entityManager.flush();

        // Act
        List<Owner> owners = ownerRepository.findOwnerByVehicle("Toyota", "Corolla");

        // Assert
        assertThat(owners).hasSize(1);
        assertThat(owners.get(0).getFirstName()).isEqualTo("John");
    }

    @Test
    public void whenFindOwnerByVehicle_NoMatchingOwnersFound_thenReturnsEmptyList() {
        // Arrange
        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        entityManager.persist(owner);

        Vehicle vehicle = new Vehicle();
        vehicle.setOwner(owner);
        vehicle.setManufacturer("Honda");
        vehicle.setModel("Civic");
        entityManager.persist(vehicle);

        entityManager.flush();

        // Act
        List<Owner> owners = ownerRepository.findOwnerByVehicle("Toyota", "Corolla");

        // Assert
        assertThat(owners).isEmpty();
    }

    @Test
    public void whenFindOwnerByVehicle_CaseSensitivity_thenReturnsEmptyListIfCaseDoesNotMatch() {
        // Arrange
        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        entityManager.persist(owner);

        Vehicle vehicle = new Vehicle();
        vehicle.setOwner(owner);
        vehicle.setManufacturer("toyota");
        vehicle.setModel("corolla");
        entityManager.persist(vehicle);

        entityManager.flush();

        // Act
        List<Owner> owners = ownerRepository.findOwnerByVehicle("Toyota", "Corolla");

        // Assert
        assertThat(owners).isEmpty();
    }

    // COUNT LICENSES BY CITY TESTS//
    ///////////////////////////////////////////////////
    @Test
    public void whenLicensesByCity_NoLicensedOwners_thenReturnsZero() {
        // Arrange
        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setPlaceOfBirth("Chicago");
        entityManager.persist(owner);
        entityManager.flush();

        // Act
        long count = ownerRepository.licensesByCity("Chicago");

        // Assert
        assertThat(count).isEqualTo(0);
    }

    @Test
    public void whenLicensesByCity_MultipleLicensedOwners_thenReturnsCorrectCount() {
        // Arrange
        Owner owner1 = new Owner();
        owner1.setFirstName("John");
        owner1.setLastName("Doe");
        owner1.setPlaceOfBirth("Chicago");
        owner1.setLicenseIssueDate(LocalDate.now());
        entityManager.persist(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Jane");
        owner2.setLastName("Smith");
        owner2.setPlaceOfBirth("Chicago");
        owner2.setLicenseIssueDate(LocalDate.now().minusDays(1));
        entityManager.persist(owner2);

        entityManager.flush();

        // Act
        long count = ownerRepository.licensesByCity("Chicago");

        // Assert
        assertThat(count).isEqualTo(2);
    }

    @Test
    public void whenLicensesByCity_OwnersPresentButNoLicenses_thenReturnsZero() {
        // Arrange
        Owner owner1 = new Owner();
        owner1.setFirstName("John");
        owner1.setLastName("Doe");
        owner1.setPlaceOfBirth("Chicago");
        entityManager.persist(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Jane");
        owner2.setLastName("Smith");
        owner2.setPlaceOfBirth("Chicago");
        entityManager.persist(owner2);

        entityManager.flush();

        // Act
        long count = ownerRepository.licensesByCity("Chicago");

        // Assert
        assertThat(count).isEqualTo(0);
    }

    // FIND BY CITY TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void whenFindByCity_NoOwnersFromCity_thenReturnsEmptyList() {
        // Arrange
        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setPlaceOfBirth("New York");
        entityManager.persist(owner);
        entityManager.flush();

        // Act
        List<Owner> owners = ownerRepository.findByCity("Chicago");

        // Assert
        assertThat(owners).isEmpty();
    }

    @Test
    public void whenFindByCity_MultipleOwnersFromCity_thenReturnsOwners() {
        // Arrange
        Owner owner1 = new Owner();
        owner1.setFirstName("John");
        owner1.setLastName("Doe");
        owner1.setPlaceOfBirth("Chicago");
        entityManager.persist(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Jane");
        owner2.setLastName("Smith");
        owner2.setPlaceOfBirth("Chicago");
        entityManager.persist(owner2);

        entityManager.flush();

        // Act
        List<Owner> owners = ownerRepository.findByCity("Chicago");

        // Assert
        assertThat(owners).hasSize(2);
        assertThat(owners.get(0).getPlaceOfBirth()).isEqualTo("Chicago");
        assertThat(owners.get(1).getPlaceOfBirth()).isEqualTo("Chicago");
    }

    @Test
    public void whenFindByCity_CaseSensitivity_thenReturnsEmptyListIfCaseDoesNotMatch() {
        // Arrange
        Owner owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setPlaceOfBirth("chicago");
        entityManager.persist(owner);
        entityManager.flush();

        // Act
        List<Owner> owners = ownerRepository.findByCity("Chicago");

        // Assert
        assertThat(owners).isEmpty();
    }
}