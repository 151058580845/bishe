package com.example.liaotian.Adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liaotian.R;
import com.example.liaotian.View.IZhangdanView;
import com.example.liaotian.View.IZufanfView;
import com.example.liaotian.entity.Fangwu;
import com.example.liaotian.entity.Zhangdan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ZufangAdapter extends BaseItemDraggableAdapter<Fangwu, BaseViewHolder> {


    private ArrayList<Fangwu> list = new ArrayList<>();
    private Calendar calendar = Calendar.getInstance(Locale.CHINA);
    private IZufanfView iZufanfView;
    private Context context;

    public ZufangAdapter(int layoutResId, ArrayList<Fangwu> data, IZufanfView iZufanfView , Context context) {
        super(layoutResId, data);
        this.list = data;
        this.iZufanfView = iZufanfView;
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, Fangwu item) {
        helper.setText(R.id.adapter_zufang_address,item.getAddress()+" "+item.getChaoxiang());
        helper.setText(R.id.adapter_zufang_money,"￥"+item.getZujin());
        helper.addOnClickListener(R.id.zufang_c);
    }
}
