package com.example.money.widget;

import com.example.money.R;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by su on 15-11-24.
 */
public abstract class CircleProgressView extends ImageView {
    protected Paint mPaint;
    protected int mRingColor;
    protected final int mRemainderColor;
    protected Resources mResources;
    protected Paint mRemainderPaint;
    protected int mRingWidth;
    protected float mProgress;

    protected float delta = 0;
    protected Animator.AnimatorListener listener;

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mResources = context.getResources();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SectorRing);
        mRingColor = a.getColor(R.styleable.SectorRing_ringColor, mResources.getColor(R.color.black));
        mRemainderColor = a.getColor(R.styleable.SectorRing_remainderColor, mResources.getColor(R.color.white));
        mRingWidth = a.getDimensionPixelSize(R.styleable.SectorRing_ringWidth, mResources.getDimensionPixelOffset(R.dimen.dimen_5_dip));
        mProgress = a.getFloat(R.styleable.SectorRing_progress, 0);
        a.recycle();

        setProgress(mProgress);
    }

    protected Paint makePaint(boolean isHollow, boolean isRoundPaint, int ringWidth, int ringColor) {
        Paint paint = new Paint();
        paint.setAntiAlias(true); //消除锯齿
        if (isHollow) {
            paint.setStyle(Paint.Style.STROKE); //绘制空心圆
        }
        if (isRoundPaint) {
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
        }
        paint.setStrokeWidth(ringWidth);
        paint.setColor(ringColor);
        return paint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureSize(widthMeasureSpec), measureSize(heightMeasureSpec));
    }

    private int measureSize(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            result = mResources.getDimensionPixelSize(R.dimen.dimen_56_dip);
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by measureSpec
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public void setProgress(float progress) {
        delta = progress;
        mProgress = progress;
    }

    public int getRingWidth() {
        return mRingWidth;
    }

    public void setRingWidth(int ringWidth) {
        this.mRingWidth = ringWidth;
        mPaint.setStrokeWidth(mRingWidth);
        mRemainderPaint.setStrokeWidth(mRingWidth);
    }

    public void setRingColor(int ringColor) {
        this.mRingColor = ringColor;
        mPaint.setColor(ringColor);
        invalidate();
    }
}
