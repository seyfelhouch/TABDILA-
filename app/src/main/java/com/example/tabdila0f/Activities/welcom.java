package com.example.tabdila0f.Activities;


import android.content.Intent;

import android.os.Bundle;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.example.tabdila0f.Adapters.PostAdapter;
import com.example.tabdila0f.Models.Post;
import com.example.tabdila0f.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class welcom extends AppCompatActivity
{
    private Button SearchBtn;
    private EditText inputText,result;
    private RecyclerView searchList;
    private String SearchInput;
    private ImageView retour;
    RecyclerView postRecyclerView ;
    PostAdapter postAdapter ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference ;
    List<Post> postList;
    public long i=0;
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        inputText = findViewById(R.id.search_product_name);
        SearchBtn = findViewById(R.id.search_btn);
        result=findViewById(R.id.result);
        retour = findViewById(R.id.retour11);
        searchList = findViewById(R.id.search_list);
        postRecyclerView  = findViewById(R.id.search_list);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        postRecyclerView.setHasFixedSize(true);
        firebaseDatabase = FirebaseDatabase.getInstance();
        searchList.setLayoutManager(new LinearLayoutManager(welcom.this));
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Posts");
        postRecyclerView.setVisibility(View.GONE);
        result.setText("The are "+i+"results");

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(welcom.this,Home.class));
            }
        });

        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SearchInput = inputText.getText().toString();

                postRecyclerView.setVisibility(View.VISIBLE);
                onStart();
                i+=1;
                result.setText("The are "+i+"results");

            }
        });


    }

    @Override
    protected void onStart()
    {
        super.onStart();
        query =databaseReference.orderByChild("title").equalTo(SearchInput);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                i = (int) dataSnapshot.getChildrenCount();
                postList = new ArrayList<>();
                for (DataSnapshot postsnap : dataSnapshot.getChildren()) {

                    Post post = postsnap.getValue(Post.class);
                    postList.add(post);
                }

                postAdapter = new PostAdapter(getApplicationContext(),postList);
                postRecyclerView.setAdapter(postAdapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    }
