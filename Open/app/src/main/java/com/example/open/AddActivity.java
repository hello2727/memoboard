package com.example.open;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class AddActivity extends AppCompatActivity {

    public static Context mContext;

    private Button btnGallery, btnCamera, btnUrl;
    private EditText editTitle, editContent, edtUrl;
    private ImageView ivSelected;
    private String title;
    private String content;
    private byte[] cover;
    private String formattedDate;

    private static final int REQUEST_GALLERY = 0;
    private static final int REQUEST_CAMERA = 1;
    String urlAddress = "http://developer.android.com/assets/images/android_logo.png";
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);
        edtUrl = findViewById(R.id.edtUrl);
        Button btnSave = findViewById(R.id.btnSave);
        btnGallery = (Button)findViewById(R.id.gallery_photo);
        btnCamera = (Button)findViewById(R.id.camera_photo);
        btnUrl = (Button)findViewById(R.id.url_photo);
        ivSelected = (ImageView)findViewById(R.id.ivSelected);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                formattedDate = df.format(c);
                title = editTitle.getText().toString();
                content = editContent.getText().toString();
                cover = imageViewToByte(ivSelected);
                Intent add = new Intent();
                add.putExtra("title",title);
                add.putExtra("content",content);
                add.putExtra("cover",cover);
                add.putExtra("time",formattedDate);
                setResult(RESULT_OK,add);
                finish();
            }
        });

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.gallery_photo:
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, REQUEST_GALLERY);
                        break;
                    case R.id.camera_photo:
                        //마쉬멜로우 이상일 경우에는 권한 체크 후 권한 요청
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                Log.d(TAG, "권한 설정 완료");
                            } else {
                                Log.d(TAG, "권한 설정 요청");
                                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            }
                        }

                        // 카메라 앱을 여는 소스
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, REQUEST_CAMERA);
                        break;
                    case R.id.url_photo:
                        String urlLink = edtUrl.getText().toString();
                        if (urlLink.isEmpty()){
                            //입력값이 없습니다.
                            Toast.makeText(AddActivity.this, "이미지 주소를 입력하세요.", Toast.LENGTH_SHORT).show();
                        } else {
                            LoadImage loadImage = new LoadImage(ivSelected);
                            loadImage.execute(urlLink);
                        }
                        break;
                }
            }
        };

        btnGallery.setOnClickListener(onClickListener);
        btnCamera.setOnClickListener(onClickListener);
        btnUrl.setOnClickListener(onClickListener);
    }

    //다양한 경로에서 사진 가져오기
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_GALLERY)
        {
            if(resultCode == RESULT_OK)
            {
                try{
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    bitmap = BitmapFactory.decodeStream(in);
                    in.close();

                    ivSelected.setImageBitmap(bitmap);
                }catch(Exception e)
                {

                }
            }else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }else if(requestCode == REQUEST_CAMERA){
            if (resultCode == RESULT_OK && data.hasExtra("data")) {
                bitmap = (Bitmap) data.getExtras().get("data");
                if(bitmap != null){
                    ivSelected.setImageBitmap(bitmap);
                }
            }
        }
    }

    //DB저장을 위한 byte 변환
    private byte[] imageViewToByte(ImageView ivSelected) {
        Bitmap bitmap = ((BitmapDrawable)ivSelected.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    //외부 url 이미지 가져오기
    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public LoadImage(ImageView ivSelected){
            this.imageView = ivSelected;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlLink = strings[0];
            try {
                InputStream inputStream = new URL(urlLink).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            ivSelected.setImageBitmap(bitmap);
        }
    }
}
