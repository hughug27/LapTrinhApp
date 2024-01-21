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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.laptrinhapp.Utils.DbManager;
import com.example.laptrinhapp.Utils.UserManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScanAct extends AppCompatActivity {
    String document_std;
    private FirebaseFirestore db = DbManager.getInstance();
    ListView listView;
    CollectionReference coursesRef = db.collection("courses");
    Query query = coursesRef.whereArrayContains("students", UserManager.getInstance().getLoggedInStudent().getStudentId());

    DocumentReference qrRef = db.document("qrcode/content");
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState );
        setContentView(R.layout.scan_act);

        listView = findViewById(R.id.listViewMain);

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<String> data = new ArrayList<>();

                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    // Access the data in each document
                    String documentId = document.getId();

                    String fieldValue = document.getString("name");
                    data.add(fieldValue);
                    ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.itemview,R.id.itemName, data);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            document_std = (String) parent.getItemAtPosition(position);
                            scan_method();
                        }
                    });


                }
            }
        });


    }

    private void scan_method() {
        ScanOptions scanOptions = new ScanOptions();
        scanOptions.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(scanOptions);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result->{
        if(result.getContents()!= null){
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Result");
            qrRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){
                        DocumentSnapshot document = task.getResult();
                        if (document.get("content").toString().equals(result.getContents())){
                            Log.i("TAG"," tc");
                            ScanThanhCong();

                            AlertDialog.Builder builder = new AlertDialog.Builder(ScanAct.this);
                            builder.setTitle("THANH CONG");
                            builder.setMessage("Da diem danh mon: " + document_std);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();

                        }else{
                            Log.i("TAG","ko tc");

                            AlertDialog.Builder builder = new AlertDialog.Builder(ScanAct.this);
                            builder.setTitle("KHONG THANH CONG");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

                        }
                    }
                }
            });


//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            }).show();
        }
    });

    private void ScanThanhCong() {
        DocumentReference docAttendRef = db.collection("students")
                .document(UserManager.getInstance().getLoggedInStudent().getStudentId())
                .collection("attend")
                .document(document_std);
        Map<String, Object> updates = new HashMap<>();
        updates.put(new java.util.Date().toString(), "1");
        docAttendRef.set(updates, SetOptions.merge()).addOnSuccessListener(aVoid -> {
            // Document successfully updated
            System.out.println("Document updated successfully!");
        }).addOnFailureListener(e -> {
            // Handle errors
            System.out.println("Error updating document: " + e.getMessage());
        });
    }
}