package com.example.laptrinhapp;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity2 extends AppCompatActivity {
    private TextView main_tv;
    private FirebaseFirestore db = DbManager.getInstance();
    DocumentReference documentReference = db.collection("qrcode").document("content");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        main_tv = findViewById(R.id.textViewMain);
        main_tv.setText(UserManager.getInstance().getLoggedInStudent().getName());


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
                            builder.setMessage("Thanh Cong");
                        }else{
                            builder.setMessage("ko Thanh Cong");
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
}