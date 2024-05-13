package com.example.vehicleRSdemo.Repository;

import com.example.vehicleRSdemo.Pojo.Insurance;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InsuranceRepository extends CrudRepository<Insurance, Integer> {

    List<Insurance> findAll();

    Optional<Insurance> findById(Integer id);

    void deleteById(Integer id);

    @Query(
            value = "SELECT COUNT(*) " +
                    "FROM insurance i ",
            nativeQuery = true)
    long countInsurance();
}
