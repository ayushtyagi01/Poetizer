package com.example.shareit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class My_post extends AppCompatActivity {
    DatabaseReference databaseReference;
    dashboard_adapter adapter;
    ArrayList<dashboard_model> list;
    RecyclerView review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);
        review=findViewById(R.id.review);
        review.setLayoutManager(new LinearLayoutManager(My_post.this));

        databaseReference= FirebaseDatabase.getInstance().getReference("POSTS").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        list=new ArrayList<>();
        adapter= new dashboard_adapter(My_post.this,list);
        review.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    dashboard_model model=dataSnapshot.getValue(dashboard_model.class);
                    list.add(model);

                }
                Toast.makeText(My_post.this, "Success", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(My_post.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}