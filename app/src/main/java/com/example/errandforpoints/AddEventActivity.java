package com.example.errandforpoints;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.errandforpoints.dbhelper.EventDB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddEventActivity extends AppCompatActivity {
    static EventDB eventDB;
    DatePicker datePicker;
    TimePicker timePicker;
    Button btnInsert;
    Button btnCancel;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_event);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        btnInsert = findViewById(R.id.btnAddEventInsert);
        btnCancel = findViewById(R.id.btnAddEventCancel);
        editText = findViewById(R.id.editAddEventDetail);

        eventDB = new EventDB(getApplicationContext(),1);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                Log.d("TAG", "month: " + month);
                int date = datePicker.getDayOfMonth();
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                String detail = editText.getText().toString();
                SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd hh mm");
                Date d;
                try {
                     d = format.parse(String.format("%d %d %d %d %d",year,month,date,hour,minute));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                eventDB.addEvent(d,detail);
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}