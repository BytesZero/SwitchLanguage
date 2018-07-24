package com.zsl.switchlanguage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.webkit.WebView;

import java.util.Locale;

/**
 * desc: 语言的基础类
 * project name:LocaleManager
 * author:zsl
 * version 1.0.0
 */

public class LocaleManager {

    private static String cLanguageValue;//当前语言

    /**
     * 设置Locale
     *
     * @param context 上下文
     * @return 新的上下文
     */
    public static Context setLocale(Context context) {
        return updateConfiguration(context, getLocale(context));
    }

    /**
     * 获取语言
     *
     * @param context 语言的对应的值
     * @return Locale
     */
    public static Locale getLocale(Context context) {
        if (TextUtils.isEmpty(cLanguageValue)) {
            cLanguageValue = PreferenceManager.getDefaultSharedPreferences(context)
                    .getString("language_value", "auto");
        }
        Locale locale;
        if ("auto".equals(cLanguageValue)) {
            locale = Locale.getDefault();
        } else {
            if (cLanguageValue.contains("-")) {
                String[] languageValueArray = cLanguageValue.split("-");
                locale = new Locale(languageValueArray[0], languageValueArray[1]);
            } else {
                locale = new Locale(cLanguageValue);
            }
        }
        return locale;
    }

    /**
     * 更新语言
     *
     * @param context       上下文
     * @param language      语言显示文字
     * @param languageValue 语言对应的值
     */
    private static void updateLanguage(Context context, String language, String languageValue) {
        setLanguageValue(languageValue);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("language_value", languageValue);
        editor.putString("language", language);
        editor.apply();
    }

    /**
     * 设置当前语言值
     *
     * @param languageValue 当前语言的值
     */
    private static void setLanguageValue(String languageValue) {
        cLanguageValue = languageValue;
    }

    /**
     * 更新语言
     *
     * @param context 上下文
     * @param locale  新 Locale
     * @return 新上下文
     */
    private static Context updateConfiguration(Context context, Locale locale) {
        //更新语言
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        res.updateConfiguration(config, res.getDisplayMetrics());
        return context;
    }

    /**
     * 切换语言
     *
     * @param context       上下文
     * @param language      语言
     * @param languageValue 语言值
     */
    public static void switchLanguage(Context context, String language, String languageValue) {
        LocaleManager.updateLanguage(context, language, languageValue);
        LocaleManager.setLocale(context);
    }

    /**
     * 重启Activity
     *
     * @param context 重启Activity
     */
    public static void restartActivity(Context context, Class<?> cls) {
        //设置内从当中当前语言为空，下次启动程序自动重新获取
        LocaleManager.setLanguageValue(null);
        //重启App
        Intent intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        //杀掉进程
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(0);
    }

    //处理Android7（N）WebView 导致应用内语言失效的问题
    public static void destoryWebView(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            new WebView(context).destroy();
        }
    }
}
