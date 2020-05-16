package com.example.liaotian;

import android.app.Application;

import com.example.liaotian.entity.MyMigration.MyMigration;
import com.luck.picture.lib.app.PictureAppMaster;
import com.mob.MobSDK;

import org.xutils.x;

import java.io.FileNotFoundException;
import java.security.SecureRandom;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;

public class App extends Application {
    private static App app;
    private static RealmConfiguration config;

    @Override
    public void onCreate(){

        super.onCreate();
        x.Ext.init(this);       //xutilss初始化
        x.Ext.setDebug(true);       //开启debug日志
        MobSDK.init(this);      //开启短信服务


    //    MultiDex.install(this);
        Realm.init(this);       //开启Realm数据库
/*        RealmConfiguration config0 = new RealmConfiguration.Builder()
                .name("default.realm")
                .schemaVersion(1)
                .migration(new MyMigration())
                .build();*/
   }
}
