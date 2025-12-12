package com.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Campaign name is required")
    @Size(min = 3, max = 50, message = "Campaign name length must be between 3 and 50 characters")
    @Column(columnDefinition = "varchar(50) not null")
    private String name;

    @NotEmpty(message = "Country is required")
    @Size(min = 2, max = 30, message = "Country length must be between 2 and 30 characters")
    @Column(columnDefinition = "varchar(30) not null")
    private String country;

    @NotEmpty(message = "Supervisor name is required")
    @Size(min = 3, max = 50, message = "Supervisor name length must be between 3 and 50 characters")
    @Column(columnDefinition = "varchar(50) not null")
    private String supervisorName;

    @NotEmpty(message = "Supervisor phone is required")
    @Size(min = 10, max = 10, message = "Supervisor phone must be 10 digits")
    @Column(columnDefinition = "varchar(10) not null")
    private String supervisorPhone;

    @Column(nullable = false)
    private Boolean registrationOpen = true;   // true = accepting pilgrims, false = closed

    @NotNull
    @Column(nullable = false)
    private Integer maxPilgrims;         // maximum allowed pilgrims in this campaign

    // Relationships

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "campaign")
    @JsonIgnore
    private Set<Pilgrim> pilgrims;

    @ManyToOne
    @JsonIgnore
    private Kitchen kitchen;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "campaign")
    private Set<Rating> ratings;
}
