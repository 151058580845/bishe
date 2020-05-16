package com.example.liaotian.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.liaotian.R;
import com.example.liaotian.View.IRegistView;
import com.example.liaotian.presenter.RegistPresenter;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.http.Function;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;


@ContentView(R.layout.activity_regist1)
public class RegistActivity extends BaseActivity implements IRegistView {

/*    @ViewInject(R.id.regist_l1)
    LinearLayout l1;*/
    @ViewInject(R.id.login_l3)
    LinearLayout l2;
    @ViewInject(R.id.regist_l3)
    LinearLayout l3;
    @ViewInject(R.id.regist_l4)
    LinearLayout l4;
    @ViewInject(R.id.regist_name)
    EditText regist_name;
    @ViewInject(R.id.regist_password)
    EditText regist_password;
    @ViewInject(R.id.regist_phone)
    EditText regist_phone;
    @ViewInject(R.id.regist_yanzhengma_ed)
    EditText regist_yanzhengma_ed;
    @ViewInject(R.id.regist_yanzhengma)
    Button regist_yanzhengma;
    @ViewInject(R.id.regist_zhuce)
    Button regist_zhuce;
    @ViewInject(R.id.regist_tijiao)
    Button regist_tijiao;
    @ViewInject(R.id.regist_back)
    TextView regist_back;
    @ViewInject(R.id.regist_shoujihao)
    TextView regist_shoujihao;
    @ViewInject(R.id.login_l1)
    LinearLayout login_l1;

    EventHandler eventHandler;
    private String phone;
    private RegistPresenter registPresenter;
    private boolean is_regist = false;
    private int status;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registPresenter = new RegistPresenter(this);

        eventHandler = new EventHandler(){
            /**
             * 在操作之后被触发
             * @param event  参数1
             * @param result 参数2 SMSSDK.RESULT_COMPLETE表示操作成功，为SMSSDK.RESULT_ERROR表示操作失败
             * @param data   事件操作的结果
             */

            @Override
            public void afterEvent(int event, int result, Object data) {

                Message message = myHandle.obtainMessage(0);
                message.arg1 = event;
                message.arg2 = result;
                message.obj = data;
                myHandle.sendMessage(message);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
        status = getIntent().getIntExtra("status",0);
        if (status==1){
            login_l1.setVisibility(View.INVISIBLE);
            regist_shoujihao.setVisibility(View.VISIBLE);
            regist_shoujihao.setText("手机号登录");
            regist_tijiao.setText("登录");
        }
        regist_tijiao.setClickable(false);
    }

    private boolean panduan(){
        phone = regist_phone.getText().toString();
        if (null == phone||"".equals(phone)||phone.length()!=11){
            Toast.makeText(this,"输入手机号错误",Toast.LENGTH_SHORT).show();
            return false;
        }
        String name = regist_name.getText().toString();
        if (null == name||"".equals(name)||name.length()>20){
            Toast.makeText(this,"输入名字错误",Toast.LENGTH_SHORT).show();
            return false;
        }
        String password = regist_password.getText().toString();
        if (null == password||"".equals(password)||password.length()>15||password.length()<6){
            Toast.makeText(this,"输入密码格式不正确错误",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Event(value = R.id.regist_zhuce)
    private void zhuce(View view){
        if (panduan()){
            phone = regist_phone.getText().toString();
            String name = regist_name.getText().toString();
            String password = regist_password.getText().toString();
            Function.regist(this,phone,name,password);
        }

    }

    @Event(value = R.id.regist_tijiao)
    private void tijiao(View view){
        String yanzhengma = regist_yanzhengma_ed.getText().toString();
        if (status == 1){
            Function.login_shoujihao(this,phone);
        }else {
            Function.is_regist(this,phone);
        }

        if (null!=yanzhengma && yanzhengma.length() == 4){
            SMSSDK.submitVerificationCode("86",phone,yanzhengma);
        }else {
            Toast.makeText(this,"验证码错误",Toast.LENGTH_SHORT).show();
        }
    }

    @Event(value = R.id.regist_yanzhengma)
    private void yanzhengma(View view){
        regist_tijiao.setClickable(true);
        Log.e("222222222222", "regist: "+"222222222" );
        phone = regist_phone.getText().toString();
        if (null == phone||"".equals(phone)||phone.length()!=11){
            Toast.makeText(this,"输入手机号错误",Toast.LENGTH_SHORT).show();
            return;
        }
        regist_yanzhengma.setClickable(false);
        SMSSDK.getVerificationCode("86",phone);
        new Thread(){
            @Override
            public void run() {
                int totalTime = 60;
                for (int i =0;i<totalTime;i++){
                    Message message = myHandle.obtainMessage(1);
                    message.arg1 = totalTime-i;
                    myHandle.sendMessage(message);
                    try{
                        sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                myHandle.sendEmptyMessage(2);
            }
        }.start();
    }

    @Event(value = R.id.regist_login)
    private void regist_login(View view){
        startActivity(new Intent(this,LoginActivity.class));
    }

    Handler myHandle = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what)
            {
                case 0:
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    Log.e("222222222", "handleMessage: "+event+result+data );
                    if (result == SMSSDK.RESULT_COMPLETE){
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                            Toast.makeText(RegistActivity.this,"验证码发送成功",Toast.LENGTH_SHORT).show();
                        }else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                            if (status!=1){
                                if (is_regist){
                                    regist_back.setVisibility(View.VISIBLE);
                                    regist_back.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            RegistActivity.this.finish();
                                        }
                                    });
                                }else {
                                    regist_back.setVisibility(View.GONE);
                                    Toast.makeText(RegistActivity.this,"验证成功",Toast.LENGTH_SHORT).show();
                                    l2.setVisibility(View.GONE);
                                    l3.setVisibility(View.VISIBLE);
                                    l4.setVisibility(View.VISIBLE);
                                    regist_tijiao.setVisibility(View.GONE);
                                    regist_zhuce.setVisibility(View.VISIBLE);
                                    is_regist = false;
                                }
                            }
                        }
                    }
                    break;
                case 1:
                    regist_yanzhengma.setText("重新发送（"+msg.arg1+")");
                    break;
                case 2:
                    regist_yanzhengma.setText("获取验证码");
                    regist_yanzhengma.setClickable(true);
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    @Override
    public void requestData(MessageEvent me) {
        super.requestData(me);
        registPresenter.dispose(me);
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(RegistActivity.this,s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setis_regist(boolean boolen) {
        this.is_regist = boolen;
    }

    @Override
    public void forwordLogin() {
        RegistActivity.this.finish();
    }

    @Override
    public void forwordMain() {
        Intent intent = new Intent(RegistActivity.this,MainActivity.class);
        startActivity(intent);
        onDestroy();
    }
}
