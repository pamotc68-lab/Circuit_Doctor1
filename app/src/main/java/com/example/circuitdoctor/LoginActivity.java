package com.example.circuitdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = AppDatabase.getInstance(this);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        TextView btnLogin = findViewById(R.id.btnLogin);
        TextView btnRegister = findViewById(R.id.btnRegister);
        TextView btnGuest = findViewById(R.id.btnGuest);

        if (btnLogin != null) {
            btnLogin.setOnClickListener(v -> handleLogin());
        }

        if (btnRegister != null) {
            btnRegister.setOnClickListener(v -> {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            });
        }

        if (btnGuest != null) {
            btnGuest.setOnClickListener(v -> {
                // ล้าง session กรณีเข้าใช้งานแบบ Guest
                getSharedPreferences("USER_SESSION", MODE_PRIVATE).edit().clear().apply();
                openMainActivity();
            });
        }
    }

    private void handleLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = db.userDao().login(email, password);

        if (user != null) {
            // บันทึก ID ผู้ใช้ลง SharedPreferences
            getSharedPreferences("USER_SESSION", MODE_PRIVATE)
                    .edit()
                    .putInt("CURRENT_USER_ID", user.id)
                    .apply();

            Toast.makeText(this, "ยินดีต้อนรับ " + user.username, Toast.LENGTH_SHORT).show();
            openMainActivity();
        } else {
            Toast.makeText(this, "อีเมลหรือรหัสผ่านไม่ถูกต้อง!", Toast.LENGTH_SHORT).show();
        }
    }

    private void openMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}