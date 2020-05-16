package com.example.liaotian.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.liaotian.Adapter.ZhangdanAdapter;
import com.example.liaotian.R;
import com.example.liaotian.View.IZhangdanView;
import com.example.liaotian.entity.CardBean;
import com.example.liaotian.entity.Zhangdan;
import com.example.liaotian.presenter.WuyezhangdanPresenter;
import com.example.liaotian.util.Contants;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.http.Function;
import com.xq.fasterdialog.dialog.base.BaseDialog;

import org.angmarch.views.NiceSpinner;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static android.provider.Telephony.Carriers.PASSWORD;

@ContentView(R.layout.activity_wuyezhangdan)
public class WuyezhangdanActivity extends BaseActivity implements IZhangdanView,View.OnClickListener {

    @ViewInject(R.id.zhangdan_toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.zhangdan_shaixuan)
    TextView zhangdan_shaixuan;
    @ViewInject(R.id.zhangdan_shijian)
    TextView zhangdan_shijian;
    @ViewInject(R.id.zhangdan_shue)
    TextView zhangdan_shue;
    @ViewInject(R.id.zhangdan_recycle)
    RecyclerView recyclerView;
    @ViewInject(R.id.zhangdan_select)
    TextView zhangdan_select;
    @ViewInject(R.id.zhangdan_xiala)
    ImageView zhangdan_xiala;
    @ViewInject(R.id.zhangdan_guanjianzi)
    EditText zhangdan_guanjianzi;

    private ZhangdanAdapter zhangdanAdapter;
    private TimePickerView pvCustomLunar;
    private OptionsPickerView<Object> pvCustomOptions;
    private List list_shaixuan = new ArrayList<>();
    private WuyezhangdanPresenter wuyezhangdanPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener((v)->this.finish());
        initLunarPicker();
        list_shaixuan.add("电费");
        list_shaixuan.add("水费");
        list_shaixuan.add("物业费");
        list_shaixuan.add("其他");
        list_shaixuan.add("全部");
        initList(list_shaixuan);
        wuyezhangdanPresenter = new WuyezhangdanPresenter(this,this);
        zhangdan_shijian.setOnClickListener(this::onClick);
        zhangdan_shaixuan.setOnClickListener(this::onClick);
        zhangdan_select.setOnClickListener(this::onClick);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Contants.isfirst = false;
        wuyezhangdanPresenter.onResume();
    }

    private void initList(List<Object> list) {

        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                zhangdan_shaixuan.setText((String) list_shaixuan.get(options1));
                if (options1==4){
                    wuyezhangdanPresenter.getZhangdan(1);
                }else {
                    wuyezhangdanPresenter.settype((String) list_shaixuan.get(options1));
                }
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });
                    }
                })
                .isDialog(true)
                .setOutSideCancelable(true)
                .build();
        pvCustomOptions.setPicker(list_shaixuan);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.zhangdan_shijian:
                pvCustomLunar.show();
                break;
            case R.id.zhangdan_shaixuan:
                pvCustomOptions.show();
                break;
            case R.id.zhangdan_select:
                showdialog();
//                showselect();
                break;
        }
    }

    private void showselect() {
        if (Contants.isfirst){
            Contants.isfirst = false;
        }else {
            Contants.isfirst=true;
        }
        if (Contants.isfirst){
            zhangdan_guanjianzi.setVisibility(View.VISIBLE);
            zhangdan_shaixuan.setVisibility(View.INVISIBLE);
            zhangdan_xiala.setVisibility(View.INVISIBLE);
        }else{
            zhangdan_guanjianzi.setVisibility(View.GONE);
            zhangdan_shaixuan.setVisibility(View.VISIBLE);
            zhangdan_xiala.setVisibility(View.VISIBLE);
        }

    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(date);
    }
    private void initLunarPicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2009, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH));
        //时间选择器 ，自定义布局
        pvCustomLunar = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(date);
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH)+1;
                zhangdan_shijian.setText(month+"");
                wuyezhangdanPresenter.settime(date);
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_lunar, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.returnData();
                                pvCustomLunar.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.dismiss();
                            }
                        });
                        //公农历切换
                        CheckBox cb_lunar = (CheckBox) v.findViewById(R.id.cb_lunar);
                        cb_lunar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                pvCustomLunar.setLunarCalendar(!pvCustomLunar.isLunarCalendar());
                                //自适应宽
                                setTimePickerChildWeight(v, isChecked ? 0.8f : 1f, isChecked ? 1f : 1.1f);
                            }
                        });

                    }

                    /**
                     * 公农历切换后调整宽
                     * @param v
                     * @param yearWeight
                     * @param weight
                     */
                    private void setTimePickerChildWeight(View v, float yearWeight, float weight) {
                        ViewGroup timePicker = (ViewGroup) v.findViewById(R.id.timepicker);
                        View year = timePicker.getChildAt(0);
                        LinearLayout.LayoutParams lp = ((LinearLayout.LayoutParams) year.getLayoutParams());
                        lp.weight = yearWeight;
                        year.setLayoutParams(lp);
                        for (int i = 1; i < timePicker.getChildCount(); i++) {
                            View childAt = timePicker.getChildAt(i);
                            LinearLayout.LayoutParams childLp = ((LinearLayout.LayoutParams) childAt.getLayoutParams());
                            childLp.weight = weight;
                            childAt.setLayoutParams(childLp);
                        }
                    }
                })
                .setType(new boolean[]{true, true, false, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();
    }

    @Override
    public void setdata_year(String year, String month, String days) {

    }

    public void showdialog(){
        new MaterialDialog.Builder(this)
                .title("搜索")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("请输入关键字", null, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
//                        Toast.makeText(WuyezhangdanActivity.this,input.toString(),Toast.LENGTH_SHORT).show();
                        wuyezhangdanPresenter.getZhangdan(3,input.toString());
                    }
                })
                .positiveText("确定")
                .show();
    }

    @Override
    public void setjine(int jine) {
        zhangdan_shue.setText(jine+"");
    }

    @Override
    public void setzhangdan(ArrayList list) {
        zhangdanAdapter = new ZhangdanAdapter(R.layout.adapter_zhangdan,list,this,this);
        recyclerView.setItemViewCacheSize(500);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(zhangdanAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void requestData(MessageEvent me) {
        super.requestData(me);
        wuyezhangdanPresenter.dispose(me);
    }
}
