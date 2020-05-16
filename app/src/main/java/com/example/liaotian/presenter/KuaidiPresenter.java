package com.example.liaotian.presenter;

import android.content.Context;
import android.util.Log;

import com.example.liaotian.View.IChuzuView;
import com.example.liaotian.View.IKuaidiView;
import com.example.liaotian.entity.Fangwu;
import com.example.liaotian.entity.User;
import com.example.liaotian.util.Contants;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.http.Function;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import io.realm.Realm;

public class KuaidiPresenter {

    private IKuaidiView iKuaidiView;
    private Realm realm;
    private Context context;

    public KuaidiPresenter(Context context, IKuaidiView iKuaidiView) {
        this.context = context;
        this.iKuaidiView = iKuaidiView;
        realm = Realm.getInstance(Contants.config);
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
                case Contants.EVENT_GETKUAIDIMESSAGE:
                    setaddress(result);
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setaddress(Map<String, Object> result) {
        String message = (String) result.get("responseString");
        try {
            JSONObject json = new JSONObject(message);
            JSONArray jsonArray = new JSONArray(json.getString("Traces"));
            if (jsonArray.length()==0){
                iKuaidiView.showToast("查询错误");
                return;
            }
            ArrayList<Fangwu> list = new ArrayList<>();
            for (int i=0;i<jsonArray.length();i++){
                Fangwu fangwu = new Fangwu();
                fangwu.setAddress(jsonArray.getJSONObject(i).getString("AcceptStation"));
                fangwu.setTime(jsonArray.getJSONObject(i).getString("AcceptTime"));
                list.add(fangwu);
            }
            iKuaidiView.setlist(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
