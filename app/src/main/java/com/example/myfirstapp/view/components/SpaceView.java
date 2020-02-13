package com.example.myfirstapp.view.components;

import android.animation.TimeAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Random;

public class SpaceView extends View {
    private static final int COUNT = 50;

    private static final int SEED = 1337;

    private static final int STAR_RADIUS = 2;

    private static final int STAR_DIAMETER = 4;
    /** The minimum scale of a star */
    private static final float ALPHA_MIN_PART = 50;
    /** How much of the scale that's based on randomness */
    private static final float ALPHA_RANDOM_PART = 100;

    private final Star[] stars = new Star[COUNT];

    private final Random mRnd = new Random(SEED);

    private TimeAnimator mTimeAnimator;

    private Paint mPaint;

    public SpaceView(Context context) {
        super(context);
        init();
    }

    public SpaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init () {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        mTimeAnimator = new TimeAnimator();
        mTimeAnimator.setTimeListener((TimeAnimator animator, long totalTime, long deltaTime) -> {
            if (!isLaidOut()) {
                return;
            }

            updateState(deltaTime);
            invalidate();
        });
    }

    @Override
    protected void onDetachedFromWindow () {
        super.onDetachedFromWindow();

        mTimeAnimator.cancel();
        mTimeAnimator.setTimeListener(null);
        mTimeAnimator.removeAllListeners();
        mTimeAnimator = null;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        for (int i = 0; i < stars.length; i++) {
            final Star star = new Star();
            initializeStar(star, w, h);
            stars[i] = star;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for (final Star star : stars) {
            // Ignore the star if it's outside of the view bounds
            if (star.y + STAR_DIAMETER < 0) {
                continue;
            }

            mPaint.setAlpha((int) star.alpha);
            canvas.drawCircle(star.x, star.y, STAR_RADIUS, mPaint);
        }
    }

    private void updateState (float deltaMs) {
        // Converting to seconds since PX/S constants are easier to understand
        final float deltaSeconds = deltaMs / 1000f;
        final int viewHeight = getHeight();
        final int viewWidth = getWidth();

        for (final Star star : stars) {
            // Move the star based on the elapsed time and it's speed
            if (star.speed < 700) {
                star.speed += 70 * deltaSeconds;
            }

            star.y -= star.speed * deltaSeconds;

            // If the star is completely outside of the view bounds after
            // updating it's position, recycle it.
            if (star.y + STAR_DIAMETER < 0) {
                star.x = (viewWidth - STAR_DIAMETER) * mRnd.nextFloat();
                star.y =  viewHeight + STAR_DIAMETER;
            }
        }
    }

    private void initializeStar (Star star, int viewWidth, int viewHeight) {
        star.x = (viewWidth - STAR_DIAMETER) * mRnd.nextFloat();
        star.y = (viewHeight - STAR_DIAMETER) * mRnd.nextFloat();
        star.alpha = ALPHA_MIN_PART + ALPHA_RANDOM_PART * mRnd.nextFloat();
        star.speed = 5f;
    }

    public void startAnimation () {
        mTimeAnimator.start();
    }

    /**
     * Pause the animation if it's running
     * Should probably be called from Activity#onPause()
     */
    public void pause() {
        if (mTimeAnimator != null && mTimeAnimator.isRunning()) {
            // Store the current play time for later.
            mTimeAnimator.pause();
        }
    }

    /**
     * Resume the animation if not already running
     * Should probably be called from Activity#onResume()
     */
    public void resume() {
        if (mTimeAnimator != null && mTimeAnimator.isPaused()) {
            mTimeAnimator.start();
            // Why set the current play time?
            // TimeAnimator uses timestamps internally to determine the delta given
            // in the TimeListener. When resumed, the next delta received will the whole
            // pause duration, which might cause a huge jank in the animation.
            // By setting the current play time, it will pick of where it left off.
        }
    }

    public void setStarsColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }

    private static class Star {
        float x;
        float y;
        float alpha;
        float speed;
    }
}
