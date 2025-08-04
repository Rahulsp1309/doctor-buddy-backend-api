package com.example.security.service;


import com.example.security.dto.AuthRequest;
import com.example.security.dto.AuthResponse;
import com.example.security.dto.DoctorDto;
import com.example.security.dto.RegisterRequest;
import com.example.security.dao.DoctorRepo;
import com.example.security.entity.Doctor;
import com.example.security.helper.DoctorMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private  final DoctorRepo doctorRepo;
    private  final PasswordEncoder passwordEncoder;
    private  final JwtService jwtService;
    private  final AuthenticationManager authenticationManager;
    private  final  DoctorMapper doctorMapper;
    public AuthResponse register(DoctorDto req) {
        Doctor user = doctorMapper.mapToEntity(req);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        doctorRepo.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();

    }

    public AuthResponse authenticate(AuthRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getEmail(), req.getPassword()
                )
        );

        var user = doctorRepo.findByEmail(req.getEmail()).orElseThrow(()-> new BadCredentialsException("bad credentials"));
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .doctor(doctorMapper.mapToDto(doctorRepo.findByEmail(req.getEmail()).orElseThrow(
                        ()->{
                            return  new EntityNotFoundException("Doctor not found");
                        }
                )))
                .token(jwtToken)
                .build();



    }
}
