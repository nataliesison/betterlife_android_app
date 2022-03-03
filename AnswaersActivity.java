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

public class AnswaersActivity extends AppCompatActivity {

    private RecyclerView AnsList;
    private ImageButton PostAnsButton;
    private EditText AnswerInputText;
    String selectedSubject;
    String selectedDept;
    FirebaseRecyclerOptions<Answers> answerOption;
    FirebaseRecyclerAdapter<Answers,AnswersViewHolder>answerAdapter;

    private String Post_Key,current_user_id;
    private FirebaseAuth mAuth;

    private DatabaseReference Userref,Questionref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answaers);
        Post_Key = getIntent().getExtras().get("Postkey").toString();
        mAuth = FirebaseAuth.getInstance();
        current_user_id =  mAuth.getCurrentUser().getUid();



        selectedSubject =  getIntent().getExtras().get("selectedSub").toString();
        selectedDept= getIntent().getExtras().get("Dept").toString();


        Userref = FirebaseDatabase.getInstance().getReference().child("Users");
        Questionref = FirebaseDatabase.getInstance().getReference().child("Departments").child("Approved").child(selectedDept).child(selectedSubject).child(Post_Key).child("answers");

//        Toolbar toolbar=findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        AnsList = (RecyclerView) findViewById(R.id.ans_list);
        AnsList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        AnsList.setLayoutManager(linearLayoutManager);

        AnswerInputText = (EditText) findViewById(R.id.answersInput);
        PostAnsButton = (ImageButton) findViewById(R.id.post_ans_button);

        PostAnsButton.setOnClickListener(v -> Userref.child(current_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String userName = dataSnapshot.child("username").getValue().toString();
                    String profileImg = dataSnapshot.child("profileImage").getValue().toString();

                    ValidateAnswer(userName,profileImg);

                    AnswerInputText.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }));

    }

    @Override
    protected void onStart() {
        super.onStart();
         answerOption = new FirebaseRecyclerOptions.Builder<Answers>().setQuery(Questionref, Answers.class).build();
         answerAdapter = new FirebaseRecyclerAdapter<Answers, AnswersViewHolder>(answerOption) {
             @Override
             protected void onBindViewHolder(@NonNull AnswersViewHolder answersViewHolder, int position, @NonNull Answers answers) {

                 answersViewHolder.myuserName.setText(answers.getUsername());
                 Picasso.get().load(answers.getProfileimage()).into(answersViewHolder.image);
                 answersViewHolder.myanswer.setText(answers.getAnswer());
                 answersViewHolder.mydate.setText(answers.getDate());
                 answersViewHolder.mytime.setText(answers.getTime());

             }
             @NonNull
             @Override
             public AnswersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                 View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_answers_layout, parent, false);
                 return new AnswersViewHolder(view);
             }
         };
        answerAdapter.startListening();
        AnsList.setAdapter(answerAdapter);
    }


    private void ValidateAnswer(String userName,String profileImg) {
        String answerText = AnswerInputText.getText().toString();
        if (TextUtils.isEmpty(answerText)){
            Toast.makeText(this,"Please Write answer to post!",Toast.LENGTH_SHORT).show();
        }
        else{
            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMM-yy");
            final String saveCurrentDate = currentDate.format(calForDate.getTime());

            Calendar calForTime = Calendar.getInstance();
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
            final String  saveCurrentTime = currentTime.format(calForDate.getTime());

            final String RandomKey = current_user_id + saveCurrentDate + saveCurrentTime;

            HashMap answersMap = new HashMap();
            answersMap.put("uid",current_user_id);
            answersMap.put("answer",answerText);
            answersMap.put("date",saveCurrentDate);
            answersMap.put("time",saveCurrentTime);
            answersMap.put("username",userName);
            answersMap.put("profileimage",profileImg);

            Questionref.child(RandomKey).updateChildren(answersMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){
                        Toast.makeText(AnswaersActivity.this, "Your answer submitted Successfully!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(AnswaersActivity.this, "Error occured, try again....", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}