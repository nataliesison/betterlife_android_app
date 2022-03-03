package com.example.psychesolutionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class diagnosisList extends AppCompatActivity {

    TextView open;
    String patientID, patientAge, patientGender, patientFull;
    Intent patientIDget;
    DatabaseReference mDiagnosisRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    RecyclerView diagnoseSesh;
    FirebaseRecyclerOptions<listDiagnosis> options;
    FirebaseRecyclerAdapter<listDiagnosis, diagnosisViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_list);

        open = findViewById(R.id.buttonOpenDiagnosis);

        mDiagnosisRef = FirebaseDatabase.getInstance().getReference().child("Sessions");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        diagnoseSesh = findViewById(R.id.diagnosisRecylerView);
        diagnoseSesh.setLayoutManager(new LinearLayoutManager(this));



        patientID = patientIDget.getStringExtra("key");
//        patientAge = patientIDget.getStringExtra("patientAge");
//        patientGender = patientIDget.getStringExtra("patientGender");
//        patientFull = patientIDget.getStringExtra("patientFullname");

        LoadDiagnosisList(patientID);


        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(diagnosisList.this, reportmodule.class);
//                intent.putExtra("patientUserID", patientID);
//                intent.putExtra("patientAge", patientAge);
//                intent.putExtra("patientGender", patientGender);
//                intent.putExtra("patientFull", patientFull);
                startActivity(intent);
            }
        });
    }
    private void LoadDiagnosisList(String spatient){
        Query query = mDiagnosisRef.child(mUser.getUid()).child(spatient).orderByChild("Date");
        options = new FirebaseRecyclerOptions.Builder<listDiagnosis>().setQuery(query, listDiagnosis.class).build();
        adapter = new FirebaseRecyclerAdapter<listDiagnosis, diagnosisViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull diagnosisViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull listDiagnosis model) {

                holder.patientSessionDone.setText(model.getSession_Pat());
                holder.patientSessionDate.setText(model.getDate_diagnosis());
                holder.patientSessionTime.setText(model.getStarted_Time());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(diagnosisList.this, reportmodule.class);
                        intent.putExtra("diagnosisKey",getRef(position).getKey().toString());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public diagnosisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_diagnosis, parent, false);

                return new diagnosisViewHolder(view);
            }
        };
        adapter.startListening();
        diagnoseSesh.setAdapter(adapter);

    }
}