package com.example.liaotian.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PublicFunction {

    /**
     * 将json格式的字符串转成Map对象
     */
    public static Map<String, String> jsonToHashMap(JSONObject jsonObject)
    {
        Map<String, String> map= new HashMap<String, String>();
        Iterator it = jsonObject.keys();
        // 遍历jsonObject数据，添加到Map对象
        while (it.hasNext())
        {
            String key = String.valueOf(it.next());
            //注意：这里获取value使用的是optString
            // optString 和getString的区别：单来说就是optString会在得不到你想要的值时候返回空字符串”“，而getString会抛出异常。
            String value = (String) jsonObject.optString(key);
            map.put(key, value);
        }
        return map;
    }



    /**
     * @Summary 获取一个json对象
     * @param JsonString 一个json格式的字符串
     * @return 正常返回一个json对象 异常返回 null
     */
    public static synchronized JSONObject getJsonObject(String JsonString)
    {
        JSONObject jsonObject = null;
        try {
            JsonString = getJsonStrFromNetData(JsonString);
            JSONArray entries = new JSONArray(JsonString);
            if(entries.length() > 0 )
            {
                jsonObject = entries.getJSONObject(0);
            }
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Summary 获取json 对象的数组
     * @param JsonString  json格式的字符串
     * @return  返回Json 对象的数组
     */
    public static synchronized List<JSONObject> getJsonObjects(String JsonString)
    {
        JsonString = getJsonStrFromNetData(JsonString);
        ArrayList<JSONObject> array = new ArrayList<JSONObject>();
        try {
            JSONArray entries = new JSONArray(JsonString);
            for (int i = 0; i < entries.length(); i++) {
                JSONObject jsObject = entries.getJSONObject(i);
                if(jsObject != null)
                {
                    array.add(jsObject);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    /***
     * @summary 通过json字符串获取 实体对象
     * @param jsonstr Json 字符串
     * @param tClass 实体的类型
     * @return 实体数组
     */
    public static synchronized <T> ArrayList<T> getEntityFromJson(String jsonstr,Class<T> tClass){
        try {
            jsonstr = getJsonStrFromNetData(jsonstr);
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<JsonObject>>(){}.getType();

            ArrayList<JsonObject> jsonObjects = gson.fromJson(jsonstr,type);
            ArrayList<T> list = new ArrayList<T>();
            for (JsonObject obj:jsonObjects
                 ) {
                list.add(new Gson().fromJson(obj,tClass));
            }
            return list;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * @summary 去除非Json的字符串部分
     * @param jsonstr 包含Json字符串的数据
     * @return json字符串
     */
    private static String getJsonStrFromNetData(String jsonstr) {
        int first = jsonstr.indexOf("[");
        int last = jsonstr.lastIndexOf("]");
        String resulr = "";
        if (last>first){
            resulr = jsonstr.substring(first,last+1);
        }
        return resulr;
    }
}
