package com.example.liaotian.Activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSONArray;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.liaotian.Adapter.ZhangdanAdapter;
import com.example.liaotian.Adapter.ZufangAdapter;
import com.example.liaotian.Fragement.ChuzuFragement;
import com.example.liaotian.Fragement.MainFragement;
import com.example.liaotian.Fragement.ZufangFragement;
import com.example.liaotian.R;
import com.example.liaotian.View.IZufanfView;
import com.example.liaotian.entity.Fangwu;
import com.example.liaotian.presenter.WuyezufangPresenter;
import com.example.liaotian.util.MessageEvent;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_chuzu)
public class WuyechuzuActivity extends BaseActivity {

    @ViewInject(R.id.chuzu_toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.chuzu_zufang)
    ImageView chuzu_zufang;
    @ViewInject(R.id.chuzu_chuzu)
    ImageView chuzu_chuzu;
    @ViewInject(R.id.chuzu_l1)
    LinearLayout chuzu_l1;
    @ViewInject(R.id.chuzu_l2)
    LinearLayout chuzu_l2;

    private WuyezufangPresenter wuyezufangPresenter;

    private ChuzuFragement chuzuFragement;
    private ZufangFragement zufangFragement;
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(v -> this.finish());
        wuyezufangPresenter = new WuyezufangPresenter(this);
        zufangFragement = new ZufangFragement(this);
        chuzuFragement = new ChuzuFragement(this);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.fragement_fangwu,zufangFragement).commit();
    }

    @Event(R.id.chuzu_zufang)
    private void zufang(View view){
        chuzu_l2.setBackgroundResource(R.color.white);
        chuzu_l1.setBackgroundResource(R.color.ll_person);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragement_fangwu,zufangFragement)
                .commit();
    }

    @Event(R.id.chuzu_chuzu)
    private void chuzu(View view){
        chuzu_l1.setBackgroundResource(R.color.white);
        chuzu_l2.setBackgroundResource(R.color.ll_person);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragement_fangwu,chuzuFragement)
                .commit();
    }


    @Override
    public void requestData(MessageEvent me) {
        super.requestData(me);
        wuyezufangPresenter.dispose(me);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wuyezufangPresenter.onDestroy();
    }
}
