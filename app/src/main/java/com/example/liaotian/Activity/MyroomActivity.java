package com.example.liaotian.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.afollestad.materialdialogs.MaterialDialog;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.liaotian.R;
import com.example.liaotian.View.ILoginView;
import com.example.liaotian.View.IMyRoomView;
import com.example.liaotian.entity.Weixiu;
import com.example.liaotian.presenter.LoginPresenter;
import com.example.liaotian.presenter.MyroomPresenter;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.http.Client;
import com.example.liaotian.util.http.Function;
import com.tandong.swichlayout.SwichLayoutInterFace;
import com.tandong.swichlayout.SwitchLayout;
import com.xq.fasterdialog.dialog.NormalDialog;
import com.xq.fasterdialog.dialog.base.BaseDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import me.shihao.library.XRadioGroup;
import okhttp3.FormBody;


@ContentView(R.layout.actiivity_myfangwu)
public class MyroomActivity extends BaseActivity implements IMyRoomView {

    @ViewInject(R.id.myfangwu_toolbar)
    Toolbar myfanfwu_toolbar;
    @ViewInject(R.id.myfangwu_address)
    TextView myfangwu_address;
    @ViewInject(R.id.myfangwu_shi)
    TextView myfangwu_shi;
    @ViewInject(R.id.myfangwu_jiage)
    TextView myfangwu_jiage;

    private MyroomPresenter myroomPresenter;
    private int roomid = 0;
    private int is_chuzu = 0;
    private String chaoxiang = "";
    private String fangshi = "";
    private String zujin = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myfanfwu_toolbar.setTitle("");
        myfanfwu_toolbar.setNavigationIcon(R.drawable.back);
        myfanfwu_toolbar.setNavigationOnClickListener((v -> this.finish()));
        roomid = getIntent().getIntExtra("room_id",0);
        myroomPresenter = new MyroomPresenter(this,this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        myroomPresenter.getroom(roomid,0);
    }

    @Event(value = R.id.myfangwu_c1)
    private void myfangwu_c1(View view){
        show1();
    }

    @Event(value = R.id.myfangwu_c2)
    private void myfangwu_c2(View view){
        if (is_chuzu == 0){
//            l.setVisibility(View.GONE);
            showToast("请先出租在修改定价");
        }else {
            show2();
        }

    }
    @Event(value = R.id.myfangwu_c4)
    private void myfangwu_c4(View view){
        if (is_chuzu == 1){
//            l.setVisibility(View.GONE);
            showToast("该房间已出租，请勿重复出租");
        }else {
            show3();
        }
    }

