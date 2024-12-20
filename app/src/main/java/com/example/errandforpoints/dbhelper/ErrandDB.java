package com.example.errandforpoints.dbhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrandDB extends SQLiteOpenHelper  {
    static final String DATABASE_NAME = "ErrandForPointsDB";
    public static final String TABLE_NAME = "Errands";
    public static final String TABLE_FORMAT = "(id INTEGER, name TEXT, dday BIGINT, point INT, details TEXT, done INT, PRIMARY KEY(id AUTOINCREMENT))";
    public static final String TABLE_INSERT_FORMAT = "(name,dday,point,details,done)";
    public ErrandDB(Context context, int version) {
        super(context, DATABASE_NAME,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s%s",PointDB.TABLE_NAME,PointDB.TABLE_FORMAT));
        sqLiteDatabase.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s%s",TABLE_NAME,TABLE_FORMAT));
        sqLiteDatabase.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s%s",EventDB.TABLE_NAME,EventDB.TABLE_FORMAT));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(String.format("DROP TABLE IF EXISTS %s",TABLE_NAME));
        onCreate(sqLiteDatabase);
    }
    public void addErrand(String name, Date date, int point, String details) {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL(String.format("INSERT INTO %s%s VALUES('%s','%d','%d','%s','%d')",TABLE_NAME,TABLE_INSERT_FORMAT,name,dateToLong(date),point, details,0));
        db.close();
    }

    public static long dateToLong(Date date) {
        return date.getTime();
    }
    public static Date longToDate(long millis) {
        return new Date(millis);
    }
    public static String DateToString(Date date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }
    public Cursor getAllDataToCursor(long date) {

        String query = String.format("select * ,1 _id from %s where done=0",TABLE_NAME);
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query,null);
        return c;
    }

    public Cursor getBriefDataToCursorAfterDate(long date) {
        String query = String.format("select name,dday,point,id as _id from %s where done=0 and dday > %d",TABLE_NAME,date);
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query,null);
        return c;
    }
    public void setDoneInformation(long id, int done) {
        String query = String.format("UPDATE %s SET done=%d WHERE id=%d",TABLE_NAME,done,id);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
        return ;
    }

    public Cursor getBriefDataToCursor() {

        String query = String.format("select name,dday,point ,1 _id from %s where done=0",TABLE_NAME);
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query,null);
        return c;
    }

}
