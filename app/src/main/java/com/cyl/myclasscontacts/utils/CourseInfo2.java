package com.cyl.myclasscontacts.utils;

/**
 * Created by 永龙 on 2015/11/26.
 */
public class CourseInfo2 {

        /**
         *
         */
        private static final long serialVersionUID = 2074656067805712769L;
        /** id */
        private int id;
        /** 课程名称  */
        private String courseName;
        /** 上课教室 */
        private String classRoom;
        /** 上课班级*/
        private String courseClass;
        /** 上课周次01*/
        private String weeks;
        /** 老师  */
        private String teacher;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    /** 上课时间S*/
        private String day;
        /** 上课时间（哪一节）开始（1--10） */
        private String ClassIndex;
        /** 上课时间（周次） 开始 */
        private String time;



        public static final int ALL = 1;
        public static final int ODD = 2;
        public static final int EVEN = 3;



    public void setClassIndex(String classIndex) {
        ClassIndex = classIndex;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getCourseClass() {
        return courseClass;
    }

    public void setCourseClass(String courseClass) {
        this.courseClass = courseClass;
    }

    public String getClassIndex() {
        return ClassIndex;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeeks() {
        return weeks;
    }

    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }


}
