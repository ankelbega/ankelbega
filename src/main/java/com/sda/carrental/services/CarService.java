package com.sda.carrental.services;

import com.sda.carrental.entities.Branch;
import com.sda.carrental.entities.Car;
import com.sda.carrental.errors.NotFoundException;
import com.sda.carrental.models.pojo.CarFilterRequestParams;
import com.sda.carrental.models.pojo.CarUpdateRequestParams;
import com.sda.carrental.models.pojo.statistic.BrandCountResponseParams;
import com.sda.carrental.models.pojo.statistic.CarCountRequestParam;
import com.sda.carrental.models.pojo.statistic.CarMaxMileageRequestParam;
import com.sda.carrental.repositories.BranchRepository;
import com.sda.carrental.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.sda.carrental.models.pojo.CarRequestParams;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private BranchService branchService;

    public Car getCarById(final Long carId) {
        return carRepository.findById(carId).orElseThrow(() -> new NotFoundException("Car not found with such id!"));
    }

    public List<Car> findAllCars() {
        return carRepository.findAllCarsAssignedToBranch();
    }

    public List<BrandCountResponseParams> countCarByBrandInReservationsMade() {
        List<Object[]> brandUser = this.carRepository.countMaxBrandByReservationsMade();
        List<BrandCountResponseParams> brandCountResponseParams = brandUser.stream().map((obj) -> {
            return new BrandCountResponseParams((String) obj[0], (BigInteger) obj[1]);
        }).collect(Collectors.toList());
        return brandCountResponseParams;
    }

    public List<CarMaxMileageRequestParam> maxMileageOfCar() {
        List<Object[]> carMileage = this.carRepository.maxMileageOfCarInRental();
        List<CarMaxMileageRequestParam> carMaxMileageRequestParams = carMileage.stream().map((obj) -> {
            return new CarMaxMileageRequestParam((String) obj[0], (String) obj[1], (Double) obj[2]);
        }).collect(Collectors.toList());
        return carMaxMileageRequestParams;
    }

    public List<CarCountRequestParam> countCarInEachBranch() {
        List<Object[]> countCar = this.carRepository.countCarInBranch();
        List<CarCountRequestParam> carCountRequestParams = countCar.stream().map((obj) -> {
            return new CarCountRequestParam((BigInteger) obj[0], (String) obj[1]);
        }).collect(Collectors.toList());
        return carCountRequestParams;
    }

    public List<Car> getAllCars() {

        return carRepository.findAll();
    }

    public Car updateTheCar(final Long carId, final CarUpdateRequestParams carUpdateRequestParams) {

        Car car = carRepository.findById(carId).orElseThrow(() -> new NotFoundException("Car not found with such id!"));
        car.setAmount(carUpdateRequestParams.getAmount());
        car.setMileage(carUpdateRequestParams.getMileage());
        carRepository.save(car);
        return car;
    }


    public List<Car> getAvailableCarsAtGivenTime(final LocalDate date) {
        return carRepository.findAvailableCarsAtGivenTime(date);
    }

    public Car saveTheCar(final CarRequestParams carRequestParams) {
        Car car = new Car();
        car.setBrand(carRequestParams.getBrand());
        car.setModel(carRequestParams.getModel());
        car.setBodyType(carRequestParams.getBodyType());
        car.setYear(carRequestParams.getYear());
        car.setColor(carRequestParams.getColor());
        car.setMileage(carRequestParams.getMileage());
        car.setAmount(carRequestParams.getAmount());
        Branch branch = branchService.readBranchById(carRequestParams.getBranchId());

        car.setBranch(branch);
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            IOUtils.copy(carRequestParams.getCarImage().getInputStream(), outputStream);
        } catch (IOException e) {

        }
        car.setImage(outputStream.toByteArray());
        carRepository.save(car);
        return car;
    }

    public void deleteTheCar(final Long carId) {
        if (carRepository.existsById(carId)) {
            carRepository.deleteById(carId);
        } else {
            throw new NotFoundException("Car not found with such id!");
        }
    }

    public List<Car> searchTheCarsByAttributes(final CarFilterRequestParams carFilterRequestParams) {

        return carRepository.findByBrandAndYearAndColor(carFilterRequestParams.getBrand(), carFilterRequestParams.getYear(), carFilterRequestParams.getColor());
    }

    public List<String> getCarsBrands() {
        return carRepository.getCarsBrands();
    }

    public List<String> getCarsColors() {
        return carRepository.getCarsColors();
    }

    public List<Integer> getCarsYears() {
        return carRepository.getCarsYears();
    }

    public Car assignCarToBranch(final Long carId, final Long branchId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new NotFoundException("Car not found with such id!"));
        Branch branch = branchRepository.findById(branchId).orElseThrow(() -> new NotFoundException("Branch not found with such id!"));
        car.setBranch(branch);
        carRepository.save(car);
        return car;
    }

}
