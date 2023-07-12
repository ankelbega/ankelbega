package com.sda.carrental.repositories;

import com.sda.carrental.entities.CarStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarStatusRepository extends JpaRepository<CarStatus, Long> {

    List<CarStatus> findByCarId(final Long carId);

    Optional<CarStatus> findByCarIdAndDate(final Long carId, final LocalDate date);


}
