package com.sda.carrental.controllers;

import com.sda.carrental.entities.Branch;
import com.sda.carrental.entities.Rental;
import com.sda.carrental.models.pojo.BranchRequestParam;
import com.sda.carrental.repositories.BranchRepository;
import com.sda.carrental.repositories.RentalRepository;
import com.sda.carrental.services.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class BranchController {

    @Autowired
    private BranchService branchService;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @PostMapping("/branch")
    public String createBranch(@Valid final BranchRequestParam branchRequestParam, BindingResult result, final Model model) {
        if (result.hasErrors()) {
            List<Rental> rentals = this.rentalRepository.findAll();
            model.addAttribute("rentals", rentals);
            return "add-branch";
        }
        Branch branch = this.branchService.saveTheBranch(branchRequestParam);
        model.addAttribute("branch", branch);
        return "redirect:/branches";
    }

    @GetMapping("/delete/{branchId}")
    public String deleteBranch(@PathVariable("branchId") final Long branchId, final Model model) {
        this.branchService.deleteBranchById(branchId);
        return "redirect:/branches";
    }

    @GetMapping("/add-branch")
    public String showAddBranch(final BranchRequestParam branchRequestParam, final Model model) {
        List<Rental> rentals = this.rentalRepository.findAll();
        model.addAttribute("rentals", rentals);
        return "add-branch";
    }

    @GetMapping("/branches")
    public String getBranches(final Model model) {
        List<Branch> branches = this.branchService.getAllBranches();
        model.addAttribute("branches", branches);
        return "branch-list";
    }

}
