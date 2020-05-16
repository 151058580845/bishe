package com.example.liaotian.presenter;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.example.liaotian.View.IPersonalInfoView;
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

public class PersonalInfoPresenter {

    private Context context;
    private IPersonalInfoView iPersonalInfoView;
    private Realm realm;
    public PersonalInfoPresenter(Context context, IPersonalInfoView iPersonalInfoView) {
        this.context = context;
        this.iPersonalInfoView = iPersonalInfoView;
        realm = Realm.getInstance(Contants.config);
    }

    public void onResume() {
        Function.getpersoalinfo(context,getUserId());
    }
    public int getUserId(){
        User user = realm.where(User.class).findFirst();
        return user.getId();
    }

    public void dispose(MessageEvent me) {
        try {
            Map<String,Object> result = (Map<String, Object>) me.getMessage();
            switch (me.getEventType()){
                case Contants.EVENT_GETPERSONALINFO:
                    setinfo(result);
                    break;
                case Contants.EVENT_SETPERSONALINFO:
                    iPersonalInfoView.showtoast("修改成功");
                    onResume();
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setinfo(Map<String, Object> result) {
        JSONObject json = null;
        try {
            json = new JSONObject((String) result.get("responseString"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        iPersonalInfoView.setpersonalinfo(json);
    }

    public void setpersonalinfo(String str0, int i) {
        Function.setpersonalinfo(str0,i,getUserId());
    }
}
