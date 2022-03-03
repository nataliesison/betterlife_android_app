package com.example.psychesolutionapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class approvedSchedViewHolder extends RecyclerView.ViewHolder {

    TextView schedPatientApproved, approvedSchedDate, approvedSchedTime,docNameView;

    public approvedSchedViewHolder(@NonNull View itemView) {
        super(itemView);

        schedPatientApproved = itemView.findViewById(R.id.textVieewApt);
        approvedSchedDate = itemView.findViewById(R.id.rejSchedDate);
        approvedSchedTime = itemView.findViewById(R.id.rejSchedTime);
        //docNameView = itemView.findViewById(R.id.rejSchedDocName);
    }

}
