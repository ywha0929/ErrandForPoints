package com.example.errandforpoints;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.errandforpoints.dbhelper.ErrandDB;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddErrandActivity extends AppCompatActivity {
    EditText editErrandName;
    EditText editErrandYear;
    EditText editErrandMonth;
    EditText editErrandDate;
    EditText editErrandPoint;
    EditText editErrandDetail;
    Button btnAddErrand;
    Button btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_errand);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnAddErrand = findViewById(R.id.btnAddErrandNow);
        editErrandName = findViewById(R.id.editErrandName);
        editErrandYear = findViewById(R.id.editErrandYear);
        editErrandMonth = findViewById(R.id.editErrandMonth);
        editErrandDate = findViewById(R.id.editErrandDate);
        editErrandPoint = findViewById(R.id.editErrandPoints);
        editErrandDetail = findViewById(R.id.editErrandDetail);
        btnCancel = findViewById(R.id.btnErrandCancel);
        btnAddErrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = Integer.parseInt( editErrandYear.getText().toString() );
                int month = Integer.parseInt( editErrandMonth.getText().toString() );
//                month -= 1;
                int date = Integer.parseInt( editErrandDate.getText().toString() );
                SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy MM dd hh mm");
                Date date1 = null;
                try {
                    date1 = dateFormat.parse(String.format("%d %d %d %d %d", year, month, date,0,0));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"This is not a valid date",Toast.LENGTH_LONG).show();
                    return;
                }
                ErrandDB db = new ErrandDB(getApplicationContext(),1);
                db.addErrand(
                        editErrandName.getText().toString(),
                        date1,
                        Integer.parseInt(editErrandPoint.getText().toString()),
                        editErrandDetail.getText().toString()

                );
                finish();

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}