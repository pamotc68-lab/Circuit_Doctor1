package com.example.circuitdoctor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ImageView btnBack = findViewById(R.id.btnBack);
        ListView listView = findViewById(R.id.listViewHistory);
        TextView tvEmpty = findViewById(R.id.tvEmpty);

        btnBack.setOnClickListener(v -> finish());

        // อ่านข้อมูลประวัติที่บันทึกไว้จาก SharedPreferences
        SharedPreferences pref = getSharedPreferences("CircuitHistory", Context.MODE_PRIVATE);
        String historyRaw = pref.getString("history_list", "");

        if (historyRaw.trim().isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            String[] historyItems = historyRaw.split("\n");
            List<String> listData = new ArrayList<>(Arrays.asList(historyItems));

            // แสดงรายการลงใน ListView
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    listData
            );
            listView.setAdapter(adapter);
        }
    }
}