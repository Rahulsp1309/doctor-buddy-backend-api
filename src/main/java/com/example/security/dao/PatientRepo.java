package com.example.security.dao;

import com.example.security.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRepo extends JpaRepository<Patient, Integer> {

    @Query(name = "select * from patient where doctor_id=?1", nativeQuery = true)
    Page<Patient> getByDoctorId(int id, Pageable page);

    @Query(value = "SELECT * FROM patient WHERE name LIKE %?1%", nativeQuery = true)
    List<Patient> getPatientsByQuery(String query);
}
