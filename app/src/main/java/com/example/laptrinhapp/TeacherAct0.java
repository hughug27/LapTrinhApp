package com.example.laptrinhapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.laptrinhapp.Recycler.CourseViewAdapter;
import com.example.laptrinhapp.Utils.DbManager;
import com.example.laptrinhapp.Utils.UserManager;
import com.example.laptrinhapp.model.Course;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TeacherAct0 extends AppCompatActivity {
    private FirebaseFirestore db = DbManager.getInstance();
    private RecyclerView recyclerView;
    private TextView textView;
    CollectionReference collectionReference = db.collection("courses");
    public List<Course> courseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_act0);
        recyclerView = findViewById(R.id.recyclerView);
        textView = findViewById(R.id.textview);

        textView.setText("Xin chao: " + UserManager.getInstance().getLoggedInTeacher().getName());
        getCourses();
//

    }

    private void getCourses() {
        collectionReference
                .whereEqualTo("teacher",UserManager.getInstance().getLoggedInTeacher().getTeacherId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots){
                    Course course = queryDocumentSnapshot.toObject(Course.class);
                    courseList.add(course);
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(new CourseViewAdapter(getApplicationContext(),courseList));

            }

        });


    }

    public void themLop(View view) {

    }
}