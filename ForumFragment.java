package com.example.psychesolutionapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

import com.example.psychesolutionapp.Utils.Comments;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ForumFragment extends Fragment {
    String selectedSubject;
    String selectedDept;
    FirebaseAuth mAuth;
    private RecyclerView queList;
    private DatabaseReference Questionref,Likesref;
    Boolean LikeChecker = false;
    String currentuserID;
    FirebaseRecyclerOptions<Questionss> quesOption;
    FirebaseRecyclerAdapter<Questionss,quesViewholder>quesAdapter;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.activity_forum_fragment, container, false);
        mAuth = FirebaseAuth.getInstance();
        currentuserID = mAuth.getCurrentUser().getUid();
        if(getActivity()!=null) {
            Intent intent = getActivity().getIntent();
            selectedSubject = intent.getStringExtra("SelectedSubject");
            selectedDept=intent.getStringExtra("dept");
        }

        queList = (RecyclerView) rootview.findViewById(R.id.all_que_list);
        queList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        queList.setLayoutManager(linearLayoutManager);
        Questionref = FirebaseDatabase.getInstance().getReference().child("Departments").child("Approved").child(selectedDept).child(selectedSubject);
        Likesref = FirebaseDatabase.getInstance().getReference().child("Likes");

        DisplayAllQuestion();


        return rootview;
    }

    private void DisplayAllQuestion() {
        quesOption = new FirebaseRecyclerOptions.Builder<Questionss>().setQuery(Questionref, Questionss.class).build();
        quesAdapter = new FirebaseRecyclerAdapter<Questionss, quesViewholder>(quesOption) {
            @Override
            protected void onBindViewHolder(@NonNull quesViewholder questionViewHolder, int position, @NonNull Questionss questionss) {

                final String  PostKey = getRef(position).getKey();

                questionViewHolder.userq.setText(questionss.getQuestion());
                Picasso.get().load(questionss.getProfileImage()).into(questionViewHolder.image);
                questionViewHolder.u.setText(questionss.getUsernamee());
                questionViewHolder.ptime.setText(questionss.getTime());
                questionViewHolder.pdate.setText(questionss.getDate());

                questionViewHolder.setLikeButtonStatus(PostKey);

                questionViewHolder.CommentPostButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent commentsIntent = new Intent(getActivity(),AnswaersActivity.class);
                        // commentsIntent.putExtra("Que",)
                        commentsIntent.putExtra("Postkey",PostKey);
                        commentsIntent.putExtra("selectedSub",selectedSubject);
                        commentsIntent.putExtra("Dept",selectedDept);
                        startActivity(commentsIntent);

                    }
                });

                questionViewHolder.LikePostButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LikeChecker = true;
                        Likesref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(LikeChecker.equals(true)){
                                    if(dataSnapshot.child(PostKey).hasChild(currentuserID))
                                    {
                                        Likesref.child(PostKey).child(currentuserID).removeValue();
                                        LikeChecker = false;
                                    }
                                    else {
                                        Likesref.child(PostKey).child(currentuserID).setValue(true);
                                        LikeChecker = false;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }

            @NonNull
            @Override
            public quesViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_ques_ans, parent, false);
                return new quesViewholder(view);
                //return null;
            }
        };
        quesAdapter.startListening();
        queList.setAdapter(quesAdapter);

    }
}