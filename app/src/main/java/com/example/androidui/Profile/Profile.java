package com.example.androidui.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidui.EditProfile.EditProfile;
import com.example.androidui.R;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    private Button BtnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BtnEdit = findViewById(R.id.BtnEdit);
        BtnEdit.setOnClickListener(submitEdit);


        loadUser();
    }


    //Chuyen den trang Edit Profile
    private View.OnClickListener submitEdit = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Profile.this, EditProfile.class);
            startActivity(intent);
        }
    };


    private void loadUser() {

        //Khai báo SharePrefs
        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = sharedpreferences.getString("token","");

        //API link
        String ServerName = "https://whatfoods.herokuapp.com/user/me";

        //Call API
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest getRequest = new StringRequest(Request.Method.GET, ServerName,
                //Login Successful
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //In ra User được trả về
                        System.out.println(response);

                        //Thêm các action khác vào đây!
                        //

                        //Show Toast
                        Toast.makeText(Profile.this, "Profile Successful", Toast.LENGTH_SHORT).show();


                    }
                },

                //Login Fail
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Show Toast
                        Toast.makeText(Profile.this, "Profile Fail", Toast.LENGTH_SHORT).show();
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