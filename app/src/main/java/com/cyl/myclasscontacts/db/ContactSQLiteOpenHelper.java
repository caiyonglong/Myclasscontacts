package com.cyl.myclasscontacts.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 永龙 on 2015/12/2.
 */
public class ContactSQLiteOpenHelper extends SQLiteOpenHelper {

    public ContactSQLiteOpenHelper(Context context) {
        super(context, "contacts.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("oncreate","创建数据库");
        db.execSQL("create table contacts(" +
                "_id integer  primary key autoincrement," +
                "name varchar(20)," +
                "number varchar(20)," +
                "tel varchar(20) )" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("alter table contacts add account varchar(20)");
    }
}
