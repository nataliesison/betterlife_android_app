package com.example.psychesolutionapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class diagnosisViewHolder extends RecyclerView.ViewHolder {

    TextView patientSessionDone, patientSessionTime, patientSessionDate;
    public diagnosisViewHolder(@NonNull View itemView) {
        super(itemView);

        patientSessionDone = itemView.findViewById(R.id.patientSessionDone);
        patientSessionTime = itemView.findViewById(R.id.patientSessionTime);
        patientSessionDate = itemView.findViewById(R.id.patientSessionDate);

    }
}
