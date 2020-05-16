package com.example.liaotian.View;

import android.content.Context;

import com.example.liaotian.entity.Weixiu;

import java.util.ArrayList;

public interface IBaoxiuView {
    void setlist(ArrayList<Weixiu> list);
    void showToast(String message);
}
