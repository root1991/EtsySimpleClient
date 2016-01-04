package com.test.util;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 * Draws indicator for each page. The current page indicator is colored differently
 * than the unselected pages.
 */
public class CircularPageIndicator extends BasePageIndicator {

    public CircularPageIndicator(Context context) {
        super(context);
    }

    public CircularPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularPageIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mViewPager == null) {
            return;
        }
        final int count = mViewPager.getAdapter().getCount();
        if (count == 0) {
            return;
        }

        if (mCurrentPage >= count) {
            setCurrentItem(count - 1);
            return;
        }

        final float lineWidthAndGap = mRadius * 2 + mGapSize;
        final float indicatorWidth = (count * lineWidthAndGap) - mGapSize;

        float horizontalOffset = getPaddingLeft();
        if (mCentered) {
            horizontalOffset += ((getWidth() - getPaddingLeft() - getPaddingRight()) / 2.0f) - (indicatorWidth / 2.0f);
        }

        //Draw Circles
        for (int i = 0; i < count; i++) {
            float pivotX = horizontalOffset + (i * lineWidthAndGap) + mRadius;
            float pivotY = getPaddingTop() + mRadius;
            canvas.drawCircle(pivotX, pivotY, mRadius, mPaintUnselected);
            if (i == mCurrentPage) {
                canvas.drawCircle(pivotX, pivotY, mRadius, mPaintSelected);
            }
        }
    }

    @Override
    protected int measureWidth(int measureSpec) {
        float result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if ((specMode == MeasureSpec.EXACTLY) || (mViewPager == null)) {
            //We were told how big to be
            result = specSize;
        } else {
            //Calculate the width according the views count
            final int count = mViewPager.getAdapter().getCount();
            result = getPaddingLeft() + (count * mRadius * 2) + ((count - 1) * mGapSize) + getPaddingRight();
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return (int) Math.ceil(result);
    }

    @Override
    protected int measureHeight(int measureSpec) {
        float result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            //We were told how big to be
            result = specSize;
        } else {
            //Measure the height
            result = getPaddingTop() + mRadius * 2 + getPaddingBottom();
            //Respect AT_MOST value if that was what is called for by measureSpec
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return (int) Math.ceil(result);
    }

}
