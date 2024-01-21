package com.example.laptrinhapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.laptrinhapp.Utils.DbManager;
import com.example.laptrinhapp.Utils.UserManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MyProfile extends AppCompatActivity {
    TextView textView;
    TextView textView2;
    TextView textView3;
    private FirebaseFirestore db = DbManager.getInstance();

    String data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        textView = findViewById(R.id.textViewTop);
        textView2 = findViewById(R.id.textViewBot);
        textView3 = findViewById(R.id.profile3);

        db.collection("students")
                .document(UserManager.getInstance().getLoggedInStudent().getStudentId())
                        .collection("attend")
                                .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        data += document.getId() + " " + document.getData() + "\n\n";
                                                        System.out.println(document.getId()+" "+document.getData());
                                                    }
                                                    textView3.setText(data);
                                                }
                                            }

        });










        textView.setText("Ho ten: "+UserManager.getInstance().getLoggedInStudent().getName());
        textView2.setText("Mssv: "+UserManager.getInstance().getLoggedInStudent().getStudentId());


    }
}