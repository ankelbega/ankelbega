package com.sda.carrental.services;

import com.sda.carrental.entities.Rental;
import com.sda.carrental.errors.NotFoundException;
import com.sda.carrental.models.pojo.RentalRequestParam;
import com.sda.carrental.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;


    public Rental updateTheRental(final RentalRequestParam rentalRequestParam, final Long rentalId) {
        Rental rental = readRentalById(rentalId);
        rental.setInternetDomain(rentalRequestParam.getInternetDomain());
        rental.setLogo(rentalRequestParam.getLogo());
        rental.setName(rentalRequestParam.getName());
        rental.setOwner(rentalRequestParam.getOwner());
        rental.setContactAddress(rentalRequestParam.getContactAddress());
        this.rentalRepository.save(rental);
        return rental;
    }

    public Rental readRentalById(final Long rentalId) {
        Rental rental = this.rentalRepository.findById(rentalId).orElseThrow(() ->
                new NotFoundException("Rental not found with such id!")
        );
        return rental;
    }

    public Rental saveTheRental(final RentalRequestParam rentalRequestParam) {
        Rental rental = new Rental();
        rental.setInternetDomain(rentalRequestParam.getInternetDomain());
        rental.setLogo(rentalRequestParam.getLogo());
        rental.setName(rentalRequestParam.getName());
        rental.setOwner(rentalRequestParam.getOwner());
        rental.setContactAddress(rentalRequestParam.getContactAddress());
        this.rentalRepository.save(rental);
        return rental;
    }
}