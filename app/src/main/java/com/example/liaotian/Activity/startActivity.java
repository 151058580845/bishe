package com.example.liaotian.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.liaotian.R;
import com.example.liaotian.util.SPUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;

import io.reactivex.functions.Consumer;


@ContentView(R.layout.startactivity)
public class startActivity extends BaseActivity{

    private Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermissionRequest();

        boolean first = (boolean) SPUtils.get(this, "first", true);
        if (first) {
            intent = new Intent(this, GuideActivity.class);

            first = false;
            SPUtils.put(this, "first", first);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    public void checkPermissionRequest(){
        RxPermissions permissions = new RxPermissions(this);
        permissions.setLogging(true);
        permissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission_group.PHONE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                LogUtil.e("aa"+aBoolean);
            }
        });
    }
}
