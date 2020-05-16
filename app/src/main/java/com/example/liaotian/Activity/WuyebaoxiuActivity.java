package com.example.liaotian.Activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.liaotian.Adapter.WeixiuAdapter;
import com.example.liaotian.Adapter.ZhangdanAdapter;
import com.example.liaotian.R;
import com.example.liaotian.View.IBaoxiuView;
import com.example.liaotian.entity.Weixiu;
import com.example.liaotian.presenter.WuyebaoxiuPresenter;
import com.example.liaotian.util.MessageEvent;
import com.xq.fasterdialog.bean.InputBean;
import com.xq.fasterdialog.dialog.EditDialog;
import com.xq.fasterdialog.dialog.NormalDialog;
import com.xq.fasterdialog.dialog.base.BaseDialog;
import com.xq.fasterdialog.dialog.base.BaseEditDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import static com.xq.fasterdialog.dialog.NormalDialog.LAYOUT_METERAIL;
import static com.xq.fasterdialog.dialog.base.BaseDialog.STYLE_ALERT;
import static com.xq.fasterdialog.dialog.base.BaseDialog.STYLE_BASE;
import static com.xq.fasterdialog.dialog.base.BaseDialog.STYLE_TRANSLUCENT;

@ContentView(R.layout.activity_baoxiu)
public class WuyebaoxiuActivity extends BaseActivity implements IBaoxiuView {

    @ViewInject(R.id.weixiu_toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.baoxiu_recycleview)
    RecyclerView recyclerView;
    TextView dialog_weixiu_danhao;

    private WuyebaoxiuPresenter wuyebaoxiuPresenter;
    private WeixiuAdapter weixiuAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener((v -> this.finish()));
        wuyebaoxiuPresenter = new WuyebaoxiuPresenter(this,this);
        dialog_weixiu_danhao = findViewById(R.id.dialog_weixiu_danhao);
    }

    @Override
    protected void onResume() {
        super.onResume();
        wuyebaoxiuPresenter.onResume();
    }

    @Override
    public void requestData(MessageEvent me) {
        super.requestData(me);
        wuyebaoxiuPresenter.dispose(me);
    }

    @Override
    public void setlist(ArrayList<Weixiu> list) {
        weixiuAdapter = new WeixiuAdapter(R.layout.adapter_baoxiu,list,this,this);
        recyclerView.setItemViewCacheSize(500);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(weixiuAdapter);
        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (view.getId()){
                            case R.id.baoxiu_all:
                                showall(list,position);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(WuyebaoxiuActivity.this,message,Toast.LENGTH_SHORT).show();
    }

    private void showall(ArrayList<Weixiu> list,int position){
        Intent intent = new Intent(this,WeixiuActivity.class);
        intent.putExtra("danhao",list.get(position).getDanhao());
        intent.putExtra("phone",list.get(position).getPhone());
        intent.putExtra("time",list.get(position).getTime());
        intent.putExtra("name",list.get(position).getName());
        intent.putExtra("jieguo",list.get(position).getJieguo());
        intent.putExtra("juti",list.get(position).getJuti());
        intent.putExtra("weixiuren",list.get(position).getWeixiuren());
        intent.putExtra("success_time",list.get(position).getSuccess_time());
        startActivity(intent);
    }

        @Event(R.id.baoxiu_add)
        private void baoxiu_submit(View view){
        new EditDialog(this)
                //设置你自己的布局方案，以下为高定制化布局
                .setCustomView(R.layout.dialog_re_password)
                //Meterail布局样式
                .setMeterailLayoutStyle()
                //标准自定义样式
                .setXQLayoutStyle()
                //EditDialog天生支持任意数量个EditText，因此可以设置多个InputBean，每个InputBean都代表对应EditText的具体内容，切记如果不设置InputBean将会导致对应的EditText无法显示
                .setInputBean0(new InputBean("维修物件"))
                .setInputBean(1,new InputBean("联系号码",null,null,11, InputType.TYPE_CLASS_PHONE))
                //输入完成回调
                .setWidthPercent(0.8f)
                .setPositiveText("上报维修")
                .setPositiveListener(new BaseEditDialog.OnEditCompletedListener() {
                    @Override
                    public void onEditCompleted(BaseEditDialog dialog, SparseArray<CharSequence> array) {
                        String str0 = array.get(0).toString();
                        String str1 = array.get(1).toString();
                        wuyebaoxiuPresenter.submit_weixiu(str0,str1);
                    }
                })
                .setTitle("维修上报")
                .show();
    }
    @Event(R.id.baoxiu_sousuo)
    private void baoxiu_sousuo(View view){
        new EditDialog(this)
                //设置你自己的布局方案，以下为高定制化布局
                .setCustomView(R.layout.dialog_re_password)
                //Meterail布局样式
                .setMeterailLayoutStyle()
                //标准自定义样式
                .setXQLayoutStyle()
                //EditDialog天生支持任意数量个EditText，因此可以设置多个InputBean，每个InputBean都代表对应EditText的具体内容，切记如果不设置InputBean将会导致对应的EditText无法显示
                .setInputBean0(new InputBean("查询条件"))//输入完成回调
                .setWidthPercent(0.8f)
                .setPositiveText("查询")
                .setPositiveListener(new BaseEditDialog.OnEditCompletedListener() {
                    @Override
                    public void onEditCompleted(BaseEditDialog dialog, SparseArray<CharSequence> array) {
                        String str0 = array.get(0).toString();
                        wuyebaoxiuPresenter.submit_chaxun(str0);
                    }
                })
                .setTitle("条件查询")
                .show();
    }
}
