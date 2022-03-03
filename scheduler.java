package com.example.psychesolutionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.os.Bundle;
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

import java.util.Calendar;
import java.util.HashMap;


public class scheduler extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mUserRef, mSchedulerRef, UsersReference;

    String[] time = {"9:10 AM", "10:20 AM", "11:30 AM", "12:40 PM", "1:50 PM", "3:00 PM", "4:10 PM", "5:20 PM", "6:30 PM"};
    String[] sessions = {"Consultation", "Psychological Assessment", "Counseling/Psychotherapy", "Training/Seminars (PCR-accredited)", "Support Group and Group Therapy", "Live-in Sessions"};
    //TextView j_spinner_selected;
    Spinner j_spinner, time_spinner, session_spinner;
    TextView dateText,termsBtn;
    Button checkBtn, checkExistingBtn;
    CheckBox checkBoxTC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mSchedulerRef = FirebaseDatabase.getInstance().getReference().child("Patient_Sched");

        dateText = findViewById(R.id.editTextDate);
        checkBoxTC = findViewById(R.id.checkBoxTC);
        checkExistingBtn = findViewById(R.id.checkExistingBtn);
        termsBtn = findViewById(R.id.tcBtn);
        checkBtn = findViewById(R.id.checkBtn);

        session_spinner = findViewById(R.id.editTextSession);
        time_spinner = findViewById(R.id.editTextTime);


        ArrayAdapter<String> adapterTime = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,time);
        ArrayAdapter<String> adapterSessions = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,sessions);

        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        time_spinner.setAdapter(adapterTime);
        session_spinner.setAdapter(adapterSessions);

        time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                String timeChoice = time_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        termsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(scheduler.this, termsAndConditions.class);
                startActivity(intent);
            }
        });

       session_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               String sessionChoice = session_spinner.getSelectedItem().toString();
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });



        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxTC.isChecked()){

                    String timeChoice = time_spinner.getSelectedItem().toString();
                    String dateChoice = dateText.getText().toString();
                    String chosenSched = timeChoice + dateChoice;
                    String chosenSesh = session_spinner.getSelectedItem().toString();

                    if(dateChoice.isEmpty()){
                        dateText.setError("Choose Date.");
                        dateText.requestFocus();

                    }
                    else{
                        mSchedulerRef.child(chosenSched).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){

                                    Toast.makeText(scheduler.this, "Schedule NOT available",Toast.LENGTH_SHORT).show();
                                }
                                else if(!snapshot.exists()){

                                    Intent intent = new Intent(scheduler.this, schedConfirmation.class);
                                    intent.putExtra("timeChoice", timeChoice);
                                    intent.putExtra("dateChoice", dateChoice);
                                    intent.putExtra("chosenSched", chosenSched);
                                    intent.putExtra("chosenSesh", chosenSesh);
                                    startActivity(intent);
                                    //addSchedule(timeChoice, dateChoice, chosenSched);
                                }
                                else{
                                    Toast.makeText(scheduler.this, "!!",Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }
                //=====================================================
                else{
                    Toast.makeText(scheduler.this, "Please check Terms & Conditions", Toast.LENGTH_LONG).show();
                }

            }
        });

        checkExistingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(scheduler.this,existing_sched_request.class);
                startActivity(intent);

            }
        });
    }

//    private void  addSchedule(String timeChoice, String dateChoice, String chosenSched){
//
//        mUserRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    String username=snapshot.child("username").getValue().toString();
//                    String userprofileImg=snapshot.child("profileImage").getValue().toString();
//
//                    final FirebaseDatabase database=FirebaseDatabase.getInstance();
//                    UsersReference=database.getReference().child("Patient_Sched");
//
//                    HashMap userMap=new HashMap();
//                    userMap.put("uid",mUser.getUid());
//                    userMap.put("usernamee",username);
//                    userMap.put("profileImage",userprofileImg);
//                    userMap.put("time",timeChoice);
//                    userMap.put("date",dateChoice);
//
//                    UsersReference.child(chosenSched).updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
//                        @Override
//                        public void onComplete(@NonNull Task task) {
//                            if(task.isSuccessful()){
//
//                                Toast.makeText(scheduler.this,"Scheduled an appointment successfully",Toast.LENGTH_LONG).show();
//
//                                return;
//                            }
//                            else {
//                                Toast.makeText(scheduler.this,task.getException().toString(),Toast.LENGTH_LONG).show();
//                                return;
//                            }
//                        }
//                    });
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

 //   }

    private void showDateDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
        this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String monthLetter;

        if(month==0){
            monthLetter = "Jan";
        }
        else if(month==1){
            monthLetter = "Feb";
        }
        else if(month==2){
            monthLetter = "Mar";
        }
        else if(month==3){
            monthLetter = "Apr";
        }
        else if(month==4){
            monthLetter = "May";
        }
        else if(month==5){
            monthLetter = "Jun";
        }
        else if(month==6){
            monthLetter = "Jul";
        }
        else if(month==7){
            monthLetter = "Aug";
        }
        else if(month==8){
            monthLetter = "Sep";
        }
        else if(month==9){
            monthLetter = "Oct";
        }
        else if(month==10){
            monthLetter = "Nov";
        }
        else{
            monthLetter = "Dec";
        }

        String dateSet = dayOfMonth  + "-" + monthLetter + "-" +year;

        dateText.setText(dateSet);
    }
}