package com.sda.carrental.models.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RentalRequestParam {
    private String name;
    private String internetDomain;
    private String contactAddress;
    private String owner;
    private byte[] logo;
}
