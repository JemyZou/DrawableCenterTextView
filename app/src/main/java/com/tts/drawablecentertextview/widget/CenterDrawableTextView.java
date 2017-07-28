package com.tts.drawablecentertextview.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.tts.drawablecentertextview.R;

/**
 * Author: Jemy
 * Email: jemy.zou@easternphoenix.com
 * Time: 2017/7/28 14:51
 **/
public class CenterDrawableTextView extends TextView {
    public static final String LEFT="0";
    public static final String RIGHT="1";
    public static String LOCATION=LEFT;

    public CenterDrawableTextView(Context context) {
        this(context,null);
    }

    public CenterDrawableTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CenterDrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CenterDrawableTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        //get drawable location
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CenterDrawableTextView);
        String location = typedArray.getString(R.styleable.CenterDrawableTextView_pictureLocation);
        if(LEFT.equalsIgnoreCase(location)){
            LOCATION = LEFT;
        } else if (RIGHT.equalsIgnoreCase(location)) {
            LOCATION = RIGHT;
        }
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float textWidth = getTextWidth();
        int compoundDrawablePadding = getCompoundDrawablePadding();
        if (LOCATION.equalsIgnoreCase(LEFT)) {
            drawableLeft(canvas,textWidth+compoundDrawablePadding);
        } else if (LOCATION.equalsIgnoreCase(RIGHT)) {
            drawableRight(canvas,textWidth+compoundDrawablePadding);
        }
        super.onDraw(canvas);
    }

    private void drawableRight(Canvas canvas, float width) {
        setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
        int drawableWidth=getDrawableWidth(2);
        float transX= -(getWidth()-(width+drawableWidth))/2;
        canvas.translate(transX,0);
    }

    private void drawableLeft(Canvas canvas, float width) {
        setGravity(Gravity.CENTER_VERTICAL);
        int drawableWidth=getDrawableWidth(0);
        float transX = (getWidth()-(width+drawableWidth))/2;
        canvas.translate(transX,0);
    }

    private float getTextWidth() {
        return getPaint().measureText(getText().toString());
    }

    /**
     * @param position
     * @return
     * 获取drawable的宽度，position：left 0 top 1 right 2 bottom 3
     */
    private int getDrawableWidth(int position) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null && drawables[position]!=null) {
            return drawables[position].getIntrinsicWidth();
        }
        return 0;
    }
}
