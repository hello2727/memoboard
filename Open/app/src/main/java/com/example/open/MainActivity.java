package com.example.open;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private TextView edtHeadline, edtbodyline;
    private ImageView ivHighlight;
    private Realm realm;
    private RecyclerView rcv;
    private RcvAdapter rcvAdapter;
    private Memo memo_Main;
    public List<Memo> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtHeadline = findViewById(R.id.headline);
        edtbodyline = findViewById(R.id.bodyline);
        ivHighlight = findViewById(R.id.ivHighlight);

        rcv = findViewById(R.id.list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv.addItemDecoration(new DividerItemDecoration(this, linearLayoutManager.getOrientation()));
        rcv.setLayoutManager(linearLayoutManager);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        RealmResults<Memo> realmResults = realm.where(Memo.class)
                .findAllAsync();

        for(Memo memo : realmResults) {
            list.add(new Memo(memo.getTitle(),memo.getContent(),memo.getCover()));
            rcvAdapter = new RcvAdapter(MainActivity.this,list);
            rcv.setAdapter(rcvAdapter);
        }

        FloatingActionButton fbtnPlus = findViewById(R.id.fbtnPlus);
        fbtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode == RESULT_OK) {

            String title = data.getStringExtra("title");
            String content = data.getStringExtra("content");
            byte[] cover = data.getByteArrayExtra("cover");
            String time = data.getStringExtra("time");
            Toast.makeText(this,title + "," + time,Toast.LENGTH_SHORT).show();

            realm.beginTransaction();
            memo_Main = realm.createObject(Memo.class);
            memo_Main.setTitle(title);
            memo_Main.setContent(content);
            memo_Main.setCover(cover);

            realm.commitTransaction();

            RealmResults<Memo> realmResults = realm.where(Memo.class)
                    .equalTo("title",title)
                    .findAllAsync();

            list.add(new Memo(title,content,cover));
            rcvAdapter = new RcvAdapter(MainActivity.this,list);
            rcv.setAdapter(rcvAdapter);
        }
    }
}
