package com.sda.carrental.models.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarUpdateRequestParams {

    @NotNull(message = "Please select the mileage!")
    private Double mileage;
    @NotNull(message = "Please select the amount!")
    private Double amount;

}
