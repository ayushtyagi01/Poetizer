package com.example.shareit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    TextView username, email, phone, mypost, save;
    CircleImageView dp;
    int Image_Request_Code = 7;
    Uri uri;
    String imageUrl,password;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    StorageReference storageReference;
    BottomNavigationView bottom_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        mypost = findViewById(R.id.mypost);
        dp = findViewById(R.id.dp);
        bottom_nav=findViewById(R.id.bottom_nav);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("USERS");

        storageReference=FirebaseStorage.getInstance().getReference();

//        dp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent, Image_Request_Code);
//            }
//        });

        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username.setText(snapshot.child("user").getValue().toString());
                email.setText(snapshot.child("email").getValue().toString());
                phone.setText(snapshot.child("phone").getValue().toString());
                password=snapshot.child("password").getValue().toString();
                Glide.with(Profile.this).load(snapshot.child("image_uri").getValue()).into(dp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Failed to load data!!", Toast.LENGTH_SHORT).show();
            }
        });
        mypost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, My_post.class);
                startActivity(intent);
            }
        });

        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull  MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.home:
                        Intent intent_add=new Intent(Profile.this,MainActivity.class);
                        startActivity(intent_add);
                        break;
                    case R.id.search:
                        Intent intent_search=new Intent(Profile.this,search_user.class);
                        startActivity(intent_search);
                        break;
                    case R.id.add_post:
                        Intent intent_profile=new Intent(Profile.this,add_post.class);
                        startActivity(intent_profile);
                        break;
                    case R.id.notification:
                        Intent intent_draft=new Intent(Profile.this,Draft.class);
                        startActivity(intent_draft);
                        break;
                    case R.id.profile:
                        break;
                }
                return false;
            }
        });
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (uri != null) {
//                    StorageReference reference = storageReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                    reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                @Override
//                                public void onSuccess(Uri uri) {
//                                    Users user=new Users(email.getText().toString(),username.getText().toString(),password,phone.getText().toString(),
//                                            FirebaseAuth.getInstance().getCurrentUser().getUid(),uri.toString());
//                                    FirebaseDatabase.getInstance().getReference("USERS").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
//                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void unused) {
//                                                    Toast.makeText(Profile.this, "Saved!!", Toast.LENGTH_SHORT).show();
//                                                }
//                                            });
//                                }
//                            });
////                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
////                            while (!uriTask.isComplete()) ;
////                            Uri urlImage = uriTask.getResult();
////                            imageUrl = urlImage.toString();
//                            //Uploadtext();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(Profile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                else{
//                    Toast.makeText(Profile.this, "Please Select Image!!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

//    private void Uploadtext() {
//        Users user=new Users(email.getText().toString(),username.getText().toString(),password,phone.getText().toString(),
//                FirebaseAuth.getInstance().getCurrentUser().getUid(),imageUrl);
//        FirebaseDatabase.getInstance().getReference("USERS").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(Profile.this, "Saved!!", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

//    @Override
//        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
//            super.onActivityResult(requestCode, resultCode, data);
//            if (resultCode == RESULT_OK) {
//                uri = data.getData();
//                dp.setImageURI(uri);
//            } else {
//                Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
//            }
//        }

}