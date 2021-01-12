package com.example.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.Adapter.PostAdapter;
import com.example.Models.Post;
import com.example.androidui.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Post> posts = new ArrayList<>();



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_news);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager;
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        loadListPost();
        return view;
    }






    public void loadListPost() {
        SharedPreferences sharedpreferences;
        sharedpreferences = this.getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = sharedpreferences.getString("token","");

        //API link
        String ServerName = "https://whatfoods.herokuapp.com/allpost";
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, ServerName, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("posts");
                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject Post = jsonArray.getJSONObject(i);

                        Post item = new Post(
                                Post.getString("_id"),
                                Post.getString("photo"),
                                Post.getString("caption"),
                                Post.getString("postedBy")
                        );

                        posts.add(item);
                    }
                    PostAdapter postAdapter = new PostAdapter(recyclerView,getContext());
                    postAdapter.setListPost(posts);
                    postAdapter.setOnLoadMoreListener(new PostAdapter.OnLoadMoreListener() {
                        @Override
                        public void onLoadMore() {
                            if (posts.size() <= 50) { // max giá trị load
                                posts.add(null); // Add 1 cái null , để làm gì ? Quay lại cái Adapter của chúng ta mà thấy , nếu gặp item null thì nó sẽ coi đó là Loading View
                                postAdapter.notifyItemInserted(posts.size() - 1); // Báo với adapter là có sự thay đổi

                                new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        posts.remove(posts.size() - 1); //  // Remove thằng null khi nãy ra
                                        postAdapter.notifyItemRemoved(posts.size()); // // Báo là có sự thay đổi
                                        int index = posts.size();
                                        int end = index + 10;

                                        for (int i = index; i < end; i++) {

                                        }
                                        postAdapter.notifyDataSetChanged();
                                        postAdapter.setLoaded();
                                    }
                                }, 2000); // delay trong 2s sẽ load tiếp dữ liệu
                            }

                        }
                    });
                    recyclerView.setAdapter(postAdapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);
                return headers;
            }
        };
        queue.add(request);
    }

}