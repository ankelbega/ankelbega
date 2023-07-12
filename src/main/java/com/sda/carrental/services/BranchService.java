package com.sda.carrental.services;

import com.sda.carrental.entities.*;
import com.sda.carrental.errors.NotFoundException;
import com.sda.carrental.models.pojo.BranchRequestParam;
import com.sda.carrental.repositories.BranchRepository;
import com.sda.carrental.repositories.CarRepository;
import com.sda.carrental.repositories.ReservationRepository;
import com.sda.carrental.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService {


    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private RentalService rentalService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Branch> getAllBranches() {
        return this.branchRepository.findAll();
    }


    public Branch saveTheBranch(final BranchRequestParam branchRequestParam) {
        Branch branch = new Branch();
        branch.setAddress(branchRequestParam.getAddress());
        Rental rental = rentalService.readRentalById(branchRequestParam.getRentalId());
        branch.setRental(rental);
        return this.branchRepository.save(branch);
    }

    public Branch readBranchById(final Long branchId) {
        Branch branch = this.branchRepository.findById(branchId).orElseThrow(() ->
                new NotFoundException("Branch not found with such id!")
        );
        return branch;
    }

    public void deleteBranchById(final Long id) {
        Branch branch = this.branchRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Branch not found with such id!"));
        getAttributesOfBranch(id, branch);
        this.branchRepository.delete(branch);
    }

    private void getAttributesOfBranch(Long id, Branch branch) {
        List<User> users = userRepository.selectUsersWithGivenBranchId(id);
        users.forEach(user -> user.setBranch(null));
        List<Reservation> reservations = reservationRepository.selectReservationWithGivenReturnBranchId(id);
        reservations.forEach(reservation -> reservation.setReturnBranch(null));
        List<Car> cars = carRepository.selectCarsWithGivenBranchId(id);
        cars.forEach(car -> car.setBranch(null));
        this.branchRepository.save(branch);
    }


}
