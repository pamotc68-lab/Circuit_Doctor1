package com.example.circuitdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private TextView tvMainCause, tvSecondaryCause;
    private TextView tvSolution1, tvSolution2, tvSolution3, tvSolution4;
    private Button btnTheory, btnRestart;

    private String category = "LED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("CATEGORY")) {
            category = intent.getStringExtra("CATEGORY");
        }

        initViews();
        displayResultData();

        btnBack.setOnClickListener(v -> finish());

        // ปุ่มดูหลักการเพิ่มเติม (เหมาะสำหรับปี 1 อ่านเพิ่มทฤษฎี)
        btnTheory.setOnClickListener(v -> showTheoryDialog());

        // ปุ่มลองวิเคราะห์ใหม่
        btnRestart.setOnClickListener(v -> {
            Intent intentDiag = new Intent(ResultActivity.this, DiagnosticActivity.class);
            intentDiag.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentDiag);
            finish();
        });
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        tvMainCause = findViewById(R.id.tvMainCause);
        tvSecondaryCause = findViewById(R.id.tvSecondaryCause);
        tvSolution1 = findViewById(R.id.tvSolution1);
        tvSolution2 = findViewById(R.id.tvSolution2);
        tvSolution3 = findViewById(R.id.tvSolution3);
        tvSolution4 = findViewById(R.id.tvSolution4);
        btnTheory = findViewById(R.id.btnTheory);
        btnRestart = findViewById(R.id.btnRestart);
    }

    private void displayResultData() {
        if ("POWER".equals(category)) {
            tvMainCause.setText("ฟิวส์ขาด หรือสายไฟฝั่งเข้าหลุด");
            tvSecondaryCause.setText("• หม้อแปลง/สวิตชิ่งเสียหาย (10%)\n• ไดโอดเรกติไฟเออร์ช็อตลงกราวด์ (5%)");
            tvSolution1.setText("✔  ใช้มัลติมิเตอร์วัดความต่อเนื่อง (Continuity) ของฟิวส์");
            tvSolution2.setText("✔  ตรวจเช็กแรงดันไฟ AC/DC ต้นทางก่อนเข้าวงจร");
            tvSolution3.setText("✔  เปลี่ยนฟิวส์ขนาดแอมแปร์เท่าเดิม");
            tvSolution4.setText("✔  ตรวจสอบการช็อตลงกราวด์ก่อนจ่ายไฟซ้ำ");

        } else if ("MOTOR".equals(category)) {
            tvMainCause.setText("แกนมอเตอร์ติดขัด หรือแรงดันไฟต่ำกว่าสเปก");
            tvSecondaryCause.setText("• คาปาซิเตอร์สตาร์ทเสื่อมสภาพ (20%)\n• ขดลวดมอเตอร์ขาดภายใน (5%)");
            tvSolution1.setText("✔  หมุนแกนมอเตอร์ด้วยมือเพื่อเช็กความฝืด");
            tvSolution2.setText("✔  วัดแรงดันไฟที่ขั้วมอเตอร์ขณะโหลดทำงาน");
            tvSolution3.setText("✔  เปลี่ยน C-Start หรือหยอดน้ำมันหล่อลื่นที่ตลับลูกปืน");
            tvSolution4.setText("✔  ทดสอบจ่ายไฟขับมอเตอร์อีกครั้ง");

        } else if ("RESISTOR".equals(category)) {
            tvMainCause.setText("ตัวต้านทานขาดวงจร (Open Circuit / OL)");
            tvSecondaryCause.setText("• วัดขณะต่ออยู่ในวงจรทำให้ค่าเพี้ยน (25%)\n• ถ่านมัลติมิเตอร์หมด/ตั้งย่านผิด (10%)");
            tvSolution1.setText("✔  ลอยขาตัวต้านทานอย่างน้อย 1 ข้างก่อนทำการวัด");
            tvSolution2.setText("✔  ตรวจเช็กรอยไหม้ หรือคราบดำบนตัวถัง");
            tvSolution3.setText("✔  เช็กแถบสีและคำนวณค่าความต้านทานใหม่อีกครั้ง");
            tvSolution4.setText("✔  เปลี่ยน R ตัวใหม่ที่มีค่าและกำลังวัตต์ (W) เท่าเดิม");

        } else if ("SIGNAL".equals(category)) {
            tvMainCause.setText("ไม่มีไฟเลี้ยง Vcc เข้าไอซี หรือสายสัญญาณขาดใน");
            tvSecondaryCause.setText("• คริสตัล (Crystal) ไม่กำเนิดความถี่ (15%)\n• ช่อง Output หลวม/Connector หลุด (10%)");
            tvSolution1.setText("✔  ใช้สโคปหรือมิเตอร์วัดไฟเลี้ยง Vcc ที่ขาไอซี");
            tvSolution2.setText("✔  วัดสัญญาณความถี่ที่ขา Crystal");
            tvSolution3.setText("✔  ตรวจสอบสายนำสัญญาณความถี่และสายกราวด์");
            tvSolution4.setText("✔  ย้ำจุดบัดกรีบริเวณ Connector");

        } else {
            // Default: LED
            tvMainCause.setText("ต่อขั้ว LED กลับด้าน (Reverse Bias)");
            tvSecondaryCause.setText("• ค่าตัวต้านทานอนุกรมสูงเกินไป (15%)\n• LED เสื่อมสภาพ/ขาดภายใน (5%)");
            tvSolution1.setText("✔  ตรวจสอบขั้ว Anode (+) และ Cathode (-) ของ LED");
            tvSolution2.setText("✔  สลับตำแหน่งสายไฟหรือหมุนขั้วอุปกรณ์ให้ถูกต้อง");
            tvSolution3.setText("✔  ตรวจสอบตัวต้านทานจำกัดกระแส (เช่น 220Ω - 1kΩ)");
            tvSolution4.setText("✔  จ่ายไฟและทดสอบการทำงานอีกครั้ง");
        }
    }

    private void showTheoryDialog() {
        String title = "หลักการทางทฤษฎี";
        String message = "";

        if ("POWER".equals(category)) {
            message = "กฎของโอห์ม (V=IR): เมื่อเกิดการช็อตวงจร ความต้านทาน (R) จะเข้าใกล้ 0 ทำให้อัตรากระแสไฟฟ้า (I) พุ่งสูงขึ้นเกินกว่าที่ฟิวส์ทนได้ ฟิวส์จึงหลอมละลายเพื่อตัดวงจรทันที";
        } else if ("MOTOR".equals(category)) {
            message = "แรงเคลื่อนไฟฟ้าตาม (Back EMF): เมื่อมอเตอร์หมุนจะสร้างแรงดันต่อต้านขึ้น หากแกนติดขัด มอเตอร์ไม่หมุน จะเกิดกระแสล็อกโรเตอร์ (Locked Rotor Current) ทำให้เกิดความร้อนสูงและไฟตก";
        } else if ("RESISTOR".equals(category)) {
            message = "การวัดความต้านทานในวงจร: หากไม่อยู่ในสถานะลอยขา ตัวต้านทานตัวอื่นที่ต่อขนานอยู่จะทำให้ค่า R รวมที่วัดได้ต่ำกว่าค่าจริงเสมอ (1/R_total = 1/R1 + 1/R2)";
        } else if ("SIGNAL".equals(category)) {
            message = "วงจรกำเนิดสัญญาณ (Oscillator): ไอซีต้องการความถี่อ้างอิงจาก Crystal ร่วมกับ C-Filter ในการสร้างสัญญาณนาฬิกา หากไม่มี Vcc หรือ C รั่ว วงจรจะไม่สามารถสร้างรูปคลื่นได้";
        } else {
            message = "คุณสมบัติไดโอด (PN Junction): LED เป็นไดโอดโพลาร์ กระแสจะไหลผ่านได้เมื่อต่อไบอัสตรง (Forward Bias) คือ Anode เป็น (+) และ Cathode เป็น (-) เท่านั้น หากต่อกลับด้านกระแสจะไม่สามารถไหลผ่านได้";
        }

        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("เข้าใจแล้ว", (dialog, which) -> dialog.dismiss())
                .show();
    }
}