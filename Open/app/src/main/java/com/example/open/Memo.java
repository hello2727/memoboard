package com.example.open;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Memo extends RealmObject {

    @Override
    public String toString() {
        return title;
    }

    @Required
    private String title;
    private String content;
    private byte[] cover;

    public Memo() {
        this.title = "아무값도 없습니다.";
    }
    public Memo(String title, String content, byte[] cover) {
        this.title = title;
        this.content = content;
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }
}
