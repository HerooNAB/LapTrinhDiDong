package com.example.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.Models.Post;
import com.example.Models.User;
import com.example.androidui.EditProfile.EditProfile;
import com.example.androidui.Login.Login;
import com.example.androidui.MainActivity.MainActivity;
import com.example.androidui.Profile.Profile;
import com.example.androidui.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    private Button BtnEdit;
    private TextView tvName;
    public User user;
    
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment
        BtnEdit = view.findViewById(R.id.BtnEdit);
        BtnEdit.setOnClickListener(submitEdit);

        loadUser();

        return view;
    }

    private void linkViews() {

    }

    private View.OnClickListener submitEdit = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(), EditProfile.class);
            startActivity(intent);
        }
    };

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
                //Login Successful
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //In ra User được trả về
                        try {

                            JSONObject obj = new JSONObject(response);




                            //Lưu Token vào SharePrefs
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("token", obj.get("token").toString());
                            editor.commit();
                            System.out.println("test prefs-------------------------------------------------------------------------");
                            System.out.println(sharedpreferences.getString("token",""));

                            //Show Toast
                            Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();

                            //Chuyển trang
                            startActivity(new Intent
                                    (getActivity(), MainActivity.class));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },

                //Login Fail
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
}