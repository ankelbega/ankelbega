package com.sda.carrental.repositories;

import com.sda.carrental.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("Select c from Car c where c.branch.id is not null")
    List<Car> findAllCarsAssignedToBranch();


    @Query(value = "select c.BRAND  , count( c.CAR_ID) as numberOfCar \n" +
            "from car c  inner join RESERVATION  r on c.CAR_ID = r.CAR_ID\n" +
            "group by c.BRAND \n" +
            "order by numberOfCar  desc\n" +
            "limit 1 ;", nativeQuery = true)
    List<Object[]> countMaxBrandByReservationsMade();

    @Query(value = "select c.BRAND , b.ADDRESS, c.mileage as MaxMileage\n" +
            "from car c  inner join branch b on c.BRANCH_ID = b.BRANCH_ID \n" +
            "order by c.MILEAGE desc\n" +
            "limit 1 ;", nativeQuery = true)
    List<Object[]> maxMileageOfCarInRental();

    @Query(value = "select count(c.CAR_ID) as numOfCar, b.ADDRESS\n" +
            "from  car c  inner join BRANCH b on b.BRANCH_ID =c.BRANCH_ID \n" +
            "group by b.ADDRESS;", nativeQuery = true)
    List<Object[]> countCarInBranch();

    @Query(value = "select c.*\n" +
            "from car c inner join branch b on c.branch_id=b.branch_id\n" +
            "where c.branch_id = :branch_id", nativeQuery = true)
    List<Car> selectCarsWithGivenBranchId(@Param("branch_id") final Long branch_id);


    List<Car> findByBrandAndYearAndColor(final String brand, final int year, final String color);

    @Query(value = "select distinct (c.brand) from Car c")
    List<String> getCarsBrands();

    @Query(value = "select distinct (c.color) from Car c")
    List<String> getCarsColors();

    @Query(value = "select distinct (c.year) from Car c")
    List<Integer> getCarsYears();

    @Query(value = "SELECT c from Car c inner join c.carStatuses cs " +
            "inner join cs.status s " +
            "WHERE cs.date =:date and s.status like 'available' ")
    List<Car> findAvailableCarsAtGivenTime(@Param("date") final LocalDate date);
}
