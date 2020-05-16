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
import com.example.liaotian.Activity.WuyechuzuXiugaiActivity;
import com.example.liaotian.Adapter.ChuzuAdapter;
import com.example.liaotian.Adapter.MainNewsAdapter;
import com.example.liaotian.Adapter.ZufangAdapter;
import com.example.liaotian.R;
import com.example.liaotian.View.IChuzuView;
import com.example.liaotian.View.IMainView;
import com.example.liaotian.View.IZufanfView;
import com.example.liaotian.entity.Fangwu;
import com.example.liaotian.presenter.ChuzuPresenter;
import com.example.liaotian.presenter.MainFragementPresenter;
import com.example.liaotian.util.MessageEvent;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

@ContentView(R.layout.fragement_chuzu)
public class ChuzuFragement extends BaseFragement implements IChuzuView {

    @ViewInject(R.id.chuzu_list)
    RecyclerView recyclerView;

    ChuzuPresenter chuzuPresenter;
    private ChuzuAdapter chuzuAdapter;
    private View view;
    private Context context;

    public ChuzuFragement(Context context){
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
        chuzuPresenter = new ChuzuPresenter(context,this);
    }

    @Override
    public void requestData(MessageEvent me) {
        super.requestData(me);
        chuzuPresenter.dispose(me);

    }

    @Override
    public void setList(ArrayList<Fangwu> list) {
        chuzuAdapter = new ChuzuAdapter(R.layout.adapter_wuyechuzu,list,this,context);
        recyclerView.setItemViewCacheSize(500);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(chuzuAdapter);
        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (view.getId()){
                            case R.id.adapter_chuzu_l:
                                startActivity(new Intent(context, MyroomActivity.class).putExtra("room_id",list.get(position).getRoom_id()));
                        }
                    }
                });
            }
        });
    }

    private void chuzu_submit(int room_id, String address){
        new MaterialDialog.Builder(context)
                .title("报价")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("请输入价格", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
//                        Toast.makeText(WuyezhangdanActivity.this,input.toString(),Toast.LENGTH_SHORT).show();
                        if ("".equals(input.toString())){
                            Toast.makeText(context,"请输入报价",Toast.LENGTH_SHORT).show();
                        }else {
                            chuzuPresenter.setchuzu(room_id,address,input.toString(),1,0,"");
                        }
                    }
                })
                .positiveText("确定")
                .negativeText("取消")
                .show();
    }
}
