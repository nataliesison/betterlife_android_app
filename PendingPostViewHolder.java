package com.example.psychesolutionapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class PendingPostViewHolder extends  RecyclerView.ViewHolder{

    CircleImageView imagepend;
    TextView myuserNamepend, myanswerpend, mydatepend, mytimepend;
    public static RecyclerView recyclerView;
    View mView;
    public PendingPostViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        myuserNamepend = (TextView) mView.findViewById(R.id.pending_username);
        myanswerpend = (TextView) mView.findViewById(R.id.Answer_pending_text);
        mydatepend = (TextView) mView.findViewById(R.id.pending_date);
        mytimepend = (TextView) mView.findViewById(R.id.pending_time);
        imagepend = (CircleImageView) mView.findViewById(R.id.post_pending_img);
    }
}
