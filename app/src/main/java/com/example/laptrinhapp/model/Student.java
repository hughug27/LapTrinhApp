package com.example.laptrinhapp.model;

import com.google.firebase.firestore.DocumentSnapshot;

import java.lang.reflect.Array;

public class Student extends People {
    private String studentId;
    private String password;

    public Student(String name, String studentId,String password) {
        super(name);
        this.studentId = studentId;
        this.password = password;
    }
    public static Student fromDocumentSnapshot(DocumentSnapshot document) {
        String name = document.getString("name");
        String password = document.getString("password");
        String studentId = document.getString("studentId");

        return new Student(name, password, studentId);
    }

    public String getStudentId() {
        return studentId;
    }

    public String getPassword() {
        return password;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
