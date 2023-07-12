package com.sda.carrental.models.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationRequestParam {

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate pickupDate;
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate returnDate;
    private Long customerId;
    private Long carId;
    private Long returnBranchId;
}
