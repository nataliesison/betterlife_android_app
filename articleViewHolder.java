package com.example.psychesolutionapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class articleViewHolder extends RecyclerView.ViewHolder {

    CircleImageView profileImageFindArticle;
    TextView usernameFindArticle, docUsername;
    public articleViewHolder(@NonNull View itemView) {
        super(itemView);

        profileImageFindArticle = itemView.findViewById(R.id.profileImageFindArticle);
        usernameFindArticle = itemView.findViewById(R.id.textVieewApt);
        docUsername = itemView.findViewById(R.id.rejSchedDate);

    }
}
