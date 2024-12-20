package com.example.errandforpoints;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.errandforpoints.adapter.EventListAdapter;
import com.example.errandforpoints.adapter.PointLogAdapter;
import com.example.errandforpoints.dbhelper.PointDB;

public class CheckErrandsDoneActivity extends AppCompatActivity {
    PointDB pointDB;
    TextView textView;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_check_errands_done);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        pointDB = new PointDB(getApplicationContext(),1);
        textView = findViewById(R.id.textViewTotalPoint);
        listView = findViewById(R.id.listPointLogs);
        textView.setText(""+pointDB.getSumOfAllPoints());
        listView.setAdapter(new PointLogAdapter(getApplicationContext(),pointDB.getAllDataToCursor(),true));

    }
}