package com.example.security.service;

import com.example.security.dto.DoctorDto;
import com.example.security.dto.PatientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CronServiceImpl implements  CronService{

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final EmailService emailService;

    @Override
    @Scheduled(cron = "0 0 7 * * *")
    public void sendUpcomingAppointments() throws Exception {
            Map<String, List<String>> doctorPatientEmailMap= new HashMap<String, List<String>>();
            List<DoctorDto> docList = doctorService.getAllDoctors();

            for(DoctorDto doc: docList){
                doctorPatientEmailMap.put(doc.getEmail(),extractPatients(doc.getId()));
            }

            emailService.sendDailyEmails(doctorPatientEmailMap);
            System.out.println("cron ran successfully!!");
    }

    private List<String> extractPatients(int id) {
        List<String> patientEmails = patientService.getAllPatients().stream()
                .filter(patientDto -> patientDto.getDoctor().getId() == id
                        && isSameDate(patientDto.getNextCheckupDate(), new Date()))
                .map(PatientDto::getName)
                .collect(Collectors.toList());
        return  patientEmails;
    }

    private boolean isSameDate(Date date1, Date date2) {

        LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return localDate1.isEqual(localDate2);
    }
}
