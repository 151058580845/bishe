package com.example.liaotian.presenter;

import android.util.Log;

import com.example.liaotian.View.IRegistView;
import com.example.liaotian.util.Contants;
import com.example.liaotian.util.MessageEvent;

import java.util.Map;

public class RegistPresenter {

    private IRegistView iRegistView;
    public RegistPresenter(IRegistView registView) {
        iRegistView = registView;
    }

    public void dispose(MessageEvent me){
        try {
            Map<String,Object> result = (Map<String, Object>) me.getMessage();
            switch (me.getEventType()){
                case Contants.EVENTBUS_REGIST:
                    regist(result);
                    break;
                case Contants.EVENTBUS_IS_REGIST:
                    is_regist(result);
                    break;
                case Contants.EVENTBUS_SHOUJIHAO_LOGIN:
                    shoujihao_login(result);
                    break;

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void shoujihao_login(Map<String, Object> result) {
        if ((int)result.get("status")==1){
            Log.e("22222222222222", "shoujihao_login: "+"222222222222" );
            iRegistView.showToast("登录成功");
           // iRegistView.setis_regist(true);
            iRegistView.forwordMain();
        }
    }

    private void is_regist(Map<String, Object> result) {
        if ((int)result.get("status")==1){
            Log.e("22222222222222", "is_regist: "+"222222222222" );
            iRegistView.showToast("该号码已被注册");
            iRegistView.setis_regist(true);
        }
    }

    private void regist(Map<String, Object> result) {
        if ((int)result.get("status")==1){
            Log.e("22222222222222", "regist: "+"222222222222" );
            iRegistView.showToast("注册成功");
            iRegistView.forwordLogin();
        }
    }
}
