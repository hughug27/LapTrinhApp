package com.example.laptrinhapp;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laptrinhapp.Utils.DbManager;
import com.example.laptrinhapp.Utils.UserManager;
import com.example.laptrinhapp.model.Teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {
    private TextView main_tv;
    private TextView textView_Sub;
    private FirebaseFirestore db = DbManager.getInstance();
    DocumentReference documentReference = db.collection("qrcode").document("content");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        main_tv = findViewById(R.id.textViewMain);
        textView_Sub = findViewById(R.id.textViewSub);
        main_tv.setText(UserManager.getInstance().getLoggedInStudent().getName());
        textView_Sub.setText(UserManager.getInstance().getLoggedInStudent().getStudentId());


    }



    public void qr_scan(View view) {
        ScanOptions scanOptions = new ScanOptions();
        scanOptions.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(scanOptions);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(),result->{
        if(result.getContents()!= null){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
            builder.setTitle("Result");
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){
                        DocumentSnapshot document = task.getResult();
                        if (document.get("content").toString().equals(result.getContents())){
                            Log.d(TAG," tc");
                            ScanThanhCong();
//                            builder.setMessage("Thanh Cong");
                        }else{
//                            builder.setMessage("ko Thanh Cong");
                            Log.d(TAG,"ko tc");

                        }
                    }else {
                        builder.setMessage("Task ko Thanh Cong");
                        Log.d(TAG,"ko tc2");

                    }
                }
            });

            builder.setMessage("thanh cong");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
    });

    private void ScanThanhCong() {
        DocumentReference docAttendRef = db.collection("students")
                .document(UserManager.getInstance().getLoggedInStudent().getStudentId())
                .collection("attend")
                .document("B202");
        Map<String, Object> updates = new HashMap<>();
        updates.put(new java.util.Date().toString(), "1");
        docAttendRef.update(updates).addOnSuccessListener(aVoid -> {
            // Document successfully updated
            System.out.println("Document updated successfully!");
        }).addOnFailureListener(e -> {
            // Handle errors
            System.out.println("Error updating document: " + e.getMessage());
        });
    }

    public void qr_scan2(View view) {
        Intent intent = new Intent(MainActivity2.this, ScanAct.class);
        startActivity(intent);

    }

    public void CaNhan(View view) {
        Intent intent = new Intent(this, MyProfile.class);
        this.startActivity(intent);
    }

    public void logOut(View view) {
        UserManager.getInstance().logout();
        onBackPressed();
    }
}