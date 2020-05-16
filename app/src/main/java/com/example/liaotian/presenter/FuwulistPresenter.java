package com.example.liaotian.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.liaotian.View.IDingshuiView;
import com.example.liaotian.View.IFuwulist;
import com.example.liaotian.entity.Fuwu;
import com.example.liaotian.entity.User;
import com.example.liaotian.entity.Weixiu;
import com.example.liaotian.util.Contants;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.http.Function;

import java.util.ArrayList;
import java.util.Map;

import io.realm.Realm;

public class FuwulistPresenter {

    private Realm realm;
    private Context context;
    private IFuwulist iFuwulist;

    public FuwulistPresenter(Context context, IFuwulist iFuwulist) {
        this.context = context;
        this.iFuwulist = iFuwulist;
        realm = Realm.getInstance(Contants.config);
    }

    public void onResume(){
        Function.getFuwu_list(context);
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
                case Contants.EVENT_GETFUWU:
                    setlist(result);
                    break;
                case Contants.EVENT_SETFUWUSUCCESS:
                    iFuwulist.back();
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setlist(Map<String, Object> result) {
        JSONArray json = JSONArray.parseArray((String) result.get("responseString"));
        ArrayList<Fuwu> list = (ArrayList<Fuwu>) JSONObject.parseArray(json.toJSONString(), Fuwu.class);
        iFuwulist.setmessage(list);
    }

    public void getlist(String toString) {
        Function.getFuwulist(context,getPhone(),toString,2);
    }

    public void setfuwu(String name) {
        Function.setfuwu(name,getPhone());
    }
}
