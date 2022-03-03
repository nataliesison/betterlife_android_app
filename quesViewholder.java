package com.example.psychesolutionapp;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class quesViewholder extends RecyclerView.ViewHolder {

    View mView;
    ImageButton LikePostButton,CommentPostButton;
    TextView DisplaynoOfLikes, u, userq, ptime, pdate;
    CircleImageView image;
    int countLikes;
    String currentUserId;
    DatabaseReference Likesref;

    public quesViewholder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        LikePostButton = (ImageButton) mView.findViewById(R.id.like_button);
        CommentPostButton = (ImageButton) mView.findViewById(R.id.comment_button);
        DisplaynoOfLikes = (TextView) mView.findViewById(R.id.no_likes);

        u = (TextView) mView.findViewById(R.id.post_que_user_name);
        image = (CircleImageView) mView.findViewById(R.id.post_que_user_image);
        userq = (TextView) mView.findViewById(R.id.user_que);
        ptime = (TextView) mView.findViewById(R.id.post_time);
        pdate = (TextView) mView.findViewById(R.id.post_date);

        Likesref = FirebaseDatabase.getInstance().getReference().child("Likes");
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public void setLikeButtonStatus(final String PostKey){
        Likesref.child(PostKey).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    countLikes = (int) dataSnapshot.child(PostKey).getChildrenCount();
                    LikePostButton.setColorFilter(Color.MAGENTA);

                    DisplaynoOfLikes.setText((countLikes +(" Likes")));
                }
                else {
                    countLikes = (int) dataSnapshot.child(PostKey).getChildrenCount();
                    LikePostButton.setColorFilter(Color.rgb(203,153,108));

                    DisplaynoOfLikes.setText((countLikes +(" Likes")));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
