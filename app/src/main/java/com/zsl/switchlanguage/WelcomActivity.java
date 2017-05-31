package com.zsl.switchlanguage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

public class WelcomActivity extends AppCompatActivity {

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);

        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 1) {
                    Intent intent = new Intent(WelcomActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                return true;
            }
        });

        mHandler.sendEmptyMessageDelayed(1, 600);
    }
}
