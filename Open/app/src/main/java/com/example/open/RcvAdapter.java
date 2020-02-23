package com.example.open;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Parcelable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RcvAdapter extends RecyclerView.Adapter<RcvAdapter.ViewHolder> {

    private Activity activity;
    private List<Memo> dataList;
    private Realm realm;

    private Context mContext;

    public RcvAdapter(Activity activity, List<Memo> dataList) {
        this.activity = activity;
        this.dataList = dataList;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHeadline, tvBodyline;
        ImageView ivHighlight;

        public ViewHolder(View itemView) {
            super(itemView);
            tvHeadline = itemView.findViewById(R.id.headline);
            tvBodyline = itemView.findViewById(R.id.bodyline);
            ivHighlight = itemView.findViewById(R.id.ivHighlight);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    Memo clickedItem = dataList.get(position);

                    Intent i = new Intent(v.getContext(), Details.class);
                    i.putExtra("title", clickedItem.getTitle()); //cardview의 title 보내기
                    i.putExtra("content", clickedItem.getContent());
                    i.putExtra("cover",clickedItem.getCover());
                    v.getContext().startActivity(i);

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    removeMemo(dataList.get(getAdapterPosition()).getTitle());
                    removeItemView(getAdapterPosition());
                    return false;
                }
            });
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Memo data = dataList.get(position);
        holder.tvHeadline.setText(data.getTitle());

        String strAll = data.getContent();
        String strPart = null;
        if(strAll.length()>=10){
            strPart = strAll.substring(0,10);
            holder.tvBodyline.setText(strPart+" ...");
        }else{
            strPart = strAll;
            holder.tvBodyline.setText(strPart);
        }

        byte[] HighlightImage = data.getCover();
        Bitmap bitmap = BitmapFactory.decodeByteArray(HighlightImage, 0, HighlightImage.length);
        holder.ivHighlight.setImageBitmap(bitmap);

    }

    private void removeItemView(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, dataList.size());
    }

    // 데이터 삭제
    private void removeMemo(String text) {
        realm = Realm.getDefaultInstance();
        final RealmResults<Memo> results = realm.where(Memo.class).equalTo("title",text).findAll();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteFromRealm(0);
            }
        });
    }
}
