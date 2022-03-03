package com.example.psychesolutionapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class FindPatientViewHolder extends RecyclerView.ViewHolder {
    CircleImageView profileImage;
    TextView username, city;

    public FindPatientViewHolder(@NonNull View itemView) {
        super(itemView);
        profileImage = itemView.findViewById(R.id.profileImageFindPatient);
        username = itemView.findViewById(R.id.usernameFindPatient);
        city = itemView.findViewById(R.id.cityFindPatient);

    }
}
