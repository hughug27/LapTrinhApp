package com.example.laptrinhapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptrinhapp.model.Student;
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

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String ID = "id";
    private static final String PASSWORD = "password";
    private EditText id;
    private EditText password;
    private Button button;


    private Button getButton;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference std_collection_ref = db.collection("students");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = findViewById(R.id.edit_text_id);
        password = findViewById(R.id.edit_text_password);
        button = findViewById(R.id.button);


    }

    public void add(View view) {
        String id_data = id.getText().toString();
        String pass_data = password.getText().toString();

        Student student1 = new Student("name",id_data,pass_data);
        TextView textView = findViewById(R.id.textView);
        textView.setText(student1.getName()+" "+ student1.getStudentId()+ student1.getPassword());

        std_collection_ref.document(student1.getStudentId()).set(student1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_SHORT).show();
                        System.out.println(e.toString());
                    }
                });
    }

    public void get(View view) {
        CollectionReference collectionReference = db.collection("courses");
        collectionReference
                .whereEqualTo("course_name", "math")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                List<Student> std_list = (List<Student>) document.get("students");
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }
}