package com.example.psychesolutionapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientviewHolder extends RecyclerView.ViewHolder {
    CircleImageView profileImageUrl;
    TextView usernamePatient,fullnamePatient;


    public PatientviewHolder(@NonNull View itemView) {
        super(itemView);

        profileImageUrl=itemView.findViewById(R.id.profileImageFindPatient);
        usernamePatient= itemView.findViewById(R.id.usernameFindPatient);
        fullnamePatient = itemView.findViewById(R.id.cityFindPatient);

    }
}
