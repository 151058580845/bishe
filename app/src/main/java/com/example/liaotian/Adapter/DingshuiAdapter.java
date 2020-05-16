package com.example.liaotian.Adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liaotian.R;
import com.example.liaotian.View.IBaoxiuView;
import com.example.liaotian.View.IDingshuiView;
import com.example.liaotian.entity.Weixiu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DingshuiAdapter extends BaseItemDraggableAdapter<Weixiu, BaseViewHolder> {


    private ArrayList<Weixiu> list = new ArrayList<>();
    private Calendar calendar = Calendar.getInstance(Locale.CHINA);
    private IDingshuiView dingshuiView;
    private Context context;

    public DingshuiAdapter(int layoutResId, ArrayList<Weixiu> data, IDingshuiView dingshuiView, Context context) {
        super(layoutResId, data);
        this.list = data;
        this.dingshuiView = dingshuiView;
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, Weixiu item) {
        helper.setText(R.id.dingshui_list_name, item.getName());
        helper.setText(R.id.dingshui_list_time, item.getTime());
        helper.setText(R.id.dingshui_list_success_time, item.getSuccess_time());
        helper.addOnClickListener(R.id.dingshui_list_all);
    }
}
