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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class articleDraft extends AppCompatActivity {

    Toolbar toolbar;

    FirebaseRecyclerOptions<draafts> options;
    FirebaseRecyclerAdapter<draafts, articleDraftViewHolder> adapter;

    DatabaseReference mArticleDraftRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_draft);

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Draft List");

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        recyclerView = findViewById(R.id.recyclerviewArticleDraftList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mArticleDraftRef = FirebaseDatabase.getInstance().getReference().child("Article_Drafts");

        LoadArticlesDraft("");
    }

    private void LoadArticlesDraft(String s) {
        Query query=mArticleDraftRef.child(mUser.getUid()).orderByChild("title").startAt(s).endAt(s+"\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<draafts>().setQuery(query, draafts.class).build();
        adapter = new FirebaseRecyclerAdapter<draafts, articleDraftViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull articleDraftViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull draafts model) {

                Picasso.get().load(model.getProfileImage()).into(holder.profileImageArticleDraft);
                holder.titleArticleDraft.setText(model.getTitle());
                holder.usernameArticleDraft.setText(model.getUsernamee());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(articleDraft.this, viewArticleDraftActivity.class);
                        intent.putExtra("articleDraftKey",getRef(position).getKey().toString());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public articleDraftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view_article_draft, parent, false);

                return new articleDraftViewHolder(view);
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
                LoadArticlesDraft(newText);
                return false;
            }
        });
        return true;
    }
}