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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class doctor_sched_list extends AppCompatActivity {

    Toolbar toolbar;

    FirebaseRecyclerOptions<approvedSched> options;
    FirebaseRecyclerAdapter<approvedSched, approvedSchedViewHolder> adapter;
    DatabaseReference mConfSchedRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    RecyclerView recyclerView, recyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_sched_list);

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Schedule");

        mConfSchedRef = FirebaseDatabase.getInstance().getReference().child("Confirmed_Schedule");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        recyclerView = findViewById(R.id.recyclerViewConfApt);
        recyclerView2 = findViewById(R.id.recyclerViewDeclApt);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        LoadScheduleList("");
        LoadUpcomingList("");
    }

    private void LoadUpcomingList(String s) {
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDateformat = new SimpleDateFormat("dd-MMM-yyyy");
        final String currentDate = currentDateformat.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTimeformat = new SimpleDateFormat("HH:mm");
        final String  currentTime = currentTimeformat.format(calForDate.getTime());

        Query query=mConfSchedRef.child(mUser.getUid()).orderByChild("date").startAt(s).endAt(s+"\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<approvedSched>().setQuery(query,approvedSched.class).build();
        adapter = new FirebaseRecyclerAdapter<approvedSched, approvedSchedViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull approvedSchedViewHolder holder, int position, @NonNull approvedSched model) {

                String schedDate = model.getDate();

                if(!schedDate.equals(currentDate)){
                    holder.schedPatientApproved.setText(model.getPatientName());
                    holder.approvedSchedTime.setText(model.getTime());
                    holder.approvedSchedDate.setText(model.getDate());
                }
                else{
                    holder.itemView.setVisibility(View.GONE);
                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
                }

            }

            @NonNull
            @Override
            public approvedSchedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_sched_approved, parent, false);

                return new approvedSchedViewHolder(view);
            }
        };

        adapter.startListening();
        recyclerView2.setAdapter(adapter);

    }

    private void LoadScheduleList(String s) {
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDateformat = new SimpleDateFormat("dd-MMM-yyyy");
        final String currentDate = currentDateformat.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTimeformat = new SimpleDateFormat("HH:mm");
        final String  currentTime = currentTimeformat.format(calForDate.getTime());

        Query query=mConfSchedRef.child(mUser.getUid()).orderByChild("date").startAt(s).endAt(s+"\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<approvedSched>().setQuery(query,approvedSched.class).build();
        adapter = new FirebaseRecyclerAdapter<approvedSched, approvedSchedViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull approvedSchedViewHolder holder, int position, @NonNull approvedSched model) {

                String schedDate = model.getDate();

                if(schedDate.equals(currentDate)){
                    holder.schedPatientApproved.setText(model.getPatientName());
                    holder.approvedSchedTime.setText(model.getTime());
                    holder.approvedSchedDate.setText(model.getDate());
                }
                else{
                    holder.itemView.setVisibility(View.GONE);
                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
                }
            }

            @NonNull
            @Override
            public approvedSchedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_sched_approved, parent, false);

                return new approvedSchedViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}