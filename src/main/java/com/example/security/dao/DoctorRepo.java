package com.example.security.dao;

import com.example.security.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepo extends JpaRepository<Doctor, Integer> {

    Optional<Doctor> findByEmail(String email);
}
