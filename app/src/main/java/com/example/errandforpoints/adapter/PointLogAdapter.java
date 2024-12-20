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

import java.text.SimpleDateFormat;
import java.util.Date;

public class PointLogAdapter extends CursorAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public PointLogAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = mLayoutInflater.inflate(R.layout.pointlog_listview,viewGroup,false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        long dateMillis = cursor.getLong(0);
        Long id =  cursor.getLong(1);
        int points = cursor.getInt(2);

        Date date = new Date(dateMillis);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(date);
        TextView textView1;
        TextView textView2;
        TextView textView3;
        textView1 = view.findViewById(R.id.listLogDate);
        textView2 = view.findViewById(R.id.listLogID);
        textView3 = view.findViewById(R.id.listLogPoint);
        textView1.setText(dateString);
        textView2.setText(""+id);
        textView3.setText(""+points);

    }
}
