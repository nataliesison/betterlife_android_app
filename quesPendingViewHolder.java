package com.example.psychesolutionapp;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class quesPendingViewHolder extends RecyclerView.ViewHolder {

    CircleImageView circleDp;
    TextView pend_un, pend_text,pend_date, pend_time;
    Button approve_pendBtn, decline_pendBtn;
    public quesPendingViewHolder(@NonNull View itemView) {
        super(itemView);

        circleDp = itemView.findViewById(R.id.post_pending_img);
        pend_un = itemView.findViewById(R.id.pending_username);
        pend_text = itemView.findViewById(R.id.Answer_pending_text);
        pend_date = itemView.findViewById(R.id.pending_date);
        pend_time = itemView.findViewById(R.id.pending_time);
        approve_pendBtn = itemView.findViewById(R.id.approveBtn);
        decline_pendBtn = itemView.findViewById(R.id.declineBtn);
    }
}
