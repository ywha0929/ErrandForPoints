package com.example.errandforpoints.adapter;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.errandforpoints.R;
import com.example.errandforpoints.dbhelper.ErrandDB;
import com.example.errandforpoints.dbhelper.EventDB;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrandListAdapter extends CursorAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public ErrandListAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = mLayoutInflater.inflate(R.layout.errand_listview,viewGroup,false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String errandName = cursor.getString(0);
        Long dateMillis =  cursor.getLong(1);
        int points = cursor.getInt(2);

        Date date = ErrandDB.longToDate(dateMillis);
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Integer Year = Integer.parseInt(format.format(date));
        format = new SimpleDateFormat("MM");
        Integer Month = Integer.parseInt(format.format(date));
        format = new SimpleDateFormat("dd");
        Integer Date = Integer.parseInt(format.format(date));



        TextView Name = view.findViewById(R.id.listErrandName);
        TextView TextDate = view.findViewById(R.id.listErrandDate);
        TextView Point = view.findViewById(R.id.listErrandPoint);
        ErrandDB.longToDate(dateMillis);
        Name.setText(errandName);

        TextDate.setText(String.format("%d-%d-%d",Year,Month,Date));
        Point.setText(""+points);

    }
}
