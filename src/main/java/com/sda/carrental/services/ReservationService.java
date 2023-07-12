package com.sda.carrental.services;

import com.sda.carrental.entities.*;
import com.sda.carrental.errors.NotFoundException;
import com.sda.carrental.models.pojo.statistic.ReservationCountBookingDateResponseParam;
import com.sda.carrental.models.pojo.statistic.ReservationCountBranchResponseParam;
import com.sda.carrental.models.pojo.ReservationRequestParam;
import com.sda.carrental.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class ReservationService {


    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private RentalRepository rentalRepository;


    public Reservation saveTheReservation(final ReservationRequestParam reservationRequestParam) {
        Reservation reservation = new Reservation();
        User user = this.userRepository.findById(reservationRequestParam.getCustomerId()).orElseThrow(() ->
                new NotFoundException("Customer not found with such id!")
        );
        Car car = this.carRepository.findById(reservationRequestParam.getCarId()).orElseThrow(() ->
                new NotFoundException("Car not found with such id!"));
        Branch returnBranch = this.branchRepository.findById(reservationRequestParam.getReturnBranchId()).orElseThrow(() ->
                new NotFoundException("Branch not found with such id!")
        );
        reservation.setBookingDate(LocalDate.now());
        reservation.setPickupDate(reservationRequestParam.getPickupDate());
        reservation.setReturnDate(reservationRequestParam.getReturnDate());
        reservation.setCar(car);
        reservation.setReturnBranch(returnBranch);
        reservation.setUser(user);
        this.reservationRepository.save(reservation);
        getAmountForEachReservation(reservationRequestParam, reservation, car, returnBranch);
        return reservation;
    }

    public double totalAmountOfReservation(final Long rentalId) {
        Rental rental = this.rentalRepository.findById(rentalId).orElseThrow(() ->
                new NotFoundException("Rental not found with such id!")
        );
        Double totalAmount = this.reservationRepository.getTotalAmount(rental.getId());
        return totalAmount;
    }

    private void getAmountForEachReservation(ReservationRequestParam reservationRequestParam, Reservation reservation, Car car, Branch returnBranch) {
        LocalDate pickupDate = reservationRequestParam.getPickupDate();
        LocalDate returnDate = reservationRequestParam.getReturnDate();
        long countDays = DAYS.between(pickupDate, returnDate);
        Branch branch = car.getBranch();
        Long branchId = branch.getId();
        Long returnBranchId = returnBranch.getId();
        if (branchId == returnBranchId) {
            reservation.setAmountOfReservation(countDays * car.getAmount());
        } else {
            reservation.setAmountOfReservation(countDays * car.getAmount() + 200);
        }
    }

    public Reservation readReservationById(final Long reservationId) {
        return this.reservationRepository.findById(reservationId).orElseThrow(() ->
                new NotFoundException("Reservation not found with such id!"));
    }

    public void deleteReservationById(final Long reservationId) {
        Reservation reservation = this.reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Reservation not found with such id!"));
        this.reservationRepository.delete(reservation);
    }

    public List<ReservationCountBranchResponseParam> countReservationByBranch() {
        List<Object[]> reservationsMade = this.reservationRepository.countReservationForEachBranch();
        List<ReservationCountBranchResponseParam> responseParams = reservationsMade.stream().map((obj) -> {
            return new ReservationCountBranchResponseParam((String) obj[0], (BigInteger) obj[1]);
        }).collect(Collectors.toList());
        return responseParams;
    }

    public List<ReservationCountBookingDateResponseParam> countReservationByBookingDate() {
        List<Object[]> reservationsBooking = this.reservationRepository.countReservationByBookingDate();
        List<ReservationCountBookingDateResponseParam> responseParams = reservationsBooking.stream().map((obj) -> {
            return new ReservationCountBookingDateResponseParam((Date) obj[0], (BigInteger) obj[1]);
        }).collect(Collectors.toList());
        return responseParams;
    }

    public List<Reservation> allReservationInRental() {
        List<Reservation> allReservation = this.reservationRepository.getAllReservation();
        return allReservation;
    }

}
