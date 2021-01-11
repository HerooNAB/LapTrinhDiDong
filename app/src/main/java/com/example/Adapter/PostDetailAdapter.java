package com.example.Adapter;

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
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostDetailAdapter extends RecyclerView.Adapter<PostDetailAdapter.ViewHolder>{

    private List<Post> mPostDetail;
    private Context context;

    public PostDetailAdapter(List<Post> mPost, Context context) {
        this.mPostDetail = mPost;
        this.context = context;
    }



    //gọi giao diện
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent,false);

        return new ViewHolder(v);
    }

    //set dữ liệu cho giao diện
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Post post = mPostDetail.get(position);
        /*
        holder.textViewIdPost.setText(post.getIdPost());
        holder.textViewCaption.setText(post.getCaption());
        holder.textViewImgUrl.setText(post.getImage());
        Picasso.get().load(post.getImage()).into(holder.imagePost);*/
        //holder.textViewName.setText(post.getPostBy());
        //holder.textViewCaption.setText(post.getCaption());
        Picasso.get().load(post.getImage()).into(holder.postImage);
    }

    @Override
    public int getItemCount() {
        return mPostDetail.size();
    }

    //Gọi đến giao diện
    public class ViewHolder extends RecyclerView.ViewHolder{

        /*public TextView textViewIdPost,textViewCaption,textViewImgUrl;
        public ImageView imagePost;*/

        public TextView textViewCaption,textViewName;
        public ImageView postImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            /*textViewIdPost = itemView.findViewById(R.id.idPost);
            textViewCaption = itemView.findViewById(R.id.caption);
            textViewImgUrl = itemView.findViewById(R.id.imgUrl);
            imagePost = itemView.findViewById(R.id.imagePost);*/

            textViewName = itemView.findViewById(R.id.username);
            postImage = itemView.findViewById(R.id.post_image);
            textViewCaption = itemView.findViewById(R.id.description);
        }
    }
}