package com.example.vehicleRSdemo.Repository;

import com.example.vehicleRSdemo.Pojo.Insurance;
import com.example.vehicleRSdemo.Pojo.Vehicle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsuranceRepository extends CrudRepository<Insurance, Integer> {

    List<Insurance> findAll();

    void deleteById(Integer id);

    Insurance findInsuranceById(Integer id);

    @Query(
            value = "SELECT COUNT(*) " +
                    "FROM insurance i ",
            nativeQuery = true)
    long countInsurance();

    @Query(
            value = "SELECT * " +
                    "FROM vehicle v, insurance i " +
                    "WHERE v.vehicleid = i.vehicle_id " +
                    "AND i.expirationdate < CURRENT_DATE",
            nativeQuery = true)
    List<Vehicle> findExpiredInsurance();
}
