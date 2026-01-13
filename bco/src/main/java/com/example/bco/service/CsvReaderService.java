package com.example.bco.service;

import com.example.bco.model.Student;
import com.opencsv.CSVReader;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvReaderService {

    public List<Student> readStudents(String csvPath) {
        List<Student> students = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(csvPath))) {

            String[] line;
            reader.readNext(); // skip header

            while ((line = reader.readNext()) != null) {

                Student student = new Student();
                student.setNameEnglish(line[0]);
                student.setNameBangla(line[1]);
                student.setFatherName(line[2]);
                student.setMotherName(line[3]);
                student.setInstitution(line[4]);
                student.setPhone(line[5]);
                student.setEmail(line[6]);
                student.setDateOfBirth(line[7]);
                student.setMedium(line[8]);
                student.setPhotoPath(line[9]);

                students.add(student);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }
}
