package com.example.laptrinhapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptrinhapp.Utils.UserManager;
import com.example.laptrinhapp.model.Course;
import com.example.laptrinhapp.model.Student;
import com.example.laptrinhapp.model.Teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String ID = "id";
    private static final String PASSWORD = "password";
    private EditText id;
    private EditText password;
    private Button login_button;
    private Button getButton;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = findViewById(R.id.edit_text_id);
        password = findViewById(R.id.edit_text_password);
        login_button = findViewById(R.id.button);



    }

    public void login(View view){
        String id_data = id.getText().toString();
        String pass_data = password.getText().toString();

        if (Integer.valueOf(id_data)>20000000){
            DocumentReference documentReference = db.collection("students").document(id_data);
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Student current_std = Student.fromDocumentSnapshot(document);
                            UserManager.getInstance().setLoggedInStudent(current_std);
                            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "User khong ton tai", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }else{
            DocumentReference documentReference = db.collection("teachers").document(id_data);
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Teacher current_teacher = Teacher.fromDocumentSnapshot(document);
                            UserManager.getInstance().setLoggedInTeacher(current_teacher);
                            Intent intent = new Intent(MainActivity.this, TeacherAct0.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(MainActivity.this, "User khong ton tai", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

//    public void add(View view) {
//        String id_data = id.getText().toString();
//        String pass_data = password.getText().toString();
//
//        Student student1 = new Student("name",id_data,pass_data);
//
//        textView.setText(student1.getName()+" "+ student1.getStudentId()+ student1.getPassword());
//
//        std_collection_ref.document(student1.getStudentId()).set(student1)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_SHORT).show();
//                        System.out.println(e.toString());
//                    }
//                });
//    }

//    public void get(View view) {
//        CollectionReference collectionReference = db.collection("courses");
//        collectionReference
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Course course = new Course(document.get("course_name").toString(), (ArrayList<String>) document.get("students"));
////                                Log.d(TAG, course.getStudent().toString());
//                                for (String str : course.getStudent()) {
//                                    get_std_name(str);
//                                }
//
//                            }
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//
//
//    }
//
//    public void get_std_name(String id){
//        db.collection("students").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//
//                        Student student = new Student(document.get("name").toString(),document.get("studentId").toString(),document.get("password").toString());
//                        textView.setText(student.getName()+" "+ student.getStudentId()+ student.getPassword());
//
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });
//    }
}