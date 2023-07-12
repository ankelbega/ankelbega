package com.sda.carrental.controllers;

import com.sda.carrental.entities.CarStatus;
import com.sda.carrental.entities.Status;
import com.sda.carrental.models.pojo.CarStatusRequestParams;
import com.sda.carrental.services.CarStatusService;
import com.sda.carrental.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CarStatusController {

    @Autowired
    private CarStatusService carStatusService;

    @Autowired
    private StatusService statusService;

    @PostMapping("/car-status/{carId}")
    public String addCarStatus(@PathVariable final Long carId,
                               @Valid final CarStatusRequestParams carStatusRequestParams, final BindingResult result,
                               final Model model) {
        if (result.hasErrors()) {
            List<Status> statuses = statusService.getAllStatus();
            model.addAttribute("statuses", statuses);
            return "add-car-status";
        }
        CarStatus carStatus = carStatusService.addCarStatus(carStatusRequestParams, carId);
        List<Status> statuses = statusService.getAllStatus();
        model.addAttribute("statuses", statuses);
        return "redirect:/statuses/{carId}?success";
    }

    @GetMapping("/car-status/{carId}")
    public ResponseEntity<List<CarStatus>> getCarStatus(@PathVariable final Long carId) {

        List<CarStatus> carStatuses = carStatusService.getCarStatuses(carId);
        return ResponseEntity.ok(carStatuses);
    }

    @PutMapping("/car-status/{carStatusId}")
    public ResponseEntity<CarStatus> putCarStatus(@PathVariable final Long carStatusId, @RequestParam final Long statusId) {

        CarStatus carStatus = carStatusService.updateCarStatus(carStatusId, statusId);
        return ResponseEntity.ok(carStatus);
    }

    @DeleteMapping("/car-status/{carStatusId}")
    public ResponseEntity<Void> deleteCarStatus(@PathVariable final Long carStatusId) {
        carStatusService.deleteCarStatus(carStatusId);
        return ResponseEntity.ok().build();
    }

}
