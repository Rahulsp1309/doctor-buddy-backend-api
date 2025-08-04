package com.example.security.service;

import com.example.security.dto.DoctorDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DoctorService {

    public DoctorDto getDoctorByEmail(String email);
    public DoctorDto getDoctorById(int id);

    public List<DoctorDto> getAllDoctors();

}
