package com.example.security.service;

import com.example.security.dto.PatientDto;
import com.example.security.dto.PatientPostResponse;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


public interface PatientService {
    PatientDto createPatient(PatientDto patient, int doctorId);

    PatientPostResponse getByDoctorID(int id, int pageNumber, int pageSize);

    PatientDto update(PatientDto patient, int id);

    PatientDto getById(int id);

    List<PatientDto> getAllPatients();

    List<PatientDto> getPatientsByQuery(String query);

}
