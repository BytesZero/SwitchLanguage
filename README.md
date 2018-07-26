# Android 多语言切换及适配指南

[![Build Status](https://travis-ci.org/yy1300326388/SwitchLanguage.svg?branch=master)](https://travis-ci.org/yy1300326388/SwitchLanguage)

### 切换语言

- Application 配置

```java
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
```

- 切换中文

```java

LocaleManager.switchLanguage(preference.getContext(), "简体中文", "zh");
```

- 切换英文

```java

LocaleManager.switchLanguage(preference.getContext(), "English", "en");
```

- 更随系统

```java

LocaleManager.switchLanguage(preference.getContext(), "跟随系统", "auto");
```

- 重启app，并启动主页

```java
LocaleManager.restartActivity(getActivity(), MainActivity.class);
```



### 核心代码

- 语言切换核心代码

``` java
//更新语言
Resources res = context.getResources();
Configuration config = new Configuration(res.getConfiguration());
config.setLocale(locale);
res.updateConfiguration(config, res.getDisplayMetrics());
return context;
```

### 注意事项

#### 1、Android N(API-24) 有 WebView 的页面，切换语言不成功

- 原因

Android N 开始系统 WebView 使用共享的同一个，所以会导致这个问题

- 解决办法

启动 App 的时候创建 `WebView` 然后 `destroy()`一下

```java
@Override
public void onCreate() {
    super.onCreate();
    //处理Android7（N）WebView 导致应用内语言失效的问题
    LocaleManager.destoryWebView(this);
}
```

#### 2、`Toolbar` 或者 `ActionBar` 的`title` 切换语言不起作用

默认 `title` 是从 `AndroidManifest.xml` 中 `Activity` 的 `label` 标签里读取的，我们在代码里手动设置一下 `title`即可

```java
//Toolbar
toolbar.setTitle(R.string.app_name);
//ActionBar
actionBar.setTitle(R.string.title_activity_settings);
```

#### 3、保存语言设置时，连续调用了 `switchLanguage` 和 `restartActivity` 结果目标语言没有保存下来。

- 原因

切换后的目标语言保存在 `SharedPreferences` 中，`restartActivity`会导致上下文丢失，所以保存失败

- 解决方法

选择语言后调用 `switchLanguage` 点击保存后只调用`restartActivity`

> 上述方案有一个新问题，就是我只切换了语言，但是没有保存，按了返回键，但是已经触发了语言切换，下次启动App会看到切换后的语言，其实这个时候用户是没有切换的

- 解决方法

在用户点击返回的时候，再次调用 `switchLanguage` 切换回当前语言

如果你有更好的思路和方案请提交给我哦

#### 4、切换从右到左的语言后，布局不跟随改变

- 原因

在较早的 Android 系统版本中不支持从右到左的布局切换，从 `Android 4.1 (Jelly Bean API 17+)` 以后才开始支持

- 解决办法

在布局文件我们把之前的`left`替换成`start` 、`right`替换成`end`,比如 `paddingLeft`替换成`paddingStart`

#### 5、获取字符串使用 `String.format(String format, Object... args)` 来创建格式化的字符串

- 转化符说明参考表

转化符 | 说明 | 示例
---|---|---
%s|字符串类型|“asdf”
%c|字符类型|'m'
%b|布尔类型| true|false
%d|整数类型（十进制）|99
%x|整数类型（十六进制）|FF
%o|整数类型（八进制）|77
%f|浮点类型|99.99
%a|十六进制浮点类型|FF.35AE
%e|指数类型|9.38e+5
%g|通用浮点类型（f和e类型中较短的)|
%h|散列码|
%%|百分比类型|99%
%n|换行符|
%tx|日期与时间类型（x代表不同的日期与时间转换符|

- 示例

```java
String str=null;
str=String.format("Hi,%s", "王力");
System.out.println(str);
str=String.format("Hi,%s:%s.%s", "王南","王力","王张");
System.out.println(str);
System.out.printf("字母a的大写是：%c %n", 'A');
System.out.printf("3>7的结果是：%b %n", 3>7);
System.out.printf("100的一半是：%d %n", 100/2);
System.out.printf("100的16进制数是：%x %n", 100);
System.out.printf("100的8进制数是：%o %n", 100);
System.out.printf("50元的书打8.5折扣是：%f 元%n", 50*0.85);
System.out.printf("上面价格的16进制数是：%a %n", 50*0.85);
System.out.printf("上面价格的指数表示：%e %n", 50*0.85);
System.out.printf("上面价格的指数和浮点数结果的长度较短的是：%g %n", 50*0.85);
System.out.printf("上面的折扣是%d%% %n", 85);
System.out.printf("字母A的散列码是：%h %n", 'A');
```
