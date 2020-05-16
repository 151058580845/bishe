package com.example.liaotian.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.liaotian.R;
import com.example.liaotian.View.IChuzuView;
import com.example.liaotian.View.ITousuView;
import com.example.liaotian.entity.Fangwu;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PhotoAdapter extends BaseItemDraggableAdapter<String, BaseViewHolder> {


    private ArrayList<String> list = new ArrayList<>();
    private Calendar calendar = Calendar.getInstance(Locale.CHINA);
    private ITousuView iTousuView;
    private Context context;
    private int num = 0;

    public PhotoAdapter(int layoutResId, ArrayList<String> data, ITousuView iTousuView , Context context) {
        super(layoutResId, data);
        this.list = data;
        this.iTousuView = iTousuView;
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        Bitmap bm = BitmapFactory.decodeFile(item);
        if (num>0){
            Glide.with(context).load(item).into((ImageView) helper.getView(R.id.adapter_phone));
            num--;
        }else {
            helper.setImageBitmap(R.id.adapter_phone,bm);
        }


    }

    public void setnum(int num){
        this.num = num;
    }
}
