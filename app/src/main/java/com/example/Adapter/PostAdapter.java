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

import org.json.JSONException;
import org.json.JSONObject;

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent,false);
        return new ViewHolder(v);
    }

    //set dữ liệu cho giao diện
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = listPost.get(position);
        JSONObject postBy = null;
        try {
            postBy = new JSONObject(post.getPostBy());
            System.out.println(postBy.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            holder.textViewName.setText(postBy.getString("name"));
            Picasso.get().load(postBy.getString("avatar")).into(holder.profileImage);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        /*
        holder.textViewIdPost.setText(post.getIdPost());
        holder.textViewCaption.setText(post.getCaption());
        holder.textViewImgUrl.setText(post.getImage());
        Picasso.get().load(post.getImage()).into(holder.imagePost);*/
        holder.textViewName.setText(post.getPostBy());
        holder.textViewCaption.setText(post.getCaption());
        Picasso.get().load(post.getImage()).into(holder.postImage);
    }

    @Override
    public int getItemCount() {
        return listPost.size();
    }

    //Gọi đến giao diện
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewCaption,textViewName;
        public ImageView postImage, profileImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);



            textViewName = itemView.findViewById(R.id.username);
            postImage = itemView.findViewById(R.id.post_image);
            profileImage = itemView.findViewById(R.id.image_profile);
            textViewCaption = itemView.findViewById(R.id.description);
        }
    }
}
