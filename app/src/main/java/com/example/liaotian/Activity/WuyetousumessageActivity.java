package com.example.liaotian.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.liaotian.Adapter.PhotoAdapter;
import com.example.liaotian.R;
import com.example.liaotian.View.ITousuView;
import com.example.liaotian.entity.Tousu;
import com.example.liaotian.presenter.TousuaddPresenter;
import com.example.liaotian.util.Contants;
import com.example.liaotian.util.FullyGridLayoutManager;
import com.example.liaotian.util.GlideEngine;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.http.Client;
import com.example.liaotian.util.http.Function;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import me.shihao.library.XRadioGroup;
import okhttp3.FormBody;

import static com.luck.picture.lib.config.PictureConfig.CHOOSE_REQUEST;

@ContentView(R.layout.activity_tousuadd)
public class WuyetousumessageActivity extends BaseActivity implements ITousuView {

    @ViewInject(R.id.tousu_toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.baoxiu_title)
    TextView baoxiu_titlel;
    @ViewInject(R.id.tousu_add_group)
    XRadioGroup tousu_add_group;
    @ViewInject(R.id.tousu_add_r1)
    RadioButton tousu_add_r1;
    @ViewInject(R.id.tousu_add_r2)
    RadioButton tousu_add_r2;
    @ViewInject(R.id.tousu_add_r3)
    RadioButton tousu_add_r3;
    @ViewInject(R.id.add2_g1)
    RadioGroup add2_g1;
    @ViewInject(R.id.add2_r2)
    RadioButton add2_r2;
    @ViewInject(R.id.add2_r1)
    RadioButton add2_r1;
    @ViewInject(R.id.add3_l)
    ConstraintLayout add3_l;
    @ViewInject(R.id.add3_e1)
    EditText add3_e1;
    @ViewInject(R.id.add4_e1)
    EditText add4_e1;
    @ViewInject(R.id.add5_e1)
    EditText add5_e1;
    @ViewInject(R.id.add7_t1)
    TextView add7_t1;
    @ViewInject(R.id.add7_e1)
    TextView add7_e1;
    @ViewInject(R.id.tousu_submit)
    Button tousu_submit;
    @ViewInject(R.id.tousu_delete)
    ImageView tousu_delete;

    @ViewInject(R.id.tousu_recycle)
    RecyclerView recyclerView;


    private PhotoAdapter photoAdapter;
    private TousuaddPresenter tousuaddPresenter;
    private ArrayList<String> selectList = new ArrayList<>();
    private ArrayList<LocalMedia> selectList2 = new ArrayList<>();
    private ArrayList<String> selectList3 = new ArrayList<>();
    private ArrayList<String> selectList4 = new ArrayList<>();
    private int id = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baoxiu_titlel.setText("投诉详情");
        tousu_submit.setVisibility(View.GONE);
        tousu_delete.setVisibility(View.VISIBLE);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener((v -> this.finish()));
        id = getIntent().getIntExtra("id",0);
        tousuaddPresenter = new TousuaddPresenter(this,this);


        add7_t1.setVisibility(View.VISIBLE);
        add7_e1.setVisibility(View.VISIBLE);
        add2_g1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.add2_r1:
                        add3_l.setVisibility(View.GONE);
                        break;
                    case R.id.add2_r2:
                        add3_l.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    @Event(R.id.tousu_delete)
    private void tousu_delete(View view){
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("id",id+"");
        FormBody formBody = builder.build();
        Client.tousu_delete(this,formBody);
    }
    @Event(value = R.id.tousu_submit)
    private void tousu_submit(View view){
        boolean num = true;
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("id",String.valueOf(id));
        switch (tousu_add_group.getCheckedRadioButtonId()){
            case R.id.tousu_add_r1:
                builder.add("leibie", String.valueOf(0));
            break;
            case R.id.tousu_add_r2:
                builder.add("leibie", String.valueOf(1));
                break;
            case R.id.tousu_add_r3:
                builder.add("leibie", String.valueOf(2));
                break;
        }
        switch (add2_g1.getCheckedRadioButtonId()){
            case R.id.add2_r1:
                builder.add("name", "匿名用户");
                break;
            case R.id.add2_r2:
                if (!"".equals(add3_e1.getText().toString())){
                    builder.add("name",add3_e1.getText().toString());
                }else {
                    showToast("请输入姓名");
                    num = false;
                }
                break;
        }
        if (!"".equals(add4_e1.getText().toString())){
            builder.add("phone",add4_e1.getText().toString());
        }else {
            showToast("请输入联系方式");
            num = false;
        }
        if (!"".equals(add5_e1.getText().toString())){
            builder.add("message",add5_e1.getText().toString());
        }else {
            showToast("请输入内容");
            num = false;
        }
        if (num)
        tousuaddPresenter.updatetousu(builder,selectList4,2);


    }

    @Override
    public void requestData(MessageEvent me) {
        super.requestData(me);
        tousuaddPresenter.dispose(me);
    }

