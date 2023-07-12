package com.sda.carrental.models.pojo.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationCountBookingDateResponseParam {

    private Date bookingDate;
    private BigInteger count;

}



