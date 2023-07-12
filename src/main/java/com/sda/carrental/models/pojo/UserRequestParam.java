package com.sda.carrental.models.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequestParam {

    @NotEmpty(message = "What's your name?")
    private String firstName;
    @NotEmpty(message = "What's your last name?")
    private String lastName;
    @NotEmpty(message = "Choose a username!")
    private String username;
    @NotEmpty(message = "You should choose a password!")
    private String password;
    @NotEmpty(message = "What's your email?")
    @Email(message = "Not well formed email address")
    private String email;
    @NotEmpty(message = "What's your address?")
    private String address;
}
