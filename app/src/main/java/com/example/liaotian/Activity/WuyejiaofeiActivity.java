package com.example.liaotian.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.example.liaotian.R;
import com.example.liaotian.View.IJiaofeiView;
import com.example.liaotian.presenter.WuyejiaofeiPresenter;
import com.example.liaotian.util.Contants;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.http.Function;
import com.example.liaotian.util.zhifubao.AuthResult;
import com.example.liaotian.util.zhifubao.PayResult;
import com.example.liaotian.util.zhifubao.util.OrderInfoUtil2_0;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Map;

import static com.example.liaotian.util.Contants.APPID;
import static com.example.liaotian.util.Contants.RSA2_PRIVATE;

@ContentView(R.layout.activity_jiaofei)
public class WuyejiaofeiActivity extends BaseActivity implements IJiaofeiView {

    @ViewInject(R.id.jiaofei_toolbar)
    Toolbar jiaofei_toolbar;
    @ViewInject(R.id.jiaofei_dianfei_money)
    TextView jiaofei_dianfei_money;
    @ViewInject(R.id.jiaofei_shuifei_money)
    TextView jiaofei_shuifei_money;
    @ViewInject(R.id.jiaofei_wuye_money)
    TextView jiaofei_wuye_money;
    @ViewInject(R.id.jiaofei_time)
    TextView jiaofei_time;
    @ViewInject(R.id.jiaofei_c1)
    ConstraintLayout jiaofei_c1;
    @ViewInject(R.id.jiaofei_c2)
    ConstraintLayout jiaofei_c2;
    @ViewInject(R.id.jiaofei_c3)
    ConstraintLayout jiaofei_c3;

    private WuyejiaofeiPresenter wuyejiaofeiPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        jiaofei_toolbar.setTitle("");
        jiaofei_toolbar.setNavigationIcon(R.drawable.back);
        jiaofei_toolbar.setNavigationOnClickListener((v -> this.finish()));

        wuyejiaofeiPresenter = new WuyejiaofeiPresenter(this,this);
        wuyejiaofeiPresenter.getPay();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        show("成功" + payResult);
                        show("缴费成功");
                        switch (msg.arg1){
                            case 1:
                                jiaofei_c1.setVisibility(View.GONE);
                                try {
                                    wuyejiaofeiPresenter.setjiaofei(0,Double.parseDouble(json.getString("dian")),json.getString("time"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 2:
                                jiaofei_c2.setVisibility(View.GONE);
                                try {
                                    wuyejiaofeiPresenter.setjiaofei(1,Double.parseDouble(json.getString("shui")),json.getString("time"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 3:
                                jiaofei_c3.setVisibility(View.GONE);
                                try {
                                    wuyejiaofeiPresenter.setjiaofei(2,Double.parseDouble(json.getString("wuye")),json.getString("time"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        show("失败" + payResult);
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        show("成功" + authResult);
                    } else {
                        // 其他状态值则为授权失败
                        show("失败" + authResult);
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    /**
     * 支付宝支付业务示例
     */
    public void payV2(View v,double jine,String name) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE))) {
            show("成功");
            return;
        }

        /*
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo 的获取必须来自服务端；
         */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2,jine,name);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = RSA2_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(WuyejiaofeiActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());
                int num = 0;
                switch (name){
                    case "水费":
                        num = 1;
                        break;
                    case "电费":
                        num = 2;
                        break;
                    case "物业费":
                        num = 3;
                        break;
                }
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.arg1 = num;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    private JSONObject json = new JSONObject();
    @Override
    public void setshuidian(JSONObject jsonObject) {
        json = jsonObject;
        try {
            if (jsonObject.getInt("dian")==0){
                jiaofei_c1.setVisibility(View.GONE);
            }else {
                jiaofei_dianfei_money.setText(jsonObject.getString("dian"));
            }
            if (jsonObject.getInt("shui")==0){
                jiaofei_c2.setVisibility(View.GONE);
            }else {
                jiaofei_shuifei_money.setText(jsonObject.getString("shui"));
            }
            if (jsonObject.getInt("wuye")==0){
                jiaofei_c3.setVisibility(View.GONE);
            }else {
                jiaofei_wuye_money.setText(jsonObject.getString("wuye"));
            }
            jiaofei_time.setText("最后缴费日期："+jsonObject.getString("time"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void requestData(MessageEvent me) {
        super.requestData(me);
        wuyejiaofeiPresenter.dispose(me);
    }

    @Event(value = R.id.jiaofei_dianfei_submit)
    private void submit_dianfei(View view) {
        try {
            payV2(view,Double.parseDouble(json.getString("dian")),"电费");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Event(value = R.id.jiaofei_shuifei_submit)
    private void submit_shuifei(View view){
        try {
            payV2(view,Double.parseDouble(json.getString("shui")),"水费");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Event(value = R.id.jiaofei_wuyefei_submit)
    private void submit_wuyefei(View view){
        try {
            payV2(view,Double.parseDouble(json.getString("wuye")),"物业费");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
