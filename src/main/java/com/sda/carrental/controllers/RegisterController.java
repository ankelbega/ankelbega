package com.sda.carrental.controllers;

import com.sda.carrental.entities.User;
import com.sda.carrental.models.pojo.UserRequestParam;
import com.sda.carrental.services.RoleService;
import com.sda.carrental.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;


@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/registration")
    public String showRegistrationForm(final UserRequestParam userRequestParam) {
        return "registration";
    }

    @PostMapping("/register/user/{roleId}")
    public String addUser(@Valid final UserRequestParam userRequestParam,final BindingResult result,
                          @PathVariable("roleId") final Long roleId) {

        Optional<User> optionalUser = userService.findByUsername(userRequestParam.getUsername());
        if (optionalUser.isPresent()) {
            result.rejectValue("username", "en", "There is already an account registered with that username");
        }
        if (result.hasErrors()) {
            return "registration";
        }
        this.userService.saveTheUser(userRequestParam, roleId);
        return "redirect:/registration?success";
    }

}
