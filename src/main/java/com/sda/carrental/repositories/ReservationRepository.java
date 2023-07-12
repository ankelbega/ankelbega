package com.sda.carrental.repositories;


import com.sda.carrental.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = "SELECT sum(r.amount_of_reservation) FROM  reservation  r, car c , branch b, rental re\n" +
            "left join revenue rev on re.rental_id=rev.rental_id\n" +
            "where r.car_id=c.car_id\n" +
            "and b.branch_id=c.branch_id\n" +
            "and b.rental_id=re.rental_id\n" +
            "and re.rental_id= :rental_id", nativeQuery = true)
    Double getTotalAmount(@Param("rental_id") final Long rental_id);

    @Query(value = "select re.rental_id \n" +
            "from reservation r\n" +
            "inner join car c\n" +
            "on c.car_id = r.car_id\n" +
            "inner join branch b\n" +
            "on b.branch_id = c.branch_id\n" +
            "inner join rental re\n" +
            "on re.rental_id=b.rental_id\n" +
            "where r.reservation_id= :reservation_id", nativeQuery = true)
    Long getRentalByReservationId(@Param("reservation_id") final Long reservation_id);

    @Query(value = "SELECT r.*" +
            " from Reservation r; ", nativeQuery = true)
    List<Reservation> getAllReservation();

    @Query(value = "select b.ADDRESS,count(r.RESERVATION_ID) as noOfReservation \n" +
            "from reservation r inner join car c on r.CAR_ID = c.CAR_ID inner join branch b on c.BRANCH_ID = b.BRANCH_ID \n" +
            "where r.RETURN_BRANCH_ID = c.BRANCH_ID \n" +
            "group by b.ADDRESS ;", nativeQuery = true)
    List<Object[]> countReservationForEachBranch();

    @Query(value = " select r.BOOKING_DATE,count(c.CAR_ID) \n" +
            "from RESERVATION r inner join car c on c.CAR_ID = r.CAR_ID inner join BRANCH b on b.BRANCH_ID =c.BRANCH_ID \n" +
            "group by r.BOOKING_DATE;", nativeQuery = true)
    List<Object[]> countReservationByBookingDate();

    @Query(value = "select r.*\n" +
            "from reservation r inner join branch b on b.branch_id=r.return_branch_id\n" +
            "where return_branch_id = :branch_id", nativeQuery = true)
    List<Reservation> selectReservationWithGivenReturnBranchId(@Param("branch_id") final Long branch_id);


    @Query(value = "SELECT r.*" +
            " from Reservation r" +
            " where r.customer_id= :customer_id", nativeQuery = true)
    List<Reservation> findByCustomerId(@Param("customer_id") final Long customerId);
}
