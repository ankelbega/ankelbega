package com.sda.carrental.repositories;

import com.sda.carrental.entities.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {

    @Query(value = "select rv.revenue_id\n" +
            "    from rental r inner join revenue rv on r.rental_id=rv.rental_id\n" +
            "    where r.rental_id=:rental_id", nativeQuery = true)
    Long getRevenueByRentalId(@Param("rental_id") final Long rental_id);
}
