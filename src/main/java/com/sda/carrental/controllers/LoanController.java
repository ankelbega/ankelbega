package com.sda.carrental.controllers;

import com.sda.carrental.entities.Loan;
import com.sda.carrental.models.pojo.LoanRequestParam;
import com.sda.carrental.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class LoanController {


    @Autowired
    private LoanService loanService;

    @PostMapping("/loan")
    public ResponseEntity<Loan> postLoan(@RequestBody final LoanRequestParam loanRequestParam) {
        Loan loan = this.loanService.saveTheLoan(loanRequestParam);
        return ResponseEntity.ok(loan);
    }


}
