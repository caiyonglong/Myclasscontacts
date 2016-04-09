package com.cyl.myclasscontacts;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cyl.myclasscontacts.db.DBoperation;
import com.cyl.myclasscontacts.db.TelInfo;
import com.cyl.myclasscontacts.manager.Global;
import com.cyl.myclasscontacts.manager.UpdateManager;
import com.cyl.myclasscontacts.utils.HttpService;
import com.cyl.myclasscontacts.utils.JsonParsing;
import com.cyl.myclasscontacts.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by 永龙 on 2015/11/22.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText num;
    private EditText pw;
    private Button btn_login;
    private String number;
    private String password;
    private SweetAlertDialog pDialog;
    private DBoperation DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        num = (EditText) findViewById(R.id.et_number);
        pw = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);

        DB = new DBoperation(this);
        btn_login.setOnClickListener(this);
        Map<String, String> userinfo = Utils.getUserInfo(getApplicationContext());
        if (userinfo.get("userID") != null) {
            num.setText(userinfo.get("userID"));
        }

    }

    @Override
    public void onClick(View v) {
        number = num.getText().toString().trim();
        password = pw.getText().toString().trim();
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        if (TextUtils.isEmpty(number) || TextUtils.isEmpty(password)) {

            pDialog.dismiss();
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("账号或密码错误")
                    .show();
        } else {
            login();
        }

    }

    private void login() {

        new Thread() {
            @Override
            public void run() {
                super.run();
                final String result = HttpService.LoginByGet(number, password);
                Log.e("result ", result + "");
                if (result == null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pDialog.dismiss();
                            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("网络异常!")
                                    .show();
                        }
                    });

                } else {
                    String status = null;
                    try {
                        final JSONObject jsonObject = new JSONObject(result);
                        status = jsonObject.getString("status");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("status", status);
                    if ("success".equals(status)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pDialog.dismiss();
                                Map<String, String> map = JsonParsing.Userinfo(result);
                                map.put("pw", password);
                                Utils.savaUserInfo(getApplicationContext(), map);

                                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("登录成功")
                                        .setContentText("是否同步云端数据")
                                        .setCancelText("No!")
                                        .setConfirmText("Yes!")
                                        .showCancelButton(true)
                                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                if ("Y".equals(checkuser())) {
                                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                    startActivity(intent);
                                                    Log.e("结果", result);
                                                    LoginActivity.this.finish();
                                                } else {
                                                    Intent intent = new Intent(getApplicationContext(), AddPhoneActivity.class);
                                                    startActivity(intent);
                                                    Log.e("结果", result);
                                                    LoginActivity.this.finish();
                                                }
                                            }
                                        })
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(final SweetAlertDialog sDialog) {
                                                initData();
                                                sDialog.setTitleText("同步成功!")
                                                        .setContentText("云端数据已保存到本地!")
                                                        .setConfirmText("OK")
                                                        .showCancelButton(false)
                                                        .setCancelClickListener(null)
                                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                                                Log.e("是否存在存在号码", checkuser() + "的值");
                                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                                startActivity(intent);
                                                                Log.e("结果", "跳到00" + checkuser());
                                                                sDialog.dismiss();
                                                                LoginActivity.this.finish();


                                                            }
                                                        })
                                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                                            }
                                        })
                                        .show();
                            }
                        });
                    } else if ("fail".equals(status)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pDialog.dismiss();

                                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("帐号不存在或密码错误")
                                        .show();
                            }
                        });
                    }

                }
            }
        }.start();

    }

    /**
     * 初始化数据，将数据保存到sqlite
     */
    private void initData() {


        new Thread() {
            @Override
            public void run() {
                String result = null;
                List<TelInfo> listinfo = new ArrayList<TelInfo>();
                try {
                    //获取key
                    String key = HttpService.DataByGetKey();
                    Map<String, String> useInfos = Utils.getUserInfo(getApplicationContext());
                    Log.e("", "" + useInfos.get("userClass"));
                    String[] datas = {"key=" + key, "phonebookOperate=query", "class=" + useInfos.get("userClass")};
                    result = HttpService.DataByPost(datas);
                } catch (Exception e) {
                    Log.e("网络连接失败", "");
                    e.printStackTrace();
                }
                Log.e("initData:result", result + "值");
                listinfo = JsonParsing.phonenumber(result);
                DB.alter();
                for (int i = 0; i < listinfo.size(); i++) {
                    TelInfo t = new TelInfo(listinfo.get(i).getName(), listinfo.get(i).getNumber(), listinfo.get(i).getTel());
                    DB.insert(t);
                    Log.e("插入数据", "成功");
                }
            }

        }.start();


    }

    /**
     * 检查时候存在用户电话
     * "Y"表示原来存在号码，则直接进入主界面
     * "N"表示原来不存在号码,则跳转到添加号码界面
     */
    private String checkuser() {
        String flag = "N";
        List<TelInfo> listinfo = new ArrayList<TelInfo>();
        Map<String, String> useInfos = Utils.getUserInfo(getApplicationContext());
        listinfo = DB.queryAll();
        for (int i = 0; i < listinfo.size(); i++) {
            if (listinfo.get(i).getNumber().equals(useInfos.get("userID"))) {
                flag = "Y";
                break;
            }
        }
        return flag;
    }
}
