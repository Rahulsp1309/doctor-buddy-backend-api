package com.example.security.controller;

import com.example.security.dto.PatientDto;
import com.example.security.dto.PatientPostResponse;
import com.example.security.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
@CrossOrigin(origins =  {"http://localhost:4200", "https://doctor-buddy.onrender.com/"})
public class PatientController {
    private final PatientService patientService;

    @PostMapping("/create-patient/{doctorId}")
    public ResponseEntity<PatientDto> createPatient(@RequestBody PatientDto patient, @PathVariable int doctorId){

        PatientDto createdPatient = patientService.createPatient(patient, doctorId);
        return new ResponseEntity<>(createdPatient, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/getByDoctorId/{id}")
    public ResponseEntity<PatientPostResponse> getByDoctorID(@PathVariable int id,
                                                             @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                                             @RequestParam(name = "pageSize", defaultValue = "10") int pageSize){
        List<PatientDto> patients;
        PatientPostResponse patientPostResponse = patientService.getByDoctorID(id, pageNumber, pageSize);

        return new ResponseEntity<>(patientPostResponse, HttpStatusCode.valueOf(200));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PatientDto> updatePatient(@RequestBody PatientDto patient, @PathVariable int id) {
        PatientDto updatedPatient = patientService.update(patient, id);
        return new ResponseEntity<>(updatedPatient, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<PatientDto> get(@PathVariable int id){
        PatientDto receivedPatient = patientService.getById(id);
        return new ResponseEntity<>(receivedPatient, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/getByQuery/{query}")
    public ResponseEntity<List<PatientDto>> get(@PathVariable String query){
        List<PatientDto> receivedPatients = patientService.getPatientsByQuery(query);
        return new ResponseEntity<>(receivedPatients, HttpStatusCode.valueOf(200));
    }

}
