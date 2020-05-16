package com.example.liaotian.Adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liaotian.R;
import com.example.liaotian.View.IChuzuView;
import com.example.liaotian.View.IKuaidiView;
import com.example.liaotian.entity.Fangwu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class KuaidiAdapter extends BaseItemDraggableAdapter<Fangwu, BaseViewHolder> {


    private ArrayList<Fangwu> list = new ArrayList<>();
    private Calendar calendar = Calendar.getInstance(Locale.CHINA);
    private IKuaidiView iKuaidiView;
    private Context context;

    public KuaidiAdapter(int layoutResId, ArrayList<Fangwu> data, IKuaidiView iKuaidiView , Context context) {
        super(layoutResId, data);
        this.list = data;
        this.iKuaidiView = iKuaidiView;
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, Fangwu item) {
        helper.setText(R.id.adapter_kuaidi_name,item.getAddress());
        helper.setText(R.id.adapter_kuaidi_time,item.getTime());
//        helper.addOnClickListener(R.id.adapter_chuzu_l);
    }
}
