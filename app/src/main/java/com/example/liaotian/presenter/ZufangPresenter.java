package com.example.liaotian.presenter;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.liaotian.View.IChuzuView;
import com.example.liaotian.View.IZufanfView;
import com.example.liaotian.entity.Fangwu;
import com.example.liaotian.entity.User;
import com.example.liaotian.util.Contants;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.http.Client;
import com.example.liaotian.util.http.Function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

public class ZufangPresenter {

    private IZufanfView iZufanfView;
    private Realm realm;
    private Context context;
    private ArrayList<Fangwu> list = new ArrayList<>();
    public ZufangPresenter(Context context, IZufanfView iZufanfView) {
        this.context = context;
        this.iZufanfView = iZufanfView;
        realm = Realm.getInstance(Contants.config);
        getZufang();
    }

    public void getZufang(){
        Function.getZufang(context);
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
                case Contants.EVENT_GETZUFANGSUCESS:
                    setzufang(result);
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private String fangshi = "全部";
    private String tingshi = "全部房型";
    private String chaoxiang = "";
    public void setList(int num, String s){
        ArrayList<Fangwu> list1 = new ArrayList<>();
        switch (num){
            case 1:
                fangshi = s;
                break;
            case 2:
                tingshi = s;
                break;
            case 3:
                chaoxiang = s;
                break;
        }
        if (fangshi.equals("全部")){
            if (tingshi.equals("全部房型")){
                if (chaoxiang.equals("")){
                    iZufanfView.setList(list);
                    return;
                }else {
                    for (int i =0;i<list.size();i++){
                        if (chaoxiang.equals(list.get(i).getChaoxiang())){
                            list1.add(list.get(i));
                        }
                    }
                }
            }else {
                if (chaoxiang.equals("")){
                    for (int i =0;i<list.size();i++){
                        if (tingshi.equals(list.get(i).getTingshi())){
                            list1.add(list.get(i));
                        }
                    }
                }else {
                    for (int i =0;i<list.size();i++){
                        if (chaoxiang.equals(list.get(i).getChaoxiang())&&tingshi.equals(list.get(i).getTingshi())){
                            list1.add(list.get(i));
                        }
                    }
                }
            }
        }else {
            if (tingshi.equals("全部房型")){
                if (chaoxiang.equals("")){
                    for (int i =0;i<list.size();i++){
                        if (fangshi.equals(list.get(i).getFangshi())){
                            list1.add(list.get(i));
                        }
                    }
                }else {
                    for (int i =0;i<list.size();i++){
                        if (fangshi.equals(list.get(i).getFangshi())&&chaoxiang.equals(list.get(i).getChaoxiang())){
                            list1.add(list.get(i));
                        }
                    }
                }
            }else {
                if (chaoxiang.equals("")){
                    for (int i =0;i<list.size();i++){
                        if (fangshi.equals(list.get(i).getFangshi())&&tingshi.equals(list.get(i).getTingshi())){
                            list1.add(list.get(i));
                        }
                    }
                }else {
                    for (int i =0;i<list.size();i++){
                        if (fangshi.equals(list.get(i).getFangshi())&&tingshi.equals(list.get(i).getTingshi())&&chaoxiang.equals(list.get(i).getChaoxiang())){
                            list1.add(list.get(i));
                        }
                    }
                }
            }
        }

        if (list1.size() > 0) {
            iZufanfView.setList(list1);
        } else {
            iZufanfView.setList(list1);
            iZufanfView.show("无此资源");
        }


    }

    private void setzufang(Map<String, Object> result) {
        JSONArray json = JSONArray.parseArray((String) result.get("responseString"));
        ArrayList<Fangwu> list = (ArrayList<Fangwu>) JSONObject.parseArray(json.toJSONString(), Fangwu.class);
        this.list = list;
        iZufanfView.setList(list);
    }
}
