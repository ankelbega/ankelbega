package com.sda.carrental.repositories;

import com.sda.carrental.entities.Refund;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {

    @Query(value = "SELECT SUM(rf.amount_surcharge)\n" +
            "from Refund rf inner join reservation r on rf.reservation_id=r.reservation_id\n" +
            "inner join car c on c.car_id = r.car_id\n" +
            "inner join branch b on b.branch_id = c.branch_id\n" +
            "inner join rental re on re.rental_id=b.rental_id\n" +
            "where re.rental_id= :rental_id", nativeQuery = true)
    Double getTotalAmountSurcharge(@Param("rental_id") final Long rental_id);

    //ne mom qe ska surcharge me jep null kur dua te bej update pasi i kalohet revenues per total.

}
