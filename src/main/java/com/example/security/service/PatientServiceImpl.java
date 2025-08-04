package com.example.security.service;

import com.example.security.dao.DoctorRepo;
import com.example.security.dao.PatientRepo;
import com.example.security.dto.PatientDto;
import com.example.security.dto.PatientPostResponse;
import com.example.security.entity.Doctor;
import com.example.security.entity.Patient;
import com.example.security.helper.PatientMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final DoctorRepo doctorRepo;
    private final PatientRepo patientRepo;
    private final PatientMapper patientMapper;

    @Override
    public PatientDto createPatient(PatientDto patient, int doctorId) {
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(()-> new EntityNotFoundException("not found"));
        Patient patientToBeCreated = patientMapper.mapToEntity(patient);

        patientToBeCreated.setDoctor(doctor);
        Patient createdPatient = patientRepo.save(patientToBeCreated);

        Set<Patient> patients = doctor.getPatients();
        patients.add(createdPatient);
        doctor.setPatients(patients);
        doctorRepo.save(doctor);
        return  patientMapper.mapToDto(createdPatient);
    }

    @Override
    public PatientPostResponse getByDoctorID(int id, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber,pageSize);
        Page<Patient> pageData = patientRepo.getByDoctorId(id, page);
        PatientPostResponse patientPostResponse = new PatientPostResponse();
        patientPostResponse.setPatients(pageData.getContent().stream().map(PatientMapper::mapToDto).collect(Collectors.toList()));
        patientPostResponse.setCount(pageData.getTotalElements());
        return  patientPostResponse;
    }

    @Override
    public PatientDto update(PatientDto newPatientDto, int id) {
        return patientRepo.findById(id)
                .map(patient -> {
                    if(newPatientDto.getName() != null) patient.setName(newPatientDto.getName());
                    if(newPatientDto.getEmail()!= null) patient.setEmail(newPatientDto.getEmail());
                    if(newPatientDto.getMedicine() != null) patient.setMedicine(newPatientDto.getMedicine());
                    if(newPatientDto.getInitialCheckupDate() != null) patient.setInitialCheckupDate(newPatientDto.getInitialCheckupDate());
                    if(newPatientDto.getNextCheckupDate() != null) patient.setNextCheckupDate(newPatientDto.getNextCheckupDate());
                    if(newPatientDto.getDisease() != null) patient.setDisease(newPatientDto.getDisease());
                    patientRepo.save(patient);
                    return PatientMapper.mapToDto(patient);
                })
                .orElseGet(() -> {
                    Patient newPatient = PatientMapper.mapToEntity(newPatientDto);
                    newPatient.setId(id);
                    return PatientMapper.mapToDto(patientRepo.save(newPatient));
                });
    }

    @Override
    public PatientDto getById(int id) {
        Patient patient = patientRepo.getById(id);
        return  PatientMapper.mapToDto(patient);
    }

    @Override
    public List<PatientDto> getAllPatients() {
        List<Patient> patientEntityList = patientRepo.findAll();
        List<PatientDto> patients = new ArrayList<>();
        for(Patient p : patientEntityList){
            patients.add(patientMapper.mapToDto(p));
        }
       return  patients;

    }

    @Override
    public List<PatientDto> getPatientsByQuery(String query) {
        List<PatientDto> patients =  patientRepo.getPatientsByQuery(query).stream().map(PatientMapper::mapToDto).collect(Collectors.toList());
        return patients;
    }
}
