package com.zsl.switchlanguage;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * desc:
 * project name:SwitchLanguage
 * author:zsl
 * version 1.0.0
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setLanguage();
    }

    /**
     * 设置语言
     */
    private void setLanguage() {
        String currentLanguage = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getString("language", "");
        Locale locale;
        if ("auto".equals(currentLanguage)) {
            locale = Locale.getDefault();
        } else {
            locale = new Locale(currentLanguage);
        }
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
    }
}
