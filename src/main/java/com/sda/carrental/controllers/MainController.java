package com.sda.carrental.controllers;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class MainController {


    @GetMapping("/")
    public String root(@AuthenticationPrincipal UserDetails currentUser) {
        Optional<? extends GrantedAuthority> authorities = currentUser.getAuthorities().stream().findFirst();
        if (authorities.isPresent()) {
            GrantedAuthority role = authorities.get();
            if (role.getAuthority().equals("ROLE_ADMIN")) {
                return "redirect:/cars";
            } else if (role.getAuthority().equals("ROLE_CUSTOMER")) {
                return "redirect:/cars";
            }
        }
        return "/";
    }
}
