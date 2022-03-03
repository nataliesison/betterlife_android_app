package com.example.psychesolutionapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class existingSchecReqViewHolder extends RecyclerView.ViewHolder {

    TextView rejSchedDate, rejSchedtime, rejSchedDocName;

    public existingSchecReqViewHolder(@NonNull View itemView) {
        super(itemView);

        rejSchedtime = itemView.findViewById(R.id.schedTimeRej);
        rejSchedDate = itemView.findViewById(R.id.schedDateRej);
        rejSchedDocName = itemView.findViewById(R.id.schedDocNameRej);
    }
}
