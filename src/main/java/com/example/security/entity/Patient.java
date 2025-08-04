package com.example.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "patient")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String email;

    private String disease;

    @Column(name = "initial_checkup_data")
    private Date initialCheckupDate;

    @Column(name = "next_checkup_data")
    private Date nextCheckupDate;

    private String medicine;

    private String dosage;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Override
    public int hashCode() {
        return Objects.hash(id, name, disease, initialCheckupDate, nextCheckupDate, medicine);
    }
}
