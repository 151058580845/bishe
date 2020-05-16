package com.example.liaotian.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.liaotian.Adapter.TousuAdapter;
import com.example.liaotian.R;
import com.example.liaotian.View.ITousuView;
import com.example.liaotian.entity.Tousu;
import com.example.liaotian.presenter.TousuPresenter;
import com.example.liaotian.util.MessageEvent;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_tousulist)
public class WuyetousuActivity extends BaseActivity implements ITousuView {

    @ViewInject(R.id.tousu_toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.tousu_recycleview)
    RecyclerView recyclerView;
    @ViewInject(R.id.tousu_ycl)
    TextView tousu_ycl;
    @ViewInject(R.id.tousu_wcl)
    TextView tousu_wcl;



    private TousuAdapter tousuAdapter;
    private TousuPresenter tousuPresenter;
    private List<Tousu> list = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener((v -> this.finish()));
        tousuPresenter = new TousuPresenter(this,this);
    }

    @Event(value = R.id.tousu_ycl)
    private void tousu_ycl(View view){
        ArrayList list1 = new ArrayList();
        for (int i=0;i<list.size();i++){
            if (list.get(i).getIs_chuli()==1){
                list1.add(list.get(i));
            }
        }
        setlist(list1);
    }

    @Event(value = R.id.tousu_wcl)
    private void tousu_wcl(View view){
        ArrayList list1 = new ArrayList();
        for (int i=0;i<list.size();i++){
            if (list.get(i).getIs_chuli()==0){
                list1.add(list.get(i));
            }
        }
        setlist(list1);
    }

    @Event(value = R.id.tousu_sousuo)
    private void tousu_sousuo(View view){
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
                        tousuPresenter.getTousu(input.toString());
                    }
                })
                .positiveText("确定")
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tousuPresenter.getTousu();
    }


    @Override
    public void requestData(MessageEvent me) {
        super.requestData(me);
        tousuPresenter.dispose(me);
    }

    @Override
    public void setl(ArrayList<Tousu> list) {
        this.list = list;
    }

    @Override
    public void setlist(ArrayList<Tousu> list) {
        tousuAdapter = new TousuAdapter(R.layout.adapter_tousu,list,this,this);
        recyclerView.setItemViewCacheSize(500);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(tousuAdapter);
        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (view.getId()){
                            case R.id.adapter_seek_message:
//                                showall(list,position);
                                startActivity(new Intent(WuyetousuActivity.this,WuyetousumessageActivity.class).putExtra("id",list.get(position).getId()));
                        }
                    }
                });
            }
        });
    }

    @Event(value = R.id.tousu_add)
    private void tousu_add(View view){
        startActivity(new Intent(this,WuyetousuaddActivity.class));
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addphoto() {

    }

    @Override
    public void setmessage(String date) {

    }

    @Override
    public void back() {

    }
}
