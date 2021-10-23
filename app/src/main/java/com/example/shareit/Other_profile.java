package com.example.shareit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Other_profile extends AppCompatActivity {
    TextView username,email,phone,mypost;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    CircleImageView dp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_profile);
        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        mypost=findViewById(R.id.mypost);
        dp=findViewById(R.id.other_dp);
        String st=getIntent().getStringExtra("text");
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("USERS");

        databaseReference.child(st).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username.setText(snapshot.child("user").getValue().toString());
                email.setText(snapshot.child("email").getValue().toString());
                phone.setText(snapshot.child("phone").getValue().toString());
                Glide.with(Other_profile.this).load(snapshot.child("image_uri").getValue()).into(dp);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Other_profile.this, "Failed to load data!!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}