package com.example.psychesolutionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class forumpendingtopics extends AppCompatActivity {

    String selectedSubjects;
    String selectedDepts;
    FirebaseAuth mAuth;
    private RecyclerView queList;
    private DatabaseReference Questionref,Likesref, mforumRef, mforumReef;
    Boolean LikeChecker = false;
    String currentuserID;
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<QuestionsPending> quesOption;
    FirebaseRecyclerAdapter<QuestionsPending,quesPendingViewHolder> quesAdapteer;
    String Patient_Qs, Doctor_Qs, Interactions, Entertainment, Healing, Health;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forumpendingtopics);

//        Intent intent2 = getIntent();
//        String selectedDepts = intent.getStringExtra("selectedDept");

        mAuth = FirebaseAuth.getInstance();
        currentuserID = mAuth.getCurrentUser().getUid();

        recyclerView = findViewById(R.id.recylerviewPendingForum);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Questionref = FirebaseDatabase.getInstance().getReference().child("Departments");
        //Likesref = FirebaseDatabase.getInstance().getReference().child("Likes");



        DisplayPendingTopics("");

    }

    private void DisplayPendingTopics(String s) {

        Query query = Questionref.child("Pending").orderByChild("date");
        quesOption = new FirebaseRecyclerOptions.Builder<QuestionsPending>().setQuery(query,QuestionsPending.class).build();
        quesAdapteer = new FirebaseRecyclerAdapter<QuestionsPending, quesPendingViewHolder>(quesOption) {
            @NonNull
            @Override
            public quesPendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forumpending, parent, false);
                return new quesPendingViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull quesPendingViewHolder holder, int position, @NonNull QuestionsPending model) {

                String pendingSelectedDept = model.getSelectedDept().toString();
                String pendingSelectedSubj = model.getSelectedSubject().toString();
                String pendingPushID = model.getPushID();

                holder.pend_un.setText(model.getUsernamee());
                holder.pend_time.setText(model.getTime());
                holder.pend_date.setText(model.getDate());
                Picasso.get().load(model.getProfileImage()).into(holder.circleDp);
                holder.pend_text.setText(model.getQuestion());

                holder.approve_pendBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(forumpendingtopics.this);
                        alertDialogBuilder.setMessage("Approve topic?");
                        alertDialogBuilder.setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        pendingForumTopic(pendingSelectedDept, pendingSelectedSubj, pendingPushID);
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

                holder.decline_pendBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(forumpendingtopics.this);
                        alertDialogBuilder.setMessage("Decline this topic?");
                        alertDialogBuilder.setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        declineForumTopic(pendingSelectedDept, pendingSelectedSubj, pendingPushID);
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

        };
        quesAdapteer.startListening();
        recyclerView.setAdapter(quesAdapteer);


        //--------------------------------
    }

    private void declineForumTopic(String pendingSelectedDept, String pendingSelectedSubj, String pendingPushID) {
        Questionref.child("Pending").child(pendingPushID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String pendingFDate = snapshot.child("date").getValue().toString();
                    String pendingFProfileImage = snapshot.child("profileImage").getValue().toString();
                    String pendingFQues = snapshot.child("question").getValue().toString();
                    String pendingFTime = snapshot.child("time").getValue().toString();
                    String pendingFUid = snapshot.child("uid").getValue().toString();
                    String pendingFUsername = snapshot.child("usernamee").getValue().toString();

                    //final FirebaseDatabase database=FirebaseDatabase.getInstance();
                    mforumReef = Questionref.child("Rejected").child(pendingSelectedDept).child(pendingSelectedSubj);

                    HashMap hashMap = new HashMap();
                    hashMap.put("date", pendingFDate);
                    hashMap.put("profileImage", pendingFProfileImage);
                    hashMap.put("question", pendingFQues);
                    hashMap.put("time", pendingFTime);
                    hashMap.put("uid", pendingFUid);
                    hashMap.put("usernamee", pendingFUsername);
                    mforumReef.child(pendingPushID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                Questionref.child("Pending").child(pendingPushID).removeValue();
                                Toast.makeText(forumpendingtopics.this, "Topic Rejected", Toast.LENGTH_LONG).show();
                                return;
                            }
                            else{
                                Toast.makeText(forumpendingtopics.this,task.getException().toString(),Toast.LENGTH_LONG).show();
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

    private void pendingForumTopic(String pendingSelectedDept, String pendingSelectedSubj, String pendingPushID) {

        Questionref.child("Pending").child(pendingPushID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String pendingFDate = snapshot.child("date").getValue().toString();
                    String pendingFProfileImage = snapshot.child("profileImage").getValue().toString();
                    String pendingFQues = snapshot.child("question").getValue().toString();
                    String pendingFTime = snapshot.child("time").getValue().toString();
                    String pendingFUid = snapshot.child("uid").getValue().toString();
                    String pendingFUsername = snapshot.child("usernamee").getValue().toString();

                    //final FirebaseDatabase database=FirebaseDatabase.getInstance();
                    mforumRef = Questionref.child("Approved").child(pendingSelectedDept).child(pendingSelectedSubj);

                    HashMap hashMap = new HashMap();
                    hashMap.put("date", pendingFDate);
                    hashMap.put("profileImage", pendingFProfileImage);
                    hashMap.put("question", pendingFQues);
                    hashMap.put("time", pendingFTime);
                    hashMap.put("uid", pendingFUid);
                    hashMap.put("usernamee", pendingFUsername);
                    mforumRef.child(pendingPushID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                Questionref.child("Pending").child(pendingPushID).removeValue();
                                Toast.makeText(forumpendingtopics.this, "Topic Approved", Toast.LENGTH_LONG).show();
                                return;
                            }
                            else{
                                Toast.makeText(forumpendingtopics.this,task.getException().toString(),Toast.LENGTH_LONG).show();
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