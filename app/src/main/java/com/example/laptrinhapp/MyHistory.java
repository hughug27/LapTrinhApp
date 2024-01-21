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

public class MyHistory extends AppCompatActivity {
    TextView textView;
    private FirebaseFirestore db = DbManager.getInstance();

    String data = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_history);
        textView = findViewById(R.id.textViewHistory);

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
                            textView.setText(data);
                        }
                    }

                });
    }
}