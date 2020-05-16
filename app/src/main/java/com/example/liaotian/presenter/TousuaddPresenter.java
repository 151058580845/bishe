package com.example.liaotian.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.example.liaotian.Activity.WuyetousuaddActivity;
import com.example.liaotian.View.ITousuView;
import com.example.liaotian.View.IZufanfView;
import com.example.liaotian.entity.Fangwu;
import com.example.liaotian.entity.User;
import com.example.liaotian.util.Contants;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.UploadUtil;
import com.example.liaotian.util.http.Client;
import com.example.liaotian.util.http.Function;
import com.google.gson.JsonObject;
import com.luck.picture.lib.entity.LocalMedia;

import org.bouncycastle.asn1.cmp.CAKeyUpdAnnContent;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import okhttp3.FormBody;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class TousuaddPresenter {

    private ITousuView iTousuView;
    private Realm realm;
    private Context context;
    private ArrayList<String> selectList = new ArrayList<>();
    private int id = 0;
    public TousuaddPresenter(Context context, ITousuView iTousuView) {
        this.context = context;
        this.iTousuView = iTousuView;
        realm = Realm.getInstance(Contants.config);
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
        try {
            Map<String,Object> result = (Map<String, Object>) me.getMessage();
            switch (me.getEventType()){
                case Contants.EVENT_GETTOUSUUCCESSID:
                    setmessage(result);
                    break;
                case Contants.EVENT_SETTOUSUSUCCUESS:
                    addphoto(result);
                    break;
                case Contants.EVENT_ADDTOUSUSECCESS:
                    iTousuView.back();
                    break;
                case Contants.EVENT_DELETESUCCESS:
                    iTousuView.back();
                    break;
                case Contants.EVENT_DELETEFAIL:
                    iTousuView.showToast("删除失败");
                    break;

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setmessage(Map<String, Object> result) {
        String date = (String) result.get("responseString");
        iTousuView.setmessage(date);
//        List<File> path = new ArrayList<>();

//        Client.uploadfile(context,path);
    }

    public void addtousu(FormBody.Builder builder, ArrayList<String> selectList){
        this.selectList = selectList;
        builder.add("user_id",String.valueOf(getUserId()));
        FormBody formBody = builder.build();
        Client.addtousu(formBody);
    }

    public void gettousu(int id,int num) {
        this.id = id;
        Function.gettousu(context,id,num);
    }
    public void addphoto(Map<String, Object> result) {
        if (id == 0){
            id = (int) result.get("responseString");
        }
//        builder.add("user_id",String.valueOf(getUserId()));
//        FormBody formBody = builder.build();
        for (int i = 0;i<selectList.size();i++){
            Client.uploadfile(context,selectList.get(i),id);
        }

    }

    public void updatetousu(FormBody.Builder builder, ArrayList<String> selectList, int num) {
        this.selectList = selectList;
        builder.add("id",String.valueOf(getUserId()));
        builder.add("num",String.valueOf(num));
        FormBody formBody = builder.build();
        Client.updatetousu(formBody);
    }
}
