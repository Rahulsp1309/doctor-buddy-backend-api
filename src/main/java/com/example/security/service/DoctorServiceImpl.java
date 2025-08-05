package com.example.security.service;

import com.example.security.dao.DoctorRepo;
import com.example.security.dto.DoctorDto;
import com.example.security.entity.Doctor;
import com.example.security.exception.UserNotFoundException;
import com.example.security.helper.DoctorMapper;
import com.example.security.helper.PatientMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements  DoctorService{

    private final DoctorRepo doctorRepo;
    private final DoctorMapper doctorMapper;

    @Override
    public DoctorDto getDoctorByEmail(String email) {
       Optional<Doctor> doctor = doctorRepo.findByEmail(email);
       if(doctor.isPresent()){
           return  doctorMapper.mapToDto(doctor.get());
       }
       return  null;
    }

    @Override
    public DoctorDto getDoctorById(int id) {
        return doctorMapper.mapToDto(doctorRepo.findById(id).orElseThrow(()-> new UserNotFoundException("doctor not found")));
    }

    @Override
    public List<DoctorDto> getAllDoctors() {
        return doctorRepo.findAll().stream().map(DoctorMapper::mapToDto).collect(Collectors.toList());
    }
}
