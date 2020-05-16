package com.example.liaotian.Fragement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.liaotian.Activity.MyroomActivity;
import com.example.liaotian.Activity.Myroom_chuzuActivity;
import com.example.liaotian.Activity.WuyechuzuXiugaiActivity;
import com.example.liaotian.Adapter.ChuzuAdapter;
import com.example.liaotian.Adapter.ZufangAdapter;
import com.example.liaotian.R;
import com.example.liaotian.View.IChuzuView;
import com.example.liaotian.View.IZufanfView;
import com.example.liaotian.entity.Fangwu;
import com.example.liaotian.presenter.ChuzuPresenter;
import com.example.liaotian.presenter.ZufangPresenter;
import com.example.liaotian.util.MessageEvent;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@ContentView(R.layout.fragement_zufang)
public class ZufangFragement extends BaseFragement implements IZufanfView {

    @ViewInject(R.id.chuzu_list)
    RecyclerView recyclerView;
    @ViewInject(R.id.zhangdan_fangshi)
    NiceSpinner zhangdan_fangshi;
    @ViewInject(R.id.zhangdan_huxing)
    NiceSpinner zhangdan_huxing;

    ZufangPresenter zufangPresenter;
    private ZufangAdapter zufangAdapter;
    private Context context;

    public ZufangFragement(Context context){
        this.context = context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this,inflater,container);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        zufangPresenter = new ZufangPresenter(context,this);
        List<String> list = new LinkedList<>(Arrays.asList("全部", "整租", "合租"));
        zhangdan_fangshi.attachDataSource(list);
        List<String> list1 = new LinkedList<>(Arrays.asList("全部房型", "一室一厅", "二室一厅", "三室一厅", "二室二厅", "三室二厅"));
        zhangdan_huxing.attachDataSource(list1);
        zhangdan_fangshi.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                zufangPresenter.setList(1,list.get(position));
            }
        });
        zhangdan_huxing.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                zufangPresenter.setList(2,list1.get(position));
            }
        });
    }

    @Override
    public void requestData(MessageEvent me) {
        super.requestData(me);
        zufangPresenter.dispose(me);

    }

    @Event(value = R.id.zufang_dong)
    private void zufang_dong(View view){
        zufangPresenter.setList(3,"东");
    }
    @Event(value = R.id.zufang_nan)
    private void zufang_nan(View view){
        zufangPresenter.setList(3,"南");
    }
    @Event(value = R.id.zufang_xi)
    private void zufang_xi(View view){
        zufangPresenter.setList(3,"西");
    }
    @Event(value = R.id.zufang_bei)
    private void zufang_bei(View view){
        zufangPresenter.setList(3,"北");
    }

    @Override
    public void setList(ArrayList<Fangwu> list) {

        zufangAdapter = new ZufangAdapter(R.layout.adapter_wuyezufang,list,this,context);
        recyclerView.setItemViewCacheSize(500);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(zufangAdapter);
        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (view.getId()){
                            case R.id.zufang_c:
                                startActivity(new Intent(context, Myroom_chuzuActivity.class).putExtra("room_id",list.get(position).getRoom_id()));
                        }
                    }
                });
            }
        });
    }

    @Override
    public void show(String message) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
