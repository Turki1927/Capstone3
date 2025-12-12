package com.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Stars rating is required")
    @Min(value = 1, message = "Stars must be at least 1")
    @Max(value = 5, message = "Stars must be at most 5")
    @Column(nullable = false)
    private Integer stars;

    private String comment;

    @Column(columnDefinition = "date Default CURRENT_DATE ")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    @ManyToOne
    @JsonIgnore
    private Kitchen kitchen;

    @ManyToOne
    @JsonIgnore
    private Campaign campaign;
}
