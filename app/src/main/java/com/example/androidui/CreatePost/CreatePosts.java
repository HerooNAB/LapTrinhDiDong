package com.example.androidui.CreatePost;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;


public class CreatePosts extends AppCompatActivity {

    ImageButton imgBtnChooseImage;
    EditText edtContextPost;
    TextView tvPosting, tvUserPost;
    ImageView imvClosePost, imvPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_posts);

        imgBtnChooseImage = findViewById(R.id.imgBtnChooseImage);
        imgBtnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(CreatePosts.this);
            }
        });

        tvPosting = findViewById(R.id.tvPosting);
        tvPosting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadPost();
            }
        });

        imvClosePost = findViewById(R.id.imvClosePost);
        imvClosePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvUserPost = findViewById(R.id.tvUserPost);


    }




    //Service UploadImage
    private void UploadImage(){
        //Khai báo SharePrefs
        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = sharedpreferences.getString("token","");
        String dataURI = sharedpreferences.getString("dataURI","");

        //API link
        String ServerName = "https://whatwhatfood.herokuapp.com/uploadimagebase64";

        //Setting Post Method
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerName,

                //Login Successful
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Lưu Token vào SharePrefs
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("ImageURL", response);
                        editor.commit();
                        System.out.println(response);
                        System.out.println("UpLoadImage Successful");
                    }
                },

                //Login Fail
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("UpLoadImage Fail");
                    }
                })
        {
            @Override
            //Truyền dữ liệu theo params
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("dataURI", dataURI);
                return params;
            }

            //Truyền dữ liệu theo Headers
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);
                return headers;
            }
        };

        //Tạo request
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Thêm request vào Call API
        requestQueue.add(stringRequest);
    }

    private void UploadPost(){
        edtContextPost = findViewById(R.id.edtContextPost);
        String contextPost = edtContextPost.getText().toString();
        System.out.println(contextPost);
        //Khai báo SharePrefs
        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = sharedpreferences.getString("token","");
        String ImageURL = sharedpreferences.getString("ImageURL", "");

        //API link
        String ServerName = "https://whatwhatfood.herokuapp.com/createpost";

        //Setting Post Method
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerName,

                //Login Successful
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        //Show Toast
                        Toast.makeText(CreatePosts.this, "CreatePost Successful", Toast.LENGTH_SHORT).show();
                    }
                },

                //Create post Fail
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Show Toast
                        Toast.makeText(CreatePosts.this, "CreatePost Fail", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            //Truyền dữ liệu theo params
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("caption", contextPost);
                params.put("photo", ImageURL);
                return params;
            }

            //Truyền dữ liệu theo Headers
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);
                return headers;
            }
        };

        //Tạo request
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Thêm request vào Call API
        requestQueue.add(stringRequest);
    }

    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);


                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //Khai báo SharePrefs
        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        imvPhotos = findViewById(R.id.imvPhotos);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        if (selectedImage != null) {
                            byte[] imageBytes = imageToByteArray(selectedImage);
                            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT); // actual conversion to Base64 String Image
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("dataURI", encodedImage);
                            editor.commit();
                            System.out.println(encodedImage);
                            System.out.println("alo alo");
                            UploadImage();
                        }
                        imvPhotos.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);

                                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                                if (bitmap != null) {
                                    byte[] imageBytes = imageToByteArray(bitmap);
                                    String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT); // actual conversion to Base64 String Image
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString("dataURI", encodedImage);
                                    editor.commit();
                                    System.out.println(encodedImage);
                                    System.out.println("alo alo gallery");
                                    UploadImage();
                                }
                                imvPhotos.setImageURI(selectedImage);
                                //imvPhoto.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }
    private byte[] imageToByteArray(Bitmap bitmapImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        return baos.toByteArray();
    }

}
