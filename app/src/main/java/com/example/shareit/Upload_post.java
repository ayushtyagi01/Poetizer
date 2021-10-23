package com.example.shareit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Upload_post extends AppCompatActivity {

    Button btn_upload,btn_draft;
    ImageView iv_bcolor,iv_pen;
    TextView tv_post;
    int pcount=0,pen_count=0,count=0,post_col=Color.RED,pen_col=Color.YELLOW;
    long maxid=0,max=0,draft_count=0;
    String font="newwishes.ttf",image_uri,user;
    FirebaseAuth auth;
    DatabaseReference databaseReference,databaseReference1;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_upload_post);
        tv_post=findViewById(R.id.tv_post);
        btn_upload=findViewById(R.id.btn_upload);
        btn_draft=findViewById(R.id.btn_draft);
        iv_bcolor=findViewById(R.id.iv_bcolor);
        iv_pen=findViewById(R.id.iv_pen);
        String st=getIntent().getStringExtra("text");
        tv_post.setText(st);

        auth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("POSTS");
        databaseReference1=firebaseDatabase.getReference("ALLPOST");

        iv_pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pen_count++;
                pen_count = pen_count % 6;
                switch (pen_count) {
                    case 0:
                        pen_col=Color.RED;
                        tv_post.setTextColor(Color.RED);
                        iv_pen.setColorFilter(Color.RED);
                        break;
                    case 1:
                        pen_col=Color.BLUE;
                        tv_post.setTextColor(Color.BLUE);
                        iv_pen.setColorFilter(Color.BLUE);
                        break;
                    case 2:
                        pen_col=Color.YELLOW;
                        tv_post.setTextColor(Color.YELLOW);
                        iv_pen.setColorFilter(Color.YELLOW);
                        break;
                    case 3:
                        pen_col=Color.BLACK;
                        tv_post.setTextColor(Color.BLACK);
                        iv_pen.setColorFilter(Color.BLACK);
                        break;
                    case 4:
                        pen_col=Color.GRAY;
                        tv_post.setTextColor(Color.GRAY);
                        iv_pen.setColorFilter(Color.GRAY);
                        break;
                    case 5:
                        pen_col=Color.CYAN;
                        tv_post.setTextColor(Color.CYAN);
                        iv_pen.setColorFilter(Color.CYAN);
                        break;
                }
            }
        });
        iv_bcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pcount++;
                pcount = pcount % 6;
                switch (pcount) {
                    case 0:
                        post_col=Color.RED;
                        tv_post.setBackgroundColor(Color.RED);
                        iv_bcolor.setColorFilter(Color.RED);
                        break;
                    case 1:
                        post_col=Color.BLUE;
                        tv_post.setBackgroundColor(Color.BLUE);
                        iv_bcolor.setColorFilter(Color.BLUE);
                        break;
                    case 2:
                        post_col=Color.YELLOW;
                        tv_post.setBackgroundColor(Color.YELLOW);
                        iv_bcolor.setColorFilter(Color.YELLOW);
                        break;
                    case 3:
                        post_col=Color.BLACK;
                        tv_post.setBackgroundColor(Color.BLACK);
                        iv_bcolor.setColorFilter(Color.BLACK);
                        break;
                    case 4:
                        post_col=Color.GRAY;
                        tv_post.setBackgroundColor(Color.GRAY);
                        iv_bcolor.setColorFilter(Color.GRAY);
                        break;
                    case 5:
                        post_col=Color.CYAN;
                        tv_post.setBackgroundColor(Color.CYAN);
                        iv_bcolor.setColorFilter(Color.CYAN);
                        break;
                }
            }
        });

        tv_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                count=count%5;
                switch (count){
                    case 0:
                        font="rightstrongline.ttf";
                        Typeface typeface= Typeface.createFromAsset(getAssets(),font);
                        tv_post.setTypeface(typeface);
                        break;
                    case 1:
                        font="anotherday.ttf";
                        Typeface typeface1=Typeface.createFromAsset(getAssets(),font);
                        tv_post.setTypeface(typeface1);
                        break;
                    case 2:
                        font="cartoonature.ttf";
                        Typeface typeface2=Typeface.createFromAsset(getAssets(),font);
                        tv_post.setTypeface(typeface2);
                        break;
                    case 3:
                        font="elegentdemo.ttf";
                        Typeface typeface3=Typeface.createFromAsset(getAssets(),font);
                        tv_post.setTypeface(typeface3);
                        break;
                    case 4:
                        font="newwishes.ttf";
                        Typeface typeface4=Typeface.createFromAsset(getAssets(),font);
                        tv_post.setTypeface(typeface4);
                        break;
                }
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
        FirebaseDatabase.getInstance().getReference("DRAFTS").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists())
                            draft_count=snapshot.getChildrenCount();
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

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("jfnroenfoienroignoin", "onClick: mkgmmgmopmerpomgpmpomeop");
                Posts post=new Posts(st,post_col,pen_col,font,String.valueOf(max+1),image_uri,user);
                databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(String.valueOf(max+1)).setValue(post)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Intent intent = new Intent(Upload_post.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(Upload_post.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull  Exception e) {
                        Toast.makeText(Upload_post.this,e.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                });
                Posts post1=new Posts(st,post_col,pen_col,font,String.valueOf(maxid+1),image_uri,user);
                databaseReference1.child(String.valueOf(maxid+1)).setValue(post1);

            }
        });
        btn_draft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Posts post=new Posts(st,post_col,pen_col,font,String.valueOf(draft_count+1),image_uri,user);
                FirebaseDatabase.getInstance().getReference("DRAFTS").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child(String.valueOf(draft_count+1)).setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent intent = new Intent(Upload_post.this, Draft.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
