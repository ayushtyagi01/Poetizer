package com.example.shareit;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class draft_adapter extends RecyclerView.Adapter<draft_adapter.Viewholder>{
    Context context;
    ArrayList<dashboard_model> list;
    draft_adapter.OnUserClicked onUserClicked;

    public draft_adapter(Context context, ArrayList<dashboard_model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public draft_adapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_draft,parent,false);
        return new draft_adapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull draft_adapter.Viewholder holder, int position) {
        dashboard_model model=list.get(position);
        holder.adddraft.setText(model.getSt());
        holder.adddraft.setTextColor(model.getPen_col());
        holder.adddraft.setBackgroundColor(model.getPost_col());
        Typeface typeface= Typeface.createFromAsset(context.getAssets(), model.getFont());
        holder.adddraft.setTypeface(typeface);
        holder.clickListener(position, FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class Viewholder extends RecyclerView.ViewHolder{
        TextView adddraft;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            adddraft = itemView.findViewById(R.id.adddraft);
        }
            private void clickListener(int position,String uid){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onUserClicked.onClicked(position,uid);
                    }
                });
        }
    }
    public void OnUserClicked(draft_adapter.OnUserClicked onUserClicked){
        this.onUserClicked=onUserClicked;
    }
    public interface OnUserClicked{
        void onClicked(int position,String uid);
    }
}
