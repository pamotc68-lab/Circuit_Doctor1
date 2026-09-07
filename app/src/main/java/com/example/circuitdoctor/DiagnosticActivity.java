package com.example.circuitdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class DiagnosticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic);

        // 1. ปุ่มย้อนกลับ (ลูกศรซ้ายบน)
        ImageView btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateToMain();
                }
            });
        }

        // 2. รายการตัวเลือกอาการ
        LinearLayout optionLed = findViewById(R.id.itemLedNotWorking);
        LinearLayout optionMotor = findViewById(R.id.itemMotorNotWorking);
        LinearLayout optionPower = findViewById(R.id.itemNoPower);
        LinearLayout optionResistor = findViewById(R.id.itemResistorIssue);
        LinearLayout optionSignal = findViewById(R.id.itemNoSignal);

        if (optionLed != null) {
            optionLed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openQuestionActivity("LED");
                }
            });
        }

        if (optionMotor != null) {
            optionMotor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openQuestionActivity("MOTOR");
                }
            });
        }

        if (optionPower != null) {
            optionPower.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openQuestionActivity("POWER");
                }
            });
        }

        if (optionResistor != null) {
            optionResistor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openQuestionActivity("RESISTOR");
                }
            });
        }

        if (optionSignal != null) {
            optionSignal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openQuestionActivity("SIGNAL");
                }
            });
        }
    }

    private void openQuestionActivity(String category) {
        Intent intent = new Intent(DiagnosticActivity.this, QuestionActivity.class);
        intent.putExtra("CATEGORY", category);
        startActivity(intent);
    }

    private void navigateToMain() {
        Intent intent = new Intent(DiagnosticActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navigateToMain();
    }
}