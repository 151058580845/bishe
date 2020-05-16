package com.example.liaotian.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.liaotian.R;
import com.example.liaotian.View.ILoginView;
import com.example.liaotian.presenter.LoginPresenter;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.http.Function;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tandong.swichlayout.SwichLayoutInterFace;
import com.tandong.swichlayout.SwitchLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


@ContentView(R.layout.actiivity_login)
public class LoginActivity extends BaseActivity implements ILoginView, SwichLayoutInterFace {

    @ViewInject(R.id.login_duanxin)
    TextView login_duanxin;
    @ViewInject(R.id.login_phone)
    MaterialEditText login_phone;
    @ViewInject(R.id.login_password)
    MaterialEditText login_password;

    private Intent intent;
    private int status;
    private LoginPresenter loginPresenter;
    public LocationClient mLocationClient ;
    public BDAbstractLocationListener myListener = new MyLocationListener();
    public LocationClientOption option;


    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
   //     setEnterSwichLayout();
        loginPresenter = new LoginPresenter(this,this);
        down();
        doLocation();
        //调用LocationClient的start()方法，便可发起定位请求
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
            // 开启定位权限,200是标识码
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        } else {
//            Toast.makeText(this, "已开启定位权限", Toast.LENGTH_SHORT).show();
            mLocationClient.start();//开始定位
        }
    }

    public void doLocation() {
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        initLocation();
    }

    @Event(value = R.id.login_register)
    private void login_register(View view){
        startActivity(new Intent(this,RegistActivity.class));
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
                    Toast.makeText(LoginActivity.this, "未开启定位权限,请手动到设置去开启权限", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
//            Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
        }
    }

    private void down(){
        login_duanxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forward(1);
            }
        });
    }

    @Event(value = R.id.login_login)
    private void login(View view){
        String phone = login_phone.getText().toString();
        String password = login_password.getText().toString();
//        Toast.makeText(this,"aaaaaaaaaa",Toast.LENGTH_SHORT).show();
        if ("".equals(phone)){
            showToast("手机号为空，请输入手机号");
        } else if ("".equals(password)){
            showToast("密码为空，请输入密码");
        }else {
            Function.login(this,phone,password);
        }

    }

    @Event(value = R.id.login_regist)
    private void forword_regist(View view){
        forward(0);
    }

    private void forward(int i){
        intent = new Intent(this,RegistActivity.class);
        intent.putExtra("status",i);
        startActivity(intent);
    }

    @Override
    public void forwordMain() {
        intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
       // onDestroy();
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void requestData(MessageEvent me) {
        super.requestData(me);
//        Log.e("3333333333", "requestData: ");
        loginPresenter.dispose(me);
    }

    @Override
    public void setEnterSwichLayout() {
     //   SwitchLayout.getSlideFromBottom(this,false, BaseEffects.getMoreSlowEffect());
        SwitchLayout.get3DRotateFromLeft(this,false,null);
    }

    @Override
    public void setExitSwichLayout() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.onDestroy();
    }
}
