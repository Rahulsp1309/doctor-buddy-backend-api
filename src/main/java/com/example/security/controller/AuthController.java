package com.example.security.controller;


import com.example.security.dto.AuthRequest;
import com.example.security.dto.AuthResponse;
import com.example.security.dto.DoctorDto;
import com.example.security.dto.RegisterRequest;
import com.example.security.service.AuthService;
import com.example.security.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins =  {"http://localhost:4200", "https://doctor-buddy.onrender.com/"})
public class AuthController {

    private final AuthService authService;

    @GetMapping("/testendpoint")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Depolyed endpoint pho singh!!");
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody DoctorDto req) {
        return ResponseEntity.ok(authService.register(req));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest req) {
        return ResponseEntity.ok(authService.authenticate(req));
    }


}
