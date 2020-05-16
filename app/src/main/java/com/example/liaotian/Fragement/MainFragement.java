package com.example.liaotian.Fragement;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.liaotian.Activity.KuaidiActivity;
import com.example.liaotian.Activity.NetworkActivity;
import com.example.liaotian.Activity.WuyebaoxiuActivity;
import com.example.liaotian.Activity.WuyechuzuActivity;
import com.example.liaotian.Activity.WuyedingshuiActivity;
import com.example.liaotian.Activity.WuyejiaofeiActivity;
import com.example.liaotian.Activity.WuyetousuActivity;
import com.example.liaotian.Activity.WuyetousuaddActivity;
import com.example.liaotian.Activity.WuyezhangdanActivity;
import com.example.liaotian.Adapter.MainNewsAdapter;
import com.example.liaotian.R;
import com.example.liaotian.View.IMainFragementView;
import com.example.liaotian.View.IMainView;
import com.example.liaotian.entity.News;
import com.example.liaotian.presenter.MainFragementPresenter;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.http.Client;
import com.sunfusheng.marqueeview.MarqueeView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import yalantis.com.sidemenu.interfaces.ScreenShotable;

@ContentView(R.layout.fragement_main)
public class MainFragement extends BaseFragement implements ScreenShotable, IMainFragementView{

    @ViewInject(R.id.main_bga)
    private BGABanner main_bga;
    @ViewInject(R.id.main_gonggao)
    private MarqueeView marqueeView;
    @ViewInject(R.id.main_reclyclerview)
    private RecyclerView recyclerView;

    private IMainView iMainView;
    private MainNewsAdapter mainNewsAdapter;
    private View view;
    private Context context;
    private MainFragementPresenter mainFragementPresenter;

    public MainFragement(){}
    public MainFragement(Context context,IMainView iMainView){
        this.context = context;
        this.iMainView = iMainView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this,inflater,container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainFragementPresenter = new MainFragementPresenter(this);
        initbga();
        initmadeng();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initbga() {
        List<View> views = new ArrayList<>();
//        views.add(getPageView(R.drawable.main_bag1));
//        views.add(getPageView(R.drawable.main_bag2));
        views.add(getPageView(R.drawable.wuye_photo1));
        views.add(getPageView(R.drawable.wuye_photo2));
        views.add(getPageView(R.drawable.wuye_photo3));
        main_bga.setData(views);
    }
    private View getPageView(@DrawableRes int resid) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(resid);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    private void initmadeng() {
        List<String> msg = new ArrayList<>();
        msg.add("1.大家出门请带好口罩");
        msg.add("2.小区开门为东门");
        marqueeView.startWithList(msg);
        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                Toast.makeText(context, position+textView.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        marqueeView.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        marqueeView.stopFlipping();
    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

    @Override
    public void requestData(MessageEvent me) {
        super.requestData(me);
        mainFragementPresenter.dispose(me);
    }

    @Override
    public void setNews(ArrayList<News> data) {
        mainNewsAdapter = new MainNewsAdapter(R.layout.main_newsadpater,data,context,iMainView);
        recyclerView.setItemViewCacheSize(500);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mainNewsAdapter);
        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (view.getId()){
                            case R.id.main_news_linear:
                                Intent intent = new Intent(context, NetworkActivity.class);
                                intent.putExtra("url",data.get(position).getUrl());
                                context.startActivity(intent);
                        }
                    }
                });
            }
        });
    }
    @Event(R.id.main_wuyezhangdan)
    private void zhangdan(View view){
        startActivity(new Intent(context,WuyezhangdanActivity.class));
    }
    @Event(R.id.main_wuyejiaofei)
    private void jiaofei(View view){
        startActivity(new Intent(context,WuyejiaofeiActivity.class));
    }
    @Event(R.id.main_wuyezufang)
    private void zufang(View view){
        startActivity(new Intent(context, WuyechuzuActivity.class));
    }

    @Event(R.id.main_wuyebaoxiu)
    private void baoxiu(View view){
        startActivity(new Intent(context, WuyebaoxiuActivity.class));
    }
    @Event(R.id.main_wuyedingshui)
    private void fuwu(View view){
        startActivity(new Intent(context, WuyedingshuiActivity.class));
    }
    @Event(R.id.main_wuyelianxi)
    private void main_lianxi(View view){
        /*Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"15105850845"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + "15105850845");
        intent.setData(data);
        startActivity(intent);
    }
    @Event(R.id.main_wuyetousu)
    private void tousu(View view){
        startActivity(new Intent(context, WuyetousuActivity.class));
    }

    @Event(R.id.main_wuyekuaidi)
    private void main_wuyekuaidi(View view){
        startActivity(new Intent(context, KuaidiActivity.class));
    }
}

