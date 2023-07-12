package com.sda.carrental.services;

import com.sda.carrental.entities.Rental;
import com.sda.carrental.entities.Reservation;
import com.sda.carrental.entities.Revenue;
import com.sda.carrental.errors.NotFoundException;
import com.sda.carrental.repositories.RentalRepository;
import com.sda.carrental.repositories.ReservationRepository;
import com.sda.carrental.repositories.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;


import static java.time.temporal.ChronoUnit.DAYS;


@Service
public class RevenueService {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RevenueRepository revenueRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RentalService rentalService;

    @Autowired
    private RefundService refundService;

    @Autowired
    private RentalRepository rentalRepository;


    public Revenue saveTheRevenue(final Long rentalId) {
        Revenue revenue = new Revenue();
        Rental rental = rentalService.readRentalById(rentalId);
        revenue.setRental(rental);
        Double totalAmountSurcharge = refundService.getTotalAmountSurcharge(rentalId);
        if (totalAmountSurcharge != null) {
            revenue.setTotalAmount(reservationService.totalAmountOfReservation(rentalId) + totalAmountSurcharge);
        } else {
            revenue.setTotalAmount(reservationService.totalAmountOfReservation(rentalId));
        }
        return this.revenueRepository.save(revenue);
    }

    public Revenue updateAmountOfRevenue(final Long rentalId) {
        Long revenueId = revenueRepository.getRevenueByRentalId(rentalId);
        Revenue revenue = revenueRepository.findById(revenueId).orElseThrow(() ->
                new NotFoundException("Revenue not found with such id!"));
        Double totalAmountSurcharge = refundService.getTotalAmountSurcharge(rentalId);
        if (totalAmountSurcharge != null) {
            revenue.setTotalAmount(reservationService.totalAmountOfReservation(rentalId) + totalAmountSurcharge);
        } else {
            revenue.setTotalAmount(reservationService.totalAmountOfReservation(rentalId));
        }
        revenueRepository.save(revenue);

        return revenue;
    }

    public Revenue updateTheRevenueAboutCancelReservation(Long reservationId) {
        Long rentalId = reservationRepository.getRentalByReservationId(reservationId);
        Long revenueId = revenueRepository.getRevenueByRentalId(rentalId);
        Revenue revenue = revenueRepository.findById(revenueId).orElseThrow(() ->
                new NotFoundException("Revenue not found with such id!"));
        double total = revenue.getTotalAmount();
        Reservation reservation = reservationService.readReservationById(reservationId);
        double amountOfReservation = reservation.getAmountOfReservation();
        LocalDate pickupDate = reservation.getPickupDate();
        long countDays = DAYS.between(pickupDate, LocalDate.now());
        if (countDays > 2) {
            double discountedPrice = amountOfReservation - (80 * amountOfReservation) / 100.0;
            revenue.setTotalAmount(total - discountedPrice);
        } else {
            revenue.setTotalAmount(total - amountOfReservation);
        }
        this.reservationRepository.delete(reservation);
        revenueRepository.save(revenue);
        return revenue;
    }
}
