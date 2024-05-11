package com.example.vehicleRSdemo.Repository;

import com.example.vehicleRSdemo.Pojo.Vehicle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle, Integer> {

    List<Vehicle> findAll();

    Optional<Vehicle> findById(Integer id);

    void deleteById(Integer id);

    @Query(
            value = "SELECT * " +
                    "FROM vehicle v, owner o " +
                    "WHERE v.user_id = o.ownerid " +
                    "AND v.user_id = :ownerId",
            nativeQuery = true)
    List<Vehicle> findAllByOwner(@Param("ownerId") Integer ownerId);


    @Query(
            value = "SELECT * " +
                    "FROM vehicle v " +
                    "WHERE v.year = :year ",
            nativeQuery = true)
    List<Vehicle> findByYear(Integer year);

    @Query(
            value = "SELECT * " +
                    "FROM vehicle v " +
                    "WHERE v.power > :power ",
            nativeQuery = true)
    List<Vehicle> findByHorsepower(Integer power);

    @Query(
            value = "SELECT * " +
                    "FROM vehicle v " +
                    "WHERE v.fuel = :fuel ",
            nativeQuery = true)
    List<Vehicle> findByFuelType(String fuel);

    @Query(
            value = "SELECT * " +
                    "FROM vehicle v " +
                    "WHERE v.manufacturer = :manufacturer ",
            nativeQuery = true)
    List<Vehicle> findVehicleByBrand (@Param("manufacturer") String manufacturer);

    @Query(
            value = "SELECT COUNT(*) " +
                    "FROM vehicle v " +
                    "WHERE v.manufacturer = :manufacturer ",
            nativeQuery = true)
    long countByBrand (@Param("manufacturer") String manufacturer);

    @Query(
            value = "SELECT COUNT(*) " +
                    "FROM vehicle v " +
                    "WHERE v.expirationdate < CURRENT_DATE",
            nativeQuery = true)
    long countUnregistered ();

    @Query(
            value = "SELECT COUNT(*) " +
                    "FROM vehicle v " +
                    "WHERE v.expirationdate > CURRENT_DATE",
            nativeQuery = true)
    long countRegistered ();

    @Query(
            value = "SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END " +
                    "FROM vehicle v " +
                    "WHERE v.expirationdate > CURRENT_DATE " +
                    "AND v.vehicleid = :id",
            nativeQuery = true)
    boolean checkRegistration(@Param("id") Integer id);


    @Query(
            value = "SELECT COUNT(*) " +
                    "FROM vehicle v " +
                    "WHERE v.transmission = :transmission ",
            nativeQuery = true)
    long countTransmission(@Param("transmission") String transmission);

    @Query(
            value = "SELECT * " +
                    "FROM vehicle v " +
                    "WHERE v.licenseplate = :licenseplate ",
            nativeQuery = true)
    Vehicle findByLicensePlate(@Param("licenseplate")String licenseplate);
}
