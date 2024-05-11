package io.zineb.model;

import jakarta.persistence.*;
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

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")

    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "fk_genre_id")
    private Gender gender;

    @Column(name = "patient_address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

}