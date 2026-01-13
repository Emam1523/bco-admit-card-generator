package com.example.bco.controller;

import com.example.bco.model.Student;
import com.example.bco.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdmitCardController {

    @Autowired
    private CsvReaderService csvReaderService;

    @Autowired
    private RollNumberService rollNumberService;

    @Autowired
    private AdmitCardPdfService pdfService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/bco")
    public String generateAdmitCards() {

        List<Student> students =
                csvReaderService.readStudents("src/main/resources/students.csv");

        rollNumberService.assignRollNumbers(students);

        for (Student student : students) {
            String pdfPath = pdfService.generateAdmitCard(student);
            emailService.sendAdmitCard(student.getEmail(), pdfPath);
        }

        return "Admit cards generated and emailed successfully.";
    }
}
