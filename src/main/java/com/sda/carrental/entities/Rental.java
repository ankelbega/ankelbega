package com.sda.carrental.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Long id;
    private String name;
    private String internetDomain;
    private String contactAddress;
    private String owner;
    private byte[] logo;

    @OneToMany(mappedBy = "rental",fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Branch> branches;

    @OneToOne(mappedBy = "rental",fetch = FetchType.LAZY)
    @JsonIgnore
    private Revenue revenue;


}
