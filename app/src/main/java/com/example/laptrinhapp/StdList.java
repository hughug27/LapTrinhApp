package com.example.laptrinhapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.laptrinhapp.Recycler.CourseViewAdapter;
import com.example.laptrinhapp.Recycler.StdAdapter;
import com.example.laptrinhapp.Utils.DbManager;
import com.example.laptrinhapp.Utils.UserManager;
import com.example.laptrinhapp.model.Course;
import com.example.laptrinhapp.model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
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
import java.util.Collection;
import java.util.List;

public class StdList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseFirestore db = DbManager.getInstance();

    public List<Student> studentsList = new ArrayList<>();
    ArrayList<String> arrayList;
    CollectionReference collectionReference = db.collection("courses");

    String course_name;
    List<String> list = new ArrayList<>();
    DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_std_list);
        recyclerView = findViewById(R.id.recyclerViewStd);
        Intent intent = getIntent();
        course_name = intent.getStringExtra("course_name");
        System.out.println("intent nhan dc: " +course_name);
        docRef = db.collection("courses").document(course_name);
        getStdInClass();
//        getStdInfo();

        studentsList.add(new Student("Nguyen Van Tam"));
        studentsList.add(new Student("Tran Minh Chin"));
        studentsList.add(new Student("Nguyen Thi Anh"));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new StdAdapter(getApplicationContext(),studentsList));
    }

    private void getStdInClass() {
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    arrayList = (ArrayList<String>) documentSnapshot.get("students");
//                    System.out.println(arrayList);

                }
            }
        });
    }

    private void getStdInfo() {
        for (String e : arrayList){
            DocumentReference docRef = db.collection("students").document(e);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    studentsList.add(documentSnapshot.toObject(Student.class));
                    System.out.println(studentsList.get(0));
                }
            });

        }
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        recyclerView.setAdapter(new StdAdapter(getApplicationContext(),studentsList));
    }
}
