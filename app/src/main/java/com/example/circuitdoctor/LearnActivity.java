package com.example.circuitdoctor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LearnActivity extends AppCompatActivity {

    private ImageButton btnBackHeader;
    private Button tabBasic, tabRealCircuit, tabElectronics;
    private LinearLayout containerLessons, navHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        initViews();

        btnBackHeader.setOnClickListener(v -> finish());
        navHome.setOnClickListener(v -> finish());

        // ระบบเปลี่ยนหมวดหมู่บทเรียน
        tabBasic.setOnClickListener(v -> selectTab(tabBasic, "BASIC"));
        tabRealCircuit.setOnClickListener(v -> selectTab(tabRealCircuit, "REAL"));
        tabElectronics.setOnClickListener(v -> selectTab(tabElectronics, "ELECTRONIC"));

        // โหลดหมวดพื้นฐานเป็น Default
        loadLessons("BASIC");
    }

    private void initViews() {
        btnBackHeader = findViewById(R.id.btnBackHeader);
        tabBasic = findViewById(R.id.tabBasic);
        tabRealCircuit = findViewById(R.id.tabRealCircuit);
        tabElectronics = findViewById(R.id.tabElectronics);
        containerLessons = findViewById(R.id.containerLessons);
        navHome = findViewById(R.id.navHome);
    }

    private void selectTab(Button selectedBtn, String category) {
        tabBasic.setBackgroundTintList(getColorStateList(android.R.color.transparent));
        tabBasic.setBackgroundColor(Color.parseColor("#1E293B"));
        tabBasic.setTextColor(Color.parseColor("#94A3B8"));

        tabRealCircuit.setBackgroundColor(Color.parseColor("#1E293B"));
        tabRealCircuit.setTextColor(Color.parseColor("#94A3B8"));

        tabElectronics.setBackgroundColor(Color.parseColor("#1E293B"));
        tabElectronics.setTextColor(Color.parseColor("#94A3B8"));

        selectedBtn.setBackgroundColor(Color.parseColor("#2563EB"));
        selectedBtn.setTextColor(Color.parseColor("#FFFFFF"));

        loadLessons(category);
    }

    private void loadLessons(String category) {
        containerLessons.removeAllViews();

        if ("REAL".equals(category)) {
            addLessonCard("การต่อวงจรบนโฟโต้บอร์ด (Breadboard)", "บทเรียน • 10 นาที", 80);
            addLessonCard("การวิเคราะห์วงจรซับซ้อนด้วย KVL & KCL", "บทเรียน • 20 นาที", 40);
            addLessonCard("การอ่านค่า C และ L ในวงจรจริง", "บทเรียน • 15 นาที", 10);
            addLessonCard("การแกะรอยปริ้นท์ (PCB Tracing)", "บทเรียน • 25 นาที", 0);

        } else if ("ELECTRONIC".equals(category)) {
            addLessonCard("การทำงานของสารกึ่งตัวนำ & ไดโอด", "บทเรียน • 15 นาที", 90);
            addLessonCard("การใช้ ทรานซิสเตอร์ (BJT) เป็นสวิตช์", "บทเรียน • 18 นาที", 50);
            addLessonCard("วงจรเรียงกระแส (Rectifier Circuit)", "บทเรียน • 12 นาที", 20);
            addLessonCard("การประยุกต์ใช้งาน ออปแอมป์ (Op-Amp)", "บทเรียน • 22 นาที", 0);

        } else {
            // BASIC (หัวข้อตามภาพเป๊ะๆ)
            addLessonCard("กฎของโอห์ม (Ohm's Law)", "บทเรียน • 10 นาที", 100);
            addLessonCard("วงจรแบ่งแรงดัน (Voltage Divider)", "บทเรียน • 12 นาที", 60);
            addLessonCard("การต่อ LED และตัวต้านทาน", "บทเรียน • 8 นาที", 30);
            addLessonCard("การใช้มัลติมิเตอร์", "บทเรียน • 15 นาที", 50);
        }
    }

    private void addLessonCard(String title, String duration, int progress) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View cardView = inflater.inflate(R.layout.item_lesson_card, containerLessons, false);

        TextView tvTitle = cardView.findViewById(R.id.tvLessonTitle);
        TextView tvDuration = cardView.findViewById(R.id.tvLessonDuration);
        TextView tvProgressText = cardView.findViewById(R.id.tvProgressText);
        ProgressBar progressBar = cardView.findViewById(R.id.progressLesson);

        tvTitle.setText(title);
        tvDuration.setText(duration);
        tvProgressText.setText(progress + "%");
        progressBar.setProgress(progress);

        cardView.setOnClickListener(v ->
                Toast.makeText(this, "เปิดบทเรียน: " + title, Toast.LENGTH_SHORT).show()
        );

        containerLessons.addView(cardView);
    }
}