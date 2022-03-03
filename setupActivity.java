package com.example.psychesolutionapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.ProgressDialog;
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

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class setupActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 101;
    CircleImageView profileImageView;
    EditText inputUsername, inputFname, inputLname, inputCity;
    Button btnSave;
    Uri imageUri;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mRef;
    StorageReference StorageRef;

    ProgressDialog mLoadingBar;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Setup Profile");

        profileImageView=findViewById(R.id.profile_image);
        inputUsername = findViewById(R.id.inputUsername);
        inputFname = findViewById(R.id.inputFname);
        inputLname = findViewById(R.id.inputLname);
        inputCity = findViewById(R.id.inputCity);
        btnSave = findViewById(R.id.btnSave);
        mLoadingBar = new ProgressDialog(this);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        mRef= FirebaseDatabase.getInstance().getReference().child("Users");
        StorageRef= FirebaseStorage.getInstance().getReference().child("ProfileImages");

        ActivityResultLauncher<Intent> profileImageActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode()== Activity.RESULT_OK){

                            imageUri = result.getData().getData();
                            profileImageView.setImageURI(imageUri);
                        }
                    }
                }
        );

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveData();
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                profileImageActivity.launch(intent);
            }
        });
    }

    private void SaveData() {

        String username = inputUsername.getText().toString();
        String firstName = inputFname.getText().toString();
        String lastName = inputLname.getText().toString();
        String city = inputCity.getText().toString();

        if(username.isEmpty() || username.length()<4){
            inputUsername.setError("Username is invalid");
            inputUsername.requestFocus();

        }
        else if(firstName.isEmpty() || firstName.length()<3){
            inputFname.setError("Please input valid name");
            inputFname.requestFocus();
        }
        else if(lastName.isEmpty() || lastName.length()<3){
            inputLname.setError("Please input valid surname");
            inputLname.requestFocus();
        }
        else if(city.isEmpty() || city.length()<3){
            inputCity.setError("Please input valid name");
            inputCity.requestFocus();
        }
        else if(imageUri==null){
            Toast.makeText(setupActivity.this,"Please Select an Image", Toast.LENGTH_SHORT).show();

        }
        else{
            mLoadingBar.setTitle("Setting up Profile");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
            StorageRef.child(mUser.getUid()).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        StorageRef.child(mUser.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("username",username);
                                hashMap.put("firstName", firstName);
                                hashMap.put("lastName", lastName);
                                hashMap.put("city", city);
                                hashMap.put("profileImage", uri.toString());
                                hashMap.put("status", "offline");
                                hashMap.put("usertype", "Patient");

                                mRef.child(mUser.getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object o) {
                                        Intent intent = new Intent(setupActivity.this, homepatient.class);
                                        startActivity(intent);
                                        mLoadingBar.dismiss();
                                        Toast.makeText(setupActivity.this,"Profile Completed", Toast.LENGTH_SHORT).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        mLoadingBar.dismiss();
                                        Toast.makeText(setupActivity.this,e.toString(), Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }
                        });

                    }

                }
            });


            //-----------------------START

        }
    }

}