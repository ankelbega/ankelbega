package com.sda.carrental.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long id;
    private String brand;
    private String model;
    private String bodyType;
    private int year;
    private String color;
    private double mileage;
    private double amount;
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JsonIgnore
    List<Reservation> reservations;

    @OneToMany(mappedBy = "car",cascade = CascadeType.REMOVE)
    private Set<CarStatus> carStatuses;
}
