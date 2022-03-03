package com.example.psychesolutionapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class rejectedSchedViewHolder extends RecyclerView.ViewHolder {

    TextView rejectedAptTime, rejectedAptDate;
    public rejectedSchedViewHolder(@NonNull View itemView) {
        super(itemView);

        rejectedAptDate = itemView.findViewById(R.id.rejectedDate);
        rejectedAptTime = itemView.findViewById(R.id.rejectedTime);
    }
}
