package com.example.liaotian.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.example.liaotian.View.IMainView;
import com.example.liaotian.entity.User;
import com.example.liaotian.util.Contants;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.SPUtils;
import com.example.liaotian.util.UploadUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class MainPresent {
    private static String TAG = "2222222222222";
    private IMainView iMainView;
    private Realm realm;
    private Context context;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == Contants.uploadsuccess) {
//                Constants.upload = 0;
                //    setpath(msg.obj.toString());
//                iPatroladdView.setpath(msg.obj.toString());
                //     (result数据类型) data = (result数据类型) msg.obj;
            }
            else if (msg.what == Contants.filecompree)
            {
                upload((File) msg.obj);
            }
        }
    };
    public MainPresent(Context context , IMainView iMainView) {
        this.context = context;
        this.iMainView = iMainView;
        realm = Realm.getInstance(Contants.config);
        getAddress();
    }

    public void getAddress(){
        User user = realm.where(User.class).findFirst();
        String address = user.getAddress();
        String[] address1 = address.split(",");
//        Log.e("22222222222", "login: "+address );
        List<String> list = new LinkedList<String>(Arrays.asList(address1));
//        Log.e("22222222222", "login: "+list );
        iMainView.setSpinner(list);
    }

    public void onDestroy(){
        if (realm!=null){
            realm.close();
//            SPUtils.put(context,Contants.login_success,0);
        }
    }

    public void dispose(MessageEvent me) {
        try {
            Map<String,Object> result = (Map<String, Object>) me.getMessage();
            switch (me.getEventType()){
                case Contants.EVENTBUS_GETTIANQI:
                    settianqi(result);
                    break;
               /* case Contants.EVENTBUS_IS_REGIST:
                    break;*/
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void settianqi(Map<String, Object> result) {
        try {
            JSONObject json = (JSONObject) result.get("responseString");
//            Log.e(TAG, "onResponse: "+json );
            JSONObject data = json.getJSONObject("data");
            String city = data.getString("city");
            JSONArray forecast = data.getJSONArray("forecast");
            JSONObject tianqi = forecast.getJSONObject(0);
            String high = tianqi.getString("high");
            String low = tianqi.getString("low");
            high = high.substring(2,high.length());
            low = low.substring(2,low.length());
//            Log.e(TAG, "settianqi: "+high+low );
            iMainView.settianqi(low+"-"+high);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//            iMainView.showToast(msg);

    }

    public void uploadfile(List<String> paths) {
        ArrayList<File> files = new ArrayList<>();

        for (int i = 0; i < paths.size(); i++) {
            File file = new File(paths.get(i));
            files.add(file);
        }
        getfile(files);
    }
    private void upload(File file) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = UploadUtil.uploadFile(file, Contants.url + "file.php");
                if (result != null) {
                    Message msg = handler.obtainMessage();
                    msg.what = Contants.uploadsuccess;
                    msg.obj = result;
//                    Log.e("2222222222222", "run: " + result);
                    handler.sendMessage(msg);
                }
            }
        }).start();
    }
    public void getfile(ArrayList<File> files){
        int i;
        for (i = 0;i<files.size();i++){
            Luban.with(context)
                    .load(files.get(i))
                    .ignoreBy(100)
                    .filter(new CompressionPredicate() {
                        @Override
                        public boolean apply(String path) {
                            return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                        }
                    }).setCompressListener(new OnCompressListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(File file) {
                    Message msg = handler.obtainMessage();
                    msg.what = Contants.filecompree;
                    msg.obj = file;
//                    Log.e("2222222222222", "run: " +file);
                    handler.sendMessage(msg);
                }

                @Override
                public void onError(Throwable e) {

                }
            }).launch();
        }
    }
}
