package com.cyl.myclasscontacts.utils;

import android.util.Log;

import com.cyl.myclasscontacts.db.TelInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 永龙 on 2015/11/22.
 */
public class JsonParsing {
    public static List<TelInfo> phonenumber(String json) {
        List<TelInfo> list = new ArrayList<TelInfo>();
        TelInfo telInfo = null;
        /**
         * {
         "status": "success",
         "data": [
         {
         "num": "1305030210",
         "name": "suen",
         "class": "13信息安全2班",
         "phone": "13975088261"
         },
         {
         "num": "1305030210",
         "name": "suen",
         "class": "13信息安全2班",
         "phone": "15197251010"
         }
         ]
         }
         */
        try {
            JSONObject jsonObject = new JSONObject(json);

            String status = jsonObject.getString("status");

            Log.e("status", status+"");
            Log.e("status", status+"");


            //解析数组
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            Log.e("长度：", String.valueOf(jsonArray.length()));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = (JSONObject) jsonArray.get(i); //获取数组中每一个对象
                String name = item.getString("name");
                String num = item.getString("num");
                String phone = item.getString("phone");
                Log.e("姓名", name);
                Log.e("电话", phone);
                Log.e("学号", num);
                telInfo = new TelInfo(); // 存放到MAP里
                telInfo.setName(name);
                telInfo.setNumber(num);
                telInfo.setTel(phone);
                list.add(telInfo);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Map<String, String> Userinfo(String json) {
        Map<String, String> map = null;

        /**
         * 得到json数据
         *{
         "status": "success",
         "data": {
         "num": "1305030212",
         "name": "蔡永龙",
         "sex": "男",
         "college": "计算机科学与工程学院",
         "major": "信息安全",
         "class"
         }
         }
         */

        try {
            JSONObject jsonObject = new JSONObject(json);
            String status = jsonObject.getString("status");
            Log.e("status", status);
            //解析数组
            JSONObject item = jsonObject.getJSONObject("data");

            String num = item.getString("num");    //获取对象中的一个值
            String name = item.getString("name");    //获取对象中的一个值
            String sex = item.getString("sex");    //获取对象中的一个值
            String college = item.getString("college");    //获取对象中的一个值
            String major = item.getString("major");    //获取对象中的一个值
            String class1 = item.getString("class");    //获取对象中的一个值

            map = new HashMap<String, String>(); // 存放到MAP里面
            map.put("num", num);
            map.put("name", name);
            map.put("sex", sex);
            map.put("college", college);
            map.put("major", major);
            map.put("class", class1);


        } catch (JSONException e) {
            e.printStackTrace();
            return map;
        }
        return map;
    }
    public static Map<String, String> Versioninfo(String json) {
        Map<String, String> map = null;
        /**
         * 得到json数据
         *{
         "version_i": "2",
         "version": "3.0",
         "apk_url": "upload/app-release.apk",
         "apk_size": "302.84Kb"
         }
         */

        try {
            JSONObject item = new JSONObject(json);
            String version_i = item.getString("version_i");
            Log.e("status", version_i+"");
            //解析数组
            String version = item.getString("version");    //获取对象中的一个值
            String url = item.getString("apk_url");    //获取对象中的一个值
            String size = item.getString("apk_size");    //获取对象中的一个值

            map = new HashMap<String, String>(); // 存放到MAP里面
            map.put("version_i", version_i);
            map.put("version", version);
            map.put("url", url);
            map.put("size", size);

        } catch (JSONException e) {
            e.printStackTrace();
            return map;
        }
        return map;
    }
    //一周课表

    /**
     * {
     * "code": 0,
     * "msg": "",
     * "info": {
     * "sid": "1305030212",
     * "week": "12",
     * "term": "2015-2016-1",
     * "remarks": "计算机网络B课程设计  刘桂开  17-18周;智能终端系统及应用开发课程设计  李曙红  15-16周;标注★的为学位课程",
     * "runTime": 0.0090939998626709,
     * "mysql": 2
     * },
     * "data": {
     * "1": {
     * "1": [
     * {
     * "course": "智能终端系统及应用开发",
     * "teacher": "石林",
     * "class": "13信息安全[1-3]班",
     * "time": "9-14周",
     * "classroom": "第五教学楼401",
     * "weeks": "000000000111111000000"
     * }
     * ],
     * "2": [],
     * "3": [],
     * "4": [],
     * "5": [
     * {
     * "course": "现代密码学基础",
     * "teacher": "杨柳",
     * "class": "13信息安全[1-3]班",
     * "time": "1-14周",
     * "classroom": "第五教学楼109",
     * "weeks": "011111111111111000000"
     * }
     * ]
     * },
     * "2": {
     * "1": [],
     * "2": [],
     * "3": [],
     * "4": [],
     * "5": []
     * },
     * "3": {
     * "1": [
     * {
     * "course": "智能终端系统及应用开发",
     * "teacher": "石林",
     * "class": "13信息安全[1-3]班",
     * "time": "9-14周",
     * "classroom": "第五教学楼2-1",
     * "weeks": "000000000111111000000"
     * }
     * ],
     * "2": [],
     * "3": [],
     * "4": [
     * {
     * "course": "软件工程A",
     * "teacher": "冯建湘",
     * "class": "13信息安全[1-3]班",
     * "time": "1-12周",
     * "classroom": "四教五合班",
     * "weeks": "011111111111100000000"
     * }
     * ],
     * "5": [
     * {
     * "course": "计算机网络B",
     * "teacher": "刘桂开",
     * "class": "13信息安全[1-3]班",
     * "time": "1-2,5-14周",
     * "classroom": "第五教学楼2-2",
     * "weeks": "011001111111111000000"
     * }
     * ]
     * },
     * "4": {
     * "1": [],
     * "2": [],
     * "3": [],
     * "4": [],
     * "5": [
     * {
     * "course": "现代密码学基础",
     * "teacher": "杨柳",
     * "class": "13信息安全[1-3]班",
     * "time": "1-14周",
     * "classroom": "第五教学楼109",
     * "weeks": "011111111111111000000"
     * }
     * ]
     * },
     * "5": {
     * "1": [
     * {
     * "course": "计算机网络B",
     * "teacher": "刘桂开",
     * "class": "13信息安全[1-3]班",
     * "time": "1-2,4-14周",
     * "classroom": "第五教学楼2-2",
     * "weeks": "011011111111111000000"
     * }
     * ],
     * "2": [],
     * "3": [
     * {
     * "course": "软件工程A",
     * "teacher": "冯建湘",
     * "class": "13信息安全[1-3]班",
     * "time": "1-12周",
     * "classroom": "第五教学楼413",
     * "weeks": "011111111111100000000"
     * }
     * ],
     * "4": [],
     * "5": []
     * },
     * "6": {
     * "1": [],
     * "2": [],
     * "3": [],
     * "4": [],
     * "5": []
     * },
     * "7": {
     * "1": [],
     * "2": [],
     * "3": [],
     * "4": [],
     * "5": []
     * }
     * }
     * }
     */
    public static List<CourseInfo> JsonCourseInfo(String json) {
        List<CourseInfo> list = new ArrayList<CourseInfo>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            String code = jsonObject.getString("code");
            String msg = jsonObject.getString("msg");
            JSONObject info = jsonObject.getJSONObject("info");

            String sid = info.getString("sid");
            String week = info.getString("week");
            String term = info.getString("term");
            String remarks = info.getString("remarks");


            Log.e("code", code);
            Log.e("ct1321t", msg);
            Log.e("cccc21312c", sid);
            Log.e("time", week);
            Log.e("weee1111e", term);
            Log.e("we111", remarks);
            int m = 0;
            JSONObject data = jsonObject.getJSONObject("data");
            for (int k = 1; k < 8; k++) {
                String name1 = "" + k;
                JSONObject weekday = data.getJSONObject(name1);
                for (int j = 1; j < 6; j++) {
                    String name = "" + j;
                    JSONArray day = weekday.getJSONArray(name);
                    for (int i = 0; i < day.length(); i++) {
                        JSONObject course = (JSONObject) day.get(i);
                        String coursename = course.getString("course");
                        String courseteacher = course.getString("teacher");
                        String courseclass = course.getString("class");
                        String coursetime = course.getString("time");
                        String courseclassroom = course.getString("classroom");
                        String weeks = course.getString("weeks");

                        CourseInfo courseInfo = new CourseInfo();
                        courseInfo.setCourseName(coursename);
                        courseInfo.setClassRoom(courseclassroom);
                        /** 上课时间（哪一天）（周一--周日）day */
                        /** 上课时间（哪一节）开始（1--10）classINdex */
                        /** 上课时间（哪一周） 开始 */
                        courseInfo.setTime(coursetime);
                        courseInfo.setTeacher(courseteacher);
                        courseInfo.setWeeks(weeks);
                        courseInfo.setCourseClass(courseclass);
                        courseInfo.setDay(k);
                        courseInfo.setClassIndex(j);
                        list.add(courseInfo);

                        Log.e("coursenam", coursename);
                        Log.e("ctt", courseteacher);
                        Log.e("ccccc", courseclass);
                        Log.e("time", coursetime);
                        Log.e("weeee", courseclassroom);
                        Log.e("we", weeks);

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }

        return list;
    }


    /**
     * {
     * "code": 0,
     * "msg": "",
     * "info": {
     * "sid": "1305030212",
     * "week": "12",
     * "term": "2015-2016-1",
     * "remarks": "计算机网络B课程设计  刘桂开  17-18周;智能终端系统及应用开发课程设计  李曙红  15-16周;标注★的为学位课程",
     * "runTime": 0.0086820125579834,
     * "mysql": 2
     * },
     * "data": [
     * {
     * "course": "计算机网络B",
     * "teacher": "刘桂开",
     * "class": "13信息安全[1-3]班",
     * "time": "1-3周",
     * "classroom": "第五教学楼301",
     * "weeks": "011100000000000000000",
     * "day": 1,
     * "session": 1
     * },
     * {
     * "course": "智能终端系统及应用开发",
     * "teacher": "石林",
     * "class": "13信息安全[1-3]班",
     * "time": "9-14周",
     * "classroom": "第五教学楼401",
     * "weeks": "000000000111111000000",
     * "day": 1,
     * "session": 1
     * },
     * {
     * "course": "IT行业资格认证考试辅导D ",
     * "teacher": "彭献武",
     * "class": "13信息安全[1-3]班,13物联[1-2]班",
     * "time": "1-8周",
     * "classroom": "第五教学楼2-1",
     * "weeks": "011111111000000000000",
     * "day": 1,
     * "session": 4
     * },
     * {
     * "course": "现代密码学基础",
     * "teacher": "杨柳",
     * "class": "13信息安全[1-3]班",
     * "time": "1-14周",
     * "classroom": "第五教学楼109",
     * "weeks": "011111111111111000000",
     * "day": 1,
     * "session": 5
     * },
     * {
     * "course": "形势与政策",
     * "teacher": "曾治国",
     * "class": "13网络[1-3]班,13计算机[1-5]班,13信息安全[1-3]班,13物联[1-2]班",
     * "time": "5周",
     * "classroom": "第五教学楼2-3",
     * "weeks": "000001000000000000000",
     * "day": 2,
     * "session": 3
     * },
     * {
     * "course": "智能终端系统及应用开发",
     * "teacher": "石林",
     * "class": "13信息安全[1-3]班",
     * "time": "7-8周",
     * "classroom": "第五教学楼109",
     * "weeks": "000000011000000000000",
     * "day": 2,
     * "session": 5
     * },
     * {
     * "course": "智能终端系统及应用开发",
     * "teacher": "石林",
     * "class": "13信息安全[1-3]班",
     * "time": "9-14周",
     * "classroom": "第五教学楼2-1",
     * "weeks": "000000000111111000000",
     * "day": 3,
     * "session": 1
     * },
     * {
     * "course": "IT行业资格认证考试辅导D ",
     * "teacher": "彭献武",
     * "class": "13信息安全[1-3]班,13物联[1-2]班",
     * "time": "1-8周",
     * "classroom": "第五教学楼2-1",
     * "weeks": "011111111000000000000",
     * "day": 3,
     * "session": 2
     * },
     * {
     * "course": "软件工程A",
     * "teacher": "冯建湘",
     * "class": "13信息安全[1-3]班",
     * "time": "1-12周",
     * "classroom": "四教五合班",
     * "weeks": "011111111111100000000",
     * "day": 3,
     * "session": 4
     * },
     * {
     * "course": "计算机网络B",
     * "teacher": "刘桂开",
     * "class": "13信息安全[1-3]班",
     * "time": "1-2,5-14周",
     * "classroom": "第五教学楼2-2",
     * "weeks": "011001111111111000000",
     * "day": 3,
     * "session": 5
     * },
     * {
     * "course": "智能终端系统及应用开发",
     * "teacher": "石林",
     * "class": "13信息安全[1-3]班",
     * "time": "7-8周",
     * "classroom": "第五教学楼2-5",
     * "weeks": "000000011000000000000",
     * "day": 4,
     * "session": 2
     * },
     * {
     * "course": "现代密码学基础",
     * "teacher": "杨柳",
     * "class": "13信息安全[1-3]班",
     * "time": "1-14周",
     * "classroom": "第五教学楼109",
     * "weeks": "011111111111111000000",
     * "day": 4,
     * "session": 5
     * },
     * {
     * "course": "计算机网络B",
     * "teacher": "刘桂开",
     * "class": "13信息安全[1-3]班",
     * "time": "1-2,4-14周",
     * "classroom": "第五教学楼2-2",
     * "weeks": "011011111111111000000",
     * "day": 5,
     * "session": 1
     * },
     * {
     * "course": "软件工程A",
     * "teacher": "冯建湘",
     * "class": "13信息安全[1-3]班",
     * "time": "1-12周",
     * "classroom": "第五教学楼413",
     * "weeks": "011111111111100000000",
     * "day": 5,
     * "session": 3
     * },
     * {
     * "course": "自动化与两型社会建设",
     * "teacher": "周少武",
     * "class": "临班406",
     * "time": "6-10,13-16周",
     * "classroom": "第五教学楼413",
     * "weeks": "000000111110011110000",
     * "day": 6,
     * "session": 1
     * },
     * {
     * "course": "书法鉴赏(公选)",
     * "teacher": "徐斌",
     * "class": "临班409",
     * "time": "6-10,13-16周",
     * "classroom": "第五教学楼2-6",
     * "weeks": "000000111110011110000",
     * "day": 6,
     * "session": 2
     * }
     * ]
     * }
     */
    public static List<CourseInfo2> JsonAllCourseInfo(String json) {
        List<CourseInfo2> list = new ArrayList<CourseInfo2>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            String code = jsonObject.getString("code");
            String msg = jsonObject.getString("msg");
            JSONObject info = jsonObject.getJSONObject("info");

            String sid = info.getString("sid");
            String week = info.getString("week");
            String term = info.getString("term");
            String remarks = info.getString("remarks");


            Log.e("code", code);
            Log.e("ct1321t", msg);
            Log.e("cccc21312c", sid);
            Log.e("time", week);
            Log.e("weee1111e", term);
            Log.e("we111", remarks);
            int m = 0;
            /**
             * {
             * "course": "书法鉴赏(公选)",
             * "teacher": "徐斌",
             * "class": "临班409",
             * "time": "6-10,13-16周",
             * "classroom": "第五教学楼2-6",
             * "weeks": "000000111110011110000",
             * "day": 6,   //星期几
             * "session": 2  //第2*session-1　---2*session节课
             * }
             */
            JSONArray data = jsonObject.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                JSONObject course = (JSONObject) data.get(i);
                String coursename = course.getString("course");
                String courseteacher = course.getString("teacher");
                String courseclass = course.getString("class");
                String coursetime = course.getString("time");
                String courseclassroom = course.getString("classroom");
                String weeks = course.getString("weeks");
                String courseday = course.getString("day");
                String session = course.getString("session");

                CourseInfo2 courseInfo = new CourseInfo2();
                courseInfo.setCourseName(coursename);
                courseInfo.setClassRoom(courseclassroom);
                /** 上课时间（哪一天）（周一--周日）day */
                /** 上课时间（哪一节）开始（1--10）classINdex */
                /** 上课时间（哪一周） 开始 */
                courseInfo.setTime(coursetime);
                courseInfo.setTeacher(courseteacher);
                courseInfo.setWeeks(weeks);
                courseInfo.setCourseClass(courseclass);
                courseInfo.setDay(courseday);
                courseInfo.setClassIndex(session);
                list.add(courseInfo);

                Log.e("coursenam", coursename);
                Log.e("ctt", courseteacher);
                Log.e("ccccc", courseclass);
                Log.e("time", coursetime);
                Log.e("weeee", courseclassroom);
                Log.e("we", weeks);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }

        return list;
    }


}