    @Event(value = R.id.add6_photo)
    private void add6_photo(View view){
//        PictureSelector.create(this)
//                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
//                .theme(R.style.picture_default_style1)
//                .maxSelectNum(9)// 最大图片选择数量 int
//                .minSelectNum(1)// 最小选择数量 int
//                .imageSpanCount(4)// 每行显示个数 int
//                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
//                .previewImage(true)// 是否可预览图片 true or false
//                .previewVideo(true)// 是否可预览视频 true or false
//                .enablePreviewAudio(true) // 是否可播放音频 true or false
//                .isCamera(true)// 是否显示拍照按钮 true or false
//                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
//                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
////                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
////                .enableCrop(true)// 是否裁剪 true or false
//                .compress(true)// 是否压缩 true or false
//                .isGif(true)// 是否显示gif图片 true or false
//                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
////                .circleDimmedLayer(true)// 是否圆形裁剪 true or false
//                .openClickSound(false)// 是否开启点击声音 true or false
//                .selectionMedia(selectList2)// 是否传入已选图片 List<LocalMedia> list
//                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
//                .minimumCompressSize(100)// 小于100kb的图片不压缩
//                .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
//                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
//                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
//                .isDragFrame(false)// 是否可拖动裁剪框(固定)
//                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code   

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PictureConfig.APPLY_STORAGE_PERMISSIONS_CODE:
                // 存储权限
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        PictureFileUtils.deleteCacheDirFile(this, PictureMimeType.ofImage());
                    } else {
                        Toast.makeText(WuyetousumessageActivity.this,
                                getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = new ArrayList<>();
                    selectList4 = new ArrayList<>();
                    selectList = selectList3;
                    ArrayList<LocalMedia> selectList1 = (ArrayList<LocalMedia>) PictureSelector.obtainMultipleResult(data);
                    for (int i=0;i<selectList1.size();i++){
                        selectList.add(selectList1.get(i).getPath());
                        selectList4.add(selectList1.get(i).getPath());
//                        selectList2.add(selectList1.get(i));
                    }
                    selectList2 = selectList1;
//                    Log.e("222222222", "onActivityResult: "+selectList );
//                    photoAdapter = new PhotoAdapter(R.layout.adapter_photo,selectList,this,this);
//                    FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
//                            6, GridLayoutManager.VERTICAL, false);
////                    recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
////        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
//                    recyclerView.setLayoutManager(manager);
//                    recyclerView.setAdapter(photoAdapter);
//                    photoAdapter.notifyDataSetChanged();
                    // 例如 LocalMedia 里面返回五种path
                    // 1.media.getPath(); 原图path
                    // 2.media.getCutPath();裁剪后path，需判断media.isCut();切勿直接使用
                    // 3.media.getCompressPath();压缩后path，需判断media.isCompressed();切勿直接使用
                    // 4.media.getOriginalPath()); media.isOriginal());为true时此字段才有值
                    // 5.media.getAndroidQToPath();Android Q版本特有返回的字段，但如果开启了压缩或裁剪还是取裁剪或压缩路径；注意：.isAndroidQTransform 为false 此字段将返回空
                    // 如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩
//                    for (LocalMedia media : selectList) {
//                        Log.i(TAG, "是否压缩:" + media.isCompressed());
//                        Log.i(TAG, "压缩:" + media.getCompressPath());
//                        Log.i(TAG, "原图:" + media.getPath());
//                        Log.i(TAG, "是否裁剪:" + media.isCut());
//                        Log.i(TAG, "裁剪:" + media.getCutPath());
//                        Log.i(TAG, "是否开启原图:" + media.isOriginal());
//                        Log.i(TAG, "原图路径:" + media.getOriginalPath());
//                        Log.i(TAG, "Android Q 特有Path:" + media.getAndroidQToPath());
//                        Log.i(TAG, "宽高: " + media.getWidth() + "x" + media.getHeight());
//                        Log.i(TAG, "Size: " + media.getSize());
//
//                        // TODO 可以通过PictureSelectorExternalUtils.getExifInterface();方法获取一些额外的资源信息，如旋转角度、经纬度等信息
//                    }
//                    mAdapter.setList(selectList);
//                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        tousuaddPresenter.gettousu(id,1);
    }

    @Override
    public void setl(ArrayList<Tousu> list) {

    }

    @Override
    public void setlist(ArrayList<Tousu> list) {

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addphoto() {

    }

    @Override
    public void setmessage(String date) {
        try {
            JSONObject json = new JSONObject(date);
            if (json.has("leibie")){
                switch (json.getInt("leibie")){
                    case 0:
                        tousu_add_r1.setChecked(true);
                        break;
                    case 1:
                        tousu_add_r2.setChecked(true);
                        break;
                    case 2:
                        tousu_add_r3.setChecked(true);
                        break;
                }
            }
            if (json.has("name")){
                if (!"".equals(json.getString("message")))
                if ("匿名用户".equals(json.getString("name"))){
                    add2_r1.setChecked(true);
                }else {
                    add2_r2.setChecked(true);
                    add3_e1.setText(json.getString("name"));
                }
            }
            if (json.has("phone")){
                if (!"".equals(json.getString("phone")))
                add4_e1.setText(json.getString("phone"));
            }
            if (json.has("message")){
                if (!"".equals(json.getString("message")))
                add5_e1.setText(json.getString("message"));
            }
            if (json.has("fankui")){
                if ("null".equals(json.getString("fankui"))){
                    add7_e1.setText("暂无反馈结果");
                }else {
                    add7_e1.setText(json.getString("fankui"));
                }

            }
            if (json.has("address")){
                String address = json.getString("address");
                address = address.substring(1,address.length());
                selectList3 = new ArrayList<>();
                String[] address1 = address.split(",");
                for (int i = 0;i<address1.length;i++){
                    selectList3.add(Contants.url+address1[i].substring(1,address1[i].length()));
                }
                if (selectList.size()>0){
                    photoAdapter = new PhotoAdapter(R.layout.adapter_photo,selectList,this,this);
                }else {
                    photoAdapter = new PhotoAdapter(R.layout.adapter_photo,selectList3,this,this);
                }

                photoAdapter.setnum(address1.length);
                FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                        6, GridLayoutManager.VERTICAL, false);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(photoAdapter);
                photoAdapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void back() {
        this.finish();
    }

}
