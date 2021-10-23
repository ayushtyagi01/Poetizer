package com.example.shareit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottom_nav;
    RecyclerView rview;
    DatabaseReference databaseReference;
    dashboard_adapter adapter;
    ArrayList<dashboard_model> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        bottom_nav=findViewById(R.id.bottom_nav);
        rview=findViewById(R.id.rview);
        rview.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        databaseReference= FirebaseDatabase.getInstance().getReference("ALLPOST");

        list=new ArrayList<>();
        adapter= new dashboard_adapter(MainActivity.this,list);
        rview.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                  dashboard_model model=dataSnapshot.getValue(dashboard_model.class);
                  list.add(model);

              }
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
              adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){

                    case R.id.home:
                        break;
                    case R.id.search:
                        Intent intent_search=new Intent(MainActivity.this,search_user.class);
                        startActivity(intent_search);
                        break;
                    case R.id.add_post:
                        try {
                            Toast.makeText(MainActivity.this, "lgbcbwhbwb", Toast.LENGTH_SHORT).show();
                            Intent intent_add = new Intent(MainActivity.this,add_post.class);
                            startActivity(intent_add);
                            break;
                        }
                        catch(Exception e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    case R.id.notification:
                        Intent intent_draft=new Intent(MainActivity.this,Draft.class);
                        startActivity(intent_draft);
                        break;
                    case R.id.profile:
                        Intent intent_profile=new Intent(MainActivity.this,Profile.class);
                        startActivity(intent_profile);
                        break;
                }
                return true;
            }
        });
    }
}