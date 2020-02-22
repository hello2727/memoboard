package com.example.open;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class Details extends AppCompatActivity {
    Button btnModify;
    TextView editTitle2, editContent2;
    ImageView ivSelected2;

    private String title2;
    private String content2;
    private byte[] cover2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        btnModify = findViewById(R.id.btnModify);
        editTitle2 = findViewById(R.id.editTitle2);
        editContent2 = findViewById(R.id.editContent2);
        ivSelected2 = findViewById(R.id.ivSelected2);

        Intent i = getIntent();
        String title = i.getStringExtra("title");
        String content = i.getStringExtra("content");
        byte[] cover = i.getByteArrayExtra("cover");
        Bitmap bitmap = BitmapFactory.decodeByteArray(cover, 0, cover.length);

        editTitle2.setText(title);
        editContent2.setText(content);
        ivSelected2.setImageBitmap(bitmap);

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //DB저장을 위한 byte 변환
    private byte[] imageViewToByte(ImageView ivSelected) {
        Bitmap bitmap = ((BitmapDrawable)ivSelected.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
