package com.example.liaotian;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.liaotian.Activity.MainActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ttt extends AppCompatActivity {
    public LocationClient mLocationClient ;
    public BDAbstractLocationListener myListener = new MyLocationListener();
    public LocationClientOption option;
    public TextView tv_show;
    public String city="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

   /*     long time = System.currentTimeMillis();
        Log.e("2222222222", "onCreate: "+time );*/

        int num = 20;
        int baoshijiage = 10;
        int nu1 =5;
        int y = 5;
        int money = num*baoshijiage*nu1*y;
        Log.e("2222222", "onCreate: "+money );

       /* getView();
        //mLocationClient为第二步初始化过的LocationClient对象
        doLocation();
        //调用LocationClient的start()方法，便可发起定位请求
        if (ContextCompat.checkSelfPermission(ttt.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
            // 开启定位权限,200是标识码
            ActivityCompat.requestPermissions(ttt.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        } else {
            Toast.makeText(ttt.this, "已开启定位权限", Toast.LENGTH_SHORT).show();
            mLocationClient.start();//开始定位
        }*/
    }

    public void doLocation() {
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        initLocation();
    }

    public void initLocation() {
        option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 200://刚才的识别码
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//用户同意权限,执行我们的操作
                    mLocationClient.start();//开始定位
                } else {
                    // 用户拒绝之后
                    Toast.makeText(ttt.this, "未开启定位权限,请手动到设置去开启权限", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            city = location.getCity();    //获取城市

            Toast.makeText(ttt.this, city, Toast.LENGTH_SHORT).show();
            tv_show.setText(city+location.getStreet()+location.getTown()+location.getCityCode()+location.getDistrict());
        }
    }

    //控件获取
    public void getView() {
        tv_show = findViewById(R.id.ttt);
    }
}
