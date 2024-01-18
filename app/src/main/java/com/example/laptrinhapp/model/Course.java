package com.example.laptrinhapp.model;

import java.lang.reflect.Array;
import java.util.List;

public class Course {
    private String courseName;
    private List<Student> student;

    public Course(String courseName, List<Student> student) {
        this.courseName = courseName;
        this.student = student;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<Student> getStudent() {
        return student;
    }

    public void setStudent(List<Student> student) {
        this.student = student;
    }
}
