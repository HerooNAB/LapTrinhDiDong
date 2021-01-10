package com.example.androidui.EditProfile;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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


import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {
    ImageView  imvBackProfile;
    TextView tvtDone, tvtChangePhoto;
    CircleImageView imgProfile;
    EditText etName, etBio, etMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        imvBackProfile = findViewById(R.id.imvBackProfile); // Quay ve Profile
        imvBackProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getFragmentManager().getBackStackEntryCount() > 1)
                {
                    getFragmentManager().popBackStack();
                }
                else {
                    System.out.println("Lỗi");
                }
            }
        });
        tvtDone = findViewById(R.id.tvtDone);               // Submit chinh sua profile
        tvtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Code nut Done here
                String name = etName.getText().toString();
                String bio = etBio.getText().toString();
                String email = etMail.getText().toString();
                editProfile(name, bio, email);
                finish();
            }
        });
        tvtChangePhoto = findViewById(R.id.tvtChangePhoto); // Thay anh profile
        tvtChangePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(EditProfile.this);
            }
        });
        etName = findViewById(R.id.etName);                 // Nhap ten nguoi dung
        etBio = findViewById(R.id.etBio);                   // Nhap Bio
        etMail = findViewById(R.id.etMail);                 // Nhap Email
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


    //Nhan vao anh profile
    public void Zooming(View view) {
        //Code nhan vao anh profile de xem chi tiet anh
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
                        editor.putString("AvatarURL", response);
                        editor.commit();
                        System.out.println(sharedpreferences.getString("AvatarURL", ""));
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

    private void editProfile(String name, String bio, String email) {
            SharedPreferences sharedpreferences;
            sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String token = sharedpreferences.getString("token","");
            String avatarUrl = sharedpreferences.getString("AvatarURL", "");
            System.out.println(avatarUrl);
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
                    params.put("avatar", avatarUrl);

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
                        imgProfile.setImageBitmap(selectedImage);
                        //imvPhoto.setImageBitmap(selectedImage);
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
                                imgProfile.setImageBitmap(BitmapFactory.decodeFile(picturePath));
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


