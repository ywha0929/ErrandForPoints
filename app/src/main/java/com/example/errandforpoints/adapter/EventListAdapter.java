package com.example.errandforpoints.adapter;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.errandforpoints.R;
import com.example.errandforpoints.dbhelper.EventDB;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventListAdapter extends CursorAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public EventListAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = mLayoutInflater.inflate(R.layout.event_listview,viewGroup,false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        long time_millis = cursor.getLong(0);
        String detail = cursor.getString(1);
        TextView textViewDate = view.findViewById(R.id.textDate);
        TextView textViewDetail = view.findViewById(R.id.textDetail);
        TextView textViewTime = view.findViewById(R.id.textTime);
        Date date = EventDB.longToDate(time_millis);

        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Integer Year = Integer.parseInt(format.format(date));
        format = new SimpleDateFormat("MM");
        Integer Month = Integer.parseInt(format.format(date));
        format = new SimpleDateFormat("dd");
        Integer Date = Integer.parseInt(format.format(date));

        SimpleDateFormat format2 = new SimpleDateFormat("HH : mm");


        textViewDate.setText(""+Year + "-"+(Month)+"-"+Date);
        textViewTime.setText(""+format2.format(date));
        textViewDetail.setText(detail);
    }
}
