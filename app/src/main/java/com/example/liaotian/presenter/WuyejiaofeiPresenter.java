package com.example.liaotian.presenter;

import android.content.Context;
import android.util.Log;
import com.example.liaotian.View.IJiaofeiView;
import com.example.liaotian.entity.User;
import com.example.liaotian.util.Contants;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.http.Function;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import io.realm.Realm;

public class WuyejiaofeiPresenter {
    private Realm realm;
    private Context context;
    private IJiaofeiView ijiaofeiView;

    public WuyejiaofeiPresenter(Context context, IJiaofeiView ijiaofeiView) {
        this.context = context;
        this.ijiaofeiView = ijiaofeiView;
        realm = Realm.getInstance(Contants.config);
    }

    public int getUserId(){
        User user = realm.where(User.class).findFirst();
        return user.getId();
    }

    public void getPay() {
        Function.getPay(context,getUserId());
    }

    public void dispose(MessageEvent me) {
        try {
            Map<String,Object> result = (Map<String, Object>) me.getMessage();
            switch (me.getEventType()){
                case Contants.EVENT_GETPAY:
//                    Log.e("33333","enter in");
                    setPay(result);
                    break;
                case Contants.EVENT_UPDATEJIAOFEISUCCESS:
                    getPay();
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setPay(Map<String, Object> result) {
        JSONObject json = null;
        try {
            json = new JSONObject((String) result.get("responseString"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ijiaofeiView.setshuidian(json);
    }

    public void setjiaofei(int i, double money, String time) {
        Function.setjiaofei(i,money,getUserId(),time);
    }
}
