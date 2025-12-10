package com.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Kitchen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Owner name cannot be empty")
    private String ownerName;

    @NotEmpty(message = "Owners phone number cannot be empty")
    private String ownerPhone;

    @Pattern(regexp = "^(Pending|Active|Suspended|Reject)$")
    private String status;

    @NotEmpty(message = "Information cannot be empty")
    private String information;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kitchen")
    private Set<Meal> meals;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "kitchen")
    private Set<Campaign> campaigns;


    @OneToMany( cascade = CascadeType.ALL, mappedBy = "kitchen")
    private Set<Violation> violations;

    @ManyToMany(mappedBy = "kitchens")
    @JsonIgnore
    private Set<Inspector> inspectors;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kitchen")
    private Set<Rating> ratings;


}
