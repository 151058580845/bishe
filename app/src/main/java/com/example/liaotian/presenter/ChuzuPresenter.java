package com.example.liaotian.presenter;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.liaotian.View.IChuzuView;
import com.example.liaotian.View.IZufanfView;
import com.example.liaotian.entity.Fangwu;
import com.example.liaotian.entity.User;
import com.example.liaotian.util.Contants;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.http.Function;

import java.util.ArrayList;
import java.util.Map;

import io.realm.Realm;

public class ChuzuPresenter {

    private IChuzuView iChuzuView;
    private Realm realm;
    private Context context;

    public ChuzuPresenter(Context context,IChuzuView iChuzuView) {
        this.context = context;
        this.iChuzuView = iChuzuView;
        realm = Realm.getInstance(Contants.config);
        getAddress();
    }

    public void getAddress(){
        Function.getAddress(context,getUserId());
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
                case Contants.EVENT_GETADDRESS:
                    setaddress(result);
                    break;
                case Contants.EVENT_SETCHUZUSUCESS:
                    getAddress();
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setaddress(Map<String, Object> result) {
        JSONArray json = JSONArray.parseArray((String) result.get("responseString"));
        ArrayList<Fangwu> list = (ArrayList<Fangwu>) JSONObject.parseArray(json.toJSONString(), Fangwu.class);
        iChuzuView.setList(list);
    }

    public void setchuzu(int room_id, String address, String jine, int is_chuzu, int num, String introduce) {
        Function.setchuzu(room_id,address,jine,is_chuzu,getUserPhone(),num,introduce);
    }
}
