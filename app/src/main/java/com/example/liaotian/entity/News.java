package com.example.liaotian.entity;

import android.widget.ImageView;

public class News {

    private String news_photo;
    private String title;
    private String news;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String  getNews_photo() {
        return news_photo;
    }

    public void setNews_photo(String news_photo) {
        this.news_photo = news_photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }
}
