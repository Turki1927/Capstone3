package com.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Objection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Objection reason is required")
    @Size(min = 5, max = 200, message = "Objection reason length must be between 5 and 200 characters")
    @Column(columnDefinition = "varchar(200) not null")
    private String reason;

    @Size(max = 200, message = "Response length must be less than 200 characters")
    @Column(columnDefinition = "varchar(200)")
    private String response;

    @Column(columnDefinition = "varchar(20) not null")
    private String status;

    @ManyToOne
    @JsonIgnore
    private Violation violation;

    @ManyToOne
    @JsonIgnore
    private Kitchen kitchen;
}