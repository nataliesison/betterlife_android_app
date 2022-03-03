package com.example.psychesolutionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class doc_sched_list_pending extends AppCompatActivity {
    Toolbar toolbar;

    FirebaseRecyclerOptions<pendingsched> options;
    FirebaseRecyclerAdapter<pendingsched,pendingSchedViewHolder> adapter;

    DatabaseReference mPendingSched, mSchedule, UsersRef, mPatientRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_sched_list_pending);

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("PENDING APPOINTMENTS");

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        recyclerView = findViewById(R.id.recyclerViewPendingApt);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPendingSched = FirebaseDatabase.getInstance().getReference().child("Patient_Sched");

        UsersRef= FirebaseDatabase.getInstance().getReference().child("Users");


        loadingPendingSched("");
    }

    private void loadingPendingSched(String s) {
        Query query=mPendingSched.orderByChild("date").startAt(s).endAt(s+"\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<pendingsched>().setQuery(query, pendingsched.class).build();
        adapter = new FirebaseRecyclerAdapter<pendingsched, pendingSchedViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull pendingSchedViewHolder holder, int position, @NonNull pendingsched model) {

                DatabaseReference docName = UsersRef.child(mUser.getUid()).child("username");
                String first = model.getFirstName();
                String last = model.getLastName();
                String time = model.getTime();
                String date = model.getDate();

                String schedID = time + date;

                String full = first + " " + last;

                holder.schedPatientName.setText(full);
                holder.approveSchedDateApt.setText(date);
                holder.approveSchedTimeApt.setText(time);

                holder.approveApt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(doc_sched_list_pending.this);
                        alertDialogBuilder.setMessage("Confirm Schedule?");
                        alertDialogBuilder.setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        pendingSched(schedID);
                                        //Toast.makeText(addArticle.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                                    }
                                });

                        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    }

                });

                holder.rejApt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(doc_sched_list_pending.this);
                        alertDialogBuilder.setMessage("Reject Schedule?");
                        alertDialogBuilder.setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        rejectPendingSched(schedID);
                                        //Toast.makeText(addArticle.this,"You clicked yes button",Toast.LENGTH_LONG).show();
                                    }
                                });

                        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    }
                });

            }

            @NonNull
            @Override
            public pendingSchedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_schedule, parent, false);

                return new pendingSchedViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    private void rejectPendingSched(String schedID) {
        mPendingSched.child(schedID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String patientUid = snapshot.child("uid").getValue().toString();
                    String patientUsername = snapshot.child("usernamee").getValue().toString();
                    String patientProfImage = snapshot.child("profileImage").getValue().toString();
                    String first = snapshot.child("firstName").getValue().toString();
                    String last = snapshot.child("lastName").getValue().toString();
                    String full = first + " " + last;
                    String datePatient = snapshot.child("date").getValue().toString();
                    String timePatient = snapshot.child("time").getValue().toString();

                    final FirebaseDatabase database=FirebaseDatabase.getInstance();
                    mSchedule=database.getReference().child("Rejected_Sched").child(patientUid);
                    String pushID=mSchedule.push().getKey();
                    HashMap userMap=new HashMap();
                    userMap.put("patientUid",patientUid);
                    userMap.put("usernamee",patientUsername);
                    userMap.put("profileImage",patientProfImage);
                    userMap.put("PatientName",full);
                    userMap.put("date",datePatient);
                    userMap.put("time",timePatient);
                    userMap.put("DoctorID", mUser.getUid());
                    mSchedule.child(schedID).updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                mPendingSched.child(schedID).removeValue();
                                Toast.makeText(doc_sched_list_pending.this, "Appointment Rejected", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else {
                                Toast.makeText(doc_sched_list_pending.this,task.getException().toString(),Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void pendingSched(String schedID){

        mPendingSched.child(schedID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String patientUid = snapshot.child("uid").getValue().toString();
                    String patientUsername = snapshot.child("usernamee").getValue().toString();
                    String patientProfImage = snapshot.child("profileImage").getValue().toString();
                    String first = snapshot.child("firstName").getValue().toString();
                    String last = snapshot.child("lastName").getValue().toString();
                    String full = first + " " + last;
                    String datePatient = snapshot.child("date").getValue().toString();
                    String timePatient = snapshot.child("time").getValue().toString();

                    final FirebaseDatabase database=FirebaseDatabase.getInstance();
                    mSchedule=database.getReference().child("Confirmed_Schedule").child(mUser.getUid());
                    String pushID=mSchedule.push().getKey();
                    HashMap userMap=new HashMap();
                    userMap.put("patientUid",patientUid);
                    userMap.put("usernamee",patientUsername);
                    userMap.put("profileImage",patientProfImage);
                    userMap.put("PatientName",full);
                    userMap.put("date",datePatient);
                    userMap.put("time",timePatient);
                    userMap.put("DoctorID", mUser.getUid());
                    mSchedule.child(schedID).updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                mPendingSched.child(schedID).removeValue();
                                Toast.makeText(doc_sched_list_pending.this, "Schedule Confirmed", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else {
                                Toast.makeText(doc_sched_list_pending.this,task.getException().toString(),Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mPendingSched.child(schedID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String patientUid = snapshot.child("uid").getValue().toString();
                    String patientUsername = snapshot.child("usernamee").getValue().toString();
                    String patientProfImage = snapshot.child("profileImage").getValue().toString();
                    String first = snapshot.child("firstName").getValue().toString();
                    String last = snapshot.child("lastName").getValue().toString();
                    String full = first + " " + last;
                    String datePatient = snapshot.child("date").getValue().toString();
                    String timePatient = snapshot.child("time").getValue().toString();

                    final FirebaseDatabase database=FirebaseDatabase.getInstance();
                    mSchedule=database.getReference().child("Confirmed_Schedule_Patient").child(patientUid);
                    String pushID=mSchedule.push().getKey();
                    HashMap userMap=new HashMap();
                    userMap.put("patientUid",patientUid);
                    userMap.put("usernamee",patientUsername);
                    userMap.put("profileImage",patientProfImage);
                    userMap.put("PatientName",full);
                    userMap.put("date",datePatient);
                    userMap.put("time",timePatient);
                    userMap.put("DoctorID", mUser.getUid());
                    mSchedule.child(schedID).updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                mPendingSched.child(schedID).removeValue();
                                Toast.makeText(doc_sched_list_pending.this, "Schedule Confirmed", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else {
                                Toast.makeText(doc_sched_list_pending.this,task.getException().toString(),Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}