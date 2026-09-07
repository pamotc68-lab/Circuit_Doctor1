package com.example.circuitdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // ปุ่มฟังก์ชันหลักกลางหน้า
    private LinearLayout btnStartDiagnostic, btnLearnCircuit;

    // ปุ่ม Bottom Navigation ด้านล่าง
    private LinearLayout navHome, navLearn, navKnowledge, navProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupClickListeners();
    }

    private void initViews() {
        // ปุ่มเมนูหลักตรงกลาง
        btnStartDiagnostic = findViewById(R.id.btnStartDiagnostic); // ปุ่มเริ่มวิเคราะห์วงจร
        btnLearnCircuit = findViewById(R.id.btnLearnCircuit);       // ปุ่มเรียนรู้วงจร

        // ปุ่มแถบเมนูด้านล่าง (Bottom Navigation)
        navHome = findViewById(R.id.navHome);
        navLearn = findViewById(R.id.navLearn);
        navKnowledge = findViewById(R.id.navKnowledge);
        navProfile = findViewById(R.id.navProfile);
    }

    private void setupClickListeners() {
        // 1. กดปุ่ม "เริ่มวิเคราะห์วงจร" -> ไปหน้า DiagnosticActivity
        if (btnStartDiagnostic != null) {
            btnStartDiagnostic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, DiagnosticActivity.class);
                    startActivity(intent);
                }
            });
        }

        // 2. กดปุ่ม "เรียนรู้วงจร" -> ไปหน้า LearnActivity
        if (btnLearnCircuit != null) {
            btnLearnCircuit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openLearnActivity();
                }
            });
        }

        // --- ระบบคลิก แถบเมนูด้านล่าง (Bottom Navigation) ---

        // หน้าหลัก (อยู่นี่แล้ว)
        if (navHome != null) {
            navHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // อยู่หน้าหลักอยู่แล้ว
                }
            });
        }

        // แท็บ "เรียนรู้" ด้านล่าง -> ไปหน้า LearnActivity
        if (navLearn != null) {
            navLearn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openLearnActivity();
                }
            });
        }

        // แท็บ "คลังความรู้" ด้านล่าง
        if (navKnowledge != null) {
            navKnowledge.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "คลังความรู้", Toast.LENGTH_SHORT).show();
                    // TODO: Intent ไปหน้า KnowledgeActivity หากมี
                }
            });
        }

        // แท็บ "โปรไฟล์" ด้านล่าง
        if (navProfile != null) {
            navProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "โปรไฟล์", Toast.LENGTH_SHORT).show();
                    // TODO: Intent ไปหน้า ProfileActivity หากมี
                }
            });
        }
    }

    // ฟังก์ชันเปิดหน้าเรียนรู้วงจร
    private void openLearnActivity() {
        Intent intent = new Intent(MainActivity.this, LearnActivity.class);
        startActivity(intent);
    }
}