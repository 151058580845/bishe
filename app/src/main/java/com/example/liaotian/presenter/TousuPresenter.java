package com.example.liaotian.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.liaotian.View.IChuzuView;
import com.example.liaotian.View.ITousuView;
import com.example.liaotian.entity.Fangwu;
import com.example.liaotian.entity.Tousu;
import com.example.liaotian.entity.User;
import com.example.liaotian.util.Contants;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.http.Function;

import java.util.ArrayList;
import java.util.Map;

import io.realm.Realm;

public class TousuPresenter {

    private ITousuView iTousuView;
    private Realm realm;
    private Context context;

    public TousuPresenter(Context context, ITousuView iTousuView) {
        this.context = context;
        this.iTousuView = iTousuView;
        realm = Realm.getInstance(Contants.config);
//        getTousu();
    }

    public void getTousu(){
        Function.getTousu(context,getUserId(),1);
    }

    public int getUserId(){
        User user = realm.where(User.class).findFirst();
        return user.getId();
    }

    public String getUserPhone(){
        User user = realm.where(User.class).findFirst();
        return user.getPhone();
    }

    public void onDestroy(){
        if (realm!=null){
            realm.close();
        }
    }

    public void dispose(MessageEvent me) {
        try {
            Map<String,Object> result = (Map<String, Object>) me.getMessage();
            switch (me.getEventType()){
                case Contants.EVENT_GETTOUSUUCCESS:
                    setlist(result);
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setlist(Map<String, Object> result) {
        JSONArray json = JSONArray.parseArray((String) result.get("responseString"));
        ArrayList<Tousu> list = (ArrayList<Tousu>) JSONObject.parseArray(json.toJSONString(), Tousu.class);
        iTousuView.setlist(list);
        iTousuView.setl(list);
    }

    public void getTousu(String message) {
        Function.getTousu(context,getUserId(),2,message);
    }
}
