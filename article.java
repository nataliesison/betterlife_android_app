package com.example.psychesolutionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.psychesolutionapp.Utils.Users;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class article extends AppCompatActivity {

    FirebaseRecyclerOptions<articlees> options;
    FirebaseRecyclerAdapter<articlees, articleViewHolder> adapter;
    Toolbar toolbar;
    DatabaseReference mArticleRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Articles");

        mArticleRef = FirebaseDatabase.getInstance().getReference().child("Articles");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        recyclerView = findViewById(R.id.recyclerviewArticleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        LoadArticles("");

    }

    private void LoadArticles(String s) {
        Query query=mArticleRef.orderByChild("title").startAt(s).endAt(s+"\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<articlees>().setQuery(query, articlees.class).build();
        adapter = new FirebaseRecyclerAdapter<articlees, articleViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull articleViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull articlees model) {

                Picasso.get().load(model.getProfileImage()).into(holder.profileImageFindArticle);
                holder.usernameFindArticle.setText(model.getTitle());
                holder.docUsername.setText(model.getUsernamee());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(article.this, viewArticleActivity.class);
                        intent.putExtra("articleKey",getRef(position).getKey().toString());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public articleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_find_article, parent, false);

                return new articleViewHolder(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                LoadArticles(newText);
                return false;
            }
        });
        return true;
    }
}