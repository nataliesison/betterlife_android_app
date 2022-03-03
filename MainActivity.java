package com.example.psychesolutionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.app.ProgressDialog;
import android.app.ProgressDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView forSignup, forgotPass;
    Button loginbtn;
    EditText email;
    EditText password;
    String emailPattern = "[a-zA-Z._]+@[a-z]+\\.+[a-z]+";
    String emailPattern1 = "[0-9]+[a-zA-Z._]+@[a-z]+\\.+[a-z]+";
    String emailPattern2 = "[a-zA-Z._]+[0-9]+@[a-z]+\\.+[a-z]+";

    signupInfo signUp;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mRef;


    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("Users");

        loginbtn = findViewById(R.id.loginbtn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        forSignup= findViewById(R.id.forsignup);
        forgotPass = findViewById(R.id.forgotPass);
        forSignup.setOnClickListener(v -> openSignup());

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perfLogin();

            }
        });
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForgotPasswordActivity.class));
            }
        });
    }

    private void perfLogin() {
        String emailAdd = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if(!emailAdd.matches(emailPattern)&&!emailAdd.matches(emailPattern1)&&!emailAdd.matches(emailPattern2)){
            email.setError("Email Address invalid");
            email.requestFocus();
        }
        else
            if(pass.isEmpty()||pass.length()<8){
            password.setError("Input proper password");
            password.requestFocus();
        }
        else{
            progressDialog.setMessage("Please wait while logging in...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(emailAdd, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        Intent register = new Intent(MainActivity.this, checkusertypepage.class);
                        register.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(register);
                        Toast.makeText(MainActivity.this,"Logging in...", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this,""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    public void openSignup()
    {
        Intent register = new Intent(this, signup.class);
        register.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(register);
    }


}