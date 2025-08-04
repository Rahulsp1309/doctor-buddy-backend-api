package com.example.security.service;

import com.example.security.dao.PatientRepo;
import com.example.security.dto.PatientDto;
import com.example.security.dto.PrescriptionRequest;
import com.example.security.exception.PdfFileNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final PatientService patientService;
    private final  DoctorService doctorService;
    private final PdfService pdfService;
    private final PatientRepo patientRepo;

    public class MultiPartRequest{
        Boolean isMulti;
        String pdfName;

        public MultiPartRequest(Boolean isMulti, String pdfName) {
            this.isMulti = isMulti;
            this.pdfName = pdfName;
        }
    }

    @Value("${spring.mail.username}")
    private String fromMail;

    @Async
    public void sendPatientReminderMail(int docId) throws Exception {
      List<PatientDto> patients = patientService.getAllPatients()
                .stream()
                .filter(patient -> {
                    Date nextCheckupDate = patient.getNextCheckupDate();
                    Date currentDate = new Date();
                    Date fiveDaysAhead = new Date(currentDate.getTime() + (5 * 24 * 60 * 60 * 1000));
                    return nextCheckupDate != null && nextCheckupDate.before(fiveDaysAhead)
                            && patient.getDoctor() != null && patient.getDoctor().getId() == docId;
                })
                .collect(Collectors.toList());
        String doctorName = patients.get(0).getDoctor().getName();

        for (PatientDto patient : patients) {
            Map<String,Object> contextVarMap = new HashMap<>();
            contextVarMap.put("patient",  patient);
            contextVarMap.put("doctor", doctorName);
            MultiPartRequest multiPartRequest = new MultiPartRequest(false, "");
            sendMailWrapper(patient.getEmail(),"mail-template","Checkup Reminder!!", contextVarMap, multiPartRequest);
            System.out.println("Mail sent Successfully!!!");
        }

    }

    @Async
    public void sendMailWithPrescription(PrescriptionRequest prescriptionRequest) throws Exception, PdfFileNotFoundException {
        try{
            PatientDto patient = patientService.getById(prescriptionRequest.getPatientId());
            String patientName = patient.getName();
            String doctorName = patientService.getById(prescriptionRequest.getPatientId()).getDoctor().getName();
            Map<String, String> dosages = prescriptionRequest.getDosage();

            StringJoiner joiner = new StringJoiner(",");
            for (String value : dosages.values()) {
                joiner.add(value);
            }

            String dosageString = joiner.toString();
            patient.setDosage(dosageString);
            patientService.update(patient, prescriptionRequest.getPatientId());



            pdfService.generatePdfFromHtml(patientName, doctorName, dosages);

            String pdfName = "prescription_pdf_" + patient.getName() + ".pdf";
            MultiPartRequest multiPartRequest = new MultiPartRequest(true, pdfName);

            Map<String,Object> contextVarMap = new HashMap<>();
            contextVarMap.put("patient",  patient);
            contextVarMap.put("doctor", doctorName);
            sendMailWrapper(patient.getEmail(),"prescription-mail-template",
                    "Prescription by Muktai Multispeciality", contextVarMap, multiPartRequest);

            System.out.println("Mail with attachment sent Successfully!!!");
        }catch(Exception e){
            throw new PdfFileNotFoundException("Pdf prescription not able to detect");
        }

    }

    @Override
    public void sendDailyEmails(Map<String, List<String>> doctorPatientEmailMap) throws Exception {
        for (Map.Entry<String, List<String>> entry : doctorPatientEmailMap.entrySet()) {
            String doctorEmail = entry.getKey();
            String doctorName = doctorService.getDoctorByEmail(doctorEmail).getName();
            List<String> patientsName = entry.getValue();

            Map<String,Object> contextVarMap = new HashMap<>();
            contextVarMap.put("patient",  patientsName);
            contextVarMap.put("doctor", doctorName);
            MultiPartRequest multiPartRequest = new MultiPartRequest(false, "");

            sendMailWrapper(doctorEmail,"appointment-reminder-template","Expected appointments for Dr." + doctorName,
                    contextVarMap, multiPartRequest);

            System.out.println("Mail sent Successfully to doctor: " + doctorName);
        }
    }
    public void sendMailWrapper(String toEmail,String templateName, String subject, Map<String, ?> contextVarMap, MultiPartRequest multiPartRequest) throws Exception{
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, multiPartRequest.isMulti);

        mimeMessageHelper.setFrom(fromMail);
        mimeMessageHelper.setSubject(subject);

        mimeMessageHelper.setTo(toEmail);

        Context context = new Context();
        for(Map.Entry<String, ?> entry : contextVarMap.entrySet()){
            context.setVariable(entry.getKey(), entry.getValue());
        }

        String processedString = templateEngine.process(templateName, context);

        mimeMessageHelper.setText(processedString, true);
        if(multiPartRequest != null && multiPartRequest.isMulti == true){
            String pdfName = multiPartRequest.pdfName;
            mimeMessageHelper.addAttachment(pdfName, new File( System.getProperty("java.io.tmpdir"),pdfName));
        }

        mailSender.send(mimeMessage);
    }
}
