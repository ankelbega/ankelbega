package com.sda.carrental.controllers;

import com.sda.carrental.entities.Branch;
import com.sda.carrental.entities.Reservation;
import com.sda.carrental.entities.User;
import com.sda.carrental.models.pojo.ReservationRequestParam;
import com.sda.carrental.repositories.BranchRepository;
import com.sda.carrental.repositories.RentalRepository;
import com.sda.carrental.repositories.ReservationRepository;
import com.sda.carrental.services.ReservationService;
import com.sda.carrental.services.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ReservationController {

    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private RevenueService revenueService;

    @PostMapping("/add-reservation")
    public String postReservation(final ReservationRequestParam reservationRequestParam, final Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        reservationRequestParam.setCustomerId(loggedInUser.getId());
        Reservation reservation = this.reservationService.saveTheReservation(reservationRequestParam);
        Long rentalId = reservationRepository.getRentalByReservationId(reservation.getId());
        revenueService.updateAmountOfRevenue(rentalId);
        model.addAttribute("reservation", reservation);
        return "redirect:/cars?successInserted";
    }

    @GetMapping("/show/signup/{carId}")
    public String showSignUpForm(@PathVariable("carId") final Long carId, Model model) {
        ReservationRequestParam reservationRequestParam = new ReservationRequestParam();
        reservationRequestParam.setCarId(carId);
        List<Branch> branches = this.branchRepository.findAll();
        model.addAttribute("branches", branches);
        model.addAttribute("reservationRequestParam", reservationRequestParam);
        return "add-reservation";
    }

    @GetMapping("/delete/reservation/{reservationId}")
    public String deleteReservation(@PathVariable("reservationId") final Long reservationId, Model model) {
        this.reservationService.deleteReservationById(reservationId);
        model.addAttribute("reservations", reservationRepository.findAll());
        return "redirect:/reservations?success";
    }


    @GetMapping("/edit/reservation/{id}")
    public String editReservation(@PathVariable("id") long id, Model model) {
        Reservation cancelReservation = reservationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid reservation Id:" + id));
        model.addAttribute("cancelReservation", cancelReservation);
        return "cancel-reservation";
    }

    @GetMapping("/reservations")
    public String getAllReservation(Model model) {
        List<Reservation> reservations = reservationRepository.findAll();
        model.addAttribute("reservations", reservations);
        return "reservation-list";
    }

    @GetMapping("/user/reservation")
    public String getUserReservation(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Reservation> userReservations = reservationRepository.findByCustomerId(loggedInUser.getId());
        model.addAttribute("reservations", userReservations);
        return "reservation-list";
    }
}
