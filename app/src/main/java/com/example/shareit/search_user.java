package com.example.shareit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class search_user extends AppCompatActivity {
    RecyclerView recview;
    DatabaseReference databaseReference;
    SearchAdapter adapter;
    ArrayList<Users> list;
    EditText search_bar;
    BottomNavigationView bottom_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        search_bar=findViewById(R.id.search_bar);
        recview=findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(search_user.this));
        bottom_nav=findViewById(R.id.bottom_nav);
        databaseReference= FirebaseDatabase.getInstance().getReference("USERS");

        list=new ArrayList<>();
        adapter= new SearchAdapter(search_user.this,list);
        recview.setAdapter(adapter);

        //readUsers();
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUsers(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){

                    case R.id.home:
                        Intent intent_search=new Intent(search_user.this,MainActivity.class);
                        startActivity(intent_search);
                        break;
                    case R.id.search:
                        break;
                    case R.id.add_post:
                        Intent intent_add=new Intent(search_user.this,add_post.class);
                        startActivity(intent_add);
                        break;
                    case R.id.notification:
                        Intent intent_draft=new Intent(search_user.this,Draft.class);
                        startActivity(intent_draft);
                        break;
                    case R.id.profile:
                        Intent intent_profile=new Intent(search_user.this,Profile.class);
                        startActivity(intent_profile);
                        break;
                }
                return true;
            }
        });


}

    private void searchUsers(String s) {
        Query query = FirebaseDatabase.getInstance().getReference("USERS").orderByChild("user").startAt(s)
                .endAt(s+"\uf8ff");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Users user = snapshot.getValue(Users.class);
                    list.add(user);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
            clickListener();
    }

    private void clickListener(){
        adapter.OnUserClicked(new SearchAdapter.OnUserClicked() {
            @Override
            public void onClicked(int position, String uid) {
                Intent intent=new Intent(search_user.this,Other_profile.class);
                intent.putExtra("text", uid);
                startActivity(intent);
            }
        });
    }

    private void readUsers(){
                databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Users model=dataSnapshot.getValue(Users.class);
                    list.add(model);

                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    }