    private void show1() {
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_fangyuan, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        LinearLayout l = bottomDialog.getWindow().findViewById(R.id.dialog_fangyuan_c1);
        RadioButton radioButton;
        if (is_chuzu == 0){
            l.setVisibility(View.GONE);
        }else {
            switch (fangshi){
                case "合租":
                    radioButton = bottomDialog.getWindow().findViewById(R.id.dialog_fangyuan_b2);
                    radioButton.setChecked(true);
                    break;
                case "整租":
                    radioButton = bottomDialog.getWindow().findViewById(R.id.dialog_fangyuan_b1);
                    radioButton.setChecked(true);
                    break;
            }
        }
        switch (chaoxiang){
            case "东":
                radioButton = bottomDialog.getWindow().findViewById(R.id.dialog_fangyuan_b3);
                radioButton.setChecked(true);
                break;
            case "南":
                radioButton = bottomDialog.getWindow().findViewById(R.id.dialog_fangyuan_b4);
                radioButton.setChecked(true);
                break;
            case "西":
                radioButton = bottomDialog.getWindow().findViewById(R.id.dialog_fangyuan_b5);
                radioButton.setChecked(true);
                break;
            case "北":
                radioButton = bottomDialog.getWindow().findViewById(R.id.dialog_fangyuan_b6);
                radioButton.setChecked(true);
                break;
        }
        bottomDialog.getWindow().findViewById(R.id.dialog_fangyuan_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormBody.Builder builder = new FormBody.Builder();
                XRadioGroup radioGroup= bottomDialog.getWindow().findViewById(R.id.dialog_fangyuan_r1);
                builder.add("num", String.valueOf(4));
                if (is_chuzu != 0) {
                    switch (radioGroup.getCheckedRadioButtonId()) {
                        case R.id.dialog_fangyuan_b1:
                            builder.add("fangshi", "整租");
                            break;
                        case R.id.dialog_fangyuan_b2:
                            builder.add("fangshi", "合租");
                            break;
                    }
                    builder.add("num", String.valueOf(3));
                }
                radioGroup= bottomDialog.getWindow().findViewById(R.id.dialog_fangyuan_r2);
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.dialog_fangyuan_b3:
                        builder.add("chaoxiang", "东");
                        break;
                    case R.id.dialog_fangyuan_b4:
                        builder.add("chaoxiang", "南");
                        break;
                    case R.id.dialog_fangyuan_b5:
                        builder.add("chaoxiang", "西");
                        break;
                    case R.id.dialog_fangyuan_b6:
                        builder.add("chaoxiang", "北");
                        break;
                }

                builder.add("room_id", String.valueOf(roomid));
                FormBody formBody = builder.build();
//                if (is_chuzu == 0){
//                    Client.setfangyuan(formBody);
//                }else {
                    Client.setChuzu(formBody);
//                }
                bottomDialog.dismiss();
            }
        });
    }

    private void show2() {
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_dingjia, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        LinearLayout l = bottomDialog.getWindow().findViewById(R.id.dialog_fangyuan_l1);
        TextView textView = bottomDialog.getWindow().findViewById(R.id.dialog_fangyuan_j1);
        textView.setText(zujin);
        bottomDialog.getWindow().findViewById(R.id.dialog_fangyuan_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText= bottomDialog.getWindow().findViewById(R.id.dialog_fangyuan_j2);
                FormBody.Builder builder = new FormBody.Builder();
                XRadioGroup radioGroup= bottomDialog.getWindow().findViewById(R.id.dialog_fangyuan_r1);
                builder.add("num", String.valueOf(5));
                builder.add("zujin", editText.getText().toString());
                builder.add("room_id", String.valueOf(roomid));
                FormBody formBody = builder.build();
                Client.setChuzu(formBody);
                bottomDialog.dismiss();
            }
        });
    }


    private void show3() {
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_chuzu, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
        bottomDialog.getWindow().findViewById(R.id.dialog_fangyuan_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormBody.Builder builder = new FormBody.Builder();
                EditText editText= bottomDialog.getWindow().findViewById(R.id.dialog_fangyuan_j1);
                if (!"".equals(editText)){
                    builder.add("zujin",editText.getText().toString());
                }
                editText= bottomDialog.getWindow().findViewById(R.id.dialog_fangyuan_j2);
                if (!"".equals(editText)){
                    builder.add("introduce",editText.getText().toString());
                }
                editText= bottomDialog.getWindow().findViewById(R.id.dialog_fangyuan_j3);
                if (!"".equals(editText)){
                    builder.add("phone",editText.getText().toString());
                }
                builder.add("num", String.valueOf(6));
                builder.add("room_id", String.valueOf(roomid));
                myroomPresenter.setchuzu(builder);
                bottomDialog.dismiss();
            }
        });

    }

    @Override
    public void requestData(MessageEvent me) {
        super.requestData(me);
        myroomPresenter.dispose(me);
    }

    @Override
    public void setmessage(JSONObject json) {
        try {
            myfangwu_address.setText(json.getString("address"));
            myfangwu_shi.setText(json.getString("tingshi"));
            myfangwu_jiage.setText(json.getString("money"));
            is_chuzu = json.getInt("is_chuzu");
            chaoxiang = json.getString("chaoxiang");
            if (is_chuzu!=0){
                fangshi = json.getString("fangshi");
                zujin = json.getString("zujin");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setList(JSONObject json) {

    }
}
