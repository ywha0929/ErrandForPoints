package com.example.errandforpoints.dbhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;


public class EventDB extends SQLiteOpenHelper{

    static final String DATABASE_NAME = "ErrandForPointsDB";
    public static final String TABLE_NAME = "Events";
    static final String TABLE_FORMAT = "(time BIGINT, detail TEXT)";

    public EventDB(Context context, int version) {
        super(context, DATABASE_NAME,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s%s",TABLE_NAME,TABLE_FORMAT));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(String.format("DROP TABLE IF EXISTS %s",TABLE_NAME));
        onCreate(sqLiteDatabase);
    }
    public void addEvent(Date date, String detail) {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL(String.format("INSERT INTO %s VALUES('%d','%s')",TABLE_NAME,dateToLong(date),detail));
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
        long upperbound = date + 24*60*60*1000;
        Log.d("TAG", "getAllDataToCursor: "+date);
        Log.d("TAG", "getAllDataToCursor: "+upperbound);
        String query = String.format("select * ,1 _id from %s where time > %d and time < %d",TABLE_NAME,date,upperbound);
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query,null);
        return c;
    }
}
