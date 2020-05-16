package com.example.liaotian.Activity;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.liaotian.Adapter.DingshuiAdapter;
import com.example.liaotian.Adapter.FuwuAdapter;
import com.example.liaotian.R;
import com.example.liaotian.View.IFuwulist;
import com.example.liaotian.entity.Fuwu;
import com.example.liaotian.presenter.FuwulistPresenter;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.http.Function;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

@ContentView(R.layout.activity_fuwulist)
public class FuwudinggouActivity extends BaseActivity implements IFuwulist {

    @ViewInject(R.id.fuwu_toolbar)
    Toolbar fuwu_toolbar;
    @ViewInject(R.id.fuwu_recycle)
    RecyclerView recyclerView;

    private FuwulistPresenter fuwulistPresenter;
    private FuwuAdapter fuwuAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fuwu_toolbar.setTitle("");
        fuwu_toolbar.setNavigationIcon(R.drawable.back);
        fuwu_toolbar.setNavigationOnClickListener((v -> this.finish()));
        fuwulistPresenter = new FuwulistPresenter(this,this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fuwulistPresenter.onResume();
    }

    @Override
    public void requestData(MessageEvent me) {
        super.requestData(me);
        fuwulistPresenter.dispose(me);
    }

    @Override
    public void setmessage(ArrayList<Fuwu> list) {
        fuwuAdapter = new FuwuAdapter(R.layout.adapter_fuwu,list,this,this);
        recyclerView.setItemViewCacheSize(500);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(fuwuAdapter);
        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (view.getId()){
                            case R.id.adapter_fuwu_caozuo:
                                caozuo(list,position);
                        }
                    }
                });
            }
        });
    }

    @Override
    public void back() {
        this.finish();
    }

    public void caozuo(ArrayList<Fuwu> list, int position){
        fuwulistPresenter.setfuwu(list.get(position).getName());
    }
}
