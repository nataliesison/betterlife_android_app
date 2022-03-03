package com.example.psychesolutionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class existing_sched_request extends AppCompatActivity {

    Toolbar toolbar;

    FirebaseRecyclerOptions<patientapprovedsched> options;
    FirebaseRecyclerAdapter<patientapprovedsched, existingSchecReqViewHolder> adapter;

    FirebaseRecyclerOptions<patientrejectedsched> optionsRej;
    FirebaseRecyclerAdapter<patientrejectedsched, rejectedSchedViewHolder> adapterRej;

    DatabaseReference mConfSchedRef,doctorRef, mRejSchedRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    RecyclerView recyclerView3, recyclerView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_sched_request);

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Schedule");

        mConfSchedRef = FirebaseDatabase.getInstance().getReference().child("Confirmed_Schedule_Patient");
        mRejSchedRef = FirebaseDatabase.getInstance().getReference().child("Rejected_Sched");
        doctorRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        recyclerView3 = findViewById(R.id.recyclerViewConfirmedApt);
        recyclerView4 = findViewById(R.id.recyclerViewDeclinedApt);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        recyclerView4.setLayoutManager(new LinearLayoutManager(this));


        Query query = mConfSchedRef.child(mUser.getUid());
        options = new FirebaseRecyclerOptions.Builder<patientapprovedsched>().setQuery(query,patientapprovedsched.class).build();
        adapter = new FirebaseRecyclerAdapter<patientapprovedsched, existingSchecReqViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull existingSchecReqViewHolder holder, int position, @NonNull patientapprovedsched model) {
                String patientID = model.getPatientUid();
                String docID = model.getDoctorID();
                String currentAcc = mUser.getUid();


                    holder.rejSchedDate.setText(model.getDate());
                    holder.rejSchedtime.setText(model.getTime());
                    holder.rejSchedDocName.setText(docID);
            }

            @NonNull
            @Override
            public existingSchecReqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_existing_conf_sched, parent, false);

                return new existingSchecReqViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView3.setAdapter(adapter);

        Query queryR = mRejSchedRef.child(mUser.getUid());
        optionsRej = new FirebaseRecyclerOptions.Builder<patientrejectedsched>().setQuery(queryR, patientrejectedsched.class).build();
        adapterRej = new FirebaseRecyclerAdapter<patientrejectedsched, rejectedSchedViewHolder>(optionsRej) {
            @Override
            protected void onBindViewHolder(@NonNull rejectedSchedViewHolder holder, int position, @NonNull patientrejectedsched model) {


                holder.rejectedAptTime.setText(model.getTime());
                holder.rejectedAptDate.setText(model.getDate());

            }

            @NonNull
            @Override
            public rejectedSchedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_existing_rej_sched, parent, false);

                return new rejectedSchedViewHolder(view);
            }
        };
        adapterRej.startListening();
        recyclerView4.setAdapter(adapterRej);
    }

}