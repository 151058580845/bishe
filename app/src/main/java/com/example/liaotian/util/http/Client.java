package com.example.liaotian.util.http;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.liaotian.Activity.WuyetousumessageActivity;
import com.example.liaotian.util.Contants;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.PublicFunction;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.liaotian.util.Contants.url;

public class Client {
    private static String TAG = "2222222222222";
    private static OkHttpClient client = new OkHttpClient();
    static {

    }



    private static String getAbsoluteUrl(String relativeUrl){
        return url+relativeUrl;
    }

    private static void sendEvent(int eventType, int status , Object responseString){
        MessageEvent me = new MessageEvent();
        me.setEventType(eventType);
        Map<String,Object> result = new HashMap<>();
    //    JSONObject json = PublicFunction.getJsonObject(responseString);
        result.put("status",status);
        result.put("responseString",responseString);
        me.setMessage(result);
        EventBus.getDefault().post(me);
    }

        //注册
    public static void regist(final Context context, FormBody formBody){
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"people_regist.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responsedata = response.body().string();
                Log.e("222222222", "onResponse: "+response.toString());
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
                    Log.e("22222222222", "onResponse: "+status );
                  //  String msg = json.getString("msg");
                    sendEvent(Contants.EVENTBUS_REGIST,status,"");
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    //查看是否被注册
    public static void is_regist(final Context context, FormBody formBody){
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"people_is_regist.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responsedata = response.body().string();
                Log.e("222222222", "onResponse: "+response.toString());
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
                    Log.e("22222222222", "onResponse: "+status );
                    //  String msg = json.getString("msg");
                    sendEvent(Contants.EVENTBUS_IS_REGIST,status,"");
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
    //手机号登陆
    public static void login_shoujihao(final Context context, FormBody formBody){
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"people_is_regist.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
                    Log.e("22222222222", "onResponse: "+status );
                    //  String msg = json.getString("msg");
                    sendEvent(Contants.EVENTBUS_SHOUJIHAO_LOGIN,status,"");
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    //登录账号
    public static void login(final Context context, FormBody formBody){
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"people_login.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
                    String msg = json.getString("msg").replace("\\", "");
                    String address = json.getString("address");
                    Log.e(TAG, "onResponse: "+address );
                    msg = msg+"+-/*"+address;
                    sendEvent(Contants.EVENTBUS_SHOUJIHAO_LOGIN,status,msg);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    //获取地址id
    public static void getCitytianqi(final Context context, String formBody){
        final Request request = new Request.Builder()
              //  .post(formBody)
               // .url(Contants.url+"people_citycode.php")
             //   .url("http://t.weather.sojson.com/api/weather/city/101043700")
                .url("http://wthrcdn.etouch.cn/weather_mini?city="+formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    sendEvent(Contants.EVENTBUS_GETTIANQI,1,json);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    //获取地址id
    public static void getNews(final Context context){
        final Request request = new Request.Builder()
                .url("http://v.juhe.cn/toutiao/index?type=top&key=468a538795ca302846f994e7559df8a7")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    sendEvent(Contants.EVENT_GETNEWS,1,json);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getZhangdan(FormBody formBody) {
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"wuyezhangdanget.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
                    String msg = json.getString("data");
                    sendEvent(Contants.EVENT_GETZHANGDAN,status,msg);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getPay(FormBody formBody) {
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"wuyejiaofeiget.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
                    String msg = json.getString("data");
                    sendEvent(Contants.EVENT_GETPAY,status,msg);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });

    }

    public static void getAddress(FormBody formBody) {
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"wuyechuzuget.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
                    String msg = json.getString("data");
                    sendEvent(Contants.EVENT_GETADDRESS,status,msg);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void setchuzu(FormBody formBody) {
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"wuyechuzuset.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
                    String msg = "";
                    if (status==1)
                    sendEvent(Contants.EVENT_SETCHUZUSUCESS,status,msg);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getZufang(FormBody formBody) {
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"wuyezufangget.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
                    String msg = json.getString("date");
                    sendEvent(Contants.EVENT_GETZUFANGSUCESS,status,msg);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void re_password(FormBody formBody) {
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"people_re_password.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
                    String msg = json.getString("data");
                    sendEvent(Contants.EVENT_GETZUFANGSUCESS,status,msg);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getWeixiu(FormBody formBody) {
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"wuyebaoxiuget.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
                    String msg = json.getString("data");
                    sendEvent(Contants.EVENT_GETWEIXIUSUCCESS,status,msg);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void setweixiu(FormBody formBody) {
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"wuyebaoxiuset.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
                    String msg = "";
                    sendEvent(Contants.EVENT_SETWEIXIUSUCCESS,status,msg);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getpersoalinfo(FormBody formBody) {
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"personalinfoget.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
                    String msg = json.getString("data");
                    sendEvent(Contants.EVENT_GETPERSONALINFO,status,msg);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void setpersonalinfo(FormBody formBody) {
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"personalinfoset.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
                    String msg = "";
                    sendEvent(Contants.EVENT_SETPERSONALINFO,status,msg);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getFuwulist(FormBody formBody) {
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"wuyefuwulist_get.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
                    String msg = json.getString("data");
                    sendEvent(Contants.EVENT_GETFUWULIST,status,msg);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getFuwu_list(FormBody formBody) {
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"wuyefuwulistget.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
                    String msg = json.getString("data");
                    sendEvent(Contants.EVENT_GETFUWU,status,msg);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getchuzuxiugai(FormBody formBody) {
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"wuyechuzuxiugaiget.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
                    String msg = json.getString("data");
                    sendEvent(Contants.EVENT_GETCHUZUXIUGAI,status,msg);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void setfuwu(FormBody formBody) {
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"wuyefuwulistset.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
//                    String msg = json.getString("data");
                    sendEvent(Contants.EVENT_SETFUWUSUCCESS,status,"");
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getTousu(FormBody formBody) {
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"wuyetousugetlist.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
                    String msg = json.getString("data");
                    sendEvent(Contants.EVENT_GETTOUSUUCCESS,status,msg);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }


    public static void uploadfile(Context context, String path, int id){
//        OkHttpClient mOkHttpClent = new OkHttpClient();
        File file = new File(path);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
//        for (File file:path){
//            if (file.exists()){
//                 builder.addFormDataPart("img", file.getName(),
//                        okhttp3.RequestBody.create(  MediaType.parse("image/png"), file));
//
//            }
//        }
                .addFormDataPart("img", file.getName(),
                okhttp3.RequestBody.create(  MediaType.parse("image/png"), file));
        builder.addFormDataPart("id",String.valueOf(id));

        okhttp3.RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(Contants.url + "wuyegetphoto.php")
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
                         @Override
                         public void onFailure(Call call, IOException e) {
                             Log.e(TAG, "onFailure: "+e );
//                             Toast.makeText(context, "失败", Toast.LENGTH_SHORT).show();
                         }
                         @Override
                         public void onResponse(Call call, Response response) throws IOException {
                             Log.e(TAG, "成功"+response);
                             String responsedata = response.body().string();
                             try {
                                 JSONObject json = new JSONObject(responsedata);
                                 int status = json.getInt("status");
                                 String msg = json.getString("address");
                                 sendEvent(Contants.EVENT_ADDTOUSUSECCESS,status,msg);
                             }catch (JSONException e){
                                 e.printStackTrace();
                             }
//                             Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
                         }
                     }
        );
    }


    public static void addtousu(FormBody formBody) {
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"wuyetousuaddset.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
                    int msg = json.getInt("id");
                    sendEvent(Contants.EVENT_SETTOUSUSUCCUESS,status,msg);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getTousu1(FormBody formBody) {
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"wuyetousuget.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
                    String msg = json.getString("date");
                    sendEvent(Contants.EVENT_GETTOUSUUCCESSID,status,msg);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void updatetousu(FormBody formBody) {
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"wuyetousuget.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
//                    int msg = json.getInt("id");
                    sendEvent(Contants.EVENT_SETTOUSUSUCCUESS,status,"");
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getroom(FormBody formBody) {
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"wuyegetroom.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
                    String msg = json.getString("date");
                    sendEvent(Contants.EVENT_GETROOMSUCCESS,status,msg);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void setChuzu(FormBody formBody) {
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"wuyechuzu_set.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
//                    int msg = json.getInt("id");
                    sendEvent(Contants.EVENT_UPDATECHUZUSUCCESS,status,"");
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void setfangyuan(FormBody formBody) {
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"wuyechuzu_set.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
//                    int msg = json.getInt("id");
                    sendEvent(Contants.EVENT_UPDATECHUZUSUCCESS,status,"");
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getroom_chuzu(FormBody formBody) {
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"wuyezufangget.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
                    String msg = json.getString("date");
                    sendEvent(Contants.EVENT_GETZUFANGSUCESS,status,msg);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void setjiaofei() {
        final Request request = new Request.Builder()
                .url("https://sp0.baidu.com/9_Q4sjW91Qh3otqbppnN2DJv/pae/channel/data/asyncqury?cb=jQuery110204759692032715892_1499865778178&appid=4001&com=&nu=557006094719139")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
                    String msg = json.getString("date");
                    sendEvent(Contants.EVENT_UPDATEJIAOFEISUCCESS,status,msg);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void setjiaofei(FormBody formBody) {
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"wuyejiaofeiupdate.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
                    String msg = json.getString("date");
                    sendEvent(Contants.EVENT_UPDATEJIAOFEISUCCESS,status,msg);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static void sendPost(String url, Map<String, String> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // POST方法
            conn.setRequestMethod("POST");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            if (params != null) {
                StringBuilder param = new StringBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if(param.length()>0){
                        param.append("&");
                    }
                    param.append(entry.getKey());
                    param.append("=");
                    param.append(entry.getValue());
                    //System.out.println(entry.getKey()+":"+entry.getValue());
                }
                //System.out.println("param:"+param.toString());
                out.write(param.toString());
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        sendEvent(Contants.EVENT_GETKUAIDIMESSAGE,1,result.toString());
    }

    public static void tousu_delete(Context context, FormBody formBody) {
        final Request request = new Request.Builder()
                .post(formBody)
                .url(url+"wuyetousudelete.php")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.e(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responsedata = response.body().string();
                try {
                    JSONObject json = new JSONObject(responsedata);
                    int status = json.getInt("status");
                    if (status == 1){
                        sendEvent(Contants.EVENT_DELETESUCCESS,status,"");
                    }else {
                        sendEvent(Contants.EVENT_DELETEFAIL,status,"");
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
