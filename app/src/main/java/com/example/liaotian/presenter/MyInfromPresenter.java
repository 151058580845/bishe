package com.example.liaotian.presenter;

import android.content.Context;
import com.example.liaotian.View.IMyInfromView;
import com.example.liaotian.entity.User;
import com.example.liaotian.entity.model.UserModel;
import com.example.liaotian.util.Contants;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.http.Function;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import io.realm.Realm;

public class MyInfromPresenter {

    private IMyInfromView iMyInfromView;
    private Realm realm;
    private UserModel userModel;
    private Context context;
    public MyInfromPresenter(Context context , IMyInfromView iMyInfromView) {
        this.context = context;
        this.iMyInfromView = iMyInfromView;
        realm = Realm.getInstance(Contants.config);
        userModel = new UserModel(realm);
    }

    public void onResume() {
        Function.getpersoalinfo(context,getUserId());
    }

    public boolean password_is(String password){
        if (password.equals(getUserpassword()))
            return true;
        else return false;
    }

    public void dispose(MessageEvent me){
        try {
            Map<String,Object> result = (Map<String, Object>) me.getMessage();
            switch (me.getEventType()){
                case Contants.EVENT_GETPERSONALINFO:
                    setinfo(result);
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

        iMyInfromView.setpersonalinfo(json);
    }


    public int getUserId(){
        User user = realm.where(User.class).findFirst();
        return user.getId();
    }

    public String getUserpassword(){
        User user = realm.where(User.class).findFirst();
        return user.getPassword();
    }

    public void setpassword(String str0) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User user = realm.where(User.class).findFirst();
                user.setPassword(str0);
            }
        });
        Function.re_password(context,getUserId(),str0);
    }
}
