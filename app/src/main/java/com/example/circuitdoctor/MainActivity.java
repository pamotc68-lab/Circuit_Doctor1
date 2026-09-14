package com.example.circuitdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. ปุ่มเข้าหน้า จำลองวงจร Breadboard (btnSimulator)
        LinearLayout btnSimulator = findViewById(R.id.btnSimulator);
        if (btnSimulator != null) {
            btnSimulator.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, SimulatorActivity.class);
                startActivity(intent);
            });
        }

        // 2. ปุ่ม คลังความรู้ (navQuiz) แถบ Bottom Navigation ด้านล่าง -> เปิดหน้าประวัติการต่อวงจร
        LinearLayout navQuiz = findViewById(R.id.navQuiz);
        if (navQuiz != null) {
            navQuiz.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            });
        }

        // 3. ปุ่ม ประวัติการใช้งาน (btnHistory) บนการ์ดเมนู -> เปิดหน้าประวัติการต่อวงจร
        LinearLayout btnHistory = findViewById(R.id.btnHistory);
        if (btnHistory != null) {
            btnHistory.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            });
        }

        // 4. ปุ่ม เริ่มวิเคราะห์ปัญหา (btnStartDiagnostic)
        LinearLayout btnStartDiagnostic = findViewById(R.id.btnStartDiagnostic);
        if (btnStartDiagnostic != null) {
            btnStartDiagnostic.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, DiagnosticActivity.class);
                startActivity(intent);
            });
        }

        // 5. ปุ่ม เรียนรู้วงจร (cardLearn และ navLearn)
        LinearLayout cardLearn = findViewById(R.id.cardLearn);
        LinearLayout navLearn = findViewById(R.id.navLearn);

        if (cardLearn != null) {
            cardLearn.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, LearnActivity.class);
                startActivity(intent);
            });
        }
        if (navLearn != null) {
            navLearn.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, LearnActivity.class);
                startActivity(intent);
            });
        }

        // 6. ปุ่ม โปรไฟล์ (navProfile)
        LinearLayout navProfile = findViewById(R.id.navProfile);
        if (navProfile != null) {
            navProfile.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            });
        }
    }
}