package com.balajiseeds.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class CustomRecyclerView extends RecyclerView {

    private final String overlayText = "No Records Found";
    private Paint textPaint;
    private Typeface typeface;

    public CustomRecyclerView(Context context) {
        super(context);
        init();
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(50);
        textPaint.setTextAlign(Paint.Align.CENTER);

//        typeface = Typeface.createFromAsset(getContext().getAssets(), ".ttf");
//        textPaint.setTypeface(typeface);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (getAdapter() == null || getAdapter().getItemCount() == 0) {
            // Draw text when RecyclerView is empty
            int x = getWidth() / 2;
            int y = getHeight() / 2;

            canvas.drawText(overlayText, x, y, textPaint);
        }
    }
}