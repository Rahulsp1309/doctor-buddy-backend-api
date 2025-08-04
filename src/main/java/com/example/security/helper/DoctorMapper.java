package com.example.security.helper;

import com.example.security.dto.DoctorDto;
import com.example.security.dto.PatientDto;
import com.example.security.entity.Doctor;

import java.util.Set;
import java.util.stream.Collectors;

public class DoctorMapper {

    public static Doctor mapToEntity(DoctorDto doctorDto){
        return Doctor.builder()
                .name(doctorDto.getName())
                .id(doctorDto.getId())
                .email(doctorDto.getEmail())
                .password(doctorDto.getPassword())
                .experience(doctorDto.getExperience())
                .speciality(doctorDto.getSpeciality())
                .build();
    }

    public static DoctorDto mapToDto(Doctor doctor){
        return DoctorDto.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .email(doctor.getEmail())
                .experience(doctor.getExperience())
                .speciality(doctor.getSpeciality())
                .build();

    }
}
