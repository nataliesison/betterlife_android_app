package com.example.psychesolutionapp;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class articleDraftViewHolder extends RecyclerView.ViewHolder {

    CircleImageView profileImageArticleDraft;
    TextView titleArticleDraft, usernameArticleDraft;

    public articleDraftViewHolder(@NonNull View itemView) {
        super(itemView);

        profileImageArticleDraft = itemView.findViewById(R.id.profileImageArticleDraft);
        titleArticleDraft = itemView.findViewById(R.id.titleArticleDraft);
        usernameArticleDraft = itemView.findViewById(R.id.usernameArticleDraft);
    }
}
