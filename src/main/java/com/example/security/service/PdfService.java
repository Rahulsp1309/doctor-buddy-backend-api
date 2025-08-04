package com.example.security.service;

import com.example.security.dto.PrescriptionRequest;

import java.util.Map;

public interface PdfService {
    public void generatePdfFromHtml(String patientName, String doctorName, Map<String,String> dosages) throws Exception;
}
