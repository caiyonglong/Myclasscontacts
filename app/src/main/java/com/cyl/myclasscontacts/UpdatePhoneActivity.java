package com.cyl.myclasscontacts;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cyl.myclasscontacts.db.DBoperation;
import com.cyl.myclasscontacts.db.TelInfo;
import com.cyl.myclasscontacts.utils.HttpService;
import com.cyl.myclasscontacts.utils.Utils;

import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by 永龙 on 2015/11/23.
 */
public class UpdatePhoneActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText PastPhone;
    private EditText NewPhone;
    private EditText RePhone;
    private Button ChangeBtn;
    private String phone = null;
    private DBoperation DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatephone);
        PastPhone = (EditText) findViewById(R.id.past_phone);
        NewPhone = (EditText) findViewById(R.id.new_phone);
        RePhone = (EditText) findViewById(R.id.re_phone);
        ChangeBtn = (Button) findViewById(R.id.change_phone);
        ChangeBtn.setOnClickListener(this);
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        if (phone != null) {
            PastPhone.setText(phone);
        }
        DB =new DBoperation(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_phone:
                change_phone();
                break;
        }
    }

    private void change_phone() {
        final String prephone = PastPhone.getText().toString().trim();
        final String newphone = NewPhone.getText().toString().trim();
        final String again_phone = RePhone.getText().toString().trim();
        final Map<String, String> useInfos = Utils.getUserInfo(this);
        Log.e("zhi", prephone + "====" + newphone + "=====" + again_phone);
        if (TextUtils.isEmpty(prephone) || TextUtils.isEmpty(again_phone) || TextUtils.isEmpty(newphone)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("ERROR")
                    .setContentText("输入框不能为空!")
                    .show();
        } else if (!again_phone.equals(newphone)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("ERROR")
                    .setContentText("两次手机号不一样!")
                    .show();
        } else {
            SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();
            new Thread() {
                @Override
                public void run() {
                    String result = null;
                    try {

                        String key = null;
                        //获取key
                        key = HttpService.DataByGetKey();
                        Log.e("key返回值", key + "");
                        String[] datas = {"key=" + key, "phonebookOperate=update", "num=" + useInfos.get("userID"),
                                "prePhone=" + prephone, "phone=" + newphone
                        };
                        result = HttpService.DataByPost(datas);
                    } catch (Exception e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new SweetAlertDialog(UpdatePhoneActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("ERROR")
                                        .setContentText("网络连接超时!")
                                        .show();
                            }
                        });
                    }
                    Log.e("返回值", result + "");
                    String status = "{\"status\":\"success\"}";
                    String status1 = "{\"status\":\"record not exist\"}";
                    String status2 = "{\"status\":\"illegal record\"}";
                    Log.e("状态", status1);
//                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                    if (status.equals(result)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"新号码："+newphone,Toast.LENGTH_SHORT).show();

                                //将新加的号码存入数据库
                                Map<String, String> useInfos = Utils.getUserInfo(getApplicationContext());
                                TelInfo telInfo =new TelInfo();
                                telInfo.setName(useInfos.get("name"));
                                telInfo.setName(useInfos.get("num"));
                                telInfo.setName(useInfos.get("phone"));
                                List<TelInfo> info =DB.queryAll();
                                long id=0;
                                for (int i=0;i<info.size();i++){
                                    if (prephone.equals(info.get(i).getTel())){
                                        id=i;
                                    }
                                }
                                telInfo.setId(id);

                                DB.update(telInfo);

                                Intent intent = new Intent(UpdatePhoneActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    UpdatePhoneActivity.this.finish();
                            }
                        });
                    } else if (status1.equals(result)) {
                        //请求失败
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                new SweetAlertDialog(UpdatePhoneActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("ERROR")
                                        .setContentText("不存在要修改的号码!")
                                        .show();

                            }
                        });
                    } else if (status2.equals(result)) {
                        //请求失败
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new SweetAlertDialog(UpdatePhoneActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("ERROR")
                                        .setContentText("要修改的号码已存在!")
                                        .show();
                            }
                        });
                    } else {
                        //请求失败
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new SweetAlertDialog(UpdatePhoneActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("ERROR")
                                        .setContentText("网络连接超时!")
                                        .show();
                            }
                        });
                    }
                }
            }.start();
            pDialog.dismiss();
        }
    }
}
