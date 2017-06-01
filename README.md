# 多语言切换demo

### 核心代码

- 语言切换

``` java
 public void swtichLanguage(Locale locale, Context context) {     Resources res = context.getResources();     Configuration conf = res.getConfiguration();     conf.setLocale(locale);     context.createConfigurationContext(conf); }
```

> 切换中文

```java

swtichLanguage(Locale.CHINESE, preference.getContext());
```

> 切换英文

```java

swtichLanguage(Locale.ENGLISH, preference.getContext());
```

> 更随系统

```java

swtichLanguage(Locale.getDefault(), preference.getContext());
```

- 重启app，并启动主页

```java

Intent intent = new Intent(context, MainActivity.class); intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); context.startActivity(intent);
 //杀掉进程
 android.os.Process.killProcess(android.os.Process.myPid()); System.exit(0);
```


> 其他代码逻辑请自行阅读源码

