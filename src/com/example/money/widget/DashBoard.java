package com.example.money.widget;

import com.example.money.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by su on 2014/9/17.
 */
public class DashBoard extends CircleProgressView {
    private RectF mBigOval;
    private static final float START = -210;
    private static final int FULL_ANGLE = 240;

    public DashBoard(Context context) {
        this(context, null);
    }

    public DashBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = makePaint(true, true, mRingWidth, mRingColor);
        mRemainderPaint = makePaint(true, true, mRingWidth, mRemainderColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(mBigOval, START, FULL_ANGLE, false, mRemainderPaint);
        canvas.drawArc(mBigOval, START, delta, false, mPaint);
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw || h != oldh) {
        	mBigOval = new RectF((mRingWidth + 1) / 2, (mRingWidth + 1) / 2,
                    getWidth() - (mRingWidth + 1) / 2, getHeight() - (mRingWidth + 1) / 2);
        }
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
