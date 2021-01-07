package com.example.androidui.EditProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidui.Profile.Profile;
import com.example.androidui.R;

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