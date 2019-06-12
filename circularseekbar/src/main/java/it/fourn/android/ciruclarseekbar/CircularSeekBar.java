package it.fourn.android.ciruclarseekbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import static it.fourn.android.ciruclarseekbar.Utils.removeAlpha;

public class CircularSeekBar extends View {

    private static final float HALF_DIVIDER = 0.5f;
    private static final float DEFAULT_SIZE_DIVIDER = 0.15f; // 1/30

    public static int INVALID_VALUE = -1;
    public static final int MAX = 100;
    public static final int MIN = 0;
    public static final int ARCH_ALPHA = 50;

    // Offset = -90 indicates that the progress starts from 12 o'clock.
    private static final int ANGLE_OFFSET = -90;

    // defaul text size in pizel
    private static final int DEFAULT_TEXT_SIZE = 71;

    // The current points value.
    private float mPoints = MIN;

    // The increment/decrement value for each movement of progress.
    private float mStep = 1;

    private float mProgressWidth = 12;
    private float mArcWidth = 12;
    private boolean mEnabled = true;

    // The counts of point update to determine whether to change previous progress.
    private int mUpdateTimes = 0;
    private float mPreviousProgress = -1;
    private float mCurrentProgress = 0;

    // Determine whether reach max of point.
    private boolean isMax = false;

    // Determine whether reach min of point.
    private boolean isMin = false;

    // arc
    private float mArcRadius = 0;
    private RectF mArcRect = new RectF();
    private float arcDiameter;
    private Paint mArcPaint;

    // colored progress arc
    private RectF mProgressRect = new RectF();
    private float mProgressSweep = 0;
    private Paint mProgressPaint;

    // indicator of progress
    private RectF mIndicatorRect = new RectF();
    // the (x, y) coordinator of indicator icon
    private float mIndicatorIconX;
    private float mIndicatorIconY;
    private float indicatorRadius;
    // The Drawable for the seek arc thumbnail
    private Paint mIndicatorPaint;

    // text
    private float mTextSize = DEFAULT_TEXT_SIZE;
    private Paint mTextPaint;
    private Rect mTextRect;
    @ColorInt
    private int mTextColor;
    private String mText;
    private boolean mDynamicTextColor;
    private boolean mIsTextProgress;

    private float mTranslateX;
    private float mTranslateY;

    // arc and progress color
    private Matrix sweepGradientMatrix;

    private OnCircularSeekBarChangeListener mOnRoundedSeekChangeListener;

    private @ColorInt
    int[] colorList;

    public CircularSeekBar(Context context) {
        this(context, null, -1);
    }

