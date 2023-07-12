package com.sda.carrental.models.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BranchRequestParam {
    @NotEmpty(message = "Address should not be empty!")
    private String address;
    @NotNull(message = "Please select a rental!")
    private Long rentalId;
}
