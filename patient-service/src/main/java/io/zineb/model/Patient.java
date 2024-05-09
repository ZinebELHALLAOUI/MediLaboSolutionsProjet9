package io.zineb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "date_of_birth")

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "patient_address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

}