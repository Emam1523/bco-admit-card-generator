package com.example.bco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendAdmitCard(String toEmail, String pdfPath) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("Chemistry Olympiad Admit Card");
            helper.setText("Please find your admit card attached.");

            FileSystemResource file = new FileSystemResource(new File(pdfPath));
            helper.addAttachment("AdmitCard.pdf", file);

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

