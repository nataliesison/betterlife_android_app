package com.example.psychesolutionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class checkusertypepage extends AppCompatActivity {

    ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mRef;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkusertypepage);

        progressBar = findViewById(R.id.progressBarLoad);
        progressBar.setMax(100);
        progressBar.setProgress(0);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("Users");
        user_id = mUser.getUid();

        final Thread thread = new Thread(){
            public void run(){
                try {
                    for(int i = 0; i<100; i++){
                        progressBar.setProgress(i);
                        sleep(20);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {

                    if (user_id == null) {
                        Toast.makeText(checkusertypepage.this,"Login NOT Successful", Toast.LENGTH_SHORT).show();
                        Intent register = new Intent(checkusertypepage.this, MainActivity.class);
                        register.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(register);
                    }
                    else {
                        mRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){

                                    String typeuser = snapshot.child("usertype").getValue().toString();

                                    Intent intent;
                                    if(typeuser.equals("Patient")){
                                        intent = new Intent(checkusertypepage.this, homepatient.class);

                                    }
                                    else if(typeuser.equals("Doctor")){
                                        intent = new Intent(checkusertypepage.this, activity_homedoc.class);

                                    }
                                    else if(typeuser.equals("Admin")){
                                        intent = new Intent(checkusertypepage.this, homeAdmin.class);

                                    }
                                    else{
                                        intent = new Intent(checkusertypepage.this, MainActivity.class);
                                    }
                                    startActivity(intent);
                                    finish();


                                }
                                else{
                                    Intent intent = new Intent(checkusertypepage.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                }
            }
        };
        thread.start();


    }


    public void openHomePatient()
    {
        Intent register = new Intent(this, homepatient.class);
        register.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(register);
    }

    public void openHomeDoc()
    {
        Intent register = new Intent(this, activity_homedoc.class);
        register.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(register);
    }


}