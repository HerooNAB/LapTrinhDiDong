package com.example.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Models.Post;
import com.example.androidui.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private List<Post>listPost;
    private Context context;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private OnLoadMoreListener onLoadMoreListener;

    public PostAdapter(RecyclerView recyclerView, Context context) {
                        this.context = context;

                        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        if (linearLayoutManager != null) {
                            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    totalItemCount = linearLayoutManager.getItemCount();
                                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }

                        isLoading = true;
                    }
                }
            });
        }
    }

    public void setListPost(List<Post> listPost) {
        this.listPost = listPost;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    //gọi giao diện
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent,false);
//        return new ViewHolder(v);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if (viewType == VIEW_TYPE_ITEM) {
            View view = layoutInflater.inflate(R.layout.post_item, parent, false);
            return new ItemViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = layoutInflater.inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            Post post = listPost.get(position);
            JSONObject postBy = null;
            try {
                postBy = new JSONObject(post.getPostBy());
                System.out.println(postBy.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                ((ItemViewHolder) holder).textViewName.setText(postBy.getString("name"));
                Picasso.get().load(postBy.getString("avatar")).into(((ItemViewHolder) holder).profileImage);
                ((ItemViewHolder) holder).textViewCaption.setText(post.getCaption());
                Picasso.get().load(post.getImage()).into(((ItemViewHolder) holder).postImage);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else if(holder instanceof LoadingViewHolder){
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
        }

    }


    //set dữ liệu cho giao diện


    @Override
    public int getItemCount() {
        return listPost == null ? 0 : listPost.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (listPost.get(position) == null) {
            return VIEW_TYPE_LOADING;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    public void setLoaded() {
        isLoading = false;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    //Gọi đến giao diện
    public class ItemViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewCaption,textViewName;
        public ImageView postImage, profileImage;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.username);
            postImage = itemView.findViewById(R.id.post_image);
            profileImage = itemView.findViewById(R.id.image_profile);
            textViewCaption = itemView.findViewById(R.id.description);
        }
    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

}
