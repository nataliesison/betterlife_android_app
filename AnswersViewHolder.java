package com.example.psychesolutionapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class AnswersViewHolder extends RecyclerView.ViewHolder {

    CircleImageView image;
    TextView myuserName, myanswer, mydate, mytime;
    public static RecyclerView recyclerView;
    View mView;
    public AnswersViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        myuserName = (TextView) mView.findViewById(R.id.ans_username);
        myanswer = (TextView) mView.findViewById(R.id.Answer_text);
        mydate = (TextView) mView.findViewById(R.id.ans_date);
        mytime = (TextView) mView.findViewById(R.id.ans_time);
        image = (CircleImageView) mView.findViewById(R.id.post_ans_img);
    }
}
