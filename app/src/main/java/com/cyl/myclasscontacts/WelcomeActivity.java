package com.cyl.myclasscontacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;


import com.cyl.myclasscontacts.utils.Utils;

import java.util.Map;

/**
 * Created by 永龙 on 2015/11/11.
 */
public class WelcomeActivity extends Activity {

    public static boolean IsFirstLogin = false; //判断是否是第一次登录
    private final int TIME_OVER = 0, NEW_VERSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Window window = getWindow();// 获取当前的窗体对象
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 隐藏了状态栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏了标题栏

        setContentView(R.layout.welcomepage);

        welcomeUI();

    }

    private void welcomeUI() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    welHandler.sendEmptyMessage(TIME_OVER);

                    // 具体消息中包含什么东西并不重要，因为接收的函数不需要该参数
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    Handler welHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME_OVER:
                    welcomeFunction();
                    break;
                case NEW_VERSION:

                    break;
            }

        }

    };

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void welcomeFunction() {

        Map<String, String> useInfos = Utils.getUserInfo(WelcomeActivity.this);
        if (useInfos.get("userName") == null) {
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
            WelcomeActivity.this.finish();
        } else {
            Intent intent = new Intent();
            intent.setClass(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            WelcomeActivity.this.finish();
        }
    }

    /**
     * 第一个启动页面中调用checkVersion方法进行版本更新检查
     *
     * @author Jerry
     *
     */

}

