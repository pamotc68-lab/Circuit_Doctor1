package com.example.circuitdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // หน่วงเวลา 2 วินาทีแล้วเปิดไปหน้า LoginActivity
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // ปิดหน้า Splash สดเพื่อไม่ให้กดย้อนกลับมาได้
            }
        }, 2000); // 2000 ms = 2 วินาที
    }
}