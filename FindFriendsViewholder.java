package com.example.psychesolutionapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class FindFriendsViewholder extends RecyclerView.ViewHolder {
    View mView;
    TextView searchUsername;
    public FindFriendsViewholder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
    }
    public void setQuestion(String question) {
        searchUsername = (TextView)mView.findViewById(R.id.search_list_username);
        searchUsername.setText(question);
    }
}
