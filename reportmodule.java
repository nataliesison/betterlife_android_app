package com.example.psychesolutionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class reportmodule extends AppCompatActivity {
    Toolbar toolbar;

    Button btnSave, btnCancel;
    TextView patientName, patientAge, patientGender, patientDateDiagnosis;
    EditText patientSession, patientTimeStart, patientTimeEnd, patientDiagnosis;
    String patientIDpass, patientAgepass, patientGenderpass, patientFullpass;
    DatabaseReference sessionRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportmodule);

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Patient Sessions History");

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        sessionRef = FirebaseDatabase.getInstance().getReference().child("Sessions").child(mUser.getUid());

        btnSave = findViewById(R.id.saveDiagnosisBtn);
        btnCancel = findViewById(R.id.cancelDiagnosisBtn);
        patientName = findViewById(R.id.patientFullname);
        patientAge = findViewById(R.id.patientAge);
        patientGender = findViewById(R.id.patientGender);
        patientDateDiagnosis = findViewById(R.id.patientDateDiagnosis);
        patientSession = findViewById(R.id.patientSession);
        patientTimeStart = findViewById(R.id.patientTimeStart);
        patientTimeEnd = findViewById(R.id.patientTimeEnd);
        patientDiagnosis = findViewById(R.id.diagnosisMultiLine);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(reportmodule.this);
                alertDialogBuilder.setMessage("Add New Diagnosis Record?");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                addNewDiagnosis();
                                Toast.makeText(reportmodule.this,"You clicked yes button", Toast.LENGTH_LONG).show();
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

    }

    private void addNewDiagnosis() {
        String timeStart = patientTimeStart.getText().toString();
        String timeEnd = patientTimeEnd.getText().toString();
        String sessionSesh = patientSession.getText().toString();
        String dateSesh = patientDateDiagnosis.getText().toString();
        String diagnosed = patientDiagnosis.getText().toString();
        String Session_Patient = patientSession.getText().toString();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("mmm-dd-yy");
        String strDate= formatter.format(date);

        Intent i = new Intent();

        patientIDpass = i.getStringExtra("patientUserID");
        patientAgepass = i.getStringExtra("agePatient");
        patientGenderpass = i.getStringExtra("patientGender");
        patientFullpass = i.getStringExtra("patientFullname");

        patientName.setText(patientFullpass);
        patientAge.setText(patientAgepass);
        patientGender.setText(patientGenderpass);
        patientDateDiagnosis.setText(strDate);





        if(TextUtils.isEmpty(timeStart)||TextUtils.isEmpty(timeEnd)||TextUtils.isEmpty(sessionSesh)){
            Toast.makeText(reportmodule.this,"Please fill up the fields.",Toast.LENGTH_LONG).show();
            return;
        }
        String sessionPushID = sessionRef.child(patientIDpass).push().getKey();

        sessionRef.child(patientIDpass).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                DatabaseReference seshRef = sessionRef.child(sessionPushID);

                HashMap hashMap = new HashMap();
                hashMap.put("Patient_ID", patientIDpass);
                hashMap.put("Patient_name", patientFullpass);
                hashMap.put("Patient_age", patientAgepass);
                hashMap.put("Patient_gender", patientGenderpass);
                hashMap.put("date_diagnosis", dateSesh);
                hashMap.put("Diagnosis", diagnosed);
                hashMap.put("Started_Time", timeStart);
                hashMap.put("Ended_Time", timeEnd);
                hashMap.put("Session_Pat", Session_Patient);

                seshRef.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(reportmodule.this,"New Diagnosis added successfully",Toast.LENGTH_LONG).show();
                            patientName.setText(" ");
                            patientAge.setText(" ");
                            patientGender.setText(" ");
                            patientSession.setText(" ");
                            patientTimeStart.setText(" ");
                            patientTimeEnd.setText(" ");
                            patientDiagnosis.setText(" ");
                            return;
                        }
                        else {
                            Toast.makeText(reportmodule.this,task.getException().toString(),Toast.LENGTH_LONG).show();
                            return;
                        }

                    }
                });






            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}