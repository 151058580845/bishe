package com.example.liaotian.Adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liaotian.R;
import com.example.liaotian.View.IDingshuiView;
import com.example.liaotian.View.IFuwulist;
import com.example.liaotian.entity.Fuwu;
import com.example.liaotian.entity.Weixiu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class FuwuAdapter extends BaseItemDraggableAdapter<Fuwu, BaseViewHolder> {


    private ArrayList<Fuwu> list = new ArrayList<>();
    private Calendar calendar = Calendar.getInstance(Locale.CHINA);
    private IFuwulist iFuwulist;
    private Context context;

    public FuwuAdapter(int layoutResId, ArrayList<Fuwu> data, IFuwulist iFuwulist, Context context) {
        super(layoutResId, data);
        this.list = data;
        this.iFuwulist = iFuwulist;
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, Fuwu item) {
        helper.setText(R.id.adapter_fuwu_name, item.getName());
        helper.setText(R.id.adapter_fuwu_money, item.getMoney());
        helper.addOnClickListener(R.id.adapter_fuwu_caozuo);
    }
}
