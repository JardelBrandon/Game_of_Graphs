package com.example.robotica.navigationdrawer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Retangulo extends View {
    private Paint paint;
    private RectF rectF;

    public Retangulo(Context context) {
        super(context);
    }

    @Override
    public void onDraw(Canvas canvas) {
        paint= new Paint();
        paint.setColor(Color.WHITE);
        int larguraBorda = 3;
        paint.setStrokeWidth(larguraBorda);
        canvas.drawRect(rectF.left, rectF.top, rectF.right, rectF.bottom, paint);
        super.onDraw(canvas);
    }

    public void setRectF(RectF rectF) {
        this.rectF = rectF;
    }
}
