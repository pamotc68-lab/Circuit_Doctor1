package com.example.circuitdoctor;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SimulatorActivity extends AppCompatActivity {

    private BreadboardTinkercadView tinkercadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulator);

        tinkercadView = findViewById(R.id.tinkercadView);
        Button btnStartSim = findViewById(R.id.btnStartSimulation);
        Button btnSave = findViewById(R.id.btnSaveProject);
        ImageView btnBack = findViewById(R.id.btnBackToMain);

        // 1. ปุ่มกดย้อนกลับไปหน้าหลัก
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 2. เลือกสีสายไฟใน Sub-Toolbar
        findViewById(R.id.colorGreen).setOnClickListener(v -> tinkercadView.setWireColor(Color.GREEN));
        findViewById(R.id.colorRed).setOnClickListener(v -> tinkercadView.setWireColor(Color.RED));
        findViewById(R.id.colorBlack).setOnClickListener(v -> tinkercadView.setWireColor(Color.BLACK));

        // 3. ปุ่มลบ / ล้างบอร์ด
        findViewById(R.id.btnDelete).setOnClickListener(v -> {
            tinkercadView.clearAll();
            Toast.makeText(this, "ล้างพื้นที่ทำงานแล้ว", Toast.LENGTH_SHORT).show();
        });

        // 4. เลือกอุปกรณ์จากแถบขวา (Components Panel)
        findViewById(R.id.itemLED).setOnClickListener(v -> {
            tinkercadView.setSelectedMode("LED");
            Toast.makeText(this, "เลือก LED: แตะที่พื้นที่ทำงานเพื่อวาง", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.itemBattery).setOnClickListener(v -> {
            tinkercadView.setSelectedMode("WIRE");
            Toast.makeText(this, "เลือก แบตเตอรี่ 9V", Toast.LENGTH_SHORT).show();
        });

        // 5. ปุ่ม Start / Stop Simulation
        btnStartSim.setOnClickListener(v -> {
            boolean isRunning = !tinkercadView.isSimulating();
            tinkercadView.setSimulating(isRunning);
            if (isRunning) {
                btnStartSim.setText("■ Stop Simulation");
                btnStartSim.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#DC2626")));
            } else {
                btnStartSim.setText("▶ Start Simulation");
                btnStartSim.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#059669")));
            }
        });

        // 6. ปุ่มบันทึกประวัติการต่อวงจร
        btnSave.setOnClickListener(v -> saveCircuitHistory());
    }

    private void saveCircuitHistory() {
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());
        String logData = "วงจรจำลอง Breadboard - บันทึกเมื่อ " + timeStamp;

        SharedPreferences pref = getSharedPreferences("CircuitHistory", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        String oldHistory = pref.getString("history_list", "");
        String updatedHistory = logData + "\n" + oldHistory;

        editor.putString("history_list", updatedHistory);
        editor.apply();

        Toast.makeText(this, "💾 บันทึกประวัติการต่อวงจรเรียบร้อยแล้ว!", Toast.LENGTH_LONG).show();
    }
}