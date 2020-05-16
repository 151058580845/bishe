package com.example.liaotian.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.liaotian.View.IBaoxiuView;
import com.example.liaotian.View.IDingshuiView;
import com.example.liaotian.entity.User;
import com.example.liaotian.entity.Weixiu;
import com.example.liaotian.util.Contants;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.http.Function;

import java.util.ArrayList;
import java.util.Map;

import io.realm.Realm;

public class WuyedingshuiPresenter {

    private Realm realm;
    private Context context;
    private IDingshuiView iDingshuiView;

    public WuyedingshuiPresenter(Context context, IDingshuiView iDingshuiView) {
        this.context = context;
        this.iDingshuiView = iDingshuiView;
        realm = Realm.getInstance(Contants.config);
    }

    public void onResume(){
        Function.getFuwulist(context,getPhone(),1);
    }

    public int getUserId(){
        User user = realm.where(User.class).findFirst();
        return user.getId();
    }
    public String getPhone(){
        User user = realm.where(User.class).findFirst();
        return user.getPhone();
    }


    public void dispose(MessageEvent me) {
        try {
            Map<String,Object> result = (Map<String, Object>) me.getMessage();
            switch (me.getEventType()){
                case Contants.EVENT_GETFUWULIST:
                    setlist(result);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setlist(Map<String, Object> result) {
        JSONArray json = JSONArray.parseArray((String) result.get("responseString"));
        ArrayList<Weixiu> list = (ArrayList<Weixiu>) JSONObject.parseArray(json.toJSONString(), Weixiu.class);
        iDingshuiView.setlist(list);
    }

    public void getlist(String toString) {
        Function.getFuwulist(context,getPhone(),toString,2);
    }
}
