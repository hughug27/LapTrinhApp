package com.example.laptrinhapp.Utils;


import com.example.laptrinhapp.model.Student;
import com.example.laptrinhapp.model.Teacher;

public class UserManager {
    private static UserManager instance;
    private Student loggedInStudent;
    private Teacher loggedInTeacher;

    private UserManager() {
        // Private constructor to prevent instantiation
    }

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void setLoggedInStudent(Student student) {
        this.loggedInStudent = student;
    }
    public void setLoggedInTeacher(Teacher teacher) {
        this.loggedInTeacher = teacher;
    }

    public Student getLoggedInStudent() {
        return loggedInStudent;
    }

    public Teacher getLoggedInTeacher(){
        return loggedInTeacher;
    }

    public void logout() {
        this.loggedInStudent = null;
        this.loggedInTeacher = null;
    }
}
