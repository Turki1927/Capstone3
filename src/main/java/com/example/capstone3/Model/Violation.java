package com.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Violation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private LocalDate date;


    @NotEmpty(message = "Violation type is required")
    @Size(min = 3, max = 40, message = "Violation type length must be between 3 and 40 characters")
    @Column(columnDefinition = "varchar(40) not null")
    private String type;

    @NotEmpty(message = "Severity is required")
    @Column(columnDefinition = "varchar(20) not null")
    private String severity;

    @NotEmpty(message = "Notes are required")
    @Size(min = 5, max = 200, message = "Notes length must be between 5 and 200 characters")
    @Column(columnDefinition = "varchar(200) not null")
    private String notes;

    @Column(columnDefinition = "varchar(20) not null")
    private String status;

    @ManyToOne
    @JsonIgnore
    private Inspector inspector;

    @ManyToOne
    @JsonIgnore
    private Kitchen kitchen;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "violation")
    private Set<Objection> objections;

}
