package com.example.errandforpoints;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.errandforpoints.adapter.ErrandListAdapter;
import com.example.errandforpoints.dbhelper.ErrandDB;

public class CheckErrandsActivity extends AppCompatActivity {
    Button btnAddErrand;
    ListView listErrand;
    ErrandDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_check_errands);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = new ErrandDB(getApplicationContext(),1);
        btnAddErrand = findViewById(R.id.btnAddErrand);
        btnAddErrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddErrandActivity.class));
            }
        });
        listErrand = findViewById(R.id.listCheckErrand);

        listErrand.setAdapter(new ErrandListAdapter(getApplicationContext(),db.getBriefDataToCursor(),true));
    }
}