package com.example.laptrinhapp.model;

import com.google.firebase.firestore.DocumentSnapshot;

public class Teacher extends People{
    String password;
    String teacherId;

    public Teacher(String name) {
        super(name);
    }

    public Teacher(String name, String password, String teacherId) {
        super(name);
        this.password = password;
        this.teacherId = teacherId;
    }

    public static Teacher fromDocumentSnapshot(DocumentSnapshot document) {
        String name = document.getString("name");
        String password = document.getString("password");
        String teacherId = document.getString("teacherId");

        return new Teacher(name, password, teacherId);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
}
