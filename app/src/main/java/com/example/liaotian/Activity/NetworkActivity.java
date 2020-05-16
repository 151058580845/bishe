package com.example.liaotian.Activity;

import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.example.liaotian.Activity.BaseActivity;
import com.example.liaotian.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_network)
public class NetworkActivity extends BaseActivity {
    @ViewInject(R.id.Network_title)
    TextView Network_title;
    @ViewInject(R.id.Network_toolbar)
    Toolbar Network_toolbar;
    @ViewInject(R.id.Network_webview)
    WebView Network_webview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Network_toolbar.setNavigationIcon(R.drawable.back);
        Network_toolbar.setTitle("");
        Network_toolbar.setNavigationOnClickListener((v -> this.finish()));
        String url = getIntent().getStringExtra("url");
//        String url = "http://192.168.0.100:80/zhihuishequ/php/login.html";
        WebSettings webSettings = Network_webview.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        Network_webview.loadUrl(url);
        Network_webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
//        Network_webview.loadUrl("https://www.baidu.com/");

    }
}
