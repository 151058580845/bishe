package com.example.liaotian.Adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liaotian.R;
import com.example.liaotian.View.IChuzuView;
import com.example.liaotian.View.IZufanfView;
import com.example.liaotian.entity.Fangwu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ChuzuAdapter extends BaseItemDraggableAdapter<Fangwu, BaseViewHolder> {


    private ArrayList<Fangwu> list = new ArrayList<>();
    private Calendar calendar = Calendar.getInstance(Locale.CHINA);
    private IChuzuView iChuzuView;
    private Context context;

    public ChuzuAdapter(int layoutResId, ArrayList<Fangwu> data, IChuzuView iChuzuView , Context context) {
        super(layoutResId, data);
        this.list = data;
        this.iChuzuView = iChuzuView;
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, Fangwu item) {
        helper.setText(R.id.adapter_chuzu_name,item.getAddress());
        helper.addOnClickListener(R.id.adapter_chuzu_l);
    }
}