    public CircularSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CircularSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircularSeekBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttributes(attrs);
        init();
    }

    private void initAttributes(AttributeSet attributeSet) {
        if (attributeSet != null) {
            final TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.CircularSeekBar);

            mStep = typedArray.getFloat(R.styleable.CircularSeekBar_cs_step, mStep);

            mProgressWidth = typedArray.getDimension(R.styleable.CircularSeekBar_cs_progressWidth, mProgressWidth);

            mArcWidth = typedArray.getDimension(R.styleable.CircularSeekBar_cs_arcWidth, mArcWidth);
            indicatorRadius = typedArray.getDimension(R.styleable.CircularSeekBar_cs_indicatorRadius, 0);
            mArcRadius = typedArray.getDimension(R.styleable.CircularSeekBar_cs_arcRadius, 0);

            int colorListId = typedArray.getResourceId(R.styleable.CircularSeekBar_cs_color_list, R.array.default_color_list);
            this.colorList = getResources().getIntArray(colorListId);

            mText = typedArray.getString(R.styleable.CircularSeekBar_cs_text);
            mTextSize = (int) typedArray.getDimension(R.styleable.CircularSeekBar_cs_text_size, mTextSize);
            mTextColor = typedArray.getColor(R.styleable.CircularSeekBar_cs_text_color, ContextCompat.getColor(getContext(), R.color.default_text_color));
            mDynamicTextColor = typedArray.getBoolean(R.styleable.CircularSeekBar_cs_dynamic_text_color, false);
            mIsTextProgress = typedArray.getBoolean(R.styleable.CircularSeekBar_cs_text_progress, false);

            mEnabled = typedArray.getBoolean(R.styleable.CircularSeekBar_cs_enabled, mEnabled);
            typedArray.recycle();
        }
    }

    private void init() {

        float density = getResources().getDisplayMetrics().density;

        mProgressWidth = (mProgressWidth * density);
        mArcWidth = (mArcWidth * density);

        mProgressSweep = mPoints / valuePerDegree();

        // arc
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(mArcWidth);

        // paint
        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(mProgressWidth);

        // indicator
        mIndicatorPaint = new Paint();
        mIndicatorPaint = new Paint();
        mIndicatorPaint.setAntiAlias(true);

        // text
        mTextRect = new Rect();
        mTextSize = (int) (mTextSize * density);
        // text paint
        mTextPaint = new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(mTextSize);

        sweepGradientMatrix = new Matrix();
        setDrawingCacheEnabled(true); // to retrieve pixel color
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);

        mTranslateX = (width * HALF_DIVIDER);
        mTranslateY = (height * HALF_DIVIDER);

        if (mArcRadius == 0) {
            mArcRadius = height * DEFAULT_SIZE_DIVIDER;
        }

        arcDiameter = 2 * mArcRadius;
        float top = height * HALF_DIVIDER - (arcDiameter * HALF_DIVIDER);
        float left = width * HALF_DIVIDER - (arcDiameter * HALF_DIVIDER);
        mArcRect.set(left, top, left + arcDiameter, top + arcDiameter);
        mProgressRect.set(left, top, left + arcDiameter, top + arcDiameter);
        mIndicatorRect.set(0, 0, 0, 0);

        updateIndicatorIconPosition();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.save();

        sweepGradientMatrix.setRotate(ANGLE_OFFSET, mArcRect.centerX(), mArcRect.centerY());
        SweepGradient sweepGradient = new SweepGradient(mArcRect.centerX(), mArcRect.centerY(), this.colorList, null);
        sweepGradient.setLocalMatrix(sweepGradientMatrix);

        // arc
        mArcPaint.setShader(sweepGradient);
        mArcPaint.setAlpha(ARCH_ALPHA);
        canvas.drawArc(mArcRect, ANGLE_OFFSET, 360, false, mArcPaint);

        // progress
        mProgressPaint.setShader(sweepGradient);
        canvas.drawArc(mProgressRect, ANGLE_OFFSET, mProgressSweep, false, mProgressPaint);

        // draw the text

        if (mIsTextProgress) {
            mText = String.valueOf(mPoints);
        }

        if (mText != null) {
            mTextPaint.getTextBounds(mText, 0, mText.length(), mTextRect);
            // center the text
            float xPos = getWidth() * HALF_DIVIDER - mTextRect.width() * HALF_DIVIDER;
            float yPos = ((mArcRect.centerY()) - ((mTextPaint.descent() + mTextPaint.ascent()) * HALF_DIVIDER));
            canvas.drawText(mText, xPos, yPos, mTextPaint);
        }

        if (mEnabled) {
            // retrieve pixel color
            // src: https://stackoverflow.com/questions/13550591/how-to-get-canvas-pixel
            buildDrawingCache();
            // indicator color
            Bitmap drownIndicatorBitmap = getDrawingCache(true);
            int x = Math.round(mTranslateX - mIndicatorIconX);
            int y = Math.round(mTranslateY - mIndicatorIconY);
            int pixelColor = drownIndicatorBitmap.getPixel(x, y);
            pixelColor = removeAlpha(pixelColor);
            mIndicatorPaint.setColor(pixelColor);
            if (mDynamicTextColor) {
                mTextPaint.setColor(pixelColor);
            }

            // draw the indicator
            canvas.translate(mTranslateX - mIndicatorIconX, mTranslateY - mIndicatorIconY);

            if (indicatorRadius == 0) {
                indicatorRadius = mArcRadius * DEFAULT_SIZE_DIVIDER;
            }
            canvas.drawCircle(0, 0, indicatorRadius, mIndicatorPaint);
        }

        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mEnabled) {
            this.getParent().requestDisallowInterceptTouchEvent(true);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (mOnRoundedSeekChangeListener != null) {
                        mOnRoundedSeekChangeListener.onStartTrackingTouch(this);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    move(event);
                    break;
                case MotionEvent.ACTION_UP:
                    if (mOnRoundedSeekChangeListener != null) {
                        mOnRoundedSeekChangeListener.onStopTrackingTouch(this);
                    }
                    move(event);
                    setPressed(false);
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    if (mOnRoundedSeekChangeListener != null) {
                        mOnRoundedSeekChangeListener.onStopTrackingTouch(this);
                    }
                    setPressed(false);
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            return true;
        }
        return false;
    }

    private void move(@NonNull MotionEvent event) {
        setPressed(true);
        // the current touch angle of arc.
        double mTouchAngle = convertTouchEventPointToAngle(event.getX(), event.getY());
        float progress = convertAngleToProgress(mTouchAngle);
        updateProgress(progress);
    }

    private double convertTouchEventPointToAngle(float xPos, float yPos) {
        // transform touch coordinate into component coordinate
        float x = xPos - mTranslateX;
        float y = yPos - mTranslateY;

        double angle = Math.toDegrees(Math.atan2(y, x) + (Math.PI * HALF_DIVIDER));
        angle = (angle < 0) ? (angle + 360) : angle;
        return angle;
    }

    private float convertAngleToProgress(double angle) {
        return Math.round(valuePerDegree() * angle);
    }

    private float valuePerDegree() {
        return MAX / 360.0f;
    }

    private void updateIndicatorIconPosition() {
        float thumbAngle = (mProgressSweep + 90);
        mIndicatorIconX = Math.round(mArcRadius * Math.cos(Math.toRadians(thumbAngle)));
        mIndicatorIconY = Math.round(mArcRadius * Math.sin(Math.toRadians(thumbAngle)));
    }

    private void updateProgress(float progress) {

        // detect points change closed to max or min
        final float maxDetectValue = Math.round(MAX * 0.95);
        final float minDetectValue = Math.round(MAX * 0.05) + MIN;

        mUpdateTimes++;
        if (progress == INVALID_VALUE) {
            return;
        }

        // avoid accidentally touch to become max from original point
        if (progress > maxDetectValue && mPreviousProgress == INVALID_VALUE) {
            return;
        }


        // record previous and current progress change
        if (mUpdateTimes == 1) {
            mCurrentProgress = progress;
        } else {
            mPreviousProgress = mCurrentProgress;
            mCurrentProgress = progress;
        }

        mPoints = progress - (progress % mStep);


        // Determine whether reach max or min to lock point update event.
        // When reaching max, the progress will drop from max (or maxDetectPoints ~ max to min (or min ~ minDetectPoints) and vice versa.
        // If reach max or min, stop increasing / decreasing to avoid exceeding the max / min.
        if (mUpdateTimes > 1 && !isMin && !isMax) {
            if (mPreviousProgress >= maxDetectValue && mCurrentProgress <= minDetectValue &&
                    mPreviousProgress > mCurrentProgress) {
                isMax = true;
                progress = MAX;
                mPoints = MAX;
                if (mOnRoundedSeekChangeListener != null) {
                    mOnRoundedSeekChangeListener.onProgressChange(this, progress);
                    return;
                }
            } else if ((mCurrentProgress >= maxDetectValue
                    && mPreviousProgress <= minDetectValue
                    && mCurrentProgress > mPreviousProgress) || mCurrentProgress <= MIN) {
                isMin = true;
                progress = MIN;
                mPoints = MIN;
                if (mOnRoundedSeekChangeListener != null) {
                    mOnRoundedSeekChangeListener.onProgressChange(this, progress);
                    return;
                }
            }
            invalidate();
        } else {

            // Detect whether decreasing from max or increasing from min, to unlock the update event.
            // Make sure to check in detect range only.
            if (isMax & (mCurrentProgress < mPreviousProgress) && mCurrentProgress >= maxDetectValue) {
                isMax = false;
            }
            if (isMin
                    && (mPreviousProgress < mCurrentProgress)
                    && mPreviousProgress <= minDetectValue && mCurrentProgress <= minDetectValue
                    && mPoints >= MIN) {
                isMin = false;
            }
        }

        if (!isMax && !isMin) {
            progress = (progress > MAX) ? MAX : progress;
            progress = (progress < MIN) ? MIN : progress;

            if (mOnRoundedSeekChangeListener != null) {
                progress = progress - (progress % mStep);

                mOnRoundedSeekChangeListener.onProgressChange(this, progress);
            }

            mProgressSweep = progress / valuePerDegree();
            updateIndicatorIconPosition();
            invalidate();
        }
    }

    public void setProgressWidth(float mProgressWidth) {
        this.mProgressWidth = mProgressWidth;
        mProgressPaint.setStrokeWidth(mProgressWidth);
    }

    public void setArcWidth(float mArcWidth) {
        this.mArcWidth = mArcWidth;
        mArcPaint.setStrokeWidth(mArcWidth);
    }

    public boolean isEnabled() {
        return mEnabled;
    }

    public void setEnabled(boolean enabled) {
        this.mEnabled = enabled;
    }

    public void setStep(float step) {
        mStep = step;
    }

    public void setArcDiameter(float arcDiameter) {
        this.arcDiameter = arcDiameter;
        invalidate();
    }

    public void setTextColor(int textColor) {
        mTextPaint.setColor(textColor);
        invalidate();
    }

    public void setTextSize(float textSize) {
        mTextSize = textSize;
        mTextPaint.setTextSize(mTextSize);
        invalidate();
    }

    public void setText(String text) {
        this.mText = text;
    }

    public boolean isTextProgress() {
        return mIsTextProgress;
    }

    public void setOnRoundedSeekChangeListener(OnCircularSeekBarChangeListener onRoundedSeekChangeListener) {
        mOnRoundedSeekChangeListener = onRoundedSeekChangeListener;
    }
}