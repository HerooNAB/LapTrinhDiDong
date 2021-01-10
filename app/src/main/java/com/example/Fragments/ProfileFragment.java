package com.example.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Adapter.MyPostAdapter;
import com.example.Models.Post;
import com.example.Models.User;
import com.example.androidui.EditProfile.EditProfile;
import com.example.androidui.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    private Button BtnEdit;
    private TextView tvName, tvBio, tvDisplayEmail, tvDisplayPhoneNumber;
    private CircleImageView imgProfile;

    private RecyclerView rvMyPosts;
    private RecyclerView.Adapter myPostAdapter;
    private List<Post> myPosts;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        // Inflate the layout for this fragment
        int mNoOfColumns = Utility.calculateNoOfColumns(getContext(),180);

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        rvMyPosts  = (RecyclerView) view.findViewById(R.id.rvMyPosts);
        rvMyPosts.setHasFixedSize(true);
        rvMyPosts.setLayoutManager(new GridLayoutManager(getContext(), 3));



        //Nut Edit
        BtnEdit = view.findViewById(R.id.BtnEdit);
        BtnEdit.setOnClickListener(submitEdit);


        //Profile
        tvName = (TextView) view.findViewById(R.id.txtProfileName);
        tvBio = (TextView) view.findViewById(R.id.txtBio);
        imgProfile = (CircleImageView) view.findViewById(R.id.imgProfile);
        tvDisplayEmail = (TextView) view.findViewById(R.id.tvDisplayEmail);

        loadUser();
        loadMyPosts();


        return view;
    }




    private View.OnClickListener submitEdit = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Chuyen du lieu sang trang edit profile
            Intent intent = new Intent(getActivity(), EditProfile.class);
            Bundle bundle = new Bundle();
            if(bundle != null)
            {
                bundle.putString("name", tvName.getText().toString());
                bundle.putString("bio", tvBio.getText().toString());
                bundle.putString("email", tvDisplayEmail.getText().toString());
            }
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    public static class Utility {
        public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
            int noOfColumns = (int) (screenWidthDp / columnWidthDp + 0.5); // +0.5 for correct rounding to int.
            return noOfColumns;
        }
    }


    private void loadUser() {

        //Khai báo SharePrefs
        SharedPreferences sharedpreferences;
        sharedpreferences = this.getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = sharedpreferences.getString("token","");

        //API link
        String ServerName = "https://whatfoods.herokuapp.com/user/me";

        //Call API
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest getRequest = new StringRequest(Request.Method.GET, ServerName,

                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //In ra User được trả về
                        try {

                            //Nhận trả về theo kiểu User
                            JSONObject User = new JSONObject(response);

                            User user = new User(
                                    User.getString("name"),
                                    User.getString("bio"),
                                    User.getString("email"),
                                    User.getString("avatar")
                            );

                            String name = user.getName();
                            String bio = user.getBio();
                            String email = user.getEmail();

                            tvName.setText(name);

                            tvBio.setText(bio);
                            tvDisplayEmail.setText(email);

                            String avatar = user.getAvatar();
                            if(avatar == null)
                            {
                                imgProfile.setImageResource(R.drawable.avatardefault);
                            }
                            else
                            {
                                Picasso.get().load(user.getAvatar()).into(imgProfile);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Thêm các action khác vào đây!
                        //

                        //Show Toast
                        Toast.makeText(getActivity(), "Profile Successful", Toast.LENGTH_SHORT).show();
                    }
                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Show Toast
                        Toast.makeText(getActivity(), "Profile Fail", Toast.LENGTH_SHORT).show();
                    }
                }
        )
                //Truyền dữ liệu theo Headers (gán token vào Authorization để xác thực đăng nhập của User)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);
                return headers;
            }
        };

        //Thực thi Call API
        queue.add(getRequest);
    }

    private void loadMyPosts() {
        SharedPreferences sharedpreferences;
        sharedpreferences = this.getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = sharedpreferences.getString("token","");

        //API link
        String ServerName = "https://whatfoods.herokuapp.com/mypost";
        myPosts = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, ServerName, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("mypost");
                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject Post = jsonArray.getJSONObject(i);
                        Post item = new Post(
                                Post.getString("_id"),
                                Post.getString("photo"),
                                Post.getString("caption"),
                                Post.getString("postedBy")
                        );
                        System.out.println(item.getIdPost());
                        myPosts.add(item);

                    }

                    myPostAdapter = new MyPostAdapter(myPosts, getContext());
                    rvMyPosts.setAdapter(myPostAdapter);

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