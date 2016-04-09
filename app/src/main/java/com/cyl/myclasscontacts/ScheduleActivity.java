package com.cyl.myclasscontacts;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cyl.myclasscontacts.adapter.CourseInfoAdapter;
import com.cyl.myclasscontacts.utils.CourseInfo;
import com.cyl.myclasscontacts.utils.CourseInfoGallery;
import com.cyl.myclasscontacts.utils.DateString;
import com.cyl.myclasscontacts.utils.HttpService;
import com.cyl.myclasscontacts.utils.JsonParsing;
import com.cyl.myclasscontacts.utils.Utils;

import java.util.Calendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by 永龙 on 2015/11/24.
 */
public class ScheduleActivity extends AppCompatActivity {
    /**
     * 第一个无内容的格子
     */
    protected TextView empty;
    /**
     * 星期一的格子
     */
    protected TextView monColum;
    /**
     * 星期二的格子
     */
    protected TextView tueColum;
    /**
     * 星期三的格子
     */
    protected TextView wedColum;
    /**
     * 星期四的格子
     */
    protected TextView thrusColum;
    /**
     * 星期五的格子
     */
    protected TextView friColum;
    /**
     * 星期六的格子
     */
    protected TextView satColum;
    /**
     * 星期日的格子
     */
    protected TextView sunColum;
    /**
     * 课程表body部分布局
     */
    protected RelativeLayout course_table_layout;
    /**
     * 屏幕宽度
     **/
    protected int screenWidth;
    /**
     * 课程格子平均宽度
     **/
    protected int aveWidth;
    private int month;
    private int day;
    private Spinner mySpinner;
    private ArrayAdapter<String> adapter;
    private static final String[] Date={"第一周","第二周"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);

        new Thread() {
            @Override
            public void run() {
                super.run();
                String tt = HttpService.ScheduleBypost();
                Utils.savaCourseInfo(getApplicationContext(), tt);
                List<CourseInfo> courseInfoList = JsonParsing.JsonCourseInfo(tt);
                Log.e("baocun", "chenggogn");
                Log.e("TT", tt + ""+courseInfoList.size());
            }
        }.start();
        initDate();
        //获得列头的控件
        empty = (TextView) this.findViewById(R.id.test_empty);
        monColum = (TextView) this.findViewById(R.id.test_monday_course);
        tueColum = (TextView) this.findViewById(R.id.test_tuesday_course);
        wedColum = (TextView) this.findViewById(R.id.test_wednesday_course);
        thrusColum = (TextView) this.findViewById(R.id.test_thursday_course);
        friColum = (TextView) this.findViewById(R.id.test_friday_course);
        satColum = (TextView) this.findViewById(R.id.test_saturday_course);
        sunColum = (TextView) this.findViewById(R.id.test_sunday_course);
        course_table_layout = (RelativeLayout) this.findViewById(R.id.test_course_rl);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //屏幕宽度
        int width = dm.widthPixels;
        //平均宽度
        int aveWidth = width / 8;
        //第一个空白格子设置为25宽
        empty.setWidth(aveWidth * 3 / 4);
        monColum.setWidth(aveWidth * 33 / 32 + 1);
        tueColum.setWidth(aveWidth * 33 / 32 + 1);
        wedColum.setWidth(aveWidth * 33 / 32 + 1);
        thrusColum.setWidth(aveWidth * 33 / 32 + 1);
        friColum.setWidth(aveWidth * 33 / 32 + 1);
        satColum.setWidth(aveWidth * 33 / 32 + 1);
        sunColum.setWidth(aveWidth * 33 / 32 + 1);
        this.screenWidth = width;
        this.aveWidth = aveWidth;
        int height = dm.heightPixels;
        int gridHeight = height / 12;
        //设置课表界面
        //动态生成12 * maxCourseNum个textview
        for (int i = 1; i <= 10; i++) {

            for (int j = 1; j <= 8; j++) {

                TextView tx = new TextView(ScheduleActivity.this);
                tx.setId((i - 1) * 8 + j);
                //除了最后一列，都使用course_text_view_bg背景（最后一列没有右边框）
                if (j == 1)
                    tx.setBackgroundDrawable(ScheduleActivity.this.
                            getResources().getDrawable(R.drawable.course_text_view_bg));
                else
                    tx.setBackgroundDrawable(ScheduleActivity.this.
                            getResources().getDrawable(R.drawable.course_course_view_bg));
                //相对布局参数
                RelativeLayout.LayoutParams rp = new RelativeLayout.LayoutParams(
                        aveWidth * 33 / 32 + 1,
                        gridHeight);
                //文字对齐方式
                tx.setGravity(Gravity.CENTER);
                //字体样式
                tx.setTextAppearance(this, R.style.courseTableText);
                //如果是第一列，需要设置课的序号（1 到 12）
                if (j == 1) {
                    tx.setText(String.valueOf(i));
                    rp.width = aveWidth * 3 / 4;
                    //设置他们的相对位置
                    if (i == 1)
                        rp.addRule(RelativeLayout.BELOW, empty.getId());
                    else
                        rp.addRule(RelativeLayout.BELOW, (i - 1) * 8);
                } else {
                    rp.addRule(RelativeLayout.RIGHT_OF, (i - 1) * 8 + j - 1);
                    rp.addRule(RelativeLayout.ALIGN_TOP, (i - 1) * 8 + j - 1);
                    tx.setText("");
                }

                tx.setLayoutParams(rp);
                course_table_layout.addView(tx);
            }
        }
        String courseinfo = Utils.getCourseInfo(getApplicationContext());
        Log.e("dddddddddddddddddd", courseinfo+"");
        final List<CourseInfo> courseInfoList = JsonParsing.JsonCourseInfo(courseinfo);

