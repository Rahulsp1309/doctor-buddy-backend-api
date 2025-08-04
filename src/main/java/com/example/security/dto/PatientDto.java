package com.example.security.dto;

import com.example.security.entity.Doctor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {

    private int id;

    private String name;

    private String email;

    private String disease;

    private Date initialCheckupDate;

    private Date nextCheckupDate;

    private String medicine;

    private String dosage;

    private Doctor doctor;
}
