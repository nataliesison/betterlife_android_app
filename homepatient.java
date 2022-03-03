package com.example.psychesolutionapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.psychesolutionapp.Utils.Comments;
import com.example.psychesolutionapp.Utils.posts;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class homepatient extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;

    NavigationView navigationView;
    DrawerLayout drawerLayout;

    Uri imageUri;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mUserRef, postRef, likeRef, commentRef;
    StorageReference StorageRef;
    String profileImageUrlV, usernameV;
    CircleImageView profileImageHeader;
    TextView usernameHeader;
    EditText inputAddpost;
    ProgressDialog progressDialog;
    StorageReference postImageRef;
    FirebaseRecyclerAdapter<posts, MyViewHolder>adapter;
    FirebaseRecyclerOptions<posts>options;
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<Comments>commentOption;
    FirebaseRecyclerAdapter<Comments,commentViewholder>commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepatient);

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("BETTER LIFE");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        }

        recyclerView = findViewById(R.id.recyclerviewing);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        inputAddpost = findViewById(R.id.inputAddpost);
        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        postRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        likeRef = FirebaseDatabase.getInstance().getReference().child("Likes");
        postImageRef = FirebaseStorage.getInstance().getReference().child("postImages");
        commentRef = FirebaseDatabase.getInstance().getReference().child("Comments");

        FirebaseMessaging.getInstance().subscribeToTopic(mUser.getUid());

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);

//        ActivityResultLauncher<Intent> postActivity = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if(result.getResultCode()== Activity.RESULT_OK){
//
//                        assert result.getData() != null;
//                        imageUri = result.getData().getData();
//                        addImagePost.setImageURI(imageUri);
//                    }
//                }
//        );

        View view = navigationView.inflateHeaderView(R.layout.drawer_header);
        profileImageHeader = view.findViewById(R.id.profileImage_header);
        usernameHeader = view.findViewById(R.id.username_header);

        navigationView.setNavigationItemSelectedListener(this);

