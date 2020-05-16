package com.example.liaotian.View;

import com.example.liaotian.entity.Fangwu;
import com.example.liaotian.entity.Weixiu;

import java.util.ArrayList;

public interface IKuaidiView {
    void setlist(ArrayList<Fangwu> list);
    void showToast(String message);
}
