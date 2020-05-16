package com.example.liaotian.util.http;

import android.content.Context;

import okhttp3.FormBody;

public class Function {

    //注册
    public static void regist(Context context,String phone , String name , String password){
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phone",phone);
        builder.add("name",name);
        builder.add("password",password);
        FormBody formBody = builder.build();
        Client.regist(context,formBody);
    }
    //查看是否被注册
    public static void is_regist(Context context,String phone){
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phone",phone);
        FormBody formBody = builder.build();
        Client.is_regist(context,formBody);
    }
    //手机号登录
    public static void login_shoujihao(Context context,String phone){
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phone",phone);
        FormBody formBody = builder.build();
        Client.login_shoujihao(context,formBody);
    }
    //登录
    public static void login(Context context,String phone,String password){
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phone",phone);
        builder.add("password",password);
        FormBody formBody = builder.build();
        Client.login(context,formBody);
    }
    public static void getCitytianqi(Context context,String city){
        Client.getCitytianqi(context,city);
    }
    public static void getNews(Context context){
        Client.getNews(context);
    }

    public static void getZhangdan(int id, int num) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("id", String.valueOf(id));
        builder.add("num", String.valueOf(num));
        FormBody formBody = builder.build();
        Client.getZhangdan(formBody);
    }

    public static void getZhangdan(int id, int num, int year, int month) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("id", String.valueOf(id));
        builder.add("num", String.valueOf(num));
        builder.add("year",String.valueOf(year));
        builder.add("month", String.valueOf(month));
        FormBody formBody = builder.build();
        Client.getZhangdan(formBody);
    }
    public static void getZhangdan(int id, int num, int type) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("id", String.valueOf(id));
        builder.add("num", String.valueOf(num));
        builder.add("type",String.valueOf(type));
        FormBody formBody = builder.build();
        Client.getZhangdan(formBody);
    }
    public static void getZhangdan(int id, int num,String select) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("id", String.valueOf(id));
        builder.add("num", String.valueOf(num));
        builder.add("select",select);
        FormBody formBody = builder.build();
        Client.getZhangdan(formBody);
    }

    public static void getPay(Context context, int userId) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("id", String.valueOf(userId));
        FormBody formBody = builder.build();
        Client.getPay(formBody);
    }

    public static void getAddress(Context context, int userId) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("id", String.valueOf(userId));
        FormBody formBody = builder.build();
        Client.getAddress(formBody);

    }

    public static void setchuzu(int room_id, String address, String jine, int is_chuzu, String phone, int num, String introduce) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("room_id", String.valueOf(room_id));
        builder.add("address", String.valueOf(address));
        builder.add("money", String.valueOf(jine));
        builder.add("is_chuzu", String.valueOf(is_chuzu));
        builder.add("phone", String.valueOf(phone));
        builder.add("num", String.valueOf(num));
        builder.add("introduce", String.valueOf(introduce));
        FormBody formBody = builder.build();
        Client.setchuzu(formBody);
    }

    public static void re_password(Context context, int id, String password) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("id", String.valueOf(id));
        builder.add("password", String.valueOf(password));
        FormBody formBody = builder.build();
        Client.re_password(formBody);
    }

    public static void getWeixiu(Context context, String phone) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phone", String.valueOf(phone));
        builder.add("num", String.valueOf(1));
        FormBody formBody = builder.build();
        Client.getWeixiu(formBody);
    }

    public static void setweixiu(String str0, String str1) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("name", String.valueOf(str0));
        builder.add("phone", String.valueOf(str1));
        FormBody formBody = builder.build();
        Client.setweixiu(formBody);
    }

    public static void getWeixiu(Context context, String phone, String str0) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phone", String.valueOf(phone));
        builder.add("tiaojian", String.valueOf(str0));
        builder.add("num", String.valueOf(2));
        FormBody formBody = builder.build();
        Client.getWeixiu(formBody);
    }

    public static void getpersoalinfo(Context context, int userId) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("id", String.valueOf(userId));
        FormBody formBody = builder.build();
        Client.getpersoalinfo(formBody);
    }

    public static void setpersonalinfo(String str0, int i, int userId) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("value", String.valueOf(str0));
        builder.add("num", String.valueOf(i));
        builder.add("id",String.valueOf(userId));
        FormBody formBody = builder.build();
        Client.setpersonalinfo(formBody);
    }

    public static void getFuwulist(Context context, String phone, int i) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phone", String.valueOf(phone));
        builder.add("num",String.valueOf(i));
        FormBody formBody = builder.build();
        Client.getFuwulist(formBody);
    }

    public static void getFuwulist(Context context, String phone, String toString, int i) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phone", String.valueOf(phone));
        builder.add("value",String.valueOf(toString));
        builder.add("num",String.valueOf(i));
        FormBody formBody = builder.build();
        Client.getFuwulist(formBody);
    }

    public static void getFuwu_list(Context context) {
        FormBody.Builder builder = new FormBody.Builder();
        FormBody formBody = builder.build();
        Client.getFuwu_list(formBody);
    }

    public static void getchuzuxiugai(Context context, int room_id) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("room_id", String.valueOf(room_id));
        FormBody formBody = builder.build();
        Client.getchuzuxiugai(formBody);
    }

    public static void setfuwu(String name, String phone) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("name", String.valueOf(name));
        builder.add("phone",String.valueOf(phone));
        FormBody formBody = builder.build();
        Client.setfuwu(formBody);
    }

    public static void getTousu(Context context, int userId,int num) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("user_id", String.valueOf(userId));
        builder.add("num", String.valueOf(num));
        FormBody formBody = builder.build();
        Client.getTousu(formBody);
    }

    public static void gettousu(Context context ,int id,int num) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("id", String.valueOf(id));
        builder.add("num", String.valueOf(num));
        FormBody formBody = builder.build();
        Client.getTousu1(formBody);
    }

    public static void getTousu(Context context, int userId, int num, String message) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("user_id", String.valueOf(userId));
        builder.add("num", String.valueOf(num));
        builder.add("tiaojian", message);
        FormBody formBody = builder.build();
        Client.getTousu(formBody);
    }

    public static void getZhangdan(int id, int num, int year, int month, int type) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("id", String.valueOf(id));
        builder.add("num", String.valueOf(num));
        builder.add("year",String.valueOf(year));
        builder.add("month", String.valueOf(month));
        builder.add("type",String.valueOf(type));
        FormBody formBody = builder.build();
        Client.getZhangdan(formBody);
    }

    public static void getroom(int roomid, int num) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("room_id", String.valueOf(roomid));
        builder.add("num", String.valueOf(num));
        FormBody formBody = builder.build();
        Client.getroom(formBody);
    }

    public static void getZufang(Context context) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("num", String.valueOf(0));
        FormBody formBody = builder.build();
        Client.getZufang(formBody);
    }

    public static void getroom_chuzu(int roomid) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("room_id", String.valueOf(roomid));
        builder.add("num", String.valueOf(1));
        FormBody formBody = builder.build();
        Client.getroom_chuzu(formBody);
    }

    public static void setjiaofei(int num, double money, int userId, String time) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("num", String.valueOf(num));
        builder.add("money", String.valueOf(money));
        builder.add("id", String.valueOf(userId));
        builder.add("time", String.valueOf(time));
        FormBody formBody = builder.build();
        Client.setjiaofei(formBody);
    }
}
