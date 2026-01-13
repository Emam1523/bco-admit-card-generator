package com.example.bco.service;

import com.example.bco.model.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RollNumberService {

    public void assignRollNumbers(List<Student> students) {

        int counter = 1;
        int year = 2026;

        for (Student student : students) {
            String roll = String.format("BCO-%d-%03d", year, counter);
            student.setRollNumber(roll);
            counter++;
        }
    }
}
