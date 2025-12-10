package com.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Name cannot ba empty")
    private String name;

    @NotEmpty(message = "Category cannot be empty")
    @Pattern(regexp = "^(Dinner|Lunch|Breakfast)$", message = "Category must be Dinner, Lunch or Breakfast")
    private String category;

    @NotEmpty(message = "Ingredients cannot be empty")
    private List<String> ingredients;

    @ManyToOne
    @JsonIgnore
    private Kitchen kitchen;

}
