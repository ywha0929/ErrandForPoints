package com.example.errandforpoints.dbhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PointDB extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "ErrandForPointsDB";
    public static final String TABLE_NAME = "Points";
    public static final String TABLE_FORMAT = "(time BIGINT, id BIGINT, point INT)";

    public PointDB(Context context, int version) {
        super(context, DATABASE_NAME,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s%s",TABLE_NAME,TABLE_FORMAT));
        sqLiteDatabase.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s%s",ErrandDB.TABLE_NAME,ErrandDB.TABLE_FORMAT));
        sqLiteDatabase.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s%s",EventDB.TABLE_NAME,EventDB.TABLE_FORMAT));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(String.format("DROP TABLE IF EXISTS %s",TABLE_NAME));
        onCreate(sqLiteDatabase);
    }
    public void addPointLog(long id, int point) {
        SQLiteDatabase db = getWritableDatabase();

        long timeNow = System.currentTimeMillis();
        String query = String.format("INSERT INTO %s VALUES('%d','%d','%d')",TABLE_NAME,timeNow,id,point);
        db.execSQL(query);
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
    public Cursor getAllDataToCursor() {

        String query = String.format("select * ,1 _id from %s",TABLE_NAME);
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query,null);
        return c;
    }
    public long getSumOfAllPoints() {
        String query = String.format("select sum(point) from %s",TABLE_NAME);
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query,null);
        Log.d("TAG", "getSumOfAllPoints: "+c.getColumnCount());
        c.moveToFirst();
        return c.getLong(0);
    }
}
