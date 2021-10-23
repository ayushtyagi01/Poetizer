package com.example.shareit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.viewholder> {
    Context context;
    ArrayList<Users> list;
    OnUserClicked onUserClicked;

    public SearchAdapter(Context context, ArrayList<Users> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SearchAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_search,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.viewholder holder, int position) {
        Users model=list.get(position);
        holder.tv_user.setText(model.getUser());
        Picasso.get().load(model.getImage_uri()).into(holder.profile);
        holder.clickListener(position,list.get(position).getUid());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        TextView tv_user;
        CircleImageView profile;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            tv_user=itemView.findViewById(R.id.tv_user);
            profile=itemView.findViewById(R.id.iv_profile);
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
    public void OnUserClicked(OnUserClicked onUserClicked){
        this.onUserClicked=onUserClicked;
    }
    public interface OnUserClicked{
        void onClicked(int position,String uid);
    }
}
