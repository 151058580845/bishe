package com.example.liaotian.util;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebView;

import androidx.annotation.RequiresApi;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.xutils.common.util.LogUtil;

public class MyWebView extends WebView
{
    private SwipeRefreshLayout srf;
    private boolean pageSlide = true;

    public MyWebView(Context context)
    {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout)
    {
        this.srf = swipeRefreshLayout;
    }

    public void setPageSlide(boolean pageSlide)
    {
        this.pageSlide = pageSlide;
        LogUtil.e("111111111pageSlide" + this.pageSlide);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {
        super.onScrollChanged(l, t, oldl, oldt);
        LogUtil.e("a111111111111111111111111111111111111" + this.getScrollY() + pageSlide);
        if (this.getScrollY() == 0 && pageSlide)
        {
            srf.setEnabled(true);
        }
        else
        {
            srf.setEnabled(false);
        }
    }
}