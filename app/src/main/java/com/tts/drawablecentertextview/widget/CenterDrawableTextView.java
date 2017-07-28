package com.tts.drawablecentertextview.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Author: Jemy
 **/
public class CenterDrawableTextView extends TextView {
    private static final int LEFT = 0;//和getCompoundDrawables()数组角标一致
    private static final int RIGHT = 2;

    public CenterDrawableTextView(Context context) {
        this(context, null);
    }

    public CenterDrawableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CenterDrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CenterDrawableTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float textWidth = getTextWidth();
        int compoundDrawablePadding = getCompoundDrawablePadding();
        switch (getDrawablePosition()) {
            case LEFT:
                drawableLeft(canvas, textWidth + compoundDrawablePadding);
                break;
            case RIGHT:
                drawableRight(canvas, textWidth + compoundDrawablePadding);
                break;
        }
        super.onDraw(canvas);
    }

    private int getDrawablePosition() {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables == null) {
            return LEFT;
        }
        if (drawables[LEFT] != null) {
            return LEFT;
        } else if (drawables[RIGHT] != null) {
            return RIGHT;
        }
        return LEFT;
    }

    private void drawableRight(Canvas canvas, float width) {
        setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        int drawableWidth = getDrawableWidth(2);
        float transX = -(getWidth() - (width + drawableWidth)) / 2;
        canvas.translate(transX, 0);
    }

    private void drawableLeft(Canvas canvas, float width) {
        setGravity(Gravity.CENTER_VERTICAL);
        int drawableWidth = getDrawableWidth(0);
        float transX = (getWidth() - (width + drawableWidth)) / 2;
        canvas.translate(transX, 0);
    }

    private float getTextWidth() {
        return getPaint().measureText(getText().toString());
    }

    /**
     * @param position
     * @return 获取drawable的宽度，position：left 0 top 1 right 2 bottom 3
     */
    private int getDrawableWidth(int position) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null && drawables[position] != null) {
            return drawables[position].getIntrinsicWidth();
        }
        return 0;
    }
}
