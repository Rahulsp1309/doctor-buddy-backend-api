package com.example.security.controller;


import com.example.security.dto.DoctorDto;
import com.example.security.dto.PrescriptionRequest;
import com.example.security.service.DoctorService;
import com.example.security.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
@CrossOrigin(origins =  {"http://localhost:4200", "https://doctor-buddy.onrender.com/"})
public class DoctorController {

    private final DoctorService doctorService;
    private final EmailService emailService;

    @GetMapping("/check-username/{email}")
    public ResponseEntity<Boolean> getDoctorByEmail(@PathVariable String email){
       Boolean isPresent = doctorService.getDoctorByEmail(email) == null ? false : true ;
        return new ResponseEntity<Boolean>(isPresent, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/getDoctorById/{id}")
    public ResponseEntity<DoctorDto> getDoctorByEmail(@PathVariable int id){
        return new ResponseEntity<DoctorDto>(doctorService.getDoctorById(id), HttpStatusCode.valueOf(200));
    }

   @PostMapping("/sendMailById/{id}")
    public void sendMail(@PathVariable int id) throws Exception {
        emailService.sendPatientReminderMail(id);
    }

    @PostMapping("/sendMailWithPrescription")
    public  void generatePrescription(@RequestBody PrescriptionRequest prescriptionRequest) throws Exception {
        System.out.println(prescriptionRequest);
        emailService.sendMailWithPrescription(prescriptionRequest);
    }

}
