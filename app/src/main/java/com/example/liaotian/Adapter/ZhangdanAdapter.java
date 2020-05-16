package com.example.liaotian.Adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liaotian.R;
import com.example.liaotian.View.IZhangdanView;
import com.example.liaotian.entity.Zhangdan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ZhangdanAdapter extends BaseItemDraggableAdapter<Zhangdan, BaseViewHolder> {

    private ArrayList<Zhangdan> list = new ArrayList<>();
    private Calendar calendar = Calendar.getInstance(Locale.CHINA);
    private IZhangdanView iZhangdanView;
    private Context context;

    public ZhangdanAdapter(int layoutResId, ArrayList<Zhangdan> data,IZhangdanView iZhangdanView , Context context) {
        super(layoutResId, data);
        this.list = data;
        this.iZhangdanView = iZhangdanView;
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Zhangdan item) {
        helper.setText(R.id.adapter_zhangdan_name,item.getName());
        helper.setText(R.id.adapter_zhangdan_time,item.getTime());
        helper.setText(R.id.adapter_zhangdan_money,"￥"+item.getMoney());
        switch (item.getType()){
            case 0:
                helper.setImageResource(R.id.adapter_zhangdan_photo,R.drawable.dianfei);
                break;
            case 1:
                helper.setImageResource(R.id.adapter_zhangdan_photo,R.drawable.shuifei);
                break;
            case 2:
                helper.setImageResource(R.id.adapter_zhangdan_photo,R.drawable.wuyefei);
                break;
            case 3:
                helper.setImageResource(R.id.adapter_zhangdan_photo,R.drawable.qitafei);
                break;
        }
    }
}
