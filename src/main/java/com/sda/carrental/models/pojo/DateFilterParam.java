package com.sda.carrental.models.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateFilterParam {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
