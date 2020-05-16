package com.example.liaotian.presenter;

import android.content.Context;

import com.example.liaotian.View.IChuzuView;
import com.example.liaotian.View.IChuzuXiugaiView;
import com.example.liaotian.entity.Fangwu;
import com.example.liaotian.entity.User;
import com.example.liaotian.util.Contants;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.http.Function;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import io.realm.Realm;

public class ChuzuXiugaiPresenter {

    private IChuzuXiugaiView iChuzuXiugaiView;
    private Realm realm;
    private Context context;
    private int room_id;
    public ChuzuXiugaiPresenter(Context context, IChuzuXiugaiView iChuzuXiugaiView,int room_id) {
        this.context = context;
        this.iChuzuXiugaiView = iChuzuXiugaiView;
        realm = Realm.getInstance(Contants.config);
        this.room_id = room_id;
        getchuzuxiugai();
    }

    public void getchuzuxiugai(){
        Function.getchuzuxiugai(context,room_id);
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
                case Contants.EVENT_GETCHUZUXIUGAI:
                    setmessage(result);
                    break;
                case Contants.EVENT_SETCHUZUSUCESS:
                    iChuzuXiugaiView.back();
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setmessage(Map<String, Object> result) {
        JSONObject data = null;
        try {
            data = new JSONObject((String) result.get("responseString"));
            iChuzuXiugaiView.setmessage(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

//    }

    public void setchuzu(int room_id, String address, String money, int is_chuzu, int num, String introduce, String phone) {
        Function.setchuzu(room_id,address,money,is_chuzu,phone,num,introduce);
    }
}
