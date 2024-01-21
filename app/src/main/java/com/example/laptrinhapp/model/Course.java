package com.example.laptrinhapp.model;

import java.util.List;

public class Course {
    private String id;
    private String name;
    private List<String> students;
    private String teacher;

    public Course(String id,String name, List<String> students, String teacher) {
        this.id = id;
        this.name = name;
        this.students = students;
        this.teacher = teacher;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Course() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
