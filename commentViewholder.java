package com.example.psychesolutionapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class commentViewholder extends RecyclerView.ViewHolder {
    CircleImageView profileImageComment;
    TextView usernameComment, comment;
    public static RecyclerView recyclerView;

    public commentViewholder(@NonNull View itemView) {
        super(itemView);
        profileImageComment = itemView.findViewById(R.id.profileImage_comment);
        usernameComment = itemView.findViewById(R.id.usernameComment);
        comment = itemView.findViewById(R.id.commentTV);
    }
}
