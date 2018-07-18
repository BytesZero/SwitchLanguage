package com.zsl.switchlanguage;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.webkit.WebView;

import java.util.Locale;

/**
 * desc:
 * project name:SwitchLanguage
 * author:zsl
 * version 1.0.0
 */

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //处理Android7（N）WebView 导致应用内语言失效的问题
        LocaleManager.destoryWebView(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this);
    }
}