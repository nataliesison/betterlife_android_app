package com.example.psychesolutionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class viewArticleActivity extends AppCompatActivity {

    Toolbar toolbar;

    DatabaseReference mArticleRef, requestRef, friendRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String profileImageurl, username, article, title, date, time;
    String articlePostID;

    TextView articleTitleView, authorUsername, authorDate, authorTime, articleMain;

    CircleImageView authorProfImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_article);

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Articles");

        articlePostID = getIntent().getStringExtra("articleKey");
        mArticleRef = FirebaseDatabase.getInstance().getReference().child("Articles");

        articleTitleView = findViewById(R.id.articleTitleView);
        authorUsername = findViewById(R.id.authorUsername);
        authorDate = findViewById(R.id.authorDate);
        authorTime = findViewById(R.id.authorTime);
        articleMain = findViewById(R.id.mainArticle);
        authorProfImage = findViewById(R.id.authorProfImage);

        LoadArticle();
    }

    private void LoadArticle() {
        mArticleRef.child(articlePostID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    profileImageurl = snapshot.child("profileImage").getValue().toString();
                    username = snapshot.child("usernamee").getValue().toString();
                    article = snapshot.child("article").getValue().toString();
                    title = snapshot.child("title").getValue().toString();
                    date = snapshot.child("date").getValue().toString();
                    time = snapshot.child("time").getValue().toString();

                    articleTitleView.setText(title);
                    authorUsername.setText(username);
                    authorDate.setText(date);
                    authorTime.setText(time);
                    articleMain.setText(article);
                    Picasso.get().load(profileImageurl).into(authorProfImage);
                }
                else{
                    Toast.makeText(viewArticleActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(viewArticleActivity.this, ""+error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}