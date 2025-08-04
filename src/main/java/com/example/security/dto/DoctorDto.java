package com.example.security.dto;


import com.example.security.entity.Doctor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {

   private int id;

    private String name;

    private String email;

    private String password;

    private String speciality;

    private int experience;

}
