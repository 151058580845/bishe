package com.example.liaotian.presenter;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.liaotian.View.IZhangdanView;
import com.example.liaotian.entity.User;
import com.example.liaotian.entity.Zhangdan;
import com.example.liaotian.entity.model.UserModel;
import com.example.liaotian.util.Contants;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.http.Function;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import io.realm.Realm;

public class WuyezhangdanPresenter {

    private Realm realm;
    private Context context;
    private IZhangdanView iZhangdanView;
    private int year = 0;
    private int month = 0;
    private int type = 110;

    public WuyezhangdanPresenter(Context context, IZhangdanView iZhangdanView) {
        this.context = context;
        this.iZhangdanView = iZhangdanView;
        realm = Realm.getInstance(Contants.config);
    }


    public void dispose(MessageEvent me) {
        try {
            Map<String,Object> result = (Map<String, Object>) me.getMessage();
            switch (me.getEventType()){
                case Contants.EVENT_GETZHANGDAN:
                    Log.e("33333","enter in");
                    setZhangdan(result);
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setZhangdan(Map<String, Object> result) {
        JSONArray json = JSONArray.parseArray((String) result.get("responseString"));
        int money=0;
        for (int i =0;i<json.size();i++) {
            money+=json.getJSONObject(i).getInteger("money");
        }
        iZhangdanView.setjine(money);
        ArrayList<Zhangdan> list = (ArrayList<Zhangdan>) JSONObject.parseArray(json.toJSONString(), Zhangdan.class);
        iZhangdanView.setzhangdan(list);
    }

    public void onResume() {
        getZhangdan();
    }

    public void getZhangdan() {
        Function.getZhangdan(getUserId(), 0);
    }
    public void getZhangdan(int num,String select) {
        Function.getZhangdan(getUserId(), num,select);
    }

    public int getUserId(){
        User user = realm.where(User.class).findFirst();
        return user.getId();
    }

    public void settime(Date date) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH)+1;
        if (type==110){
            Function.getZhangdan(getUserId(),1,year,month);
        }else {
            Function.getZhangdan(getUserId(),4,year,month,type);
        }
        Log.e("33333", "settime: "+year+month);
    }

    public void settype(String s) {
        int type = 110;
        switch (s){
            case Contants.type1:
                type=0;
                break;
            case Contants.type2:
                type=1;
                break;
            case Contants.type3:
                type=2;
                break;
            case Contants.type4:
                type=3;
                break;
//            case Contants.type5:
//                type = 4;
//                break;
        }
        this.type = type;
        if (year==0&&month==0){
            Function.getZhangdan(getUserId(),2,type);
        }else {
            Function.getZhangdan(getUserId(),4,year,month,type);
        }
    }

    public void getZhangdan(int x) {
        if (year==0&&month==0){
            getZhangdan();
        }else {
            Function.getZhangdan(getUserId(),1,year,month);
        }
    }

}