        //五种颜色的背景
        int[] background = {R.drawable.course_info_blue, R.drawable.course_info_green,
                R.drawable.course_info_red, R.drawable.course_info_red,
                R.drawable.course_info_yellow};

        final String[] course = {"智能终端系统及应用开发\n@第五教学楼109", "智能终端系统及应用开发\n@第五教学楼2-1",
                "计算机网络\n@第五教学楼2-2", "现代密码学基础\n@第五教学楼109",
                "现代密码学基础\n@第五教学楼109", "计算机网络\n@第五教学楼2-1", "软件工程A\n@第四教学楼五合班"
                , "软件工程A\n@第五教学楼403"
        };
        final int[] time = {1, 1, 5, 5, 5, 1, 2, 3};
        int[] day = {1, 3, 3, 1, 4, 5, 3, 5};

        for (int i = 0; i < courseInfoList.size(); i++) {
            // 添加课程信息
            TextView courseInfo = new TextView(this);
            courseInfo.setText(courseInfoList.get(i).getCourseName()
                    + "\n@" + courseInfoList.get(i).getClassRoom());
            //该textview的高度根据其节数的跨度来设置
            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                    aveWidth * 31 / 32,
                    (gridHeight - 3) * 2);
            //textview的位置由课程开始节数和上课的时间（day of week）确定
            rlp.topMargin = 2 + (courseInfoList.get(i).getClassIndex() - 1) * gridHeight * 2;
            rlp.leftMargin = 3;
            // 偏移由这节课是星期几决定
            rlp.addRule(RelativeLayout.RIGHT_OF, courseInfoList.get(i).getDay());
            //字体剧中
            courseInfo.setGravity(Gravity.CENTER);
            // 设置一种背景
            int colorIndex = ((courseInfoList.get(i).getClassIndex() - 1) * 8 + courseInfoList.get(i).getDay()) % (background.length - 1);
            courseInfo.setBackgroundResource(background[colorIndex]);
            courseInfo.setTextSize(12);
            courseInfo.setLayoutParams(rlp);
            courseInfo.setTextColor(Color.WHITE);
            //设置不透明度
            courseInfo.getBackground().setAlpha(666);
            final int finalI1 = i;
            courseInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), 2 * courseInfoList.get(finalI1).getClassIndex()
                            + "-" + (2 * courseInfoList.get(finalI1).getClassIndex() - 1) + "节的" + courseInfoList.get(finalI1).getClassIndex(), Toast.LENGTH_SHORT).show();

                    SweetAlertDialog sweetAlertDialog =new SweetAlertDialog(ScheduleActivity.this,SweetAlertDialog.NORMAL_TYPE);
                    sweetAlertDialog.setTitle("课程");
                    sweetAlertDialog.setContentText(courseInfoList.get(finalI1).getCourseName());
                    sweetAlertDialog.show();
                }
            });
            course_table_layout.addView(courseInfo);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    private void initDate(){
        Log.e("当前日期为", DateString.StringData());
        mySpinner = (Spinner) findViewById(R.id.my_spinner);
        adapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Date);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mySpinner.setAdapter(adapter);

    }

}