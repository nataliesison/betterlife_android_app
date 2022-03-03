package com.example.psychesolutionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.psychesolutionapp.Utils.Patients;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class ChatUsersActivity extends AppCompatActivity {

    FirebaseRecyclerOptions<Patients>options;
    FirebaseRecyclerAdapter<Patients,PatientviewHolder> adapter;
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    FirebaseUser mUser;

    Toolbar toolbar;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerviewPL);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("Patients");

        LoadPatients("");
    }

    private void LoadPatients(String s) {
        Query query = mRef.child(mUser.getUid()).orderByChild("username").startAt(s).endAt(s+"\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<Patients>().setQuery(query, Patients.class).build();
        adapter = new FirebaseRecyclerAdapter<Patients, PatientviewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PatientviewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Patients model) {

                Picasso.get().load(model.getProfileImage()).into(holder.profileImageUrl);
                holder.usernamePatient.setText(model.getUsername());
                holder.fullnamePatient.setText(model.getFullname());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ChatUsersActivity.this, ChatActivity.class);
                        intent.putExtra("OtherUserID", getRef(position).getKey().toString());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public PatientviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simgle_view_patient,parent,false);

                return new PatientviewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}