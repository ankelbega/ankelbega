package com.sda.carrental.models.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarRequestParams {
    @NotEmpty(message = "What is the brand?")
    private String brand;
    @NotEmpty(message = "What kind of model?")
    private String model;
    @NotEmpty(message = "What kind of body?")
    private String bodyType;
    @NotNull(message = "Please select the production year!")
    private Integer year;
    @NotEmpty(message = "Color should not be empty!")
    private String color;
    @NotNull(message = "Please select the mileage!")
    private Double mileage;
    @NotNull(message = "Please select the amount!")
    private Double amount;
    @NotNull(message = "Please select a branch!")
    private Long branchId;
    private MultipartFile carImage;
}
