package com.example.psychesolutionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.psychesolutionapp.Utils.Comments;
import com.example.psychesolutionapp.Utils.answeeers;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class forumApproveReject extends AppCompatActivity {

    private RecyclerView pending_list;
    private Button approveBtn, declineBtn;
    private EditText AnswerInputText;
    String selectedSubject;
    String selectedDept;
    FirebaseRecyclerOptions<Pending> pendingOption;
    FirebaseRecyclerAdapter<Pending,PendingPostViewHolder>pendingAdapter;

    private String Post_Key,current_user_id;
    private FirebaseAuth mAuth;

    private DatabaseReference Userref,Questionref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_approve_reject);

        Post_Key = getIntent().getExtras().get("Postkey").toString();
        mAuth = FirebaseAuth.getInstance();
        current_user_id =  mAuth.getCurrentUser().getUid();



        selectedSubject =  getIntent().getExtras().get("selectedSub").toString();
        selectedDept= getIntent().getExtras().get("Dept").toString();


        Userref = FirebaseDatabase.getInstance().getReference().child("Users");
        Questionref = FirebaseDatabase.getInstance().getReference().child("Departments").child(selectedDept).child(selectedSubject).child(Post_Key).child("answers");

//        Toolbar toolbar=findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        pending_list = (RecyclerView) findViewById(R.id.pending_list);
        pending_list.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        pending_list.setLayoutManager(linearLayoutManager);

        AnswerInputText = (EditText) findViewById(R.id.answersInput);
        approveBtn = (Button) findViewById(R.id.approveBtn);
        declineBtn = (Button) findViewById(R.id.declineBtn);

//        approveBtn.setOnClickListener(v -> Userref.child(current_user_id).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()){
//                    String userName = dataSnapshot.child("username").getValue().toString();
//                    String profileImg = dataSnapshot.child("profileImage").getValue().toString();
//
//                    ValidatePost(userName,profileImg);
//
//                    AnswerInputText.setText("");
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }


            // CHECK APPROVE REJECT FRIEND REQUEST FILE TO SEE HOW THE APPROVE REJECT WILL WORK IN FORUM POST
//        }));
//
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        pendingOption = new FirebaseRecyclerOptions.Builder<Pending>().setQuery(Questionref, Pending.class).build();
//        pendingAdapter = new FirebaseRecyclerAdapter<Pending, PendingPostViewHolder>(pendingOption) {
//            @Override
//            protected void onBindViewHolder(@NonNull PendingPostViewHolder holder, int position, @NonNull Pending model) {
//                holder.myuserNamepend.setText(model.getPend_username());
//                Picasso.get().load(model.getPend_profileimage()).into(holder.imagepend);
//                holder.myanswerpend.setText(model.getPend_answer());
//                holder.mydatepend.setText(model.getPend_date());
//                holder.mytimepend.setText(model.getPend_time());
//            }
//
//            @NonNull
//            @Override
//            public PendingPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forumpending, parent, false);
//                return new PendingPostViewHolder(view);
//            }
//        };
//        pendingAdapter.startListening();
//        pending_list.setAdapter(pendingAdapter);
//    }

//    private void ValidatePost(String userName,String profileImg) {
//        String answerText = Answer_text.getText().toString();
//        if (TextUtils.isEmpty(answerText)){
//            Toast.makeText(this,"Please Write answer to post!",Toast.LENGTH_SHORT).show();
//        }
//        else{
//            Calendar calForDate = Calendar.getInstance();
//            SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMM-yy");
//            final String saveCurrentDate = currentDate.format(calForDate.getTime());
//
//            Calendar calForTime = Calendar.getInstance();
//            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
//            final String  saveCurrentTime = currentTime.format(calForDate.getTime());
//
//            final String RandomKey = current_user_id + saveCurrentDate + saveCurrentTime;
//
//            HashMap answersMap = new HashMap();
//            answersMap.put("uid",current_user_id);
//            answersMap.put("answer",answerText);
//            answersMap.put("date",saveCurrentDate);
//            answersMap.put("time",saveCurrentTime);
//            answersMap.put("username",userName);
//            answersMap.put("profileimage",profileImg);
//
//            Questionref.child(RandomKey).updateChildren(answersMap).addOnCompleteListener(new OnCompleteListener() {
//                @Override
//                public void onComplete(@NonNull Task task) {
//                    if(task.isSuccessful()){
//                        Toast.makeText(AnswaersActivity.this, "Your answer submitted Successfully!", Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//                        Toast.makeText(AnswaersActivity.this, "Error occured, try again....", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
    }
}