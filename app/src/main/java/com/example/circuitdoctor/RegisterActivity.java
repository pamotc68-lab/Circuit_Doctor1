package com.example.circuitdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = AppDatabase.getInstance(this);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        ImageView btnBack = findViewById(R.id.btnBack);
        TextView btnSubmitRegister = findViewById(R.id.btnSubmitRegister);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        if (btnSubmitRegister != null) {
            btnSubmitRegister.setOnClickListener(v -> handleRegister());
        }
    }

    private void handleRegister() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบทุกช่อง", Toast.LENGTH_SHORT).show();
            return;
        }

        // ตรวจสอบว่าอีเมลซ้ำหรือไม่
        User existingUser = db.userDao().checkEmailExists(email);
        if (existingUser != null) {
            Toast.makeText(this, "อีเมลนี้ถูกใช้งานแล้ว", Toast.LENGTH_SHORT).show();
            return;
        }

        // บันทึกผู้ใช้ใหม่ลงฐานข้อมูล
        User newUser = new User(username, email, password);
        db.userDao().registerUser(newUser);

        // ดึงข้อมูลผู้ใช้ที่เพิ่งสร้างมาเพื่อเอา ID บันทึกลง Session
        User createdUser = db.userDao().login(email, password);
        if (createdUser != null) {
            getSharedPreferences("USER_SESSION", MODE_PRIVATE)
                    .edit()
                    .putInt("CURRENT_USER_ID", createdUser.id)
                    .apply();
        }

        Toast.makeText(this, "สมัครสมาชิกสำเร็จ!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}