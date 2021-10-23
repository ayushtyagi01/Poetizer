package com.example.shareit;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class dashboard_adapter extends RecyclerView.Adapter<dashboard_adapter.Viewholder> {

    Context context;
    ArrayList<dashboard_model> list;
    private boolean btn_like=false;
    FirebaseDatabase firebaseDatabase;
    TextToSpeech textToSpeech;

    public dashboard_adapter(Context context, ArrayList<dashboard_model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dashboard_design,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull dashboard_adapter.Viewholder holder, int position) {
        dashboard_model model=list.get(position);
        holder.addPost.setText(model.getSt());
        holder.addPost.setTextColor(model.getPen_col());
        holder.addPost.setBackgroundColor(model.getPost_col());
        Typeface typeface= Typeface.createFromAsset(context.getAssets(), model.getFont());
        holder.addPost.setTypeface(typeface);
        holder.username.setText(model.getUser());
        Picasso.get().load(model.getImage_uri()).into(holder.profile);

        FirebaseDatabase.getInstance().getReference("LIKES").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(model.getUid()).hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                    holder.like.setImageResource(R.drawable.ic_liked);
                }
                else{
                    holder.like.setImageResource(R.drawable.ic_like);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_like=true;
                    FirebaseDatabase.getInstance().getReference("LIKES").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (btn_like) {
                                if(snapshot.child(model.getUid()).hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                                    FirebaseDatabase.getInstance().getReference("LIKES").child(model.getUid())
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                                    btn_like=false;
                                    holder.like.setImageResource(R.drawable.ic_like);
                                }
                                else{
                                    FirebaseDatabase.getInstance().getReference("LIKES").child(model.getUid())
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("true");
                                    btn_like=false;
                                    holder.like.setImageResource(R.drawable.ic_liked);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
            }
        });
        textToSpeech=new TextToSpeech(context.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS){
                    int lang=textToSpeech.setLanguage((Locale.ENGLISH));
                }
            }
        });
        holder.speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int s=textToSpeech.speak(model.getSt(),TextToSpeech.QUEUE_FLUSH,null);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static  class Viewholder extends RecyclerView.ViewHolder{

        TextView addPost,username;
        ImageView like,speech;
        CircleImageView profile;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            addPost=itemView.findViewById(R.id.addPost);
            profile=itemView.findViewById(R.id.profile_image);
            username=itemView.findViewById(R.id.user);
            like=itemView.findViewById(R.id.like);
            speech=itemView.findViewById(R.id.speech);
        }
    }
}

