package com.example.psychesolutionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SubjectActivity extends AppCompatActivity {

    TextView dept;
    String[] empty={"Empty List","Empty List"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

//        Toolbar toolbar=findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String[] ITsubjectList = intent.getStringArrayExtra("ITkey");
        String[] ITcourseCode=intent.getStringArrayExtra("ITcode");
        String[] COMPsubjectList = intent.getStringArrayExtra("COMPkey");
        String[] COMPcourseCode=intent.getStringArrayExtra("COMPcode");
        String[] MECHsubjectList=intent.getStringArrayExtra("MECHkey");
        String[] CIVILsubjectList=intent.getStringArrayExtra("CIVILkey");
        String[] CIVILcourseCode=intent.getStringArrayExtra("CIVILcode");
        String[] ELECTRICALsubjectList=intent.getStringArrayExtra("ELECkey");
        String[] ENTCsubjectList=intent.getStringArrayExtra("ENTCkey");
        String[] Code=intent.getStringArrayExtra("Compcode");
        dept=findViewById(R.id.title_dept);

        RecyclerView Subjects = findViewById(R.id.list_subs);
        Subjects.setLayoutManager(new LinearLayoutManager(this));
        if(ITsubjectList!=null) {
            Subjects.setAdapter(new SubjectsAdapter(ITsubjectList,"Patient_Qs",this));
            dept.setText("Patient Questions");
        }
        else if(COMPsubjectList!=null){
            Subjects.setAdapter(new SubjectsAdapter(COMPsubjectList,"FAQ",this));
            dept.setText("F A Qs");
        }
        else if(MECHsubjectList!=null){
            Subjects.setAdapter(new SubjectsAdapter(MECHsubjectList,"Interactions",this));
            dept.setText("Interactions");
        }
        else if(CIVILsubjectList!=null){
            Subjects.setAdapter(new SubjectsAdapter(CIVILsubjectList,"Entertainment",this));
            dept.setText("Entertainment");
        }
        else if(ELECTRICALsubjectList!=null){
            Subjects.setAdapter(new SubjectsAdapter(ELECTRICALsubjectList,"Healing",this));
            dept.setText("Healing");
        }
        else if(ENTCsubjectList!=null){
            Subjects.setAdapter(new SubjectsAdapter(ENTCsubjectList,"Health",this));
            dept.setText("Health");
        }

        else {
            Subjects.setAdapter(new SubjectsAdapter(empty,"empty",this));
        }
    }

}