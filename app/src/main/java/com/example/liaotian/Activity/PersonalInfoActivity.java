package com.example.liaotian.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.liaotian.R;
import com.example.liaotian.View.IPersonalInfoView;
import com.example.liaotian.presenter.PersonalInfoPresenter;
import com.example.liaotian.util.MessageEvent;
import com.xq.fasterdialog.bean.InputBean;
import com.xq.fasterdialog.dialog.EditDialog;
import com.xq.fasterdialog.dialog.base.BaseEditDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@ContentView(R.layout.activity_personinfrom)
public class PersonalInfoActivity extends BaseActivity implements IPersonalInfoView {

    @ViewInject(R.id.tb_personal_info)
    Toolbar toolbar;
    @ViewInject(R.id.tv_personal_info_name)
    TextView tv_personal_info_name;
    @ViewInject(R.id.tv_personal_info_gender)
    TextView tv_personal_info_gender;
    @ViewInject(R.id.tv_personal_info_height)
    TextView tv_personal_info_height;
    @ViewInject(R.id.tv_personal_info_weight)
    TextView tv_personal_info_weight;
    @ViewInject(R.id.tv_personal_info_birthday)
    TextView tv_personal_info_birthday;

    private TimePickerView pvTime;
    private PersonalInfoPresenter personalInfoPresenter;
    private OptionsPickerView sexOptions,heightOptions,weightOptions;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(v -> this.finish());

        personalInfoPresenter = new PersonalInfoPresenter(this,this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        personalInfoPresenter.onResume();
    }

    @Override
    public void requestData(MessageEvent me) {
        super.requestData(me);
        personalInfoPresenter.dispose(me);
    }

    @Event(R.id.cl_personal_info_name)
    private void cl_personal_info_name(View view){
        new EditDialog(this)
                //设置你自己的布局方案，以下为高定制化布局
                .setCustomView(R.layout.dialog_re_password)
                //Meterail布局样式
                .setMeterailLayoutStyle()
                //标准自定义样式
                .setXQLayoutStyle()
                //EditDialog天生支持任意数量个EditText，因此可以设置多个InputBean，每个InputBean都代表对应EditText的具体内容，切记如果不设置InputBean将会导致对应的EditText无法显示
                .setInputBean0(new InputBean("输入昵称"))//输入完成回调
                .setWidthPercent(0.8f)
                .setPositiveText("修改")
                .setPositiveListener(new BaseEditDialog.OnEditCompletedListener() {
                    @Override
                    public void onEditCompleted(BaseEditDialog dialog, SparseArray<CharSequence> array) {
                        String str0 = array.get(0).toString();
                        personalInfoPresenter.setpersonalinfo(str0,1);
                    }
                })
                .setTitle("修改昵称")
                .show();
    }
    @Event(R.id.cl_personal_info_gender)
    private void cl_personal_info_gender(View view){
        sex();
        sexOptions.show();
    }

    @Event(R.id.cl_personal_info_height)
    private void cl_personal_info_height(View view){
        height();
        heightOptions.show();
    }
    @Event(R.id.cl_personal_info_weight)
    private void cl_personal_info_weight(View view){
        initWeight();
        weightOptions.show();
    }
    @Event(R.id.cl_personal_info_birthday)
    private void cl_personal_info_birthday(View view){
        initBirth();
        pvTime.show();
    }

    private void sex() {
        ArrayList<String> options1Items = new ArrayList<>();
        options1Items.add("男");
        options1Items.add("女");

        sexOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
//                personalInfoPresenter.changeSex(options1);
                personalInfoPresenter.setpersonalinfo(options1Items.get(options1),2);
            }
        })
                .setCancelText("取消")
                .setSubmitText("确定")
                .setTitleText("性别选择")
                .setContentTextSize(20)
                .build();
        sexOptions.setPicker(options1Items);
    }

    private void height(){
        ArrayList<Integer> options1Items = new ArrayList<>();
        for (int i = 60;i<210;i++){
            options1Items.add(i);
        }
        heightOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                personalInfoPresenter.setpersonalinfo(options1Items.get(options1)+"",3);
            }
        })
                .setSubmitText("确定")
                .setCancelText("取消")
                .build();
        heightOptions.setPicker(options1Items);
    }
    private void initWeight()
    {
        ArrayList<Integer> options1Items = new ArrayList<>();
        for (int i = 20;i<150;i++){
            options1Items.add(i);
        }
        weightOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                personalInfoPresenter.setpersonalinfo(options1Items.get(options1)+"",4);

            }
        })
                .setSubmitText("确定")
                .setCancelText("取消")
                .build();
        weightOptions.setPicker(options1Items);
    }

    private void initBirth()
    {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH));
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int month = calendar.get(Calendar.MONTH)+1;
                personalInfoPresenter.setpersonalinfo(calendar.get(Calendar.YEAR)+"-"+month+"-"+calendar.get(Calendar.DAY_OF_MONTH),5);
                // Toast.makeText(MainActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                Log.i("pvTime", "onTimeSelect");
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate,endDate)
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("pvTime", "onCancelClickListener");
                    }
                })
                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(2.0f)
                .isAlphaGradient(true)
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.3f);
            }
        }
    }


    @Override
    public void setpersonalinfo(JSONObject jsonObject) {
        try {
            tv_personal_info_name.setText(jsonObject.getString("name"));
            tv_personal_info_gender.setText(jsonObject.getString("sex"));
            tv_personal_info_height.setText(jsonObject.getString("height"));
            tv_personal_info_weight.setText(jsonObject.getString("weight"));
            tv_personal_info_birthday.setText(jsonObject.getString("birth"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showtoast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
