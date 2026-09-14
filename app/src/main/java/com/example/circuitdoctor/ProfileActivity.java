package com.example.circuitdoctor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvProfileName, tvUsername, tvEmail;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = AppDatabase.getInstance(this);

        tvProfileName = findViewById(R.id.tvProfileName);
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);
        TextView btnLogout = findViewById(R.id.btnLogout);

        // ปุ่มออกจากระบบ
        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> logout());
        }

        // ปุ่ม Navigation ด้านล่าง
        View navHome = findViewById(R.id.navHome);
        if (navHome != null) {
            navHome.setOnClickListener(v -> {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserData(); // โหลดข้อมูลผู้ใช้ใหม่ทุกครั้งที่หน้านี้แสดงผล
    }

    private void loadUserData() {
        SharedPreferences prefs = getSharedPreferences("USER_SESSION", MODE_PRIVATE);
        int userId = prefs.getInt("CURRENT_USER_ID", -1);

        if (userId != -1) {
            User user = db.userDao().getUserById(userId);
            if (user != null) {
                tvProfileName.setText(user.username);
                tvUsername.setText(user.username);
                tvEmail.setText(user.email);
                return;
            }
        }

        // กรณีล็อกอินแบบ Guest
        tvProfileName.setText("ผู้ใช้งานทั่วไป (Guest)");
        tvUsername.setText("Guest Account");
        tvEmail.setText("ไม่ได้ลงทะเบียน");
    }

    private void logout() {
        // เคลียร์ค่า Session ที่จำไว้
        getSharedPreferences("USER_SESSION", MODE_PRIVATE).edit().clear().apply();
        Toast.makeText(this, "ออกจากระบบแล้ว", Toast.LENGTH_SHORT).show();

        // เด้งกลับไปหน้า Login
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}