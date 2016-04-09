package com.cyl.myclasscontacts.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 永龙 on 2015/10/28.
 */
public class Utils {
    //保存个人信息到data.xml文件中
    public static boolean savaUserInfo(Context context, Map<String, String> userInfo){
        SharedPreferences sp=context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sp.edit();
        editor.putString("userID",userInfo.get("num"));
        editor.putString("userPW",userInfo.get("pw"));
        editor.putString("userName",userInfo.get("name"));
        editor.putString("userSex",userInfo.get("sex"));
        editor.putString("userCollege",userInfo.get("college"));
        editor.putString("userMajor",userInfo.get("major"));
        editor.putString("userClass",userInfo.get("class"));
        editor.commit();
        return true;
    }
    //从data.xml文件中取出个人信息
    public static Map<String,String> getUserInfo(Context context){
        SharedPreferences sp=context.getSharedPreferences("data", Context.MODE_PRIVATE);
        String userID = sp.getString("userID",null);
        String pw = sp.getString("userPW",null);
        String userName = sp.getString("userName",null);
        String userSex = sp.getString("userSex",null);
        String userCollege = sp.getString("userCollege",null);
        String userMajor = sp.getString("userMajor",null);
        String userClass = sp.getString("userClass", null);
        Map<String,String> userMap =new HashMap<String,String>();
        userMap.put("userID", userID);
        userMap.put("userPW", pw);
        userMap.put("userName", userName);
        userMap.put("userSex", userSex);
        userMap.put("userCollege", userCollege);
        userMap.put("userMajor", userMajor);
        userMap.put("userClass", userClass);
        return userMap;
    }

    public static boolean savaCourseInfo(Context context,String coursejson){
        SharedPreferences sp=context.getSharedPreferences("CourseInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sp.edit();
        editor.putString("coursejson",coursejson);
        editor.commit();
        return true;
    }
    //从data.xml文件中取出数据账号和密码
    public static String getCourseInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("CourseInfo", Context.MODE_PRIVATE);
        String coursejson = sp.getString("coursejson", null);
        return coursejson;
    }
    public static boolean savaVersioninfo(Context context,String coursejson){
        SharedPreferences sp=context.getSharedPreferences("versionjson", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sp.edit();
        editor.putString("versionjson",coursejson);
        editor.commit();
        return true;
    }
    //从data.xml文件中取出数据账号和密码
    public static String getVersioninfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("Versioninfo", Context.MODE_PRIVATE);
        String coursejson = sp.getString("versionjson", null);
        return coursejson;
    }





}
