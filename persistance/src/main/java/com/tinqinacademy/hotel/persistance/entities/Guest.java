package com.tinqinacademy.hotel.persistance.entities;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "guests")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthdate;

    @Column(name = "id_card_number", nullable = false)
    private String IdCardNumber;

    @Column(name = "id_card_validity", nullable = false)
    private String IdCardValidity;

    @Column(name = "id_card_authority", nullable = false)
    private String IdCardAuthority;

    @Column(name = "id_card_issue_date", nullable = false)
    private LocalDate IdCardIssueDate;

    @Column(name = "phone_number", nullable = false)
    private String phoneNo;
}
