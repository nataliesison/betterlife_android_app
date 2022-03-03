package com.example.psychesolutionapp;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class pendingSchedViewHolder extends RecyclerView.ViewHolder {

    TextView schedPatientName, approveSchedDateApt, approveSchedTimeApt;
    Button approveApt, rejApt;
    public pendingSchedViewHolder(@NonNull View itemView) {
        super(itemView);

        schedPatientName = itemView.findViewById(R.id.textVieewApt);
        approveSchedDateApt =itemView.findViewById(R.id.rejSchedDate);
        approveSchedTimeApt = itemView.findViewById(R.id.rejSchedTime);
        approveApt = itemView.findViewById(R.id.approveApt);
        rejApt = itemView.findViewById(R.id.rejApt);
    }
}
