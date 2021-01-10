package com.example.androidui.CreatePost;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidui.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreatePosts extends AppCompatActivity {

    ImageView imvClosePost, imvPhotos;
    TextView tvPosting, tvUserPost;
    CircleImageView imgProfile;
    EditText txtContentPost;
    ImageButton imgBtnPhotos, imgBtnCamera;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PICK_PHOTO_LIBRARY = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_posts);

        imvClosePost = findViewById(R.id.imvClosePost); // Dong bai post
        imvPhotos = findViewById(R.id.imvPhotos);       // Noi hien hinh anh
        tvPosting = findViewById(R.id.tvPosting);       // Submit post bai
        tvUserPost = findViewById(R.id.tvUserPost);     //
        imgProfile = findViewById(R.id.imgProfile);         //Hinh anh User
        txtContentPost = findViewById(R.id.txtContentPost); // Nhap noi dung bai post
        imgBtnPhotos = findViewById(R.id.imgBtnPhotos);     //Nut mo photo trong lib
        imgBtnCamera = findViewById(R.id.imgBtnCamera);     //Mo camera
    }




    //Nut tao bai post
    public void AddPhotos(View view) {
        //Code them anh dang bai viet
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, PICK_PHOTO_LIBRARY);
        }
    }

    // Mo camera va xu li su kien chup anh
    public void HandleCamera(View view) {
        //Code xu ly mo camera
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    //tra ve ket qua anh
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //mo camera
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null)
        {
            Bitmap photoBitmap = (Bitmap) data.getExtras().get("data");
            imvPhotos.setImageBitmap(photoBitmap);
        }
        // mo thu vien
        if(requestCode == PICK_PHOTO_LIBRARY && resultCode == RESULT_OK && data != null){
            Bitmap pickPhoto = (Bitmap) data.getExtras().get("data");
            imvPhotos.setImageBitmap(pickPhoto);
        }
    }

    //Dong bai post
    public void ClosePost(View view) {
        imvClosePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    public void DisplayPhoto(View view) {
        //Cham vao phong to anh

    }

    //Dang bai viet
    public void AddPost(View view) {
    }
}