package com.example.open;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static android.content.ContentValues.TAG;

public class Details extends AppCompatActivity {
    Button btnModify, btnAdd, btnDelete;
    TextView editTitle2, editContent2;
    ImageView ivSelected2;

    Realm realm;
    Memo memo;

    private String title;
    private String content;
    private byte[] cover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        realm = Realm.getDefaultInstance();
        btnModify = findViewById(R.id.btnModify);
        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
        editTitle2 = findViewById(R.id.editTitle2);
        editContent2 = findViewById(R.id.editContent2);
        ivSelected2 = findViewById(R.id.ivSelected2);

        Intent i = getIntent();
        title = i.getStringExtra("title");
        content = i.getStringExtra("content");
        cover = i.getByteArrayExtra("cover");
        Bitmap bitmap = BitmapFactory.decodeByteArray(cover, 0, cover.length);

        editTitle2.setText(title);
        editContent2.setText(content);
        ivSelected2.setImageBitmap(bitmap);

        Realm.init(this);
        realm = Realm.getDefaultInstance();
        // TODO : default.realm
        // RealmConfiguration config = new RealmConfiguration.Builder().build();
        RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm.realm").build();
        Realm.setDefaultConfiguration(config);

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        // 쿼리를 해서 하나를 가져온다.
                        Memo memo = realm.where(Memo.class).equalTo("title", title).findFirst();

                        memo.setTitle(editTitle2.getText().toString());
                        memo.setContent(editContent2.getText().toString());
                        memo.setCover(cover);

                    }
                });
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup= new PopupMenu(getApplicationContext(), v);//v는 클릭된 뷰를 의미

                getMenuInflater().inflate(R.menu.option_button, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.btnGallery:
                                break;
                            case R.id.btnCamera:
                                break;
                            case R.id.btnUrl:
                                break;
                        }
                        return false;
                    }
                });
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
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
