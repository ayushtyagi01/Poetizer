package com.example.shareit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class add_post extends AppCompatActivity {
    BottomNavigationView bottom_nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_post);
        final EditText et_post = findViewById(R.id.et_post);
        Button btn_add = findViewById(R.id.btn_add);
        bottom_nav=findViewById(R.id.bottom_nav);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

                String st = et_post.getText().toString();
                if (st.isEmpty()){
                    Toast.makeText(add_post.this, "Enter text!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(add_post.this, Upload_post.class);
                intent.putExtra("text", st);
                startActivity(intent);
            }
        });
        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){

                    case R.id.home:
                        Intent intent_add=new Intent(add_post.this,MainActivity.class);
                        startActivity(intent_add);
                        break;
                    case R.id.search:
                        Intent intent_search=new Intent(add_post.this,search_user.class);
                        startActivity(intent_search);
                        break;
                    case R.id.add_post:
                        break;
                    case R.id.notification:
                        Intent intent_draft=new Intent(add_post.this,Draft.class);
                        startActivity(intent_draft);
                        break;
                    case R.id.profile:
                        Intent intent_profile=new Intent(add_post.this,Profile.class);
                        startActivity(intent_profile);
                        break;
                }
                return true;
            }
        });
    }
}