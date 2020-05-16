package com.example.liaotian.presenter;

import android.content.Context;
import android.util.Log;

import com.example.liaotian.View.ILoginView;
import com.example.liaotian.entity.User;
import com.example.liaotian.entity.model.UserModel;
import com.example.liaotian.util.Contants;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.SPUtils;
import com.example.liaotian.util.http.Function;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class LoginPresenter {

    ILoginView iLoginView;
    private JSONObject infrom;
    private Realm realm;
    private UserModel userModel;
    private Context context;

    public LoginPresenter(Context context,ILoginView iLoginView) {
        this.context = context;
        this.iLoginView = iLoginView;

        realm = Realm.getInstance(Contants.config);

        userModel = new UserModel(realm);
        int login = (int) SPUtils.get(context,Contants.login_success,0);
        if (login == 1){
            iLoginView.forwordMain();
        }
    }

    public void dispose(MessageEvent me){
        try {
            Map<String,Object> result = (Map<String, Object>) me.getMessage();
            switch (me.getEventType()){
                case Contants.EVENTBUS_SHOUJIHAO_LOGIN:
                    login(result);
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void login(Map<String, Object> result) {
        if ((int)result.get("status")==1){
            String msg = (String) result.get("responseString");
            String message=msg.substring(0, msg.indexOf("+-/*"));
            String address=msg.substring(message.length()+4, msg.length());
            try {
               infrom = new JSONObject(message);
                User user = new User();
                user.setId(infrom.getInt("id"));
                user.setName(infrom.getString("name"));
                user.setPhone(infrom.getString("phone"));
                user.setPassword(infrom.getString("password"));
                user.setAddress(address);
                userModel.delectAll1();
                userModel.saveUser(user);
                SPUtils.put(context,Contants.login_success,1);
                iLoginView.forwordMain();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            iLoginView.showToast("账号密码错误");
        }
    }

    public void onDestroy(){
        if (realm!=null){
            realm.close();
        }
    }


}
