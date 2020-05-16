package com.example.liaotian.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liaotian.R;
import com.example.liaotian.View.IMainView;
import com.example.liaotian.entity.News;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MainNewsAdapter extends BaseItemDraggableAdapter<News, BaseViewHolder> {
    private ArrayList<News> list;
    private JSONArray news = new JSONArray();
    private ImageView imageView;
    private Context context;
    private IMainView iMainView;
    public MainNewsAdapter(int layoutResId, ArrayList<News> data, Context context, IMainView iMainView) {
        super(layoutResId, data);
        this.list = data;
        this.context = context;
        this.iMainView = iMainView;
    }
    BaseViewHolder holder;

    @Override
    protected void convert(BaseViewHolder helper, News item) {
        helper.setText(R.id.main_newstitle,item.getTitle())
                .setText(R.id.main_news,item.getNews())
                .addOnClickListener(R.id.main_news_linear);
        Glide.with(context).load(item.getNews_photo()).into((ImageView) helper.getView(R.id.main_newsimage));
    }
}
