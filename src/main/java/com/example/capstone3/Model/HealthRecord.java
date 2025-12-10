package com.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HealthRecord {

    @Id
    private Integer id;

    @Column(nullable = false)
    private boolean diabetic;

    @Column(nullable = false)
    private boolean highBloodPressure;

    @Column(nullable = false)
    private boolean foodAllergy;

    @Size(max = 255, message = "Allergy details must not exceed 255 characters")
    @Column(length = 255)
    private String allergyDetails;

    @Column(nullable = false)
    private boolean heartDisease;

    @Column(nullable = false)
    private boolean asthma;

    @OneToOne
    @MapsId
    @JsonIgnore
    private Pilgrim pilgrim;
}
