package com.cyl.myclasscontacts.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;



/**
 * Created by 永龙 on 2015/11/12.
 */
public class HttpService {
    public static String LoginByGet(String usernumber,String password) {
        try {

            String path ="http://suen.pw/interface/senate/verify.php?num="+usernumber+"&pw="+password;
            //创建URL实例
            URL url = new URL(path);
            //获取HettpConnection对象
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream is = conn.getInputStream();
                String text=null;
                text = StreamTools.readInputStream(is);
                Log.e("text",text);
                return text;
            } else {
                Log.e("text",code+"");
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("text", "异常");
        }
        Log.e("text", "异常2");
        return null;
    }

    public static String LoginByPost(String usernumber,String password) {
        try {
            String datas ="num="+usernumber+"&pw="+password;
            String path ="http://suen.pw/interface/senate/verify.php";
            //创建URL实例
            URL url = new URL(path);
            //获取HettpConnection对象
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            //设置请求头
            //将数据传递给服务器
            Log.e("datas",datas);
            conn.setDoOutput(true);
            //得到输出流
            OutputStream os = conn.getOutputStream();
            os.write(datas.getBytes());

            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream is = conn.getInputStream();
                String text=null;
                text = StreamTools.readInputStream(is);
                return text;
            }
            else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String DataByGetKey() {
        try {
            String path = "http://suen.pw/interface/classHelper/encrypt.php";
            //创建URL实例
            URL url = new URL(path);
            //获取HettpConnection对象
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream is = conn.getInputStream();
                String text=null;
                text = StreamTools.readInputStream(is);
                Log.e("text", text);
                return text;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
    public static String DataByGetVersion() {
        try {
            String path = "http://1.hcyl.sinaapp.com/appversion/download.php";
            //创建URL实例
            URL url = new URL(path);
            //获取HettpConnection对象
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream is = conn.getInputStream();
                String text=null;
                text = StreamTools.readInputStream(is);
                Log.e("text", text);
                return text;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    //采用HttpConnection post方式传递
    public static String DataByPost(String[] data) {
        try {
            String Url="http://suen.pw/interface/classHelper/phonebook.php";
            String datas = data[0];
            for (int i = 1; i < data.length; i++) {
                datas = datas + "&" + data[i];
            }
            //创建URL实例
            URL url = new URL(Url);
            //获取HettpConnection对象
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            //设置请求头
            //将数据传递给服务器
            Log.e("datas",datas);
            conn.setDoOutput(true);
            //得到输出流
            OutputStream os = conn.getOutputStream();
            os.write(datas.getBytes());

            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream is = conn.getInputStream();
                String text=null;
                text = StreamTools.readInputStream(is);
                Log.e("xinxi", text);
                return text;

            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //采用HttpConnection post方式传递  一周课表
    public static String ScheduleBypost() {
        try {
            String Url = "http://hnust.ticknet.cn/api/schedule";

            String datas ="type=0&week=12&term=2015-2016-1";

            //创建URL实例
            URL url = new URL(Url);
            //获取HettpConnection对象
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            //设置请求头
            conn.setRequestProperty("Token","g5l5ascq8brjakpfyk3f1o139nbdiuf1");

            //将数据传递给服务器
            Log.e("datas",datas);
            conn.setDoOutput(true);
            //得到输出流
            OutputStream os = conn.getOutputStream();
            os.write(datas.getBytes());

            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream is = conn.getInputStream();
                String text=null;
                text = StreamTools.readInputStream(is);
                Log.e("xinxi", text);
                return text;

            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //全部课表
    public static String ScheduleAllBypost() {
        try {
            String Url = "http://hnust.ticknet.cn/api/schedule";

            String datas ="type=2&week=12&term=2015-2016-1";

            //创建URL实例
            URL url = new URL(Url);
            //获取HettpConnection对象
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            //设置请求头
            conn.setRequestProperty("Token","g5l5ascq8brjakpfyk3f1o139nbdiuf1");

            //将数据传递给服务器
            Log.e("datas",datas);
            conn.setDoOutput(true);
            //得到输出流
            OutputStream os = conn.getOutputStream();
            os.write(datas.getBytes());

            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream is = conn.getInputStream();
                String text=null;
                text = StreamTools.readInputStream(is);
                return text;

            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //全部课表
    public static String ScheduleScoBypost() {
        try {
            String Url = "http://hnust.ticknet.cn/api/schedule";

            String datas ="type=1&week=12&term=2015-2016-1";

            //创建URL实例
            URL url = new URL(Url);
            //获取HettpConnection对象
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            //设置请求头
            conn.setRequestProperty("Token","g5l5ascq8brjakpfyk3f1o139nbdiuf1");

            //将数据传递给服务器
            Log.e("datas",datas);
            conn.setDoOutput(true);
            //得到输出流
            OutputStream os = conn.getOutputStream();
            os.write(datas.getBytes());

            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream is = conn.getInputStream();
                String text=null;
                text = StreamTools.readInputStream(is);
                return text;

            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
