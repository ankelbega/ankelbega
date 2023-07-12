package com.sda.carrental.models.pojo.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarMaxMileageRequestParam {

    private String brandName;
    private String branchAddress;
    private Double maxMileageOfCar;

}
