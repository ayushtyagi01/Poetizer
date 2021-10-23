package com.example.shareit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Draft extends AppCompatActivity {
    draft_adapter adapter;
    DatabaseReference databaseReference,databaseReference2,databaseReference1;
    ArrayList<dashboard_model> list;
    RecyclerView review;
    long max=0,maxid=0;
    String image_uri,user;
    BottomNavigationView bottom_nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft);
        review=findViewById(R.id.rev);
        review.setLayoutManager(new LinearLayoutManager(Draft.this));
        bottom_nav=findViewById(R.id.bottom_nav);
        databaseReference= FirebaseDatabase.getInstance().getReference("DRAFTS").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        list=new ArrayList<>();
        adapter= new draft_adapter(Draft.this,list);
        review.setAdapter(adapter);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference2=firebaseDatabase.getReference("POSTS");
        databaseReference1=firebaseDatabase.getReference("ALLPOST");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    dashboard_model model=dataSnapshot.getValue(dashboard_model.class);
                    list.add(model);
                }
                Toast.makeText(Draft.this, "Success", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Draft.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    maxid=snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                    max=snapshot.getChildrenCount();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        FirebaseDatabase.getInstance().getReference("USERS").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            image_uri = snapshot.child("image_uri").getValue().toString();
                            user=snapshot.child("user").getValue().toString();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        clickListener();
    }

    private void clickListener() {
        adapter.OnUserClicked(new draft_adapter.OnUserClicked() {
            @Override
            public void onClicked(int position, String uid) {
                Posts post=new Posts(list.get(position).getSt(),list.get(position).getPost_col(),
                        list.get(position).getPen_col(),list.get(position).getFont(),String.valueOf(max+1),image_uri,user);
                databaseReference2.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(String.valueOf(max+1)).setValue(post)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Intent intent = new Intent(Draft.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(Draft.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                Posts post1=new Posts(list.get(position).getSt(),list.get(position).getPost_col(),
                        list.get(position).getPen_col(),list.get(position).getFont(),String.valueOf(maxid+1),image_uri,user);
                databaseReference1.child(String.valueOf(maxid+1)).setValue(post1);
            }
        });
        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){

                    case R.id.home:
                        Intent intent_add=new Intent(Draft.this,MainActivity.class);
                        startActivity(intent_add);
                        break;
                    case R.id.search:
                        Intent intent_search=new Intent(Draft.this,search_user.class);
                        startActivity(intent_search);
                        break;
                    case R.id.add_post:
                        Intent intent_draft=new Intent(Draft.this,add_post.class);
                        startActivity(intent_draft);
                        break;
                    case R.id.notification:
                        break;
                    case R.id.profile:
                        Intent intent_profile=new Intent(Draft.this,Profile.class);
                        startActivity(intent_profile);
                        break;
                }
                return true;
            }
        });
    }
}