package com.cyl.myclasscontacts.utils;

import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;

import com.baidu.apistore.sdk.ApiStoreSDK;
import com.cyl.myclasscontacts.manager.Global;

import java.util.Map;

/**
 * Created by 永龙 on 2015/11/24.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化全局变量
        initGlobal();
    }

    /**
     * 初始化全局变量 实际工作中这个方法中serverVersion从服务器端获取，最好在启动画面的activity中执行
     */
    public void initGlobal() {
        try {
            // 获取本地版本号
            Global.localVersion = getPackageManager().getPackageInfo(
                    getPackageName(), 0).versionCode;
            // 假设服务端版本号为2，这个应该是要获取服务器端的版本号的，这里只是假设服务端版本号2
       //     Global.serverVersion =getserviceversion();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
//    private int getserviceversion() {
//
//        final int[] result = {Global.localVersion};
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                String versioninfo = HttpService.DataByGetVersion();
//                Log.e("MyApplication",versioninfo +"的值");
//                Utils.savaVersioninfo(getApplicationContext(),versioninfo);
//                Map<String,String> version = JsonParsing.Versioninfo(versioninfo);
//                Log.e("服务器版本号", version.get("version_i"));
//                result[0] =Integer.parseInt(version.get("version_i"));
//            }
//        }.start();
//
//        return result[0];
//
//    }

}
