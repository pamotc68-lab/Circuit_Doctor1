package com.example.circuitdoctor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class BreadboardSimulatorView extends View {

    private Paint boardPaint, holePaint, ledPaint, wirePaint, textPaint;
    private boolean isPowerOn = false;
    private boolean isLedOn = false;
    private String selectedComponent = "WIRE"; // WIRE, LED, RESISTOR

    // รายการสายไฟ/อุปกรณ์ที่ต่อไว้
    private List<Wire> wires = new ArrayList<>();
    private float startX, startY, currentX, currentY;
    private boolean isDrawingWire = false;

    public BreadboardSimulatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        boardPaint = new Paint();
        boardPaint.setColor(Color.parseColor("#E2E8F0"));

        holePaint = new Paint();
        holePaint.setColor(Color.parseColor("#334155"));

        ledPaint = new Paint();
        ledPaint.setColor(Color.GRAY);
        ledPaint.setStyle(Paint.Style.FILL);

        wirePaint = new Paint();
        wirePaint.setColor(Color.parseColor("#EF4444"));
        wirePaint.setStrokeWidth(8f);
        wirePaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(36f);
        textPaint.setAntiAlias(true);
    }

    public void setSelectedComponent(String component) {
        this.selectedComponent = component;
    }

    public void togglePower() {
        this.isPowerOn = !this.isPowerOn;
        checkCircuit();
        invalidate();
    }

    public void clearBoard() {
        wires.clear();
        isLedOn = false;
        invalidate();
    }

    private void checkCircuit() {
        // หากเปิดไฟและมีการเชื่อมสายไฟ/อุปกรณ์ในวงจร
        if (isPowerOn && wires.size() >= 2) {
            isLedOn = true;
        } else {
            isLedOn = false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 1. วาดตัว Breadboard
        float boardLeft = 50, boardTop = 100, boardRight = getWidth() - 50, boardBottom = getHeight() - 200;
        canvas.drawRect(boardLeft, boardTop, boardRight, boardBottom, boardPaint);

        // 2. วาดรูบน Breadboard (Grid Holes)
        float startHoleX = 90, startHoleY = 150;
        float spacing = 45;
        for (int row = 0; row < 12; row++) {
            for (int col = 0; col < 15; col++) {
                canvas.drawCircle(startHoleX + (col * spacing), startHoleY + (row * spacing), 8, holePaint);
            }
        }

        // 3. วาดสายไฟ/อุปกรณ์ที่ถูกลากต่อไว้
        for (Wire wire : wires) {
            if (wire.type.equals("LED")) {
                ledPaint.setColor(isLedOn ? Color.parseColor("#FEF08A") : Color.RED);
                canvas.drawCircle(wire.endX, wire.endY, 25, ledPaint);
            } else {
                wirePaint.setColor(wire.type.equals("RESISTOR") ? Color.parseColor("#F59E0B") : Color.parseColor("#3B82F6"));
                canvas.drawLine(wire.startX, wire.startY, wire.endX, wire.endY, wirePaint);
            }
        }

        // 4. วาดเส้นที่กำลังลากอยู่
        if (isDrawingWire) {
            wirePaint.setColor(Color.GREEN);
            canvas.drawLine(startX, startY, currentX, currentY, wirePaint);
        }

        // 5. แสดงสถานะวงจร
        textPaint.setColor(isLedOn ? Color.GREEN : Color.RED);
        canvas.drawText(isLedOn ? "สถานะ: วงจรทำงาน (LED ติด)" : "สถานะ: วงจรเปิด/ยังไม่ครบวงจร", 60, getHeight() - 80, textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                currentX = startX;
                currentY = startY;
                isDrawingWire = true;
                return true;

            case MotionEvent.ACTION_MOVE:
                currentX = event.getX();
                currentY = event.getY();
                invalidate();
                return true;

            case MotionEvent.ACTION_UP:
                isDrawingWire = false;
                wires.add(new Wire(startX, startY, event.getX(), event.getY(), selectedComponent));
                checkCircuit();
                invalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }

    private static class Wire {
        float startX, startY, endX, endY;
        String type;

        Wire(float startX, float startY, float endX, float endY, String type) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            this.type = type;
        }
    }
}