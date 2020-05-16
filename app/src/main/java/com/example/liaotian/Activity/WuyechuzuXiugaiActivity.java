package com.example.liaotian.Activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.example.liaotian.R;
import com.example.liaotian.View.IChuzuXiugaiView;
import com.example.liaotian.presenter.ChuzuXiugaiPresenter;
import com.example.liaotian.util.MessageEvent;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_chuzu_xiugai)
public class WuyechuzuXiugaiActivity extends BaseActivity implements IChuzuXiugaiView {

    @ViewInject(R.id.fragement_chuzu_toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.fragement_chuzu_title)
    TextView fragement_chuzu_title;
    @ViewInject(R.id.fragement_chuzu_num)
    TextView fragement_chuzu_num;
    @ViewInject(R.id.fragement_chuzu_money)
    EditText fragement_chuzu_money;
    @ViewInject(R.id.fragement_chuzu_phone)
    EditText fragement_chuzu_phone;
    @ViewInject(R.id.fragement_chuzu_jieshao)
    EditText fragement_chuzu_jieshao;
    @ViewInject(R.id.fragement_chuzu_address)
    EditText fragement_chuzu_address;
    @ViewInject(R.id.fragement_chuzu_submit)
    Button fragement_chuzu_submit;

    private int room_id;
    private int num;
    private ChuzuXiugaiPresenter chuzuXiugaiPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        room_id = getIntent().getIntExtra("room_id",0);
        num = getIntent().getIntExtra("num",0);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(v -> this.finish());
        init();
        chuzuXiugaiPresenter = new ChuzuXiugaiPresenter(this,this,getIntent().getIntExtra("room_id",0));
    }

    private void init(){
        if (num ==2){
            fragement_chuzu_address.setEnabled(false);
            fragement_chuzu_money.setEnabled(false);
            fragement_chuzu_jieshao.setEnabled(false);
            fragement_chuzu_phone.setEnabled(false);
            fragement_chuzu_submit.setText("联系一下");
            fragement_chuzu_title.setText("详情");
        }
    }

    @Override
    public void setmessage(JSONObject data) {
        try {
            fragement_chuzu_address.setText(data.getString("address"));
            fragement_chuzu_num.setText(data.getInt("look")+"");
            fragement_chuzu_money.setText(data.getString("money"));
            fragement_chuzu_jieshao.setText(data.getString("introduce"));
            fragement_chuzu_phone.setText(data.getString("phone"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void back() {
        this.finish();
    }

    @Override
    public void requestData(MessageEvent me) {
        super.requestData(me);
        chuzuXiugaiPresenter.dispose(me);
    }

    @Event(value = R.id.fragement_chuzu_submit)
    private void fragement_chuzu_submit(View view){
        if (num == 1){
            chuzuXiugaiPresenter.setchuzu(room_id,fragement_chuzu_address.getText().toString(),fragement_chuzu_money.getText().toString(),1,1,fragement_chuzu_jieshao.getText().toString(),fragement_chuzu_phone.getText().toString());
        }else if (num ==2){
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + fragement_chuzu_phone.getText().toString());
            intent.setData(data);
            startActivity(intent);
        }
    }
}
