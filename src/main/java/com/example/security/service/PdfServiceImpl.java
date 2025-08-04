package com.example.security.service;

import com.example.security.dto.PrescriptionRequest;
import com.example.security.exception.PdfFileNotFoundException;
import jakarta.mail.internet.MimeBodyPart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PdfServiceImpl implements PdfService{

    private final TemplateEngine templateEngine;

    public void generatePdfFromHtml(String patientName,String doctorName,Map<String,String> dosages) throws Exception, PdfFileNotFoundException {
        Context context = new Context();
        context.setVariable("patient", patientName);
        context.setVariable("doctor", doctorName);
        context.setVariable("dosages", dosages);
        String processedString = templateEngine.process("prescription-pdf-template", context);

        String outputFolder =  "C:\\Users\\rspat\\Downloads" + File.separator + "prescription_pdf_" + patientName + ".pdf";
      //  String outputFolder = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "prescription_pdf_" + patientName + ".pdf";
      //  String outputFolder = "/temp" + File.separator + "prescription_pdf_" + patientName + ".pdf";

     //   File outputFolder = new File(System.getProperty("java.io.tmpdir"), "prescription_pdf_"+ patientName+".pdf");

        OutputStream outputStream = new FileOutputStream(outputFolder);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(processedString);
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();

    }
}
