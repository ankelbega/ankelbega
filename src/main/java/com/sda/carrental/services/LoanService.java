package com.sda.carrental.services;

import com.sda.carrental.entities.*;
import com.sda.carrental.errors.NotFoundException;
import com.sda.carrental.models.pojo.LoanRequestParam;
import com.sda.carrental.repositories.LoanRepository;
import com.sda.carrental.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserRepository userRepository;


    public Loan saveTheLoan(final LoanRequestParam loanRequestParam) {
        Loan loan = new Loan();
        loan.setComments(loanRequestParam.getComments());
        Reservation reservation = reservationService.readReservationById(loanRequestParam.getReservationId());
        loan.setReservation(reservation);
        loan.setRentalDate(LocalDateTime.now());
        User user = userRepository.findById(loanRequestParam.getEmployeeId()).orElseThrow(() ->
                new NotFoundException("Employee not found with such id!"));
        loan.setUser(user);
        return this.loanRepository.save(loan);
    }
}