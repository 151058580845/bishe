package com.example.liaotian.View;

import com.alibaba.fastjson.JSONArray;
import com.example.liaotian.entity.Fangwu;

import java.util.ArrayList;
import java.util.List;

public interface IZufanfView {
    void setList(ArrayList<Fangwu> list);
    void show(String message);
}
