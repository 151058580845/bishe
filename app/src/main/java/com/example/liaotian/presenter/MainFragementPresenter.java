package com.example.liaotian.presenter;

import android.util.Log;

import com.example.liaotian.View.IMainFragementView;
import com.example.liaotian.entity.News;
import com.example.liaotian.entity.User;
import com.example.liaotian.entity.model.UserModel;
import com.example.liaotian.util.Contants;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.http.Function;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import io.realm.Realm;

public class MainFragementPresenter {

    private IMainFragementView iMainFragementView;
    private Realm realm;
    private UserModel userModel;


    public MainFragementPresenter(IMainFragementView iMainFragementView) {
        this.iMainFragementView = iMainFragementView;
        realm = Realm.getInstance(Contants.config);
        userModel = new UserModel(realm);
    }

    public void dispose(MessageEvent me){
        try {
            Map<String,Object> result = (Map<String, Object>) me.getMessage();
            switch (me.getEventType()){
                case Contants.EVENT_GETNEWS:
                    setNews(result);
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setNews(Map<String, Object> result) {
        JSONObject json = (JSONObject) result.get("responseString");
        try {
            JSONObject result1 = json.getJSONObject("result");
//            JSONObject data = result1.getJSONObject("data");
            JSONArray data = result1.getJSONArray("data");
            Log.e("3333333", "setNews: "+data );
            ArrayList<News> list = new ArrayList<>();
            for(int i =0; i<data.length();i++){
                News news = new News();
                news.setNews_photo(data.getJSONObject(i).getString("thumbnail_pic_s"));
                news.setTitle(data.getJSONObject(i).getString("title"));
                news.setNews(data.getJSONObject(i).getString("date"));
                news.setUrl(data.getJSONObject(i).getString("url"));
                list.add(news);
            }
            iMainFragementView.setNews(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getUserId(){
        User user = realm.where(User.class).findFirst();
        return user.getId();
    }

/*    public void getZhangdan() {
        Function.getZhangdan(getUserId(), num);
    }*/
}
