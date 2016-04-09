package com.cyl.myclasscontacts;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cyl.myclasscontacts.manager.Global;
import com.cyl.myclasscontacts.manager.UpdateManager;
import com.cyl.myclasscontacts.utils.HttpService;
import com.cyl.myclasscontacts.utils.JsonParsing;
import com.cyl.myclasscontacts.utils.Utils;

import java.io.File;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by 永龙 on 2015/11/23.
 */
public class SettingsActivity extends AppCompatActivity {
    String versioninfo;
    private static final int DOWN_INFO = 1;

    private static final int DOWN_OVER = 2;
    private static int version_i;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_INFO:
                    Log.e("zhizhizhihzihzi","版本号 === "+version_i);
                    checkVersion();
                    break;
                case DOWN_OVER:

                    break;
                default:
                    break;
            }
        }

        ;
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        LinearLayout info_user = (LinearLayout) findViewById(R.id.info_user);
        info_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout checkout_update = (LinearLayout) findViewById(R.id.checkout_update);
        checkout_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //getserviceversion();
                Toast.makeText(getApplicationContext(),"更新功能开发中...",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getserviceversion() {

        new Thread() {
            @Override
            public void run() {
                super.run();
                versioninfo = HttpService.DataByGetVersion();
                Map<String,String> version = JsonParsing.Versioninfo(versioninfo);
                version_i =Integer.parseInt(version.get("version_i"));
                mHandler.sendEmptyMessage(DOWN_INFO);

            }
        }.start();


    }

    /**
     * 检查版本更新
     *
     */
    public void checkVersion() {
        // 判断本地版本是否小于服务器端的版本号

        Log.e("版本号", Global.localVersion + "服务器版本号:Global.serverVersion" +Global.serverVersion);


        if ( Global.localVersion <version_i) {
            // 发现新版本，提示用户更新
            UpdateManager updateManager = new UpdateManager(this);
            String updateMsg="有新版本";
            updateManager.checkUpdateInfo(updateMsg);
        } else {
            SweetAlertDialog update = new SweetAlertDialog(SettingsActivity.this,SweetAlertDialog.NORMAL_TYPE);
            update.setContentText("暂无更新!");
            update.show();
            // 清理工作，略去
            cheanUpdateFile();
        }
    }

    /**
     * 清理缓存的下载文件
     */
    private void cheanUpdateFile() {
        File updateFile = new File(Global.downloadDir, "myclasshelper.apk");
        if (updateFile.exists()) {
            // 当不需要的时候，清除之前的下载文件，避免浪费用户空间
            updateFile.delete();
        }
    }

}
