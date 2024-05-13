package com.example.vehicleRSdemo.Repository;

import com.example.vehicleRSdemo.Pojo.Owner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnerRepository extends CrudRepository<Owner, Integer> {


    List<Owner> findAll();

    Optional<Owner> findById(Integer ownerId);

    void deleteById(Integer id);

    @Query(
            value = "SELECT * " +
                    "FROM owner o, vehicle v " +
                    "WHERE v.user_id = o.ownerid " +
                    "AND v.manufacturer = :manufacturer " +
                    "AND v.model = :model ",
            nativeQuery = true)
    List<Owner> findOwnerByVehicle(String manufacturer, String model);

    @Query(
            value = "SELECT COUNT(*) " +
                    "FROM owner o " +
                    "WHERE o.placeofbirth = :placeofbirth " +
                    "AND o.licensedate IS NOT NULL ",
            nativeQuery = true)
    long licensesByCity(@Param("placeofbirth") String placeofbirth);

    @Query(
            value = "SELECT * " +
                    "FROM owner o " +
                    "WHERE o.placeofbirth = :placeofbirth ",
            nativeQuery = true)
    List<Owner> findByCity(@Param("placeofbirth") String placeofbirth);
}
