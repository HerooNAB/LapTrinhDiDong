package com.example.androidui.EditProfile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidui.Profile.Profile;
import com.example.androidui.R;
import com.example.androidui.Register.Register;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {
    ImageView imvCreatePosts, imvBackProfile;
    TextView tvtDone, tvtChangePhoto;
    CircleImageView imgProfile;
    EditText etName, etPhone, etBio, etMail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        imvBackProfile = findViewById(R.id.imvBackProfile); // Quay ve Profile
        tvtDone = findViewById(R.id.tvtDone);               // Submit chinh sua profile
        tvtChangePhoto = findViewById(R.id.tvtChangePhoto); // Thay anh profile
        etName = findViewById(R.id.etName);                 // Nhap ten nguoi dung
        etPhone = findViewById(R.id.etPhone);
        etBio = findViewById(R.id.etBio);
        etMail = findViewById(R.id.etMail);
        imgProfile = findViewById(R.id.imgProfile);         // Photo User


    }



    private void editProfile(String name, String email, String bio) {

        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = sharedpreferences.getString("token","");
        //API
        String ServerName = "https://whatfoods.herokuapp.com/user/me/update";
        //Call API
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.PUT, ServerName,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        //ShowToast
                        Toast.makeText(EditProfile.this, "Edit Successful", Toast.LENGTH_SHORT).show();
                        System.out.println("Thanh cong");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Show Toast
                        Toast.makeText(EditProfile.this, "Edit Fail", Toast.LENGTH_SHORT).show();
                    }
                }
        )
                //Truyền dữ liệu theo params
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("bio", bio);
                return params;
            }
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);
                return headers;
            }
        };

        //Thực thi Call API
        queue.add(postRequest);
    }



    //quay ve profile
    public void GoBackProfile(View view) {
        Intent intent = new Intent(EditProfile.this, Profile.class);
        startActivity(intent);
    }


    //Nut thay doi hinh anh profile
    public void ChangePhoto(View view) {
        //Code change photo da tao su kien onClick
        Intent intent = new Intent(EditProfile.this, Profile.class);
        startActivity(intent);

    }


    //Nut luu ket qua chinh sua profile
    public void Save(View view) {
        //Code nut Done here da tao su kien onClick

    }


    //Nhan vao anh profile
    public void Zooming(View view) {
        //Code nhan vao anh profile de xem chi tiet anh

    }


}