//        send_post_imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addPost();
//            }
//        });
//        addImagePost.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                postActivity.launch(intent);
//
//            }
//        });

        loadpost();

    }

    private void loadpost() {
        options = new FirebaseRecyclerOptions.Builder<posts>().setQuery(postRef, posts.class).build();
        adapter = new FirebaseRecyclerAdapter<posts, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull posts model) {

                final String postKey;
                postKey = getRef(position).getKey();
                holder.postDescription.setText(model.getPostDesc());
                holder.timeAgo.setText(model.getDatePost());
                holder.profileUsernamePost.setText(model.getUsername());
                Picasso.get().load(model.getPostImageUrl()).into(holder.postImage);
                Picasso.get().load(model.getUserProfileImageUrl()).into(holder.profileImagePost);
                holder.countLikes(postKey, mUser.getUid(), likeRef);
                holder.countComment(postKey, mUser.getUid(), commentRef);
                holder.likeImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        likeRef.child(postKey).child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){

                                    likeRef.child(postKey).child(mUser.getUid()).removeValue();
                                    holder.likeImage.setColorFilter(Color.rgb(203,153,108));
                                    notifyDataSetChanged();

                                }
                                else{

                                    likeRef.child(postKey).child(mUser.getUid()).setValue("like");
                                    holder.likeImage.setColorFilter(Color.MAGENTA);
                                    notifyDataSetChanged();

                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                                Toast.makeText(homepatient.this,""+error.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });

                holder.sendComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String comment = holder.inputComment.getText().toString();
                        if(comment.isEmpty()){
                            Toast.makeText(homepatient.this, "Please Write something", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            AddComment(holder, postKey, commentRef, mUser.getUid(), comment);
                        }
                    }
                });

                LoadComment(postKey);
                holder.postImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(homepatient.this, ImageViewActivity.class);
                        intent.putExtra("url", model.getPostImageUrl());
                        startActivity(intent);
                    }
                });


            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ssingle_view_post, parent, false);
                return new MyViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    private void LoadComment(String postKey) {
        MyViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(homepatient.this));
        commentOption = new FirebaseRecyclerOptions.Builder<Comments>().setQuery(commentRef.child(postKey), Comments.class).build();
        commentAdapter = new FirebaseRecyclerAdapter<Comments, commentViewholder>(commentOption) {
            @Override
            protected void onBindViewHolder(@NonNull commentViewholder holder, int position, @NonNull Comments model) {
                Picasso.get().load(model.getProfileImage()).into(holder.profileImageComment);
                holder.usernameComment.setText(model.getUsername());
                holder.comment.setText(model.getComment());

            }

            @NonNull
            @Override
            public commentViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_comment,parent, false);
                return new commentViewholder(view);
            }
        };
        commentAdapter.startListening();
        MyViewHolder.recyclerView.setAdapter(commentAdapter);
    }

    private void AddComment(MyViewHolder holder, String postKey, DatabaseReference commentRef, String uid, String comment) {
        HashMap hashMap = new HashMap();
        hashMap.put("username", usernameV);
        hashMap.put("profileImage", profileImageUrlV);
        hashMap.put("comment", comment);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String strDate= formatter.format(date);

        String userPostID = mUser.getUid()+(strDate);

        commentRef.child(postKey).child(userPostID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){

                    Toast.makeText(homepatient.this, "Comment Added", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    holder.inputComment.setText(null);

                }
                else{
                    Toast.makeText(homepatient.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    private void addPost() {
//        String postDesc = inputAddpost.getText().toString();
//        if(postDesc.isEmpty() || postDesc.length()<2){
//
//            inputAddpost.setError("Please write something");
//        }
//        else if(imageUri==null){
//
//            Toast.makeText(this, "Please select an Image", Toast.LENGTH_SHORT).show();
//
//        }
//        else{
//            progressDialog.setTitle("Posting...");
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();
//
//            Date date = new Date();
//            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
//            String strDate= formatter.format(date);
//
//            String userPostID = mUser.getUid()+(strDate);
//
//            postImageRef.child(userPostID).putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                    if(task.isSuccessful()){
//                        postImageRef.child(userPostID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//
//                                HashMap<String,Object> hashMap = new HashMap<>();
//                                hashMap.put("datePost", strDate);
//                                hashMap.put("postImageUrl", uri.toString());
//                                hashMap.put("postDesc", postDesc);
//                                hashMap.put("userProfileImageUrl", profileImageUrlV);
//                                hashMap.put("username", usernameV);
//                                postRef.child(userPostID).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
//                                    @Override
//                                    public void onComplete(@NonNull Task task) {
//                                        if(task.isSuccessful()){
//                                            progressDialog.dismiss();
//                                            Toast.makeText(homepatient.this, "Post Added", Toast.LENGTH_SHORT).show();
//                                            addImagePost.setImageResource(R.drawable.ic_baseline_image_24);
//                                            inputAddpost.setText("");
//                                        }
//                                        else{
//                                            progressDialog.dismiss();
//                                            Toast.makeText(homepatient.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
//                                        }
//
//                                    }
//                                });
//                            }
//                        });
//                    }
//                    else{
//                        progressDialog.dismiss();
//                        Toast.makeText(homepatient.this, ""+task.getException().toString(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mUser == null){
            SendUserToLoginActivity();
        }
        else{

            mUserRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){

                        profileImageUrlV = String.valueOf(snapshot.child("profileImage").getValue());
                        usernameV = String.valueOf(snapshot.child("username").getValue());

                        Picasso.get().load(profileImageUrlV).into(profileImageHeader);
                        usernameHeader.setText(usernameV);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(homepatient.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                }
            });

        }
    }

    private void SendUserToLoginActivity() {
        Intent intent = new Intent(homepatient.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.home:
                startActivity(new Intent(homepatient.this, homepatient.class));
                break;

            case R.id.profile:
                startActivity(new Intent(homepatient.this, ProfileActivity.class));
                //3.Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                break;

            case R.id.scheduler:
                startActivity(new Intent(homepatient.this, scheduler.class));
                //Toast.makeText(this, "Scheduler", Toast.LENGTH_SHORT).show();
                break;

            case R.id.messenger:
                startActivity(new Intent(homepatient.this, ChatUsersActivity.class));
                break;

            case R.id.forum:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_patient, new DepartmentFragment()).commit();
                //Toast.makeText(this, "Forum", Toast.LENGTH_SHORT).show();
                break;

            case R.id.articles:
                startActivity(new Intent(homepatient.this, article.class));
                //Toast.makeText(this, "Article", Toast.LENGTH_SHORT).show();
                break;

            case R.id.logout:
                //Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent register = new Intent(this, MainActivity.class);
                startActivity(register);
                finish();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if(item.getItemId()==android.R.id.home){

            drawerLayout.openDrawer(GravityCompat.START);
            return true;

        }

        return true;

    }
}