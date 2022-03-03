package com.example.psychesolutionapp;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    CircleImageView profileImagePost;
    ImageView postImage, likeImage, commentImage, sendComment;
    TextView profileUsernamePost, timeAgo, postDescription, likeCounter, commentCounter;
    EditText inputComment;
    public static RecyclerView recyclerView;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        profileImagePost = itemView.findViewById(R.id.profileImagePost);
        postImage = itemView.findViewById(R.id.postImage);
        profileUsernamePost = itemView.findViewById(R.id.profileUsernamePost);
        timeAgo = itemView.findViewById(R.id.timeAgo);
        postDescription = itemView.findViewById(R.id.postDescription);
        likeImage = itemView.findViewById(R.id.likeImage);
        commentImage = itemView.findViewById(R.id.commentImage);
        likeCounter = itemView.findViewById(R.id.likeCounter);
        commentCounter = itemView.findViewById(R.id.commentCounter);
        inputComment = itemView.findViewById(R.id.inputComment);
        sendComment = itemView.findViewById(R.id.sendComment);
        recyclerView = itemView.findViewById(R.id.recyclerViewcomments);
    }

    public void countLikes(String postKey, String uid, DatabaseReference likeRef) {
        likeRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int totalLikes = (int) snapshot.getChildrenCount();
                    likeCounter.setText(totalLikes+"");
                }
                else{
                    likeCounter.setText("0");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        likeRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(uid).exists()){
                    likeImage.setColorFilter(Color.MAGENTA);
                }
                else{
                    likeImage.setColorFilter(Color.rgb(203,153,108));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void countComment(String postKey, String uid, DatabaseReference commentRef) {
        commentRef.child(postKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int totalComment = (int) snapshot.getChildrenCount();
                    commentCounter.setText(totalComment+"");
                }
                else{
                    commentCounter.setText("0");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
