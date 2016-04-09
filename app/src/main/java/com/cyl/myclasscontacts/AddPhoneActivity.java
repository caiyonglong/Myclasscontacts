package com.cyl.myclasscontacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cyl.myclasscontacts.db.DBoperation;
import com.cyl.myclasscontacts.db.TelInfo;
import com.cyl.myclasscontacts.utils.HttpService;
import com.cyl.myclasscontacts.utils.JsonParsing;
import com.cyl.myclasscontacts.utils.Utils;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by 永龙 on 2015/11/23.
 */
public class AddPhoneActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText NewPhone;
    private Button AddBtn;
    private String newphone;
    private DBoperation DB;
    private Map<String, String> userInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addphone);
        NewPhone = (EditText) findViewById(R.id.add_etphone);
        AddBtn = (Button) findViewById(R.id.add_btnphone);
        userInfos = Utils.getUserInfo(getApplicationContext());
        AddBtn.setOnClickListener(this);
        DB = new DBoperation(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_btnphone:
                newphone = NewPhone.getText().toString().trim();

                List<TelInfo> info = DB.queryAll();
                int myphoneNum = 0;
                Log.e("数据库中的值", info.size() + "");
                if (info.size() > 0) {

                    for (int i = 0; i < info.size(); i++) {
                        if (userInfos.get("userID").equals(info.get(i).getNumber())) {
                            myphoneNum++;
                            Log.e("我的号码", "有" + myphoneNum);
                        }
                    }
                }
                if (newphone.length() != 11) {
                    new SweetAlertDialog(AddPhoneActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("添加失败！")
                            .setContentText("请输入正确的手机号!")
                            .show();
                } else if (myphoneNum >= 3) {
                    new SweetAlertDialog(AddPhoneActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("添加失败！")
                            .setContentText("手机号最大上限3个!")
                            .show();
                } else {
                    addnewphone(newphone);
                }
                break;
        }
    }

    private void addnewphone(final String newphone) {
        // 读取xml文件
        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("mobliesoap.xml");
        try {
            // 显示电话号码地理位置，该段代码不合理，仅供参考
            new SweetAlertDialog(AddPhoneActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("查询成功！")
                    .setContentText(getMobileAddress(inStream, newphone))
                    .show();
        } catch (Exception e) {
            Log.e("tttttt", e.toString());
            Toast.makeText(AddPhoneActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
        }
    }
        /*
        new Thread() {
            @Override
            public void run() {
                String result = null;
                try {
                    String key = null;
                    //获取key
                    key = HttpService.DataByGetKey();
                    Log.e("key返回值", key + "");
                    String[] datas = {"key=" + key, "phonebookOperate=insert", "num=" + userInfos.get("userID"),
                            "name=" + userInfos.get("userName"), "class=" + userInfos.get("userClass"), "phone=" + newphone
                    };
                    result = HttpService.DataByPost(datas);
                } catch (Exception e) {
                    e.printStackTrace();
                    //请求失败
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new SweetAlertDialog(AddPhoneActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("ERROR")
                                    .setContentText("网络连接超时!")
                                    .show();
                        }
                    });
                }
                Log.e("返回值", result + "");
                String status = "{\"status\":\"success\"}";
                String status1 = "{\"status\":\"record has existed\"}";
                Log.e("标准值", status);


//              添加成功
                if (status.equals(result)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new SweetAlertDialog(AddPhoneActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("添加成功!")
                                    .setContentText(newphone + "已添加!")
                                    .setConfirmText("确定")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            // reuse previous dialog instance
                                            TelInfo telInfo = new TelInfo();
                                            telInfo.setName(userInfos.get("name"));
                                            telInfo.setName(userInfos.get("num"));
                                            telInfo.setName(userInfos.get("phone"));
                                            DB.insert(telInfo);
                                            Intent intent = new Intent(AddPhoneActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            sDialog.dismiss();
                                            AddPhoneActivity.this.finish();
                                        }
                                    })
                                    .show();
                        }
                    });
                } else if (status1.equals(result)) {
                    //请求失败
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new SweetAlertDialog(AddPhoneActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("添加失败！")
                                    .setContentText("号码已存在,请不要重复添加!")
                                    .show();
                        }
                    });
                } else {
                    //请求失败
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new SweetAlertDialog(AddPhoneActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("ERROR")
                                    .setContentText("网络连接超时!")
                                    .show();
                        }
                    });
                }
            }
        }.start();
    }
    */


    /**
     * 获取电话号码地理位置
     *
     * @param inStream
     * @param mobile
     * @return
     * @throws Exception
     */
    private String getMobileAddress(InputStream inStream, String mobile) throws Exception {

        // 替换xml文件中的电话号码
        String soap = readSoapFile(inStream, mobile);
        Log.e("moblie de zhi",soap+"de hihihih");
        byte[] data = soap.getBytes();
        // 提交Post请求
        URL url = new URL("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(5 * 1000);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
        conn.setRequestProperty("Content-Length", String.valueOf(data.length));
        OutputStream outStream = conn.getOutputStream();
        outStream.write(data);
        outStream.flush();
        outStream.close();
        if (conn.getResponseCode() == 200) {
            // 解析返回信息
            return parseResponseXML(conn.getInputStream());
        }
        return "Error";
    }


    private String readSoapFile(InputStream inStream, String mobile) throws Exception {
        // 从流中获取文件信息
        byte[] data = readInputStream(inStream);
        String soapxml = new String(data);
        // 占位符参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", mobile);
        // 替换文件中占位符
        return replace(soapxml, params);
    }


    /**
     * 读取流信息
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    private byte[] readInputStream(InputStream inputStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inputStream.close();
        return outSteam.toByteArray();
    }


    /**
     * 替换文件中占位符
     *
     * @param xml
     * @param params
     * @return
     * @throws Exception
     */
    private String replace(String xml, Map<String, String> params) throws Exception {
        String result = xml;
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String name = "\\$" + entry.getKey();
                Pattern pattern = Pattern.compile(name);
                Matcher matcher = pattern.matcher(result);
                if (matcher.find()) {
                    result = matcher.replaceAll(entry.getValue());
                }
            }
        }
        return result;
    }


    /**
     * 解析XML文件
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    private static String parseResponseXML(InputStream inStream) throws Exception {
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inStream, "UTF-8");
        int eventType = parser.getEventType();// 产生第一个事件
        while (eventType != XmlPullParser.END_DOCUMENT) {
            // 只要不是文档结束事件
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    String name = parser.getName();// 获取解析器当前指向的元素的名称
                    if ("getMobileCodeInfoResult".equals(name)) {
                        return parser.nextText();
                    }
                    break;
            }
            eventType = parser.next();
        }
        return null;
    }
}
