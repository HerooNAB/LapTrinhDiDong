package com.example.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Models.Post;
import com.example.androidui.R;
import com.example.androidui.Test.TestActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    private List<Post>listPost;
    private Context context;

    public PostAdapter(List<Post> listPost, Context context) {
        this.listPost = listPost;
        this.context = context;
    }


    //gọi giao diện
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ViewHolder(v);
    }

    //set dữ liệu cho giao diện
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = listPost.get(position);
        holder.textViewIdPost.setText(post.getIdPost());
        holder.textViewCaption.setText(post.getCaption());
        holder.textViewImgUrl.setText(post.getImage());
        Picasso.get().load(post.getImage()).into(holder.imagePost);
    }

    @Override
    public int getItemCount() {
        return listPost.size();
    }

    //Gọi đến giao diện
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewIdPost,textViewCaption,textViewImgUrl;
        public ImageView imagePost;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewIdPost = itemView.findViewById(R.id.idPost);
            textViewCaption = itemView.findViewById(R.id.caption);
            textViewImgUrl = itemView.findViewById(R.id.imgUrl);
            imagePost = itemView.findViewById(R.id.imagePost);

        }
    }
}
