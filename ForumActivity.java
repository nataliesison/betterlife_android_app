package com.example.psychesolutionapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForumActivity extends AppCompatActivity {

    Toolbar toolbar;

    FirebaseAuth mAuth;
    DatabaseReference mRef;
    FirebaseUser mUser;

    NavigationView navigationView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        //Toolbar toolbar=findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListner);
        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1, new ForumFragment()).commit();
        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListner = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.bforum:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1,new ForumFragment()).commit();
                    break;
                case R.id.bhome:
                    mAuth = FirebaseAuth.getInstance();
                    mUser = mAuth.getCurrentUser();
                    mRef = FirebaseDatabase.getInstance().getReference().child("Users");
                            if(mUser!=null){
                                mRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){

                                            String typeuser = snapshot.child("usertype").getValue().toString();

                                            Intent intent;
                                            if(typeuser.equals("Patient")){
                                                intent = new Intent(getApplicationContext(), homepatient.class);

                                            }
                                            else{
                                                intent = new Intent(getApplicationContext(), activity_homedoc.class);

                                            }
                                            startActivity(intent);
                                            finish();


                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }

                            else{
                                Intent intent = new Intent(getApplicationContext(), ForumActivity.class);
                                startActivity(intent);
                                finish();

                            }


//                    Intent intent=new Intent(getApplicationContext(),activity_homedoc.class);
//                    startActivity(intent);
//                    finish();
                    break;
                case R.id.badd:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1,new AddButtonQue()).commit();
                    break;
                case R.id.bsearch:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1,new SearchQuestionFragment()).commit();
                    break;
//                case R.id.bfindf:
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1,new FindFriend()).commit();
//                    break;
            }
            return true;
        }
    };

}