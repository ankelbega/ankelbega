package com.sda.carrental.controllers;


import com.sda.carrental.entities.Revenue;
import com.sda.carrental.entities.Role;
import com.sda.carrental.entities.User;
import com.sda.carrental.repositories.RevenueRepository;
import com.sda.carrental.services.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Controller
public class RevenueController {

    @Autowired
    private RevenueService revenueService;

    @Autowired
    private RevenueRepository revenueRepository;

    @GetMapping("/revenue")
    public String showRevenueList(Model model) {
        List<Revenue> revenues = revenueRepository.findAll();
        model.addAttribute("revenues", revenues);
        return "revenue-rental";
    }

    @PostMapping("/update/revenue/{rentalId}")
    public String updateRevenueAmount(@PathVariable("rentalId") Long rentalId, Model model) {
        Revenue revenue = this.revenueService.updateAmountOfRevenue(rentalId);
        model.addAttribute("revenue", revenue);
        return "revenue-rental";
    }

    @GetMapping("/cancel/reservation/{reservationId}")
    public String updateRevenueByCancelReservation(@PathVariable("reservationId") final Long reservationId,
                                                   final Model model) {
        this.revenueService.updateTheRevenueAboutCancelReservation(reservationId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Role> roles = (List<Role>) authentication.getAuthorities();
        boolean isAdmin = false;
        return isAdmin ? "redirect:/reservation" : "redirect:/user/reservation";
    }
}
