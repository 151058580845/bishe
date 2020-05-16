package com.example.liaotian.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.example.liaotian.R;
import com.example.liaotian.entity.Weixiu;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

@ContentView(R.layout.dialog_weixiu)
public class WeixiuActivity extends BaseActivity {

    @ViewInject(R.id.weixiu_toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.dialog_weixiu_danhao)
    TextView dialog_weixiu_danhao;
    @ViewInject(R.id.dialog_weixiu_phone)
    TextView dialog_weixiu_phone;
    @ViewInject(R.id.dialog_weixiu_shijian)
    TextView dialog_weixiu_shijian;
    @ViewInject(R.id.dialog_weixiu_wujian)
    TextView dialog_weixiu_wujian;
    @ViewInject(R.id.dialog_weixiu_jieguo)
    TextView dialog_weixiu_jieguo;
    @ViewInject(R.id.dialog_weixiu_weixiuren)
    TextView dialog_weixiu_weixiuren;
    @ViewInject(R.id.dialog_weixiu_success_time)
    TextView dialog_weixiu_success_time;
    @ViewInject(R.id.dialog_weixiu_juti)
    TextView dialog_weixiu_juti;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener((v -> this.finish()));
        dialog_weixiu_danhao.setText(getIntent().getStringExtra("danhao"));
        dialog_weixiu_phone.setText(getIntent().getStringExtra("phone"));
        dialog_weixiu_shijian.setText(getIntent().getStringExtra("time"));
        dialog_weixiu_wujian.setText(getIntent().getStringExtra("name"));
        if (getIntent().getStringExtra("jieguo").equals("0"))
             dialog_weixiu_jieguo.setText("维修中");
        else dialog_weixiu_jieguo.setText(getIntent().getStringExtra("jieguo"));

        if (getIntent().getStringExtra("weixiuren").equals("0"))
             dialog_weixiu_weixiuren.setText("暂无人接单");
        else dialog_weixiu_weixiuren.setText(getIntent().getStringExtra("jieguo"));
//        dialog_weixiu_weixiuren.setText(getIntent().getStringExtra("weixiuren"));
        if (getIntent().getStringExtra("success_time").equals("0"))
            dialog_weixiu_success_time.setText("--");
        else dialog_weixiu_success_time.setText(getIntent().getStringExtra("success_time"));
        if (getIntent().getStringExtra("juti").equals("0"))
            dialog_weixiu_juti.setText("--");
        else dialog_weixiu_juti.setText(getIntent().getStringExtra("juti"));
        /*dialog_weixiu_success_time.setText(getIntent().getStringExtra("success_time"));
        dialog_weixiu_juti.setText(getIntent().getStringExtra("juti"));*/
    }

    @Event(R.id.weixiu_submit)
    private void weixiu_submit(View view){
        this.finish();
    }
}
