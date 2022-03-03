package com.example.psychesolutionapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    CircleImageView profileImageView;
    EditText inputUsernameProf, inputFirstNameProf, inputLastNameProf, inputCityProf;
    Button btnUpdateProf;

    ProgressDialog mLoadingBar;

    DatabaseReference mUserRef;
    StorageReference StorageRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mLoadingBar = new ProgressDialog(this);

        profileImageView = findViewById(R.id.circleImageView);
        inputUsernameProf = findViewById(R.id.inputUsernameProf);
        inputFirstNameProf = findViewById(R.id.inputFirstNameProf);
        inputLastNameProf = findViewById(R.id.inputLastNameProf);
        inputCityProf = findViewById(R.id.inputCityProf);
        btnUpdateProf = findViewById(R.id.btnUpdateProf);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        StorageRef = FirebaseStorage.getInstance().getReference().child("ProfileImages");

        ActivityResultLauncher<Intent> profileImageActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {

                            imageUri = result.getData().getData();
                            profileImageView.setImageURI(imageUri);
                        }
                    }
                }
        );

        mUserRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    String profileImageUrl = snapshot.child("profileImage").getValue().toString();
                    String city = snapshot.child("city").getValue().toString();
                    String lastName = snapshot.child("lastName").getValue().toString();
                    String firstName = snapshot.child("firstName").getValue().toString();
                    String username = snapshot.child("username").getValue().toString();

                    Picasso.get().load(profileImageUrl).into(profileImageView);
                    inputCityProf.setText(city);
                    inputUsernameProf.setText(username);
                    inputFirstNameProf.setText(firstName);
                    inputLastNameProf.setText(lastName);

                } else {
                    Toast.makeText(ProfileActivity.this, "Data does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(ProfileActivity.this, "Data does not exist" + error.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        });

//        profileImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                profileImageActivity.launch(intent);
//            }
//        });

        btnUpdateProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DatabaseReference usertypeRef = mUserRef.child(mUser.getUid()).child("usertype");
//                String userType = usertypeRef.toString();
//
                SaveData();

            }
        });

    }

    private void SaveData() {

        String username = inputUsernameProf.getText().toString();
        String firstName = inputFirstNameProf.getText().toString();
        String lastName = inputLastNameProf.getText().toString();
        String city = inputCityProf.getText().toString();

        if (username.isEmpty() || username.length() < 4) {
            inputUsernameProf.setError("Username is invalid");
            inputUsernameProf.requestFocus();

        } else if (firstName.isEmpty() || firstName.length() < 3) {
            inputFirstNameProf.setError("Please input valid name");
            inputFirstNameProf.requestFocus();
        } else if (lastName.isEmpty() || lastName.length() < 3) {
            inputLastNameProf.setError("Please input valid surname");
            inputLastNameProf.requestFocus();
        } else if (city.isEmpty() || city.length() < 3) {
            inputCityProf.setError("Please input valid name");
            inputCityProf.requestFocus();
        } else {
            mLoadingBar.setTitle("Setting up Profile");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            mUserRef.child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("username", username);
                    hashMap.put("firstName", firstName);
                    hashMap.put("lastName", lastName);
                    hashMap.put("city", city);
                    hashMap.put("status", "offline");

                    String userType = snapshot.child("usertype").getValue().toString();
                    mUserRef.child(mUser.getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Intent intent;

                            if (userType.equals("Doctor")) {
                                intent = new Intent(ProfileActivity.this, activity_homedoc.class);

                            } else if (userType.equals("Patient")) {
                                intent = new Intent(ProfileActivity.this, homepatient.class);
                            } else {
                                intent = new Intent(ProfileActivity.this, homeAdmin.class);
                            }
                            //startActivity(intent);
                            mLoadingBar.dismiss();
                            Toast.makeText(ProfileActivity.this, "Update Completed", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mLoadingBar.dismiss();
                            Toast.makeText(ProfileActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
//    private void updateDp(String userType){
//
//        StorageRef.child(mUser.getUid()).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                if(task.isSuccessful()){
//                    StorageRef.child(mUser.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//
//                            HashMap<String, Object> hashMap = new HashMap<>();
//                            hashMap.put("profileImage", uri.toString());
//
//                            mUserRef.child(mUser.getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
//                                @Override
//                                public void onSuccess(Object o) {
//                                    Intent intent;
//                                    if(userType.equals("Doctor")){
//                                        intent = new Intent(ProfileActivity.this, activity_homedoc.class);
//
//                                    }
//                                    else if(userType.equals("Patient")){
//                                        intent = new Intent(ProfileActivity.this, homepatient.class);
//                                    }
//                                    else{
//                                        intent = new Intent(ProfileActivity.this, homeAdmin.class);
//                                    }
//                                    //startActivity(intent);
//                                    mLoadingBar.dismiss();
//                                    Toast.makeText(ProfileActivity.this,"Profile Completed", Toast.LENGTH_SHORT).show();
//
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    mLoadingBar.dismiss();
//                                    Toast.makeText(ProfileActivity.this,e.toString(), Toast.LENGTH_SHORT).show();
//
//                                }
//                            });
//
//                        }
//                    });
//
//                }
//
//            }
//        });
    //end of storageref

}