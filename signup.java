package com.example.psychesolutionapp;

import android.app.ProgressDialog;
import android.app.ProgressDialog;
import android.app.ProgressDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;


public class signup extends AppCompatActivity {

    private EditText fname, lname, emailAdd, pass, conpassw;
    Button signupbtn;
    String emailPattern = "[a-zA-Z._]+@[a-z]+\\.+[a-z]+";
    String emailPattern1 = "[0-9]+[a-zA-Z._]+@[a-z]+\\.+[a-z]+";
    String emailPattern2 = "[a-zA-Z._]+[0-9]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    private TextView btnCancels;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    signupInfo Signupinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailAdd = findViewById(R.id.signemail);
        pass = findViewById(R.id.signpass);
        conpassw = findViewById(R.id.signconpass);
        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        signupbtn = findViewById(R.id.signupbtn);
        btnCancels = findViewById(R.id.canceelsignup);

        btnCancels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                perfAuth();

            }
        });
    }

    private void perfAuth() {
        String emailad = emailAdd.getText().toString().trim();
        String passw = pass.getText().toString().trim();
        String conpass = conpassw.getText().toString().trim();

            if(TextUtils.isEmpty(emailad) && TextUtils.isEmpty(passw) && TextUtils.isEmpty(conpass)){
                Toast.makeText(signup.this, "Please fill up all fields.", Toast.LENGTH_SHORT).show();
            }

            else if(emailad.isEmpty()){
                emailAdd.setError("Please fill up the field");
                emailAdd.requestFocus();

            }
            else if(!emailad.matches(emailPattern)&&!emailad.matches(emailPattern1)&&!emailad.matches(emailPattern2)){
                emailAdd.setError("Email is invalid");
                emailAdd.requestFocus();
            }
            else if(passw.isEmpty()){
                pass.setError("Please fill up the field");
                pass.requestFocus();

            }
            else if(conpass.isEmpty()){
                conpassw.setError("Please fill up the field");
                conpassw.requestFocus();

            }
            else if(passw.length()<8){
                pass.setError("Minimum characters of 8");
                pass.requestFocus();
            }
            else if (!isValidPassword(passw)) {
                //Toast.makeText(signup.this, "Valid", Toast.LENGTH_SHORT).show();
                pass.setError("Password should contain characters, numbers & special characters");
            }
            else if(!passw.equals(conpass)){
                conpassw.setError("Password didn't match");
                pass.setError("Password didn't match");

            }

            else{
                progressDialog.setMessage("Please wait for the registration...");
                progressDialog.setTitle("Registration");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                mAuth.createUserWithEmailAndPassword(emailad, passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(signup.this,"Registration Successful", Toast.LENGTH_SHORT).show();
                            openSetup();
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(signup.this,""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }



    }
    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
    public void openSetup()
    {
        Intent register = new Intent(this, setupActivity.class);
        register.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(register);
    }

    public void openLogin()
    {
        Intent register = new Intent(this, MainActivity.class);
        register.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(register);
    }


}