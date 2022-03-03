
package com.example.psychesolutionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class viewArticleDraftActivity extends AppCompatActivity {

    Toolbar toolbar;

    DatabaseReference mArticleDraftRef, UsersReference, UsersRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String profileImageurl, username, article, title, date, time;
    String articleDraftID;

    EditText articlePost, articleTitle;
    Button btnDraft, btnPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_article_draft);

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Draft");

        UsersRef= FirebaseDatabase.getInstance().getReference().child("Users");
//        currentUserID=mAuth.getCurrentUser().getUid();

        mArticleDraftRef = FirebaseDatabase.getInstance().getReference().child("Article_Drafts");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        articleDraftID = getIntent().getStringExtra("articleDraftKey");

        articlePost = findViewById(R.id.articlePost);
        articleTitle = findViewById(R.id.articleTitle);
        btnDraft = findViewById(R.id.btnDraft);
        btnPost = findViewById(R.id.btnPost);

        LoadArticleDraft();

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(viewArticleDraftActivity.this);
                alertDialogBuilder.setMessage("Proceed to Post this draft?");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                postDraft();
                                startActivity(new Intent(viewArticleDraftActivity.this,articleDraft.class));
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        btnDraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(viewArticleDraftActivity.this);
                alertDialogBuilder.setMessage("Save draft changes??");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                updateDraft();
                                startActivity(new Intent(viewArticleDraftActivity.this,articleDraft.class));
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    public void postDraft(){
        mArticleDraftRef.child(mUser.getUid()).child(articleDraftID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    final String titleArticle = articleTitle.getText().toString();
                    final String postArticle = articlePost.getText().toString();

                    if(TextUtils.isEmpty(titleArticle)||TextUtils.isEmpty(postArticle)){
                        Toast.makeText(viewArticleDraftActivity.this,"Please fill up the fields.",Toast.LENGTH_LONG).show();
                        return;
                    }
                    UsersRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                String username=snapshot.child("username").getValue().toString();
                                String userprofileImg=snapshot.child("profileImage").getValue().toString();
                                Calendar calForDate = Calendar.getInstance();
                                SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMM-yy");
                                final String saveCurrentDate = currentDate.format(calForDate.getTime());

                                Calendar calForTime = Calendar.getInstance();
                                SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
                                final String  saveCurrentTime = currentTime.format(calForDate.getTime());

                                final FirebaseDatabase database=FirebaseDatabase.getInstance();
                                UsersReference=database.getReference().child("Articles");
                                String pushID=UsersReference.push().getKey();
                                HashMap userMap=new HashMap();
                                userMap.put("uid",mUser.getUid());
                                userMap.put("usernamee",username);
                                userMap.put("profileImage",userprofileImg);
                                userMap.put("title",titleArticle);
                                userMap.put("article",postArticle);
                                userMap.put("date",saveCurrentDate);
                                userMap.put("time",saveCurrentTime);
                                UsersReference.child(pushID).updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if(task.isSuccessful())
                                        {
                                            articleTitle = findViewById(R.id.articleTitle);
                                            articlePost = findViewById(R.id.articlePost);
                                            Toast.makeText(viewArticleDraftActivity.this,"Article added successfully",Toast.LENGTH_LONG).show();
                                            articleTitle.setText(" ");
                                            articlePost.setText(" ");
                                            return;
                                        }
                                        else {
                                            Toast.makeText(viewArticleDraftActivity.this,task.getException().toString(),Toast.LENGTH_LONG).show();
                                            return;
                                        }
                                    }
                                });

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });

    }

    public void updateDraft(){
        final String titleArticle = articleTitle.getText().toString();
        final String postArticle = articlePost.getText().toString();

        mArticleDraftRef.child(mUser.getUid()).child(articleDraftID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String username=snapshot.child("usernamee").getValue().toString();
                    String userprofileImg=snapshot.child("profileImage").getValue().toString();
                    Calendar calForDate = Calendar.getInstance();
                    SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMM-yy");
                    final String saveCurrentDate = currentDate.format(calForDate.getTime());

                    Calendar calForTime = Calendar.getInstance();
                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
                    final String  saveCurrentTime = currentTime.format(calForDate.getTime());

                    final FirebaseDatabase database=FirebaseDatabase.getInstance();
                    UsersReference=database.getReference().child("Article_Drafts").child(mUser.getUid());
                    //String pushID=UsersReference.push().getKey();
                    HashMap userMap=new HashMap();
                    userMap.put("uid",mUser.getUid());
                    userMap.put("usernamee",username);
                    userMap.put("profileImage",userprofileImg);
                    userMap.put("title",titleArticle);
                    userMap.put("article",postArticle);
                    userMap.put("date",saveCurrentDate);
                    userMap.put("time",saveCurrentTime);
                    UsersReference.child(articleDraftID).updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful())
                            {
                                articleTitle = findViewById(R.id.articleTitle);
                                articlePost = findViewById(R.id.articlePost);
                                Toast.makeText(viewArticleDraftActivity.this,"Updated Draft successfully",Toast.LENGTH_LONG).show();
                                articleTitle.setText(" ");
                                articlePost.setText(" ");
                                return;
                            }
                            else {
                                Toast.makeText(viewArticleDraftActivity.this,task.getException().toString(),Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    });

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void LoadArticleDraft() {
        mArticleDraftRef.child(mUser.getUid()).child(articleDraftID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

//                    profileImageurl = snapshot.child("profileImage").getValue().toString();
//                    username = snapshot.child("usernamee").getValue().toString();
                    article = snapshot.child("article").getValue().toString();
                    title = snapshot.child("title").getValue().toString();
//                    date = snapshot.child("date").getValue().toString();
//                    time = snapshot.child("time").getValue().toString();

                    articleTitle.setText(title);
                    articlePost.setText(article);
                }
                else{
                    Toast.makeText(viewArticleDraftActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(viewArticleDraftActivity.this, ""+error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}