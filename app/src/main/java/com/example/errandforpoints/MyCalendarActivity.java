package com.example.errandforpoints;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.errandforpoints.adapter.ErrandListAdapter;
import com.example.errandforpoints.adapter.EventListAdapter;
import com.example.errandforpoints.dbhelper.ErrandDB;
import com.example.errandforpoints.dbhelper.EventDB;
import com.example.errandforpoints.dbhelper.PointDB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyCalendarActivity extends AppCompatActivity {
    CalendarView calendarView;
    Button btnAddEvent;
    ListView listView;
    EventDB eventDB;
    Switch aSwitch;
    ErrandDB errandDB;
    PointDB pointDB;
    long todayDate;
    long selectedDate = 0L;
    boolean isErrand = false;
    @Override
    protected void onStart() {
        super.onStart();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd hh mm");

        Date currentTime = Calendar.getInstance().getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Integer Year = Integer.parseInt(format.format(currentTime));
        format = new SimpleDateFormat("MM");
        Integer Month = Integer.parseInt(format.format(currentTime));
        format = new SimpleDateFormat("dd");
        Integer Date = Integer.parseInt(format.format(currentTime));

//        Month-=1;

        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy MM dd hh mm");
        Date d;
        try {
            d = newFormat.parse(String.format("%d %d %d %d %d",Year,Month,Date,0,0));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        todayDate = d.getTime();
        listView.setAdapter(new EventListAdapter(getApplicationContext(),eventDB.getAllDataToCursor(d.getTime()),true));


//        Date currentTime = Calendar.getInstance().getTime();
//        Log.d("TAG", "onStart: " + currentTime.getTime());
//        calendarView.setDate(currentTime.getTime());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_calendar);
        calendarView = findViewById(R.id.calendarView);
        btnAddEvent = findViewById(R.id.btnAddEvent);
        listView = findViewById(R.id.listMyCalendarEvents);
        eventDB = new EventDB(getApplicationContext(),1);
        errandDB = new ErrandDB(getApplicationContext(),1);

        aSwitch = findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {

                    Log.d("TAG", "onCheckedChanged: " + todayDate);
                    isErrand = true;
                    buttonView.setText("Errands");
                    long targetDate = selectedDate == 0 ? todayDate : selectedDate;
                    listView.setAdapter(new ErrandListAdapter(getApplicationContext(),errandDB.getBriefDataToCursorAfterDate(targetDate),true));
                }else {
                    isErrand = false;
                    buttonView.setText("Events");
                    Date currentTime = Calendar.getInstance().getTime();

                    SimpleDateFormat format = new SimpleDateFormat("yyyy");
                    Integer Year = Integer.parseInt(format.format(currentTime));
                    format = new SimpleDateFormat("MM");
                    Integer Month = Integer.parseInt(format.format(currentTime));
                    format = new SimpleDateFormat("dd");
                    Integer Date = Integer.parseInt(format.format(currentTime));

//                    Month-=1;

                    SimpleDateFormat newFormat = new SimpleDateFormat("yyyy MM dd hh mm");
                    Date d;
                    try {
                        d = newFormat.parse(String.format("%d %d %d %d %d",Year,Month,Date,0,0));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    long targetDate = selectedDate == 0 ? todayDate : selectedDate;
                    listView.setAdapter(new EventListAdapter(getApplicationContext(),eventDB.getAllDataToCursor(targetDate),true));
                }
            }
        });



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd hh mm");
                Date d;
                try {
                    d = format.parse(String.format("%d %d %d %d %d",year,month+1,date,0,0));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                long date_millis = EventDB.dateToLong(d);
                selectedDate = date_millis;
                Log.d("TAG", "onSelectedDayChange(dateMillis): "+date_millis);
                Log.d("TAG", "onSelectedDayChange: "+calendarView.getDate());
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("TAG", "onItemClick: " + position);
                Log.d("TAG", "onItemClick: " + id);

                errandDB.setDoneInformation(Integer.parseInt(""+id),1);
                Toast.makeText(getApplicationContext(),"this errand was sucessfully done!", Toast.LENGTH_LONG).show();
                int point = Integer.parseInt(((TextView)view.findViewById(R.id.listErrandPoint)).getText().toString());
                pointDB = new PointDB(getApplicationContext(),1);
                pointDB.addPointLog(id,point);
            }
        });
    }
}