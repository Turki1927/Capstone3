package com.example.capstone3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Pilgrim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "The name is required")
    private String name;

    @Email(message = "The email must be a valid email")
    @NotEmpty(message = "The email is required")
    private String email;

    @NotEmpty(message = "The phone number is required")
    private String phoneNumber;

    @NotEmpty(message = "The gender is required")
    @Pattern(regexp = "male|female",message = "The gender must be male or female")
    private String gender;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "pilgrim")
    @PrimaryKeyJoinColumn
    private HealthRecord healthRecord;

    @ManyToOne
    private Campaign campaign;
}
