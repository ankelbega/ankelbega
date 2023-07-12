package com.sda.carrental.controllers;

import com.sda.carrental.entities.Branch;
import com.sda.carrental.entities.Car;
import com.sda.carrental.models.pojo.CarFilterRequestParams;
import com.sda.carrental.models.pojo.CarUpdateRequestParams;
import com.sda.carrental.models.pojo.DateFilterParam;
import com.sda.carrental.models.pojo.statistic.*;
import com.sda.carrental.repositories.BranchRepository;
import com.sda.carrental.services.CarService;
import com.sda.carrental.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.sda.carrental.models.pojo.CarRequestParams;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;


import java.util.List;

@Controller
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/car")
    public String createCar(@Valid final CarRequestParams carRequestParams, final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            List<Branch> branches = this.branchRepository.findAll();
            model.addAttribute("branches", branches);
            return "add-car";
        }
        Car car = carService.saveTheCar(carRequestParams);
        model.addAttribute("car", car);
        return "redirect:/cars";
    }

    @GetMapping("/add-car")
    public String showAddCar(final CarRequestParams carRequestParams, final Model model) {
        List<Branch> branches = this.branchRepository.findAll();
        model.addAttribute("branches", branches);
        return "add-car";
    }

    @PostMapping("update/car/{carId}")
    public String updateCar(@PathVariable final Long carId, @Valid final CarUpdateRequestParams carUpdateRequestParams,
                            final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            return "edit-cars";
        }
        Car updatedCar = carService.updateTheCar(carId, carUpdateRequestParams);
        List<Car> cars = carService.getAllCars();
        model.addAttribute("cars", cars);
        return "redirect:/cars";
    }

    @GetMapping("edit/car/{carId}")
    public String showEditCarPage(@PathVariable final Long carId, final CarUpdateRequestParams carUpdateRequestParams,
                                  final Model model) {
        Car car = carService.getCarById(carId);
        model.addAttribute("mileage", car.getMileage());
        model.addAttribute("amount", car.getAmount());
        return "edit-cars";
    }

    @GetMapping("/car/{carId}")
    public String deleteCar(@PathVariable("carId") final Long carId) {
        carService.deleteTheCar(carId);
        return "redirect:/cars";
    }

    @GetMapping("/cars")
    public String getAllCars(final CarFilterRequestParams carFilterRequestParams, final Model model) {
        List<Car> cars = carService.findAllCars();
        model.addAttribute("cars", cars);
        List<String> carsBrands = carService.getCarsBrands();
        model.addAttribute("carsBrands", carsBrands);
        List<String> carsColors = carService.getCarsColors();
        model.addAttribute("carsColors", carsColors);
        List<Integer> carsYears = carService.getCarsYears();
        model.addAttribute("carsYears", carsYears);
        return "car-list";
    }

    @GetMapping("/filter-available-cars/{branchId}")
    public String showAvailableCars(@PathVariable("branchId") final Long branchId, final DateFilterParam dateFilterParam) {
        return "assign-cars";
    }

    @GetMapping("/load-available-cars/{branchId}")
    public String getAvailableCars(@PathVariable("branchId") final Long branchId, final DateFilterParam dateFilterParam, final Model model) {
        List<Car> availableCars = carService.getAvailableCarsAtGivenTime(dateFilterParam.getDate());
        model.addAttribute("availableCars", availableCars);
        return "assign-cars";
    }

    @GetMapping("image-car/{carId}")
    public void getCarImage(@PathVariable final Long carId, HttpServletResponse response) throws IOException {
        Car car = carService.getCarById(carId);
        byte[] carImage = car.getImage();
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.setHeader("Cache-Control", "max-age=2628000");
        try (OutputStream out = response.getOutputStream()) {
            out.write(carImage);
        }
    }

    @GetMapping("/assign/car/{carId}/branch/{branchId}")
    public String assignCarToBranch(@PathVariable final Long carId, @PathVariable final Long branchId,
                                    final DateFilterParam dateFilterParam, final Model model) {
        Car car = carService.assignCarToBranch(carId, branchId);
        List<Car> availableCars = carService.getAvailableCarsAtGivenTime(dateFilterParam.getDate());
        model.addAttribute("availableCars", availableCars);
        return "redirect:/load-available-cars/{branchId}?success";
    }

    @PostMapping("/filter/cars")
    public String filterByAttributes(final CarFilterRequestParams carFilterRequestParams, final Model model) {
        List<Car> filteredCars = carService.searchTheCarsByAttributes(carFilterRequestParams);
        model.addAttribute("filteredCars", filteredCars);
        return "filtered-cars";
    }

    @GetMapping("/statistic")
    public String countBrandForEachReservation(final Model model) {
        List<BrandCountResponseParams> brandCountResponseParams = carService.countCarByBrandInReservationsMade();
        model.addAttribute("brandCountResponseParams", brandCountResponseParams);
        List<CarMaxMileageRequestParam> carMaxMileageRequestParams = carService.maxMileageOfCar();
        model.addAttribute("carMaxMileageRequestParams", carMaxMileageRequestParams);
        List<CarCountRequestParam> carCountRequestParams = carService.countCarInEachBranch();
        model.addAttribute("carCountRequestParams", carCountRequestParams);
        List<ReservationCountBranchResponseParam> countReservationByBranch = reservationService.countReservationByBranch();
        model.addAttribute("countReservationBranch", countReservationByBranch);
        List<ReservationCountBookingDateResponseParam> countReservationByBookingDate = reservationService.countReservationByBookingDate();
        model.addAttribute("countReservationBooking", countReservationByBookingDate);
        return "statistic";
    }

}
