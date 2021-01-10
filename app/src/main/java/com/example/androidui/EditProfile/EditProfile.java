package com.example.androidui.EditProfile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidui.R;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {
    ImageView  imvBackProfile;
    TextView tvtDone, tvtChangePhoto;
    CircleImageView imgProfile;
    EditText etName, etBio, etMail;



    static final int PICK_PHOTO_LIBRARY = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        imvBackProfile = findViewById(R.id.imvBackProfile); // Quay ve Profile
        tvtDone = findViewById(R.id.tvtDone);               // Submit chinh sua profile
        tvtChangePhoto = findViewById(R.id.tvtChangePhoto); // Thay anh profile
        etName = findViewById(R.id.etName);                 // Nhap ten nguoi dung
        etBio = findViewById(R.id.etBio);
        etMail = findViewById(R.id.etMail);
        imgProfile = findViewById(R.id.imgProfile);         // Photo User


        receiveData();

    }



    //Du lieu User chuyen tu Profile sang EditProfile
    private void receiveData() {
    Bundle bundle = getIntent().getExtras();
    bundle.getString("name");
    bundle.getString("bio");
    bundle.getString("email");

    etName.setText(getIntent().getStringExtra("name"));
    etBio.setText(getIntent().getStringExtra("bio"));
    etMail.setText(getIntent().getStringExtra("email"));

    }


    //quay ve profile fragment
    public void onBackPressed(View view) {
        if(getFragmentManager().getBackStackEntryCount() > 1)
        {
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }

    //Nut thay doi hinh anh profile
    public void ChangePhoto(View view) {
        //Code change photo da tao su kien onClick
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // mo thu vien
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, PICK_PHOTO_LIBRARY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_PHOTO_LIBRARY && resultCode == RESULT_OK && data != null){
            Bitmap pickPhoto = (Bitmap) data.getExtras().get("data");
            imgProfile.setImageBitmap(pickPhoto);
        }
    }

    //Nut luu ket qua chinh sua profile
    public void Save(View view) {
        //Code nut Done here
        String name = etName.getText().toString();
        String bio = etBio.getText().toString();
        String email = etMail.getText().toString();

        editProfile(name, bio, email);
        finish();

    }


    //Nhan vao anh profile
    public void Zooming(View view) {
        //Code nhan vao anh profile de xem chi tiet anh

    }


        private void editProfile(String name, String bio, String email) {

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


                            //ShowToast
                            Toast.makeText(EditProfile.this, "Edit Successful", Toast.LENGTH_SHORT).show();

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
                    params.put("bio", bio);
                    params.put("email", email);

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
}


