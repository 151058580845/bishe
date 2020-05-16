package com.example.liaotian.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSONArray;
import com.example.liaotian.View.IChuzuView;
import com.example.liaotian.View.IMyRoomView;
import com.example.liaotian.entity.Fangwu;
import com.example.liaotian.entity.User;
import com.example.liaotian.util.Contants;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.http.Client;
import com.example.liaotian.util.http.Function;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import io.realm.Realm;
import okhttp3.FormBody;

public class MyroomPresenter {

    private IMyRoomView iMyRoomView;
    private Realm realm;
    private Context context;
    private int room_id;
    public MyroomPresenter(Context context, IMyRoomView iMyRoomView) {
        this.context = context;
        this.iMyRoomView = iMyRoomView;
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
                case Contants.EVENT_GETROOMSUCCESS:
                    setmessage(result);
                    break;
                case Contants.EVENT_UPDATECHUZUSUCCESS:
                    getroom(room_id,0);
                    iMyRoomView.showToast("修改成功");
                    break;
                case Contants.EVENT_GETZUFANGSUCESS:
//                    getroom(room_id,0);
                    setmessage1(result);
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setmessage1(Map<String, Object> result) {
        try {
            JSONObject json = new JSONObject((String) result.get("responseString"));
            iMyRoomView.setList(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private JSONObject json = new JSONObject();
    private void setmessage(Map<String, Object> result) {
        String date = (String) result.get("responseString");
        try {
            JSONObject json = new JSONObject(date);
            this.json = json;
            iMyRoomView.setmessage(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getroom( int roomid,int num) {
        this.room_id = roomid;
        Function.getroom(roomid,num);
    }

    public void setchuzu(FormBody.Builder builder) {
        builder.add("user_id",String.valueOf(getUserId()));
        try {
            builder.add("address","");
            builder.add("tingshi","");
            builder.add("fangshi","");
            builder.add("chaoxiang","");
            if (json.has("address")){}
            builder.add("address",json.getString("address"));
            if (json.has("tingshi"))
            builder.add("tingshi",json.getString("tingshi"));
            if (json.has("fangshi"))
            builder.add("fangshi",json.getString("fangshi"));
            if (json.has("chaoxiang"))
            builder.add("chaoxiang",json.getString("chaoxiang"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FormBody formBody = builder.build();
        Client.setChuzu(formBody);
    }

    public void getroom_chuzu(int roomid, int i) {
        this.room_id = roomid;
        Function.getroom_chuzu(roomid);
    }
}
