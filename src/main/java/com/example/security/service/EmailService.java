package com.example.security.service;

import com.example.security.dto.PrescriptionRequest;

import java.util.List;
import java.util.Map;

public interface EmailService {
    public void sendPatientReminderMail(int id) throws Exception;

    public void sendMailWithPrescription(PrescriptionRequest prescriptionRequest) throws Exception;

    public void sendDailyEmails(Map<String, List<String>> doctorPatientEmailMap) throws Exception;

    public void sendMailWrapper(String toEmail,String templateName, String subject, Map<String, ?> contextVarMap, EmailServiceImpl.MultiPartRequest multiPartRequest) throws Exception;

}
