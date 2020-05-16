package com.example.liaotian.presenter;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.liaotian.View.IBaoxiuView;
import com.example.liaotian.View.IJiaofeiView;
import com.example.liaotian.entity.User;
import com.example.liaotian.entity.Weixiu;
import com.example.liaotian.entity.Zhangdan;
import com.example.liaotian.util.Contants;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.http.Function;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Map;

import io.realm.Realm;

public class WuyebaoxiuPresenter {

    private Realm realm;
    private Context context;
    private IBaoxiuView iBaoxiuView;

    public WuyebaoxiuPresenter(Context context, IBaoxiuView iBaoxiuView) {
        this.context = context;
        this.iBaoxiuView = iBaoxiuView;
        realm = Realm.getInstance(Contants.config);
    }

    public void onResume(){
        Function.getWeixiu(context,getphone());
    }

    public int getUserId(){
        User user = realm.where(User.class).findFirst();
        return user.getId();
    }
    public String getphone(){
        User user = realm.where(User.class).findFirst();
        return user.getPhone();
    }

    public void dispose(MessageEvent me) {
        try {
            Map<String,Object> result = (Map<String, Object>) me.getMessage();
            switch (me.getEventType()){
                case Contants.EVENT_GETWEIXIUSUCCESS:
                    setweixiu(result);
                    break;
                case Contants.EVENT_SETWEIXIUSUCCESS:
                    onResume();
                    iBaoxiuView.showToast("上报维修成功");
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setweixiu(Map<String, Object> result) {
        JSONArray json = JSONArray.parseArray((String) result.get("responseString"));
        ArrayList<Weixiu> list = (ArrayList<Weixiu>) JSONObject.parseArray(json.toJSONString(), Weixiu.class);
        iBaoxiuView.setlist(list);
    }

    public void submit_weixiu(String str0, String str1) {
        if (str0.length()>0&&str1.length()==11){
            Function.setweixiu(str0,str1);
        }else {
            iBaoxiuView.showToast("输入错误,请重新输入");
        }
    }

    public void submit_chaxun(String str0) {
        Function.getWeixiu(context,getphone(),str0);
    }
}
