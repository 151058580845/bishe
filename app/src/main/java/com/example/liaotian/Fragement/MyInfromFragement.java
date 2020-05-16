package com.example.liaotian.Fragement;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.liaotian.Activity.LoginActivity;
import com.example.liaotian.Activity.PersonalInfoActivity;
import com.example.liaotian.R;
import com.example.liaotian.View.IMainFragementView;
import com.example.liaotian.View.IMainView;
import com.example.liaotian.View.IMyInfromView;
import com.example.liaotian.presenter.MainFragementPresenter;
import com.example.liaotian.presenter.MyInfromPresenter;
import com.example.liaotian.util.Contants;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.SPUtils;
import com.example.liaotian.util.http.Function;
import com.xq.fasterdialog.bean.InputBean;
import com.xq.fasterdialog.dialog.EditDialog;
import com.xq.fasterdialog.dialog.base.BaseDialog;
import com.xq.fasterdialog.dialog.base.BaseEditDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.lym.image.select.imageloader.GlideImageLoader;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

@ContentView(R.layout.fragement_personal_center)
public class MyInfromFragement extends BaseFragement implements ScreenShotable, IMyInfromView {

    @ViewInject(R.id.tb_personal_center_name)
    TextView tb_personal_center_name;
    @ViewInject(R.id.tb_personal_center_sex)
    TextView tb_personal_center_sex;
    @ViewInject(R.id.tb_personal_center_birthday)
    TextView tb_personal_center_birthday;

    private Context context;
    private MyInfromPresenter myInfromPresenter;
    private IMainView iMainView;

    public MyInfromFragement(){}

    public MyInfromFragement(Context context, IMainView iMainView){
        this.context = context;
        this.iMainView = iMainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myInfromPresenter = new MyInfromPresenter(context,this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this,inflater,container);
    }


    @Event(R.id.myinfrom_touxiang)
    private void touxiang(View view){
//        iMainView.openpictyre();
    }

    @Event(R.id.cl_personal_info)
    private void personal_info(View view){
        context.startActivity(new Intent(getActivity(), PersonalInfoActivity.class));
    }

    @Event(R.id.cl_personal_pw)
    private void re_password(View view){
        new EditDialog(context)
                //设置你自己的布局方案，以下为高定制化布局
                .setCustomView(R.layout.dialog_re_password)
                //Meterail布局样式
                .setMeterailLayoutStyle()
                //标准自定义样式
                .setXQLayoutStyle()
                //EditDialog天生支持任意数量个EditText，因此可以设置多个InputBean，每个InputBean都代表对应EditText的具体内容，切记如果不设置InputBean将会导致对应的EditText无法显示
                .setInputBean0(new InputBean("请输入原密码"))
                .setInputBean1(new InputBean("请输入新密码"))
                .setInputBean(2,new InputBean("请重复新密码",null,null,10, InputType.TYPE_CLASS_NUMBER))
                //输入完成回调
                .setPositiveText("确认")
                .setPositiveListener(new BaseEditDialog.OnEditCompletedListener() {
                    @Override
                    public void onEditCompleted(BaseEditDialog dialog, SparseArray<CharSequence> array) {
                        String str0 = array.get(0).toString();
                        String str1 = array.get(1).toString();
                        String str2 = array.get(2).toString();
                        if (myInfromPresenter.password_is(str0)){
                            if (str1.equals(str2)){
                                myInfromPresenter.setpassword(str1);
                                Toast.makeText(context,"修改成功",Toast.LENGTH_SHORT).show();
                            }else
                                Toast.makeText(context,"两次密码输入不一致",Toast.LENGTH_SHORT).show();
                        }else
                        Toast.makeText(context,"原密码错误",Toast.LENGTH_SHORT).show();
                    }
                })
                .setTitle("密码重置")
                .show();
    }

    @Event(R.id.loguot)
    private void loguot(View view){
        SPUtils.put(context, Contants.login_success,0);
        startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public void onResume() {
        super.onResume();
        myInfromPresenter.onResume();
    }

    @Override
    public void requestData(MessageEvent me) {
        super.requestData(me);
        myInfromPresenter.dispose(me);
    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

    @Override
    public void setpersonalinfo(JSONObject json) {
        try {
            tb_personal_center_name.setText(json.getString("name"));
            tb_personal_center_sex.setText(json.getString("sex"));
            tb_personal_center_birthday.setText(json.getString("birth"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
