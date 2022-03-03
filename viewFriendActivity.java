package com.example.psychesolutionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class viewFriendActivity extends AppCompatActivity {

    DatabaseReference mUserRef, requestRef, friendRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String profileImageurl, username, address;

    CircleImageView profImage;
    TextView Username, address2;
    Button btnPerform, btnDecline;
    String currentState = "nothing_happen";
    String firstnamePL, lastnamePL, fullnamePL;
    String userID;

    String URL = "https://fcm.googleapis.com/fcm/send";
    RequestQueue requestQueue;

    String myProfileImageUrl, myUsername, myAddress, myFirstname, myLastname, myfullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_friend);
        userID = getIntent().getStringExtra("userKey");
        //Toast.makeText(this, ""+userID, Toast.LENGTH_SHORT).show();

        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        requestRef = FirebaseDatabase.getInstance().getReference().child("Requests");
        friendRef = FirebaseDatabase.getInstance().getReference().child("Patients");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        btnPerform = findViewById(R.id.btnPerform);
        btnDecline = findViewById(R.id.btnDecline);

        profImage = findViewById(R.id.profileImagePatient);
        Username = findViewById(R.id.usernamepatient);
        address2 = findViewById(R.id.address);
        
        LoadUser();
        LoadMyProfile();
        hideBtn(userID);

        btnPerform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerformAction(userID);
            }
        });
        CheckUserExistence(userID);
        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Unfriend(userID);
            }
        });

    }

    private void hideBtn(String userID){
        friendRef.child(mUser.getUid()).child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    btnPerform.setText("Add Patient");
                    btnDecline.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        friendRef.child(userID).child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(!snapshot.exists()){
                    btnPerform.setText("Add Patient");
                    btnDecline.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void Unfriend(String userID) {
            friendRef.child(mUser.getUid()).child(userID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        friendRef.child(userID).child(mUser.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(viewFriendActivity.this, "Removed to Patient List", Toast.LENGTH_SHORT).show();
                                    currentState = "nothing_happen";
                                    btnPerform.setText("Add Patient");
                                    btnDecline.setVisibility(View.GONE);
                                }

                            }
                        });

                    }
                }
            });

        }

    //--------------------END

    private void CheckUserExistence(String userID) {

        friendRef.child(mUser.getUid()).child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    currentState = "friend";
                    //btnPerform.setText("Send Message");
                    btnDecline.setText("Remove to Patient List");
                    btnDecline.setVisibility(View.VISIBLE);
                    btnPerform.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(currentState.equals("nothing_happen")){
            currentState = "nothing_happen";
            btnPerform.setText("Add Patient");
            btnDecline.setVisibility(View.GONE);

        }
    }

    private void PerformAction(String userID) {

        final HashMap hashMap = new HashMap();
        hashMap.put("status", "friend");
        hashMap.put("username", username);
        hashMap.put("profileImage", profileImageurl);
        hashMap.put("fullname", fullnamePL);

        final HashMap hashMap1 = new HashMap();
        hashMap1.put("status", "friend");
        hashMap1.put("username", myUsername);
        hashMap1.put("profileImage", myProfileImageUrl);
        hashMap1.put("fullname", myfullname);
        friendRef.child(mUser.getUid()).child(userID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    friendRef.child(userID).child(mUser.getUid()).updateChildren(hashMap1).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            Toast.makeText(viewFriendActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                            currentState = "Friend";
                            //btnPerform.setText("Send Message");
                            btnDecline.setText("Remove to Patient List");
                            btnDecline.setVisibility(View.VISIBLE);
                            btnPerform.setVisibility(View.GONE);
                        }
                    });

                }
            }
        });

//        if(currentState.equals("friend")){
//            Intent intent = new Intent(viewFriendActivity.this, ChatActivity.class);
//            intent.putExtra("OtherUserID", userID);
//            startActivity(intent);
//
//        }
    }

    private void LoadUser() {
        mUserRef.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    profileImageurl = snapshot.child("profileImage").getValue().toString();
                    username = snapshot.child("username").getValue().toString();
                    address = snapshot.child("city").getValue().toString();
                    firstnamePL = snapshot.child("firstName").getValue().toString();
                    lastnamePL = snapshot.child("lastName").getValue().toString();
                    fullnamePL = firstnamePL + " " + lastnamePL;


                    Picasso.get().load(profileImageurl).into(profImage);
                    Username.setText(username);
                    address2.setText(address);
                }
                else{
                    Toast.makeText(viewFriendActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(viewFriendActivity.this, ""+error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void LoadMyProfile() {
        mUserRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    myProfileImageUrl = snapshot.child("profileImage").getValue().toString();
                    myUsername = snapshot.child("username").getValue().toString();
                    myAddress = snapshot.child("city").getValue().toString();
                    myFirstname = snapshot.child("firstName").getValue().toString();
                    myLastname = snapshot.child("lastName").getValue().toString();
                    myfullname = myFirstname + " " + myLastname;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}