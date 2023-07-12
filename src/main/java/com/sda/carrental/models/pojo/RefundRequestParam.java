package com.sda.carrental.models.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RefundRequestParam {

    private double amountSurcharge;
    private String comments;
    private Long reservationId;
    private Long employeeId;
}
