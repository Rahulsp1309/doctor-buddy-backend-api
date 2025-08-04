package com.example.security.helper;

import com.example.security.dto.PatientDto;
import com.example.security.entity.Patient;

public class PatientMapper {
    public static Patient mapToEntity(PatientDto patientDto) {
        return Patient.builder()
                .id(patientDto.getId())
                .name(patientDto.getName())
                .email(patientDto.getEmail())
                .disease(patientDto.getDisease())
                .medicine(patientDto.getMedicine())
                .initialCheckupDate(patientDto.getInitialCheckupDate())
                .nextCheckupDate(patientDto.getNextCheckupDate())
                .dosage(patientDto.getDosage())
                .doctor(patientDto.getDoctor())
                .build();
    }

    public static PatientDto mapToDto(Patient patient){
        return PatientDto.builder()
                .id(patient.getId())
                .name(patient.getName())
                .email(patient.getEmail())
                .disease(patient.getDisease())
                .medicine(patient.getMedicine())
                .initialCheckupDate(patient.getInitialCheckupDate())
                .nextCheckupDate(patient.getNextCheckupDate())
                .dosage(patient.getDosage())
                .doctor(patient.getDoctor())
                .build();
    }
}
