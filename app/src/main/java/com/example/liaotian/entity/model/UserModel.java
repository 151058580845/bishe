package com.example.liaotian.entity.model;

import com.example.liaotian.entity.User;

import io.realm.Realm;

public class UserModel {

    private Realm realm;

    public UserModel(Realm realm) {
        this.realm = realm;
    }

    //清空
    //每次更新数据前先清空
    public void delectAll1(){
        realm.beginTransaction();
        realm.delete(User.class);
        realm.commitTransaction();
    }

    public void saveUser(User user){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }
}
