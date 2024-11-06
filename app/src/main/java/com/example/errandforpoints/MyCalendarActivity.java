package com.example.errandforpoints;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.errandforpoints.adapter.EventListAdapter;
import com.example.errandforpoints.dbhelper.EventDB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyCalendarActivity extends AppCompatActivity {
    CalendarView calendarView;
    Button btnAddEvent;
    ListView listView;
    EventDB eventDB;

    @Override
    protected void onStart() {
        super.onStart();
        Date currentTime = Calendar.getInstance().getTime();

        calendarView.setDate(currentTime.getTime());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_calendar);
        calendarView = findViewById(R.id.calendarView);
        btnAddEvent = findViewById(R.id.btnAddEvent);
        listView = findViewById(R.id.listMyCalendarEvents);
        eventDB = new EventDB(getApplicationContext(),1);



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd");
                Date d;
                try {
                    d = format.parse(String.format("%d %d %d",year,month,date));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                long date_millis = EventDB.dateToLong(d);

                listView.setAdapter(new EventListAdapter(getApplicationContext(),eventDB.getAllDataToCursor(date_millis),true));
                //
                Toast.makeText(getApplicationContext(),"i : " + year + ", i1 : " + month + ",i2 : " + date,Toast.LENGTH_LONG).show();
//                SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd hh mm");
//                Date d;
//                try {
//                    d = format.parse(String.format("%d %d %d",year,month,date));
//                } catch (ParseException e) {
//                    throw new RuntimeException(e);
//                }

            }
        });

        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddEventActivity.class));
            }
        });
    }
}