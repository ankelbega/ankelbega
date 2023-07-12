package com.sda.carrental.models.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarStatusRequestParams {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please, first choose a date!")
    private LocalDate date;
    @NotNull(message = "Please,select a status!")
    private Long statusId;
}
