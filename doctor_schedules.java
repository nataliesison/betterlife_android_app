package com.example.psychesolutionapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class doctor_schedules extends AppCompatActivity {

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    CircleImageView profileImageHeader;
    TextView usernameHeader, docName;
    Uri imageUri;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mUserRef, postRef, likeRef, commentRef;
    CircleImageView docDisplayPic;
    ImageView docSchedBtn, docPendingaptBtn, docUpdateProfBtn, addNewArticlebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_schedules);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");

        docDisplayPic = findViewById(R.id.docDisplayPic);
        docSchedBtn = findViewById(R.id.docSchedBtn);
        docPendingaptBtn = findViewById(R.id.docPendingaptBtn);
        docUpdateProfBtn = findViewById(R.id.docUpdateProfBtn);
        addNewArticlebtn = findViewById(R.id.addNewArticleBtn);
        docName = findViewById(R.id.docName);

        mUserRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String profPic = String.valueOf(snapshot.child("profileImage").getValue());
                    String firstName = String.valueOf(snapshot.child("firstName").getValue());
                    String lastName = String.valueOf(snapshot.child("lastName").getValue());
                    String fullName = firstName +" "+lastName;

                    Picasso.get().load(profPic).into(docDisplayPic);
                    docName.setText(fullName);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        docUpdateProfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(doctor_schedules.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        addNewArticlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(doctor_schedules.this, addArticle.class);
                startActivity(intent);
            }
        });
        docPendingaptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(doctor_schedules.this, doc_sched_list_pending.class);
                startActivity(intent);
            }
        });
        docSchedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(doctor_schedules.this, doctor_sched_list.class);
                startActivity(intent);
            }
        });

    }

}