package com.example.circuitdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class QuestionActivity extends AppCompatActivity {

    private ImageButton btnBackHeader;
    private TextView tvCategoryTitle, tvCategorySub, tvStep, tvPercentage, tvQuestionTitle;
    private ProgressBar progressBarQuestion;
    private RadioGroup rgOptions;
    private RadioButton rbOption1, rbOption2, rbOption3;
    private Button btnPrevious, btnNext;

    private int currentQuestion = 1;
    private final int TOTAL_QUESTIONS = 5;
    private String category = "LED"; // Default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("CATEGORY")) {
            category = intent.getStringExtra("CATEGORY");
        }

        initViews();

        btnBackHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPreviousQuestion();
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPreviousQuestion();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goNextQuestion();
            }
        });

        updateQuestionUI();
    }

    private void initViews() {
        btnBackHeader = findViewById(R.id.btnBackHeader);
        tvCategoryTitle = findViewById(R.id.tvCategoryTitle);
        tvCategorySub = findViewById(R.id.tvCategorySub);
        tvStep = findViewById(R.id.tvStep);
        tvPercentage = findViewById(R.id.tvPercentage);
        tvQuestionTitle = findViewById(R.id.tvQuestionTitle);
        progressBarQuestion = findViewById(R.id.progressBarQuestion);
        rgOptions = findViewById(R.id.rgOptions);
        rbOption1 = findViewById(R.id.rbOption1);
        rbOption2 = findViewById(R.id.rbOption2);
        rbOption3 = findViewById(R.id.rbOption3);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
    }

    private void goPreviousQuestion() {
        if (currentQuestion > 1) {
            currentQuestion--;
            updateQuestionUI();
        } else {
            finish();
        }
    }

    private void goNextQuestion() {
        if (rgOptions.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "กรุณาเลือกคำตอบก่อนทำรายการถัดไป", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentQuestion < TOTAL_QUESTIONS) {
            currentQuestion++;
            updateQuestionUI();
        } else {
            // เมื่อตอบครบ 5 ข้อแล้ว จะส่ง Intent ไปเปิดหน้า ResultActivity พร้อมแนบ CATEGORY ไปด้วย
            Intent intent = new Intent(QuestionActivity.this, ResultActivity.class);
            intent.putExtra("CATEGORY", category);
            startActivity(intent);
            finish(); // ปิดหน้านี้ทันทีเพื่อไม่ให้กด Back กลับมา
        }
    }

    private void updateQuestionUI() {
        int progress = (currentQuestion * 100) / TOTAL_QUESTIONS;
        progressBarQuestion.setProgress(progress);

        tvStep.setText("ข้อที่ " + currentQuestion + " จาก " + TOTAL_QUESTIONS);
        tvPercentage.setText(progress + "%");

        if (currentQuestion == TOTAL_QUESTIONS) {
            btnNext.setText("ดูผลวิเคราะห์");
        } else {
            btnNext.setText("ถัดไป");
        }

        rgOptions.clearCheck();
        rbOption1.setChecked(true);

        if ("RESISTOR".equals(category)) {
            // --- หมวด ค่าความต้านทานผิดปกติ ---
            tvCategoryTitle.setText("ค่าความต้านทานผิดปกติ");
            tvCategorySub.setText("คำถามวิเคราะห์ปัญหา (ข้อละ 3 ตัวเลือก)");

            switch (currentQuestion) {
                case 1:
                    tvQuestionTitle.setText("ข้อ 1: ท่านได้ลอยตัวต้านทานออกจากวงจรก่อนวัดหรือไม่?");
                    rbOption1.setText("วัดขณะต่ออยู่ในวงจร");
                    rbOption2.setText("ลอยขาออก 1 ข้างแล้วจึงวัด");
                    rbOption3.setText("ถอดออกมาวัดภายนอกเลย");
                    break;
                case 2:
                    tvQuestionTitle.setText("ข้อ 2: ลักษณะค่าที่วัดได้ผิดปกติอย่างไร?");
                    rbOption1.setText("ค่าขึ้นสูงมาก / ขาดวงจร (OL)");
                    rbOption2.setText("ค่าต่ำกว่าแถบสี/สเปกมาก");
                    rbOption3.setText("ค่าสวิงไม่คงที่");
                    break;
                case 3:
                    tvQuestionTitle.setText("ข้อ 3: สภาพภายนอกตัวต้านทานเป็นอย่างไร?");
                    rbOption1.setText("ตัวถังไหม้ดำ / แตก");
                    rbOption2.setText("แถบสีซีดจางระบุยาก");
                    rbOption3.setText("สภาพภายนอกสมบูรณ์ดี");
                    break;
                case 4:
                    tvQuestionTitle.setText("ข้อ 4: ตัวต้านทานที่วัดเป็นประเภทใด?");
                    rbOption1.setText("แบบค่าคงที่ (Fixed Resistor)");
                    rbOption2.setText("แบบปรับค่าได้ (Potentiometer)");
                    rbOption3.setText("แบบไวต่ออุณหภูมิ/แสง (NTC/PTC/LDR)");
                    break;
                case 5:
                    tvQuestionTitle.setText("ข้อ 5: เครื่องมือวัด (Multimeter) พร้อมใช้งานหรือไม่?");
                    rbOption1.setText("แบตเตอรี่มิเตอร์ขึ้นเตือนอ่อน");
                    rbOption2.setText("ตั้งย่านวัดผิดประเภท (เช่น ตั้งย่านกระแส/แรงดัน)");
                    rbOption3.setText("เครื่องมือวัดปกติ เช็ก Calibration แล้ว");
                    break;
            }

        } else if ("SIGNAL".equals(category)) {
            // --- หมวด สัญญาณไม่ออก ---
            tvCategoryTitle.setText("สัญญาณไม่ออก");
            tvCategorySub.setText("คำถามวิเคราะห์ปัญหา (ข้อละ 3 ตัวเลือก)");

            switch (currentQuestion) {
                case 1:
                    tvQuestionTitle.setText("ข้อ 1: รูปแบบสัญญาณ Output ที่พบจากมัลติมิเตอร์/สโคป คืออะไร?");
                    rbOption1.setText("ไม่มีสัญญาณเลย (เป็นเส้นตรง 0V)");
                    rbOption2.setText("สัญญาณเป็นคลื่นไฟ DC ค้าง");
                    rbOption3.setText("มีสัญญาณแต่รูปร่างเพี้ยน/มี Noise สูง");
                    break;
                case 2:
                    tvQuestionTitle.setText("ข้อ 2: ไอซีสร้างสัญญาณ (Oscillator/MCU) มีไฟเลี้ยงหรือไม่?");
                    rbOption1.setText("ไม่มีไฟเลี้ยง Vcc");
                    rbOption2.setText("ไฟเลี้ยงมาไม่ครบทุกขา");
                    rbOption3.setText("ไฟเลี้ยงปกติ");
                    break;
                case 3:
                    tvQuestionTitle.setText("ข้อ 3: สัญญาณอินพุต (Input) ที่ป้อนเข้ามาปกติหรือไม่?");
                    rbOption1.setText("ไม่มีสัญญาณอินพุตเข้ามา");
                    rbOption2.setText("สัญญาณอินพุตอ่อนเกินไป");
                    rbOption3.setText("สัญญาณอินพุตมาปกติ");
                    break;
                case 4:
                    tvQuestionTitle.setText("ข้อ 4: คริสตัล (Crystal Oscillator) กำเนิดความถี่ทำงานหรือไม่?");
                    rbOption1.setText("ไม่มีคลื่นความถี่ออกจากขา Crystal");
                    rbOption2.setText("C-Filter รอบๆ คริสตัลรั่ว/ช็อต");
                    rbOption3.setText("คริสตัลทำงานปกติ");
                    break;
                case 5:
                    tvQuestionTitle.setText("ข้อ 5: สายนำสัญญาณและช่อง Output เป็นอย่างไร?");
                    rbOption1.setText("สายสัญญาณขาดใน");
                    rbOption2.setText("ช่อง Output/Connector หลวม");
                    rbOption3.setText("สายสัญญาณปกติ");
                    break;
            }

        } else if ("POWER".equals(category)) {
            // --- หมวด วงจรไม่จ่ายไฟ ---
            tvCategoryTitle.setText("วงจรไม่จ่ายไฟ");
            tvCategorySub.setText("คำถามวิเคราะห์ปัญหา (ข้อละ 3 ตัวเลือก)");

            switch (currentQuestion) {
                case 1:
                    tvQuestionTitle.setText("ข้อ 1: แหล่งจ่ายไฟต้นทาง (Adapter/Battery) ปกติหรือไม่?");
                    rbOption1.setText("ไฟต้นทางไม่จ่ายไฟเลย");
                    rbOption2.setText("ไฟต้นทางจ่ายแรงดันต่ำกว่าปกติ");
                    rbOption3.setText("ไฟต้นทางจ่ายไฟปกติ");
                    break;
                case 2:
                    tvQuestionTitle.setText("ข้อ 2: สถานะของฟิวส์ (Fuse) ในวงจรเป็นอย่างไร?");
                    rbOption1.setText("ฟิวส์ขาด / ดำ");
                    rbOption2.setText("เบรกเกอร์ทริป (ตัดวงจร)");
                    rbOption3.setText("ฟิวส์สมบูรณ์ดี");
                    break;
                case 3:
                    tvQuestionTitle.setText("ข้อ 3: สวิตช์เปิด-ปิด หรือสายไฟหลักมีปัญหาหรือไม่?");
                    rbOption1.setText("สวิตช์เสีย / ไม่ต่อนำกระแส");
                    rbOption2.setText("สายไฟขาดใน / จุดบัดกรีหลุด");
                    rbOption3.setText("สวิตช์และสายไฟปกติ");
                    break;
                case 4:
                    tvQuestionTitle.setText("ข้อ 4: มีกลิ่นไหม้หรือรอยไหม้บริเวณหม้อแปลง/สวิตชิ่งหรือไม่?");
                    rbOption1.setText("มีกลิ่นไหม้/รอยไหม้ชัดเจน");
                    rbOption2.setText("มีเสียงร้องจี๊ดผิดปกติ");
                    rbOption3.setText("ปกติ ไม่มีสิ่งผิดปกติ");
                    break;
                case 5:
                    tvQuestionTitle.setText("ข้อ 5: เมื่อวัดฝั่ง Output พบการช็อตลงกราวด์หรือไม่?");
                    rbOption1.setText("ช็อตลงกราวด์ (0 Ohm)");
                    rbOption2.setText("ค่าความต้านทานต่ำผิดปกติ");
                    rbOption3.setText("ไม่พบการช็อต");
                    break;
            }

        } else if ("MOTOR".equals(category)) {
            // --- หมวด มอเตอร์ไม่ทำงาน ---
            tvCategoryTitle.setText("มอเตอร์ไม่ทำงาน");
            tvCategorySub.setText("ไม่มีการหมุน / หมุนช้า");

            switch (currentQuestion) {
                case 1:
                    tvQuestionTitle.setText("ข้อ 1: เมื่อจ่ายไฟแล้ว มอเตอร์มีปฏิกิริยาอย่างไร?");
                    rbOption1.setText("นิ่งสนิท ไม่มีเสียง");
                    rbOption2.setText("มีเสียงสั่น/คราง แต่แกนไม่หมุน");
                    rbOption3.setText("หมุนช้า และไม่มีแรง");
                    break;
                case 2:
                    tvQuestionTitle.setText("ข้อ 2: เมื่อใช้มือลองหมุนแกนมอเตอร์ (ขณะปิดไฟ) รู้สึกอย่างไร?");
                    rbOption1.setText("หมุนได้ลื่นไหลปกติ");
                    rbOption2.setText("ฝืด / ติดขัด");
                    rbOption3.setText("ล็อกแน่น หมุนไม่ไป");
                    break;
                case 3:
                    tvQuestionTitle.setText("ข้อ 3: มีแรงดันไฟจ่ายถึงขั้วมอเตอร์หรือไม่?");
                    rbOption1.setText("ไม่มีไฟมาที่ขั้วมอเตอร์");
                    rbOption2.setText("ไฟมาแต่แรงดันตกเมื่อโหลดทำงาน");
                    rbOption3.setText("มีไฟมาตามสเปกปกติ");
                    break;
                case 4:
                    tvQuestionTitle.setText("ข้อ 4: ตัวมอเตอร์มีความร้อนผิดปกติหรือไม่?");
                    rbOption1.setText("ร้อนจัดอย่างรวดเร็ว");
                    rbOption2.setText("อุ่นๆ ตามปกติ");
                    rbOption3.setText("ไม่มีความร้อนเลย");
                    break;
                case 5:
                    tvQuestionTitle.setText("ข้อ 5: วงจรควบคุม/รีเลย์ (Driver Unit) ทำงานหรือไม่?");
                    rbOption1.setText("ไม่ส่งสัญญาณขับมอเตอร์");
                    rbOption2.setText("รีเลย์ไม่ตัดต่อ (สัมผัสไม่แตะ)");
                    rbOption3.setText("วงจรควบคุมทำงานปกติ");
                    break;
            }

        } else {
            // --- หมวด LED ไม่ติด ---
            tvCategoryTitle.setText("LED ไม่ติด");
            tvCategorySub.setText("คำถามวิเคราะห์ปัญหา (ข้อละ 3 ตัวเลือก)");

            switch (currentQuestion) {
                case 1:
                    tvQuestionTitle.setText("ข้อ 1: ไฟ LED มีลักษณะการติดอย่างไร?");
                    rbOption1.setText("ไม่ติดเลย");
                    rbOption2.setText("ติดๆ ดับๆ / กระพริบ");
                    rbOption3.setText("ติดสว่างน้อย/ริบหรี่");
                    break;
                case 2:
                    tvQuestionTitle.setText("ข้อ 2: ตรวจสอบแรงดันไฟเข้าแหล่งจ่าย?");
                    rbOption1.setText("มีแรงดันไฟเข้าปกติ");
                    rbOption2.setText("แรงดันไฟตก / ต่ำกว่าปกติ");
                    rbOption3.setText("ไม่มีแรงดันไฟเข้ามาเลย");
                    break;
                case 3:
                    tvQuestionTitle.setText("ข้อ 3: สภาพภายนอกของตัวต้านทาน?");
                    rbOption1.setText("ไหม้ / มีคราบดำ");
                    rbOption2.setText("ร้อนผิดปกติ");
                    rbOption3.setText("สภาพภายนอกปกติ");
                    break;
                case 4:
                    tvQuestionTitle.setText("ข้อ 4: สภาพสายไฟและการเชื่อมต่อ?");
                    rbOption1.setText("สายขาด / หลุด");
                    rbOption2.setText("ต่อสลับขั้ว");
                    rbOption3.setText("ต่อสายแน่นหนาดี");
                    break;
                case 5:
                    tvQuestionTitle.setText("ข้อ 5: อุปกรณ์ LED ที่ใช้นำมาจากไหน?");
                    rbOption1.setText("อุปกรณ์ใหม่ทั้งหมด");
                    rbOption2.setText("อุปกรณ์นำกลับมาใช้ซ้ำ");
                    rbOption3.setText("ไม่แน่ใจ");
                    break;
            }
        }
    }
}