package com.example.capstone3.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inspector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Inspector name is required")
    @Size(min = 3, max = 40, message = "Inspector name length must be between 3 and 40 characters")
    @Column(columnDefinition = "varchar(40) not null")
    private String name;

    @NotEmpty(message = "Phone number is required")
    @Size(min = 10, max = 10, message = "Phone number must be 10 digits")
    @Column(columnDefinition = "varchar(10) not null")
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "inspector")
    private Set<Violation> violations;

    @ManyToMany
    private Set<Kitchen> kitchens;
}
