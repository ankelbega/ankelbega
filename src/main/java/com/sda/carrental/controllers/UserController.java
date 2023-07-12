package com.sda.carrental.controllers;


import com.sda.carrental.entities.User;
import com.sda.carrental.models.constants.RoleEnum;
import com.sda.carrental.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("/customers")
    public ResponseEntity<List<User>> getCustomers() {
        List<User> users = this.userService.readAllCustomer();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/not-assigned-employees/{branchId}")
    public String getAllEmployeesNotAssignedInABranch(@PathVariable Long branchId, final Model model) {
        List<User> employees = userService.getAllUsersNotAssignedInABranch(RoleEnum.ROLE_EMPLOYEE.toString());
        model.addAttribute("employees", employees);
        return "not-assigned-employees-list";
    }

    @GetMapping("/assigned-employees/{branchId}")
    public String getAllEmployeesAssignedInABranch(@PathVariable("branchId") final Long branchId, final Model model) {
        List<User> managers = userService.getAllUsersAssignedInABranch(RoleEnum.ROLE_MANAGER.toString(), branchId);
        List<User> employees = userService.getAllUsersAssignedInABranch(RoleEnum.ROLE_EMPLOYEE.toString(), branchId);
        model.addAttribute("managers", managers);
        model.addAttribute("employees", employees);
        return "assigned-employees-list";
    }

    @PostMapping("/assign/employees/{branchId}")
    public String assignEmployeesToBranch(@RequestParam("employeesId") final List<Long> employeesId, @PathVariable final Long branchId, Model model) {
        userService.assignEmployeesToBranch(employeesId, branchId);
        List<User> employees = userService.getAllUsersNotAssignedInABranch(RoleEnum.ROLE_EMPLOYEE.toString());
        model.addAttribute("employees", employees);
        return "not-assigned-employees-list";
    }

    @GetMapping("/not-assigned-managers/{branchId}")
    public String getAllManagersNotAssignedInABranch(@PathVariable final Long branchId, Model model) {
        List<User> managers = userService.getAllUsersNotAssignedInABranch(RoleEnum.ROLE_MANAGER.toString());
        model.addAttribute("managers", managers);
        return "manager-list";
    }

    @GetMapping("/assign/manager/{branchId}")
    public String assignManagerToBranch(@RequestParam("managerId") final Long managerId, @PathVariable final Long branchId, Model model) {
        userService.assignManagerToBranch(managerId, branchId);
        List<User> managers = userService.getAllUsersAssignedInABranch(RoleEnum.ROLE_MANAGER.toString(), branchId);
        List<User> employees = userService.getAllUsersAssignedInABranch(RoleEnum.ROLE_EMPLOYEE.toString(), branchId);
        model.addAttribute("managers", managers);
        model.addAttribute("employees", employees);
        return "assigned-employees-list";
    }

    @GetMapping("/remove/assigned-user/{userId}/branch/{branchId}")
    public String removeAssignedUserFromBranch(@PathVariable final Long userId,@PathVariable final Long branchId, Model model) {
        userService.removeUserFromBranch(userId);
        List<User> managers = userService.getAllUsersAssignedInABranch(RoleEnum.ROLE_MANAGER.toString(), branchId);
        List<User> employees = userService.getAllUsersAssignedInABranch(RoleEnum.ROLE_EMPLOYEE.toString(), branchId);
        model.addAttribute("managers", managers);
        model.addAttribute("employees", employees);
        return "assigned-employees-list";
    }
}
