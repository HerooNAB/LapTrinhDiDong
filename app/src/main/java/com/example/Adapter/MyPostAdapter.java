package com.example.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Fragments.PostDetailFragment;
import com.example.Models.Post;
import com.example.androidui.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.ViewHolder>{

    private List<Post> mPost;
    private Context context;

    public MyPostAdapter(List<Post> mPost, Context context) {
        this.mPost = mPost;
        this.context = context;
    }



    //gọi giao diện
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_item,parent,false);
        return new ViewHolder(v);
    }

    //set dữ liệu cho giao diện
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Post post = mPost.get(position);
        /*
        holder.textViewIdPost.setText(post.getIdPost());
        holder.textViewCaption.setText(post.getCaption());
        holder.textViewImgUrl.setText(post.getImage());
        Picasso.get().load(post.getImage()).into(holder.imagePost);*/
        //holder.textViewName.setText(post.getPostBy());
        //holder.textViewCaption.setText(post.getCaption());
        Picasso.get().load(post.getImage()).into(holder.postImage);
       holder.postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit().putString("IdPost", post.getIdPost()).apply();

                ((FragmentActivity)context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new PostDetailFragment()).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }

    //Gọi đến giao diện
    public class ViewHolder extends RecyclerView.ViewHolder{
        /*
        public TextView textViewIdPost,textViewCaption,textViewImgUrl;
        public ImageView imagePost;
        */
        //public TextView textViewCaption,textViewName;
        public ImageView postImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        /*
            textViewIdPost = itemView.findViewById(R.id.idPost);
            textViewCaption = itemView.findViewById(R.id.caption);
            textViewImgUrl = itemView.findViewById(R.id.imgUrl);
            imagePost = itemView.findViewById(R.id.imagePost);
        */
            //textViewName = itemView.findViewById(R.id.username);
            postImage = itemView.findViewById(R.id.post_image);
           //textViewCaption = itemView.findViewById(R.id.description);
        }
    }
}
