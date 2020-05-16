package com.example.liaotian.Activity;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liaotian.Fragement.MainFragement;
import com.example.liaotian.Fragement.MyInfromFragement;
import com.example.liaotian.R;
import com.example.liaotian.View.IMainView;
import com.example.liaotian.presenter.MainPresent;
import com.example.liaotian.util.Contants;
import com.example.liaotian.util.MessageEvent;
import com.example.liaotian.util.http.Function;

import org.angmarch.views.NiceSpinner;
import org.lym.image.select.PictureSelector;
import org.lym.image.select.imageloader.GlideImageLoader;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;


@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements IMainView ,ViewAnimator.ViewAnimatorListener {

    @ViewInject(R.id.main_spinner)
    NiceSpinner main_spinner;
    @ViewInject(R.id.main_toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.mian_tianqi)
    TextView mian_tianqi;

    @ViewInject(R.id.main_drawer_layout)
    DrawerLayout drawerLayout;
    @ViewInject(R.id.main_left_drawer)
    LinearLayout linearLayout;

    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ViewAnimator viewAnimator;


    private MyInfromFragement myInfromFragement;
    private MainPresent mainPresent;
    private MainFragement fragment_main;
    private FragmentManager fm;
    private FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settoolbar();
        /*toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener((View v)->MainActivity.this.finish());*/
        mainPresent = new MainPresent(this,this);

        myInfromFragement = new MyInfromFragement(this,this);
        fragment_main = new MainFragement(this,this);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.fragement_context,fragment_main).commit();

        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout.setOnClickListener((v)->{drawerLayout.closeDrawers();});
        createMenuList();
        viewAnimator = new ViewAnimator(this,list, fragment_main, drawerLayout, this);
        viewAnimator = new ViewAnimator(this,list, myInfromFragement, drawerLayout, this);
    }

    @Event(R.id.mian_tianqi)
    private void tianqi(View view){

    }

    private void settoolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.home_menu);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Function.getNews(this);
        Function.getCitytianqi(MainActivity.this,"南湖");
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(Contants.t1, R.drawable.my);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(Contants.t2, R.drawable.zhuye);
        list.add(menuItem);
//        SlideMenuItem menuItem2 = new SlideMenuItem(Contants.t3, R.drawable.wuyejiaofei);
//        list.add(menuItem2);
//        SlideMenuItem menuItem3 = new SlideMenuItem(Contants.t4, R.drawable.wuyehetong);
//        list.add(menuItem3);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        switch (item.getItemId()){
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public void setSpinner(List<String> list) {
        if (list.get(0).equals("")){
            main_spinner.setVisibility(View.INVISIBLE);
        }else
        main_spinner.attachDataSource(list);
    }

    @Override
    public void settianqi(String tianqi) {
        mian_tianqi.setText(tianqi);
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
    private static final int IMAGES_CODE = 101;
    @Override
    public void openpictyre() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //版本判断
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
            }
        }
        PictureSelector.with(this)
                .selectSpec()
                .setImageLoader(new GlideImageLoader())
                .setSpanCount(3)
                .setOpenCamera()
                .setAuthority("com.example.liaotian")
                .setMaxSelectImage(9)
                .startForResult(IMAGES_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openpictyre();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGES_CODE && resultCode == Activity.RESULT_OK) {
            if (null != data) {
                List<String> paths = PictureSelector.obtainPathResult(data);
                mainPresent.uploadfile(paths);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresent.onDestroy();
    }

    @Override
    public void requestData(MessageEvent me) {
        super.requestData(me);
        mainPresent.dispose(me);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition) {
        View view = findViewById(R.id.fragement_context);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
        animator.start();
        switch (topPosition){
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragement_context, myInfromFragement).commit();
//                return myInfromFragement;
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragement_context, fragment_main).commit();
                break;
            case 735:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragement_context, fragment_main).commit();
                break;
            case 945:
                break;
        }
        return fragment_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case Contants.t1:
//                Toast.makeText(this,"mmmmm",Toast.LENGTH_SHORT).show();
                return replaceFragment(screenShotable, position);
            default:
                return replaceFragment(screenShotable, position);
        }
    }


    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();
    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }
}
