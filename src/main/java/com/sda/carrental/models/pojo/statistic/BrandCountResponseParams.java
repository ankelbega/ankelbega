package com.sda.carrental.models.pojo.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandCountResponseParams {

    private String name;
    private BigInteger count;


}
