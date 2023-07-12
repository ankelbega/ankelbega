package com.sda.carrental.controllers;

import com.sda.carrental.entities.Status;
import com.sda.carrental.models.pojo.CarStatusRequestParams;
import com.sda.carrental.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class StatusController {

    @Autowired
    private StatusService statusService;

    @GetMapping("/statuses/{carId}")
    public String showAddCarStatuses(@PathVariable final Long carId, final CarStatusRequestParams carStatusRequestParams,
                                     final Model model) {

        List<Status> statuses = statusService.getAllStatus();
        model.addAttribute("statuses", statuses);
        return "add-car-status";
    }

}
