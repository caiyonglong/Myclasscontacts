package com.cyl.myclasscontacts.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 永龙 on 2015/11/18.
 */
public class SidebarView extends View {
    //首字母
    public String[] arrLetters = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#" };
    private OnLetterClickedListener listener = null;
    private TextView textView_dialog;
    private int isChoosedPosition = -1;

    public void setTextView_dialog(TextView textView_dialog) {
        this.textView_dialog = textView_dialog;
    }

    public SidebarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //当前View 的宽高度
        int width = getWidth();
        int height = getHeight();

        //当前每个字母所占的高度
        int singleTextHeight = height / arrLetters.length;

        /**
         * Paint类介绍
         * Paint即画笔，在绘图过程中起到了极其重要的作用，画笔主要保存了颜色，
         * 样式等绘制信息，指定了如何绘制文本和图形，画笔对象有很多设置方法，
         * 大体上可以分为两类，一类与图形绘制相关，一类与文本绘制相关。
         * 1.图形绘制

         * setAntiAlias(boolean aa);
         * 设置是否使用抗锯齿功能，会消耗较大资源，绘制图形速度会变慢。

         * 2.文本绘制
         * setTextSize(float textSize);
         * 设置绘制文字的字号大小
         * setTypeface(Typeface typeface);
         * 设置Typeface对象，即字体风格，包括粗体，斜体以及衬线体，非衬线体等
         */
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(16);
        paint.setTypeface(Typeface.DEFAULT);
        for (int i=0 ;i<arrLetters.length;i++){
            paint.setColor(Color.RED);
            if (i==isChoosedPosition){
                paint.setColor(Color.RED);
                paint.setFakeBoldText(true);
            }
            float x = (width -paint.measureText(arrLetters[i]))/2;
            float y = singleTextHeight*(i+1);
            canvas.drawText(arrLetters[i],x,y,paint);
            paint.reset();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        int position = (int) (y / getHeight() * arrLetters.length);
        int lastChoosedPosition = isChoosedPosition;
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                setBackgroundColor(Color.WHITE);
                if (textView_dialog != null) {
                    textView_dialog.setVisibility(View.GONE);
                }
                isChoosedPosition = -1;
                invalidate();
                break;
            default:
                // 触摸边框背景颜色改变
                setBackgroundColor(Color.parseColor("#ffddcc"));
                if (lastChoosedPosition != position) {
                    if (position >= 0 && position < arrLetters.length) {
                        if (listener != null) {
                            listener.onLetterClicked(arrLetters[position]);
                        }
                        if (textView_dialog != null) {
                            textView_dialog.setVisibility(View.VISIBLE);
                            textView_dialog.setText(arrLetters[position]);
                        }
                        isChoosedPosition = position;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }
    public interface OnLetterClickedListener {
        public void onLetterClicked(String str);
    }

    public void setOnLetterClickedListener(OnLetterClickedListener listener) {
        this.listener = listener;
    }
}
