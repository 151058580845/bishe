package com.example.liaotian.Adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liaotian.R;
import com.example.liaotian.View.IBaoxiuView;
import com.example.liaotian.View.ITousuView;
import com.example.liaotian.entity.Tousu;
import com.example.liaotian.entity.Weixiu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class TousuAdapter extends BaseItemDraggableAdapter<Tousu, BaseViewHolder> {


    private ArrayList<Tousu> list = new ArrayList<>();
    private Calendar calendar = Calendar.getInstance(Locale.CHINA);
    private ITousuView iTousuView;
    private Context context;

    public TousuAdapter(int layoutResId, ArrayList<Tousu> data, ITousuView iTousuView, Context context) {
        super(layoutResId, data);
        this.list = data;
        this.iTousuView = iTousuView;
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, Tousu item) {
        helper.setText(R.id.adapter_message, item.getMessage());
        helper.setText(R.id.adapter_date, item.getDate());
        helper.addOnClickListener(R.id.adapter_seek_message);
    }
}
