package com.example.liaotian.presenter;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.liaotian.View.IMainView;
import com.example.liaotian.View.IZufanfView;
import com.example.liaotian.entity.Fangwu;
import com.example.liaotian.entity.User;
import com.example.liaotian.entity.Zhangdan;
import com.example.liaotian.util.Contants;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.http.Function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

public class WuyezufangPresenter {

//    private IZufanfView iZufanfView;
    private Realm realm;
    private Context context;

    public WuyezufangPresenter(Context context) {
        this.context = context;
//        this.iZufanfView = iZufanfView;
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

    public void onDestroy(){
        if (realm!=null){
            realm.close();
        }
    }

    public void dispose(MessageEvent me) {
    }

}
