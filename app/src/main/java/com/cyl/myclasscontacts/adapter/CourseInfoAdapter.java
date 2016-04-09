package com.cyl.myclasscontacts.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cyl.myclasscontacts.R;
import com.cyl.myclasscontacts.utils.CourseInfo;
import com.cyl.myclasscontacts.utils.CourseInfoGallery;

import java.util.List;

/**
 * Created by 永龙 on 2015/11/26.
 */
public class CourseInfoAdapter extends BaseAdapter {


    private Context context;
    private TextView[] courseTextViewList;
    private int screenWidth;
    private int currentWeek;

    public CourseInfoAdapter(Context context, List<CourseInfo> courseList, int width, int currentWeek) {
        super();
        this.screenWidth = width;
        this.context = context;
        this.currentWeek = currentWeek;
        createGalleryWithCourseList(courseList);
    }

    private void createGalleryWithCourseList(List<CourseInfo> courseList) {
        //五种颜色的背景
        int[] background = {R.drawable.course_info_blue, R.drawable.course_info_green,
                R.drawable.course_info_red, R.drawable.course_info_red,
                R.drawable.course_info_yellow};
        this.courseTextViewList = new TextView[courseList.size()];
        for (int i = 0; i < courseList.size(); i++) {
            final CourseInfo course = courseList.get(i);
            TextView textView = new TextView(context);
            textView.setText(course.getCourseName() + "@" + course.getClassRoom());
            textView.setLayoutParams(new CourseInfoGallery.LayoutParams((screenWidth / 6) * 3, (screenWidth / 6) * 3));
            textView.setTextColor(Color.WHITE);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(10, 0, 0, 0);

            textView.getBackground().setAlpha(222);
//			textView.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View arg0) {
//					// TODO Auto-generated method stub
//					Intent intent = new Intent();
//					Bundle mBundle = new Bundle();
//					mBundle.putSerializable("courseInfo", course);
//					intent.putExtras(mBundle);
//					intent.setClass(context, DetailCourseInfoActivity.class);
//					context.startActivity(intent);
//				}
//			});
            this.courseTextViewList[i] = textView;
        }
    }

    @Override
    public int getCount() {

        return courseTextViewList.length;
    }

    @Override
    public Object getItem(int index) {

        return courseTextViewList[index];
    }

    @Override
    public long getItemId(int arg0) {

        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return courseTextViewList[position];
    }

    public float getScale(boolean focused, int offset) {
        return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
    }


}
