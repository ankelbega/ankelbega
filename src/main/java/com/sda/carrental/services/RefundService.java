package com.sda.carrental.services;


import com.sda.carrental.entities.Loan;
import com.sda.carrental.entities.Refund;
import com.sda.carrental.entities.Reservation;
import com.sda.carrental.entities.User;
import com.sda.carrental.errors.NotFoundException;
import com.sda.carrental.models.pojo.LoanRequestParam;
import com.sda.carrental.models.pojo.RefundRequestParam;
import com.sda.carrental.repositories.RefundRepository;
import com.sda.carrental.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RefundService {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefundRepository refundRepository;

    public Refund saveTheRefund(final RefundRequestParam refundRequestParam) {
        Refund refund = new Refund();
        refund.setComments(refundRequestParam.getComments());
        Reservation reservation = reservationService.readReservationById(refundRequestParam.getReservationId());
        refund.setReservation(reservation);
        refund.setRentalDate(LocalDateTime.now());
        User user = userRepository.findById(refundRequestParam.getEmployeeId()).orElseThrow(() ->
                new NotFoundException("Employee not found with such id!"));
        refund.setUser(user);
        refund.setAmountSurcharge(refundRequestParam.getAmountSurcharge());
        return this.refundRepository.save(refund);
    }

    public Double getTotalAmountSurcharge(Long rentalId) {
        Double totalAmountSurcharge = this.refundRepository.getTotalAmountSurcharge(rentalId);
        return totalAmountSurcharge;
    }

}
