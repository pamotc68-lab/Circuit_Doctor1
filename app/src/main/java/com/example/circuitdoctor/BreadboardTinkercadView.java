package com.example.circuitdoctor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class BreadboardTinkercadView extends View {

    private Paint boardPaint, lineRedPaint, lineBluePaint, holePaint, textPaint;
    private Paint wirePaint, ledPaint, batteryPaint;
    private boolean isSimulating = false;
    private int selectedWireColor = Color.RED;
    private String selectedMode = "WIRE"; // WIRE, LED, RESISTOR

    private List<PlacedWire> wires = new ArrayList<>();
    private List<PlacedComponent> components = new ArrayList<>();

    private float startX, startY, curX, curY;
    private boolean isDrawing = false;

    public BreadboardTinkercadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaints();
    }

    private void initPaints() {
        boardPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        boardPaint.setColor(Color.parseColor("#F8FAFC")); // สีขาวบอร์ด

        lineRedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lineRedPaint.setColor(Color.parseColor("#EF4444"));
        lineRedPaint.setStrokeWidth(4f);

        lineBluePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lineBluePaint.setColor(Color.parseColor("#3B82F6"));
        lineBluePaint.setStrokeWidth(4f);

        holePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        holePaint.setColor(Color.parseColor("#1E293B"));

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.parseColor("#64748B"));
        textPaint.setTextSize(24f);

        wirePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        wirePaint.setStyle(Paint.Style.STROKE);
        wirePaint.setStrokeWidth(8f);
        wirePaint.setStrokeCap(Paint.Cap.ROUND);

        ledPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ledPaint.setStyle(Paint.Style.FILL);

        batteryPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        batteryPaint.setColor(Color.parseColor("#334155"));
    }

    public void setSimulating(boolean simulating) {
        this.isSimulating = simulating;
        invalidate();
    }

    public boolean isSimulating() { return isSimulating; }

    public void setWireColor(int color) { this.selectedWireColor = color; }
    public void setSelectedMode(String mode) { this.selectedMode = mode; }

    public void clearAll() {
        wires.clear();
        components.clear();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.parseColor("#0F172A")); // Background กราฟิกเข้มแบบ Tinkercad

        // 1. วาดแบตเตอรี่ 9V (จำลอง Power Source ด้านข้าง)
        canvas.drawRoundRect(new RectF(40, 60, 180, 220), 16, 16, batteryPaint);
        textPaint.setColor(Color.WHITE);
        canvas.drawText("9V Battery", 55, 145, textPaint);

        // 2. วาดตัว Breadboard
        float bLeft = 220, bTop = 40, bRight = getWidth() - 40, bBottom = getHeight() - 100;
        RectF boardRect = new RectF(bLeft, bTop, bRight, bBottom);
        canvas.drawRoundRect(boardRect, 20, 20, boardPaint);

        // เส้นจ่ายไฟ + / -
        canvas.drawLine(bLeft + 30, bTop + 30, bRight - 30, bTop + 30, lineRedPaint);
        canvas.drawLine(bLeft + 30, bTop + 50, bRight - 30, bTop + 50, lineBluePaint);

        // วาดรู Breadboard (Grid)
        for (float x = bLeft + 50; x < bRight - 40; x += 35) {
            for (float y = bTop + 80; y < bBottom - 40; y += 35) {
                canvas.drawCircle(x, y, 6, holePaint);
            }
        }

        // 3. วาดอุปกรณ์ (LED / Resistor)
        for (PlacedComponent comp : components) {
            if (comp.type.equals("LED")) {
                // ถ้าเปิด Simulation + มีการต่อวงจร ให้สว่างสีเหลืองสด
                ledPaint.setColor(isSimulating ? Color.parseColor("#FACC15") : Color.parseColor("#DC2626"));
                canvas.drawCircle(comp.x, comp.y, 22, ledPaint);
                // ขายืน LED
                wirePaint.setColor(Color.GRAY);
                wirePaint.setStrokeWidth(4f);
                canvas.drawLine(comp.x - 10, comp.y, comp.x - 10, comp.y + 40, wirePaint);
                canvas.drawLine(comp.x + 10, comp.y, comp.x + 15, comp.y + 40, wirePaint);
            }
        }

        // 4. วาดสายไฟ (เป็นเส้นโค้ง Bezier เหมือน Tinkercad)
        for (PlacedWire w : wires) {
            wirePaint.setColor(w.color);
            wirePaint.setStrokeWidth(8f);

            Path path = new Path();
            path.moveTo(w.startX, w.startY);
            float midY = (w.startY + w.endY) / 2 - 30; // ดึงเส้นให้โค้งขึ้น
            path.quadTo((w.startX + w.endX) / 2, midY, w.endX, w.endY);
            canvas.drawPath(path, wirePaint);

            // วาดจุดเชื่อมสายไฟ
            canvas.drawCircle(w.startX, w.startY, 8, wirePaint);
            canvas.drawCircle(w.endX, w.endY, 8, wirePaint);
        }

        // 5. วาดสายไฟที่กำลังลากอยู่
        if (isDrawing && selectedMode.equals("WIRE")) {
            wirePaint.setColor(selectedWireColor);
            wirePaint.setStrokeWidth(8f);
            canvas.drawLine(startX, startY, curX, curY, wirePaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                curX = startX;
                curY = startY;

                if (selectedMode.equals("LED")) {
                    components.add(new PlacedComponent("LED", startX, startY));
                    invalidate();
                    return true;
                }
                isDrawing = true;
                return true;

            case MotionEvent.ACTION_MOVE:
                curX = event.getX();
                curY = event.getY();
                invalidate();
                return true;

            case MotionEvent.ACTION_UP:
                if (isDrawing && selectedMode.equals("WIRE")) {
                    wires.add(new PlacedWire(startX, startY, event.getX(), event.getY(), selectedWireColor));
                }
                isDrawing = false;
                invalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }

    private static class PlacedWire {
        float startX, startY, endX, endY;
        int color;
        PlacedWire(float sx, float sy, float ex, float ey, int c) {
            this.startX = sx; this.startY = sy; this.endX = ex; this.endY = ey; this.color = c;
        }
    }

    private static class PlacedComponent {
        String type;
        float x, y;
        PlacedComponent(String type, float x, float y) {
            this.type = type; this.x = x; this.y = y;
        }
    }
}