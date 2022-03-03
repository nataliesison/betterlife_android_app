package com.example.psychesolutionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;

public class schedConfirmation extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mUserRef, mSchedulerRef, UsersReference;
    Button btnConfirm;
    TextView patientName, dateApt, timeApt,cancelApt, chosenSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sched_confirmation);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mSchedulerRef = FirebaseDatabase.getInstance().getReference().child("Patient_Sched");
        btnConfirm = findViewById(R.id.btnConfirmApt);
        cancelApt = findViewById(R.id.cancelApt);
        chosenSession = findViewById(R.id.chosenSession);

        String timeChoice = getIntent().getStringExtra("timeChoice");
        String dateChoice = getIntent().getStringExtra("dateChoice");
        String chosenSched = getIntent().getStringExtra("chosenSched");
        String chosenSesh = getIntent().getStringExtra("chosenSesh");

        mUserRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String firstName = snapshot.child("firstName").getValue().toString();
                String LastName = snapshot.child("lastName").getValue().toString();

                String fullName = firstName + " " + LastName;

                patientName = findViewById(R.id.patientNameApt);
                timeApt = findViewById(R.id.timeApt);
                dateApt = findViewById(R.id.dateApt);

                patientName.setText(fullName);
                timeApt.setText(timeChoice);
                dateApt.setText(dateChoice);
                chosenSession.setText(chosenSesh);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cancelApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(schedConfirmation.this, scheduler.class);
                startActivity(intent);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(schedConfirmation.this);
                alertDialogBuilder.setMessage("Are you sure you want to book a consultation?");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                addnewSched();
                                //Toast.makeText(addArticle.this,"You clicked yes button",Toast.LENGTH_LONG).show();
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

    public void addnewSched(){
        String timeChoice = getIntent().getStringExtra("timeChoice");
        String dateChoice = getIntent().getStringExtra("dateChoice");
        String chosenSched = getIntent().getStringExtra("chosenSched");
        String chosenSesh = getIntent().getStringExtra("chosenSesh");

        mUserRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String username=snapshot.child("username").getValue().toString();
                    String userprofileImg=snapshot.child("profileImage").getValue().toString();
                    String firstName = snapshot.child("firstName").getValue().toString();
                    String lastName = snapshot.child("lastName").getValue().toString();

                    final FirebaseDatabase database=FirebaseDatabase.getInstance();
                    UsersReference=database.getReference().child("Patient_Sched");

                    HashMap userMap=new HashMap();
                    userMap.put("uid",mUser.getUid());
                    userMap.put("usernamee",username);
                    userMap.put("firstName", firstName);
                    userMap.put("lastName", lastName);
                    userMap.put("profileImage",userprofileImg);
                    userMap.put("time",timeChoice);
                    userMap.put("date",dateChoice);
                    userMap.put("session", chosenSesh);

                    UsersReference.child(chosenSched).updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){

                                Toast.makeText(schedConfirmation.this,"Scheduled an appointment successfully",Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(schedConfirmation.this, scheduler.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(schedConfirmation.this,task.getException().toString(),Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}