package com.sda.carrental.controllers;


import com.sda.carrental.entities.Rental;
import com.sda.carrental.models.pojo.RentalRequestParam;
import com.sda.carrental.repositories.RentalRepository;
import com.sda.carrental.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private RentalRepository rentalRepository;

    @PostMapping("/rental/{rentalId}")
    public String updateRental(final RentalRequestParam rentalRequestParam, final Model model, @PathVariable("rentalId") final Long rentalId) {
        Rental rental = this.rentalService.updateTheRental(rentalRequestParam, rentalId);
        model.addAttribute("rental", rental);
        return "redirect:/rental";
    }
    @GetMapping("/edit/{id}")
    public String editRental(@PathVariable("id") long id, Model model) {
        Rental rental = rentalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rental Id:" + id));
        model.addAttribute("rental", rental);
        return "edit-rental";
    }
    @GetMapping("/signup")
    public String showSignUpForm(RentalRequestParam rentalRequestParam) {
        return "add-rental";
    }

    @GetMapping("/rental")
    public String showRentalList(Model model) {
        List<Rental> rentals = rentalRepository.findAll();
        model.addAttribute("rentals", rentals);
        return "rental-list";
    }
    @PostMapping("/add/rental")
    public String addRental(final RentalRequestParam rentalRequestParam, final Model model) {
        Rental rental = this.rentalService.saveTheRental(rentalRequestParam);
        model.addAttribute("rental", rental);
        return "redirect:/rental";
    }

}
