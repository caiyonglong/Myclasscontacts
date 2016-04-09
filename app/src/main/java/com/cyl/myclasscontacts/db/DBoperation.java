package com.cyl.myclasscontacts.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 永龙 on 2015/12/2.
 */
public class DBoperation {
    private ContactSQLiteOpenHelper helper;

    public DBoperation(Context context) {
        helper = new ContactSQLiteOpenHelper(context);
    }

    public void insert(TelInfo telInfo) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values =new ContentValues();
        if (telInfo!=null) {

            values.put("name", telInfo.getName());
            values.put("number", telInfo.getNumber());
            values.put("tel", telInfo.getTel());
            long id = db.insert("contacts", null, values);
            telInfo.setId(id);
        }
        db.close();
    }
    public void alter(){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from contacts");
        db.execSQL("update sqlite_sequence SET seq = 0 where name ='contacts'");
        db.close();
    }

    public int delete(long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int count =db.delete("contacts", "_id=?", new String[]{id + ""});
        db.close();
        return count;
    }
    public int update(TelInfo telInfo) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put("name",telInfo.getName());
        values.put("number",telInfo.getNumber());
        values.put("tel", telInfo.getTel());

        int count =db.update("contacts", values, "_id=?", new String[]{telInfo.getId() + ""});
        db.close();
        return count;
    }
    public static void hello() {
        Log.e("hel;","hell");
    }

    public List<TelInfo> queryAll(){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor =db.query("contacts", null, null, null, null, null, null);

        List<TelInfo> list =new ArrayList<TelInfo>();
        while (cursor.moveToNext()){
            long id =cursor.getLong(cursor.getColumnIndex("_id"));
            String name = cursor.getString(1);
            String number =cursor.getString(2);
            String Tel =cursor.getString(3);
            list.add(new TelInfo(id,name,number,Tel));
        }
        cursor.close();
        db.close();
        return list;
    }

}
