package com.example.liaotian.View;

import com.example.liaotian.entity.Tousu;
import com.example.liaotian.entity.Weixiu;

import java.util.ArrayList;

public interface ITousuView {
    void setl(ArrayList<Tousu> list);

    void setlist(ArrayList<Tousu> list);
    void showToast(String message);
    void addphoto();

    void setmessage(String date);
    void back();
}
