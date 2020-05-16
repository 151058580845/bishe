package com.example.liaotian.View;

import com.example.liaotian.entity.Fangwu;

import org.json.JSONObject;

import java.util.ArrayList;

public interface IMyRoomView {
    void setmessage(JSONObject json);
    void showToast(String message);

    void setList(JSONObject json);
}
