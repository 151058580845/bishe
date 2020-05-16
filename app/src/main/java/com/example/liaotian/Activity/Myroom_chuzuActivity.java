package com.example.liaotian.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.liaotian.R;
import com.example.liaotian.View.IMyRoomView;
import com.example.liaotian.presenter.MyroomPresenter;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.http.Client;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import me.shihao.library.XRadioGroup;
import okhttp3.FormBody;


@ContentView(R.layout.actiivity_myfangwu)
public class Myroom_chuzuActivity extends BaseActivity implements IMyRoomView {

    @ViewInject(R.id.myfangwu_toolbar)
    Toolbar myfanfwu_toolbar;
    @ViewInject(R.id.addfuwu_title)
    TextView addfuwu_title;
    @ViewInject(R.id.myfangwu_address)
    TextView myfangwu_address;
    @ViewInject(R.id.myfangwu_shi)
    TextView myfangwu_shi;
    @ViewInject(R.id.myfangwu_jiage)
    TextView myfangwu_jiage;
    @ViewInject(R.id.myfangwu_c1)
    ConstraintLayout myfangwu_c1;
    @ViewInject(R.id.myfangwu_c2)
    ConstraintLayout myfangwu_c2;
    @ViewInject(R.id.myfangwu_c4)
    ConstraintLayout myfangwu_c4;
    @ViewInject(R.id.myfangwu_c1_t)
    TextView myfangwu_c1_t;
    @ViewInject(R.id.myfangwu_zujin)
    TextView myfangwu_zujin;


    private MyroomPresenter myroomPresenter;
    private int roomid = 0;
    private int is_chuzu = 0;
    private String chaoxiang = "";
    private String fangshi = "";
    private String zujin = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addfuwu_title.setText("房子详情");
        myfanfwu_toolbar.setTitle("");
        myfanfwu_toolbar.setNavigationIcon(R.drawable.back);
        myfanfwu_toolbar.setNavigationOnClickListener((v -> this.finish()));
        roomid = getIntent().getIntExtra("room_id",0);
        myroomPresenter = new MyroomPresenter(this,this);
//        myfangwu_c1.setVisibility(View.GONE);
        myfangwu_c1_t.setText("联系房东");
        myfangwu_c2.setVisibility(View.GONE);
        myfangwu_c4.setVisibility(View.GONE);
        myfangwu_zujin.setVisibility(View.VISIBLE);
    }

    private String phone = "110";
    @Override
    protected void onResume() {
        super.onResume();
        myroomPresenter.getroom_chuzu(roomid,0);
    }

    @Override
    public void requestData(MessageEvent me) {
        super.requestData(me);
        myroomPresenter.dispose(me);
    }

    @Event(value = R.id.myfangwu_c1)
    private void myfangwu_c1(View view){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    public void setmessage(JSONObject json) {

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setList(JSONObject json) {
        try {
            phone = json.getString("phone");
            myfangwu_shi.setText(json.getString("tingshi"));
            myfangwu_address.setText(json.getString("address"));
            myfangwu_jiage.setText(json.getString("zujin")+"元/月");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
