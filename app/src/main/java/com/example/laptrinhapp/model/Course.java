package com.example.laptrinhapp.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseName;
    private ArrayList<String> student;

    public Course(String courseName, ArrayList<String> student) {
        this.courseName = courseName;
        this.student = student;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public ArrayList<String> getStudent() {
        return student;
    }

    public void setStudent(ArrayList<String> student) {
        this.student = student;
    }
}
