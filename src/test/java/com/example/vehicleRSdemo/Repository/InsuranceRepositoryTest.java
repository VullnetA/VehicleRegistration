package com.example.vehicleRSdemo.Repository;

import com.example.vehicleRSdemo.Pojo.Insurance;
import com.example.vehicleRSdemo.Pojo.InsuranceCompany;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
class InsuranceRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private InsuranceRepository insuranceRepository;

    // FIND ALL TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void whenFindAll_noInsurancesPresent_thenReturnsEmptyList() {
        // Act
        List<Insurance> insurances = insuranceRepository.findAll();

        // Assert
        assertThat(insurances).isEmpty();
    }

    @Test
    public void whenFindAll_singleInsurancePresent_thenReturnsSingleInsurance() {
        // Arrange
        Insurance insurance = new Insurance();
        insurance.setInsuranceCompany(InsuranceCompany.AKTIVA);
        insurance.setInsuranceFee(2000);
        entityManager.persist(insurance);
        entityManager.flush();

        // Act
        List<Insurance> insurances = insuranceRepository.findAll();

        // Assert
        assertThat(insurances).hasSize(1);
        assertThat(insurances.get(0).getInsuranceCompany()).isEqualTo(InsuranceCompany.AKTIVA);
    }

    @Test
    public void whenFindAll_multipleInsurancesPresent_thenReturnsAllInsurances() {
        // Arrange
        Insurance insurance1 = new Insurance();
        insurance1.setInsuranceCompany(InsuranceCompany.AKTIVA);
        insurance1.setInsuranceFee(2000);
        entityManager.persist(insurance1);

        Insurance insurance2 = new Insurance();
        insurance2.setInsuranceCompany(InsuranceCompany.SAVA);
        insurance2.setInsuranceFee(3000);
        entityManager.persist(insurance2);

        entityManager.flush();

        // Act
        List<Insurance> insurances = insuranceRepository.findAll();

        // Assert
        assertThat(insurances).hasSize(2);
    }

    // FIND BY ID TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void whenFindById_InsuranceExists_thenReturnsInsurance() {
        // Arrange
        Insurance insurance = new Insurance();
        insurance.setInsuranceCompany(InsuranceCompany.AKTIVA);
        insurance.setInsuranceFee(2000);
        entityManager.persist(insurance);
        entityManager.flush();
        Integer id = insurance.getId();

        // Act
        Optional<Insurance> foundInsurance = insuranceRepository.findById(id);

        // Assert
        assertThat(foundInsurance.isPresent()).isTrue();
        assertThat(foundInsurance.get().getId()).isEqualTo(id);
    }

    @Test
    public void whenFindById_InsuranceDoesNotExist_thenReturnsEmpty() {
        // Arrange
        Integer nonExistentId = 999;

        // Act
        Optional<Insurance> foundInsurance = insuranceRepository.findById(nonExistentId);

        // Assert
        assertThat(foundInsurance.isPresent()).isFalse();
    }

    // DELETE BY ID TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void whenDeleteById_ExistingInsurance_thenInsuranceIsDeleted() {
        // Arrange
        Insurance insurance = new Insurance();
        insurance.setInsuranceCompany(InsuranceCompany.AKTIVA);
        insurance.setInsuranceFee(2000);
        entityManager.persist(insurance);
        entityManager.flush();
        Integer id = insurance.getId();

        // Act
        insuranceRepository.deleteById(id);
        entityManager.flush();

        // Assert
        Optional<Insurance> deletedInsurance = insuranceRepository.findById(id);
        assertThat(deletedInsurance.isPresent()).isFalse();
    }

    @Test
    public void whenDeleteById_NonExistentOwner_thenNoException() {
        assertThrows(EmptyResultDataAccessException.class, () -> insuranceRepository.deleteById(-99));
    }

    // COUNT INSURANCES TESTS //
    ///////////////////////////////////////////////////
    @Test
    public void whenCountInsurance_NoInsurances_thenCountIsZero() {
        // Act
        long count = insuranceRepository.countInsurance();

        // Assert
        assertThat(count).isEqualTo(0);
    }

    @Test
    public void whenCountInsurance_SingleInsurance_thenCountIsOne() {
        // Arrange
        Insurance insurance = new Insurance();
        insurance.setInsuranceCompany(InsuranceCompany.AKTIVA);
        insurance.setInsuranceFee(2000);
        entityManager.persist(insurance);
        entityManager.flush();

        // Act
        long count = insuranceRepository.countInsurance();

        // Assert
        assertThat(count).isEqualTo(1);
    }

    @Test
    public void whenCountInsurance_MultipleInsurances_thenCorrectCount() {
        // Arrange
        Insurance insurance1 = new Insurance();
        insurance1.setInsuranceCompany(InsuranceCompany.AKTIVA);
        insurance1.setInsuranceFee(2000);
        entityManager.persist(insurance1);

        Insurance insurance2 = new Insurance();
        insurance2.setInsuranceCompany(InsuranceCompany.UNIQA);
        insurance2.setInsuranceFee(1000);
        entityManager.persist(insurance2);

        entityManager.flush();

        // Act
        long count = insuranceRepository.countInsurance();

        // Assert
        assertThat(count).isEqualTo(2);
    }
}