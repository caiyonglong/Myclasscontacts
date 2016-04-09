package com.cyl.myclasscontacts;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cyl.myclasscontacts.db.DBoperation;
import com.cyl.myclasscontacts.db.TelInfo;
import com.cyl.myclasscontacts.utils.JsonParsing;
import com.cyl.myclasscontacts.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 永龙 on 2015/11/23.
 */
public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView name;
    private TextView number;
    private LinearLayout cancel;
    private LinearLayout change_phone;
    private LinearLayout add_phone;
    private LinearLayout owner_phone;
    private TextView my_phone;
    private List<String> list;
    private DBoperation DB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        Map<String, String> useInfos = Utils.getUserInfo(this);
        name = (TextView) findViewById(R.id.user_name);
        number = (TextView) findViewById(R.id.user_number);
        cancel = (LinearLayout) findViewById(R.id.user_cancel);
        change_phone = (LinearLayout) findViewById(R.id.user_change_phone);
        add_phone = (LinearLayout) findViewById(R.id.user_add_phone);
        owner_phone = (LinearLayout) findViewById(R.id.user_owner_phone);
        my_phone = (TextView) findViewById(R.id.my_phone);

        name.setText(useInfos.get("userName"));
        number.setText(useInfos.get("userID"));
        DB =new DBoperation(this);
        /*
        获取自己的手机号
         */
        DBoperation DB =new DBoperation(this);
        List<TelInfo> info = DB.queryAll();
        String myphone = null;
        if (info != null) {
            for (int i = 0; i < info.size(); i++) {
                if (info.get(i).getName() != null) {
                    if (useInfos.get("userID").equals(info.get(i).getNumber())) {
                        myphone += info.get(i).getTel() + "\n";
                    }
                }
            }
        }
        my_phone.setText(myphone + "");

        change_phone.setOnClickListener(this);
        cancel.setOnClickListener(this);
        add_phone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_cancel:
                AlertDialog.Builder altDlgBulider = new AlertDialog.Builder(UserInfoActivity.this);
                altDlgBulider.setTitle("是否注销");
                altDlgBulider.setCancelable(false);
                altDlgBulider.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Map<String, String> useInfos = Utils.getUserInfo(getApplicationContext());

                        SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
                        sp.edit().remove("userPW").remove("userName").remove("userSex").remove("userCollege")
                                .remove("userClass")
                                .remove("userMajor").commit();
                        DB.alter();
                        String usernumber = useInfos.get("userID");
                        Log.e("numbere", usernumber);
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                altDlgBulider.setNegativeButton("否", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //点击了取消
                        Log.e("点击了取消", "");
                    }
                });
                altDlgBulider.show();
                break;
            case R.id.user_change_phone:
                Intent intent = new Intent(getApplicationContext(), UpdatePhoneActivity.class);
                startActivity(intent);
                break;
            case R.id.user_add_phone:
                Intent intent1 = new Intent(getApplicationContext(), AddPhoneActivity.class);
                startActivity(intent1);
                break;
            case R.id.user_owner_phone:
                break;
        }

    }


}

