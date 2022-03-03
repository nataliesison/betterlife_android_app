package com.example.psychesolutionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class addArticle extends AppCompatActivity{

    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    EditText articleTitle, articlePost;
    TextView btnDraftList;
    Button btnPost, btnDraft;
    CircleImageView profileImageHeader;
    TextView usernameHeader;
    FirebaseAuth mAuth;
    String currentUserID;
    DatabaseReference UsersReference;
    DatabaseReference UsersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);

        mAuth= FirebaseAuth.getInstance();
        UsersRef= FirebaseDatabase.getInstance().getReference().child("Users");
        currentUserID=mAuth.getCurrentUser().getUid();

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Article");

        articleTitle = findViewById(R.id.articleTitle);
        articlePost = findViewById(R.id.articlePost);
        btnDraft = findViewById(R.id.btnDraft);
        btnPost = findViewById(R.id.btnPost);
        btnDraftList = findViewById(R.id.btnDraftList);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(addArticle.this);
                alertDialogBuilder.setMessage("Post this article?");
                        alertDialogBuilder.setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        addNewArticle();
                                        //Toast.makeText(addArticle.this,"You clicked yes button",Toast.LENGTH_LONG).show();
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
        btnDraftList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(addArticle.this, articleDraft.class));
            }
        });

        btnDraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(addArticle.this);
                alertDialogBuilder.setMessage("Save this to draft?");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                addNewDraft();
                                //Toast.makeText(addArticle.this,"You clicked yes button",Toast.LENGTH_LONG).show();
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

    public void addNewArticle(){
        final String titleArticle = articleTitle.getText().toString();
        final String postArticle = articlePost.getText().toString();

        if(TextUtils.isEmpty(titleArticle)||TextUtils.isEmpty(postArticle)){
            Toast.makeText(addArticle.this,"Please fill up the fields.",Toast.LENGTH_LONG).show();
            return;
        }
        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
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
                    userMap.put("uid",currentUserID);
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
                                Toast.makeText(addArticle.this,"Article added successfully",Toast.LENGTH_LONG).show();
                                articleTitle.setText(" ");
                                articlePost.setText(" ");
                                return;
                            }
                            else {
                                Toast.makeText(addArticle.this,task.getException().toString(),Toast.LENGTH_LONG).show();
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

    public void addNewDraft(){
        final String titleArticle = articleTitle.getText().toString();
        final String postArticle = articlePost.getText().toString();

        if(TextUtils.isEmpty(titleArticle)||TextUtils.isEmpty(postArticle)){
            Toast.makeText(addArticle.this,"Please fill up the fields.",Toast.LENGTH_LONG).show();
            return;
        }
        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
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
                    UsersReference=database.getReference().child("Article_Drafts");
                    String pushID=UsersReference.push().getKey();
                    HashMap userMap=new HashMap();
                    userMap.put("uid",currentUserID);
                    userMap.put("usernamee",username);
                    userMap.put("profileImage",userprofileImg);
                    userMap.put("title",titleArticle);
                    userMap.put("article",postArticle);
                    userMap.put("date",saveCurrentDate);
                    userMap.put("time",saveCurrentTime);
                    UsersReference.child(currentUserID).child(pushID).updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful())
                            {
                                articleTitle = findViewById(R.id.articleTitle);
                                articlePost = findViewById(R.id.articlePost);
                                Toast.makeText(addArticle.this,"Article added to drafts",Toast.LENGTH_LONG).show();
                                articleTitle.setText(" ");
                                articlePost.setText(" ");
                                return;
                            }
                            else {
                                Toast.makeText(addArticle.this,task.getException().toString(),Toast.LENGTH_LONG).show();
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