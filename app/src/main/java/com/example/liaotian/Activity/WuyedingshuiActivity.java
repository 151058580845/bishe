package com.example.liaotian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.liaotian.Adapter.DingshuiAdapter;
import com.example.liaotian.Adapter.WeixiuAdapter;
import com.example.liaotian.R;
import com.example.liaotian.View.IDingshuiView;
import com.example.liaotian.entity.Weixiu;
import com.example.liaotian.presenter.WuyedingshuiPresenter;
import com.example.liaotian.util.MessageEvent;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

@ContentView(R.layout.activity_dingshui)
public class WuyedingshuiActivity extends BaseActivity implements IDingshuiView {

    @ViewInject(R.id.dingshui_toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.dingshui_recycleview)
    RecyclerView recyclerView;
    TextView dialog_weixiu_danhao;

    private WuyedingshuiPresenter wuyedingshuiPresenter;
    private DingshuiAdapter dingshuiAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener((v -> this.finish()));
        wuyedingshuiPresenter = new WuyedingshuiPresenter(this,this);
    }

    @Override
    public void requestData(MessageEvent me) {
        wuyedingshuiPresenter.dispose(me);
    }

    @Override
    protected void onResume() {
        super.onResume();
        wuyedingshuiPresenter.onResume();
    }

    @Override
    public void setlist(ArrayList<Weixiu> list) {
        dingshuiAdapter = new DingshuiAdapter(R.layout.adapter_dingshui,list,this,this);
        recyclerView.setItemViewCacheSize(500);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(dingshuiAdapter);
        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (view.getId()){
                            case R.id.baoxiu_all:
//                                showall(list,position);
                        }
                    }
                });
            }
        });
    }

    @Event(R.id.dingshui_add)
    private void dingshui_add(View view){
        startActivity(new Intent(this,FuwudinggouActivity.class));
    }

    @Event(R.id.dingshui_sousuo)
    private void dingshui_sousuo(View view){
        showdialog();
    }
    public void showdialog(){
        new MaterialDialog.Builder(this)
                .title("搜索")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("请输入关键字", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
//                        Toast.makeText(WuyezhangdanActivity.this,input.toString(),Toast.LENGTH_SHORT).show();
//                        wuyezhangdanPresenter.getZhangdan(3,input.toString());
                        wuyedingshuiPresenter.getlist(input.toString());
                    }
                })
                .positiveText("确定")
                .show();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(WuyedingshuiActivity.this,message,Toast.LENGTH_SHORT).show();
    }
}
