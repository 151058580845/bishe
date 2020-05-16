package com.example.liaotian.Adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liaotian.R;
import com.example.liaotian.View.IBaoxiuView;
import com.example.liaotian.View.IZufanfView;
import com.example.liaotian.entity.Fangwu;
import com.example.liaotian.entity.Weixiu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class WeixiuAdapter extends BaseItemDraggableAdapter<Weixiu, BaseViewHolder> {


    private ArrayList<Weixiu> list = new ArrayList<>();
    private Calendar calendar = Calendar.getInstance(Locale.CHINA);
    private IBaoxiuView iBaoxiuView;
    private Context context;

    public WeixiuAdapter(int layoutResId, ArrayList<Weixiu> data, IBaoxiuView iBaoxiuView, Context context) {
        super(layoutResId, data);
        this.list = data;
        this.iBaoxiuView = iBaoxiuView;
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, Weixiu item) {
        helper.setText(R.id.baoxiu_danhao, item.getDanhao());
        helper.setText(R.id.baoxiu_shijian, item.getTime());
        helper.setText(R.id.baoxiu_wujian, item.getName());
        helper.setText(R.id.baoxiu_jieguo,  item.getJieguo());
        if (item.getJieguo().equals("0")){
            helper.setText(R.id.baoxiu_jieguo,  "维修中");
        }
        helper.addOnClickListener(R.id.baoxiu_all);
    }
}
