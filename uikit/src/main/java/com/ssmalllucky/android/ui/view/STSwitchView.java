package com.ssmalllucky.android.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.ssmalllucky.android.ui.R;

/**
 * 开关按钮，替换Android系统自带的Switch控件。
 * <p>
 * 相比于Switch控件，SwitchView的优势：
 * <p>
 * 1. 饱满的UI显示，适配更大的屏幕
 * <p>
 * 2. 状态切换时的按钮背景颜色动态变化
 */
public class STSwitchView extends View {

    public static final String TAG = "SwitchView";

    private static final int STATE_SWITCH_ON = 4;
    private static final int STATE_SWITCH_ON2 = 3;
    private static final int STATE_SWITCH_OFF2 = 2;
    private static final int STATE_SWITCH_OFF = 1;

    private final LinearInterpolator interpolator = new LinearInterpolator();
    private final Paint paint = new Paint();
    private final Path sPath = new Path();
    private final Path bPath = new Path();
    private final RectF bRectF = new RectF();
    private float sAnim, bAnim;
    private RadialGradient shadowGradient;

    protected float ratioAspect = 0.68f; // (0,1]
    protected float animationSpeed = 0.03f; // (0,1]

    private int state;
    private int lastState;
    private boolean isCanVisibleDrawing = false;
    private OnClickListener mOnClickListener;
    protected int colorPrimary;
    protected int colorPrimaryDark;
    protected int colorOff;
    protected int colorOffDark;
    protected int colorShadow;
    protected int colorBar;
    protected int colorBackground;
    protected boolean hasShadow;
    protected boolean isChecked;

    private float sRight;
    private float sCenterX, sCenterY;
    private float sScale;

    private float bOffset;
    private float bRadius, bStrokeWidth;
    private float bWidth;
    private float bLeft;
    private float bRight;
    private float bOnLeftX, bOn2LeftX, bOff2LeftX, bOffLeftX;

    private float shadowReservedHeight;

    public STSwitchView(Context context) {
        this(context, null);
    }

    public STSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        final int DEFAULT_COLOR_PRIMARY = 0xFF4BD763;
        final int DEFAULT_COLOR_PRIMARY_DARK = 0xFF3AC652;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.STSwitchView);
        colorPrimary = a.getColor(R.styleable.STSwitchView_primaryColor, DEFAULT_COLOR_PRIMARY);
        colorPrimaryDark = a.getColor(R.styleable.STSwitchView_primaryColorDark, DEFAULT_COLOR_PRIMARY_DARK);
        colorOff = a.getColor(R.styleable.STSwitchView_offColor, 0xFFE3E3E3);
        colorOffDark = a.getColor(R.styleable.STSwitchView_offColorDark, 0xFFEAEAEA);
        colorShadow = a.getColor(R.styleable.STSwitchView_shadowColor, 0xFFEAEAEA);
        colorBar = a.getColor(R.styleable.STSwitchView_barColor, 0xFFFFFFFF);
        colorBackground = a.getColor(R.styleable.STSwitchView_bgColor, 0xFFEAEAEA);
        ratioAspect = a.getFloat(R.styleable.STSwitchView_ratioAspect, 0.68f);
        hasShadow = a.getBoolean(R.styleable.STSwitchView_hasShadow, true);
        isChecked = a.getBoolean(R.styleable.STSwitchView_isOpened, false);
        state = isChecked ? STATE_SWITCH_ON : STATE_SWITCH_OFF;
        lastState = state;
        a.recycle();

        if (colorPrimary == DEFAULT_COLOR_PRIMARY && colorPrimaryDark == DEFAULT_COLOR_PRIMARY_DARK) {
            try {
                TypedValue primaryColorTypedValue = new TypedValue();
                context.getTheme().resolveAttribute(android.R.attr.colorPrimary, primaryColorTypedValue, true);
                if (primaryColorTypedValue.data > 0) colorPrimary = primaryColorTypedValue.data;
                TypedValue primaryColorDarkTypedValue = new TypedValue();
                context.getTheme().resolveAttribute(android.R.attr.colorPrimaryDark, primaryColorDarkTypedValue, true);
                if (primaryColorDarkTypedValue.data > 0)
                    colorPrimaryDark = primaryColorDarkTypedValue.data;
            } catch (Exception ignore) {
            }
        }
    }

    public void setColor(int newColorPrimary, int newColorPrimaryDark) {
        setColor(newColorPrimary, newColorPrimaryDark, colorOff, colorOffDark);
    }

    public void setColor(int newColorPrimary, int newColorPrimaryDark, int newColorOff, int newColorOffDark) {
        setColor(newColorPrimary, newColorPrimaryDark, newColorOff, newColorOffDark, colorShadow);
    }

    public void setColor(int newColorPrimary, int newColorPrimaryDark, int newColorOff, int newColorOffDark, int newColorShadow) {
        colorPrimary = newColorPrimary;
        colorPrimaryDark = newColorPrimaryDark;
        colorOff = newColorOff;
        colorOffDark = newColorOffDark;
        colorShadow = newColorShadow;
        invalidate();
    }

    public void setColor(int newColorPrimary, int newColorPrimaryDark, int newColorOff, int newColorOffDark, int newColorShadow, int newColorBar, int newColorBackground) {
        colorPrimary = newColorPrimary;
        colorPrimaryDark = newColorPrimaryDark;
        colorOff = newColorOff;
        colorOffDark = newColorOffDark;
        colorShadow = newColorShadow;
        colorBar = newColorBar;
        colorBackground = newColorBackground;
        invalidate();
    }

    /**
     * 是否设置阴影
     *
     * @param shadow
     */
    public void setShadow(boolean shadow) {
        hasShadow = shadow;
        invalidate();
    }

    /**
     * 开启/关闭 状态
     *
     * @return true 为开启，false 为关闭
     */
    public boolean isChecked() {
        return isChecked;
    }

    /**
     * 设置 开启/关闭 状态
     *
     * @param isOpened true 为开启，false 为关闭
     */
    public void setChecked(boolean isOpened) {
        int wishState = isOpened ? STATE_SWITCH_ON : STATE_SWITCH_OFF;
        if (wishState == state) {
            return;
        }
        refreshState(wishState);
    }

    /**
     * 设置 开启/关闭 状态，包含动画
     *
     * @param isOpened true 为开启，false 为关闭
     */
    public void toggleSwitch(boolean isOpened) {
        int wishState = isOpened ? STATE_SWITCH_ON : STATE_SWITCH_OFF;
        if (wishState == state) {
            return;
        }
        if ((wishState == STATE_SWITCH_ON && (state == STATE_SWITCH_OFF || state == STATE_SWITCH_OFF2))
                || (wishState == STATE_SWITCH_OFF && (state == STATE_SWITCH_ON || state == STATE_SWITCH_ON2))) {
            sAnim = 1;
        }
        bAnim = 1;
        refreshState(wishState);
    }

    /**
     * 刷新按钮状态
     *
     * @param newState
     */
    private void refreshState(int newState) {
        if (!isChecked && newState == STATE_SWITCH_ON) {
            isChecked = true;
        } else if (isChecked && newState == STATE_SWITCH_OFF) {
            isChecked = false;
        }
        lastState = state;
        state = newState;
        if(onCheckChangedListener != null) onCheckChangedListener.onCheckChanged(isChecked);
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int resultWidth;
        if (widthMode == MeasureSpec.EXACTLY) {
            resultWidth = widthSize;
        } else {
            resultWidth = (int) (50 * getResources().getDisplayMetrics().density + 0.5f)
                    + getPaddingLeft() + getPaddingRight();
            if (widthMode == MeasureSpec.AT_MOST) {
                resultWidth = Math.min(resultWidth, widthSize);
            }
        }

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int resultHeight;
        if (heightMode == MeasureSpec.EXACTLY) {
            resultHeight = heightSize;
        } else {
            resultHeight = (int) (resultWidth * ratioAspect) + getPaddingTop() + getPaddingBottom();
            if (heightMode == MeasureSpec.AT_MOST) {
                resultHeight = Math.min(resultHeight, heightSize);
            }
        }
        setMeasuredDimension(resultWidth, resultHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        isCanVisibleDrawing = w > getPaddingLeft() + getPaddingRight() && h > getPaddingTop() + getPaddingBottom();

        if (isCanVisibleDrawing) {
            int actuallyDrawingAreaWidth = w - getPaddingLeft() - getPaddingRight();
            int actuallyDrawingAreaHeight = h - getPaddingTop() - getPaddingBottom();

            int actuallyDrawingAreaLeft;
            int actuallyDrawingAreaRight;
            int actuallyDrawingAreaTop;
            int actuallyDrawingAreaBottom;
            if (actuallyDrawingAreaWidth * ratioAspect < actuallyDrawingAreaHeight) {
                actuallyDrawingAreaLeft = getPaddingLeft();
                actuallyDrawingAreaRight = w - getPaddingRight();
                int heightExtraSize = (int) (actuallyDrawingAreaHeight - actuallyDrawingAreaWidth * ratioAspect);
                actuallyDrawingAreaTop = getPaddingTop() + heightExtraSize / 2;
                actuallyDrawingAreaBottom = getHeight() - getPaddingBottom() - heightExtraSize / 2;
            } else {
                int widthExtraSize = (int) (actuallyDrawingAreaWidth - actuallyDrawingAreaHeight / ratioAspect);
                actuallyDrawingAreaLeft = getPaddingLeft() + widthExtraSize / 2;
                actuallyDrawingAreaRight = getWidth() - getPaddingRight() - widthExtraSize / 2;
                actuallyDrawingAreaTop = getPaddingTop();
                actuallyDrawingAreaBottom = getHeight() - getPaddingBottom();
            }

            shadowReservedHeight = (int) ((actuallyDrawingAreaBottom - actuallyDrawingAreaTop) * 0.07f);
            float sLeft = actuallyDrawingAreaLeft;
            float sTop = actuallyDrawingAreaTop + shadowReservedHeight;
            sRight = actuallyDrawingAreaRight;
            float sBottom = actuallyDrawingAreaBottom - shadowReservedHeight;

            float sHeight = sBottom - sTop;
            sCenterX = (sRight + sLeft) / 2;
            sCenterY = (sBottom + sTop) / 2;

            bLeft = sLeft;
            bWidth = sBottom - sTop;
            bRight = sLeft + bWidth;
            final float halfHeightOfS = bWidth / 2; // OfB
            bRadius = halfHeightOfS * 0.95f;
            bOffset = bRadius * 0.2f; // offset of switching
            bStrokeWidth = (halfHeightOfS - bRadius) * 2;
            bOnLeftX = sRight - bWidth;
            bOn2LeftX = bOnLeftX - bOffset;
            bOffLeftX = sLeft;
            bOff2LeftX = bOffLeftX + bOffset;
            sScale = 1 - bStrokeWidth / sHeight;

            sPath.reset();
            RectF sRectF = new RectF();
            sRectF.top = sTop;
            sRectF.bottom = sBottom;
            sRectF.left = sLeft;
            sRectF.right = sLeft + sHeight;
            sPath.arcTo(sRectF, 90, 180);
            sRectF.left = sRight - sHeight;
            sRectF.right = sRight;
            sPath.arcTo(sRectF, 270, 180);
            sPath.close();

            bRectF.left = bLeft;
            bRectF.right = bRight;
            bRectF.top = sTop + bStrokeWidth / 2;  // bTop = sTop
            bRectF.bottom = sBottom - bStrokeWidth / 2; // bBottom = sBottom
            float bCenterX = (bRight + bLeft) / 2;
            float bCenterY = (sBottom + sTop) / 2;

            int red = colorShadow >> 16 & 0xFF;
            int green = colorShadow >> 8 & 0xFF;
            int blue = colorShadow & 0xFF;
            shadowGradient = new RadialGradient(bCenterX, bCenterY, bRadius, Color.argb(200, red, green, blue),
                    Color.argb(25, red, green, blue), Shader.TileMode.CLAMP);
        }
    }

    private void calcBPath(float percent) {
        bPath.reset();
        bRectF.left = bLeft + bStrokeWidth / 2;
        bRectF.right = bRight - bStrokeWidth / 2;
        bPath.arcTo(bRectF, 90, 180);
        bRectF.left = bLeft + percent * bOffset + bStrokeWidth / 2;
        bRectF.right = bRight + percent * bOffset - bStrokeWidth / 2;
        bPath.arcTo(bRectF, 270, 180);
        bPath.close();
    }

    private float calcBTranslate(float percent) {
        float result = 0;
        switch (state - lastState) {
            case 1:
                if (state == STATE_SWITCH_OFF2) {
                    result = bOffLeftX; // off -> off2
                } else if (state == STATE_SWITCH_ON) {
                    result = bOnLeftX - (bOnLeftX - bOn2LeftX) * percent; // on2 -> on
                }
                break;
            case 2:
                if (state == STATE_SWITCH_ON) {
                    result = bOnLeftX - (bOnLeftX - bOffLeftX) * percent; // off2 -> on
                } else if (state == STATE_SWITCH_ON2) {
                    result = bOn2LeftX - (bOn2LeftX - bOffLeftX) * percent;  // off -> on2
                }
                break;
            case 3:
                result = bOnLeftX - (bOnLeftX - bOffLeftX) * percent; // off -> on
                break;
            case -1:
                if (state == STATE_SWITCH_ON2) {
                    result = bOn2LeftX + (bOnLeftX - bOn2LeftX) * percent; // on -> on2
                } else if (state == STATE_SWITCH_OFF) {
                    result = bOffLeftX;  // off2 -> off
                }
                break;
            case -2:
                if (state == STATE_SWITCH_OFF) {
                    result = bOffLeftX + (bOn2LeftX - bOffLeftX) * percent;  // on2 -> off
                } else if (state == STATE_SWITCH_OFF2) {
                    result = bOff2LeftX + (bOnLeftX - bOff2LeftX) * percent;  // on -> off2
                }
                break;
            case -3:
                result = bOffLeftX + (bOnLeftX - bOffLeftX) * percent;  // on -> off
                break;
            default: // init
            case 0:
                if (state == STATE_SWITCH_OFF) {
                    result = bOffLeftX; //  off -> off
                } else if (state == STATE_SWITCH_ON) {
                    result = bOnLeftX; // on -> on
                }
                break;
        }
        return result - bOffLeftX;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isCanVisibleDrawing) return;

        paint.setAntiAlias(true);
        final boolean isOn = (state == STATE_SWITCH_ON || state == STATE_SWITCH_ON2);
        // Draw background
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(isOn ? colorPrimary : colorOff);
        canvas.drawPath(sPath, paint);

        sAnim = sAnim - animationSpeed > 0 ? sAnim - animationSpeed : 0;
        bAnim = bAnim - animationSpeed > 0 ? bAnim - animationSpeed : 0;

        final float dsAnim = interpolator.getInterpolation(sAnim);
        final float dbAnim = interpolator.getInterpolation(bAnim);
        // Draw background animation
        final float scale = sScale * (isOn ? dsAnim : 1 - dsAnim);
        final float scaleOffset = (sRight - sCenterX - bRadius) * (isOn ? 1 - dsAnim : dsAnim);
        canvas.save();
        canvas.scale(scale, scale, sCenterX + scaleOffset, sCenterY);
        paint.setColor(colorBackground);
        canvas.drawPath(sPath, paint);
        canvas.restore();
        // To prepare center bar path
        canvas.save();
        canvas.translate(calcBTranslate(dbAnim), shadowReservedHeight);
        final boolean isState2 = (state == STATE_SWITCH_ON2 || state == STATE_SWITCH_OFF2);
        calcBPath(isState2 ? 1 - dbAnim : dbAnim);
        // Use center bar path to draw shadow
        if (hasShadow) {
            paint.setStyle(Paint.Style.FILL);
            paint.setShader(shadowGradient);
            canvas.drawPath(bPath, paint);
            paint.setShader(null);
        }
        canvas.translate(0, -shadowReservedHeight);
        // draw bar
        canvas.scale(0.98f, 0.98f, bWidth / 2, bWidth / 2);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(colorBar);
        canvas.drawPath(bPath, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(bStrokeWidth * 0.5f);
        paint.setColor(isOn ? colorPrimaryDark : colorOffDark);
        canvas.drawPath(bPath, paint);
        canvas.restore();

        paint.reset();
        if (sAnim > 0 || bAnim > 0) invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if ((state == STATE_SWITCH_ON || state == STATE_SWITCH_OFF) && (sAnim * bAnim == 0)) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    return true;
                case MotionEvent.ACTION_UP:
                    lastState = state;

                    bAnim = 1;
                    if (state == STATE_SWITCH_OFF) {
                        refreshState(STATE_SWITCH_OFF2);
                        listener.toggleToOn(this);
                    } else if (state == STATE_SWITCH_ON) {
                        refreshState(STATE_SWITCH_ON2);
                        listener.toggleToOff(this);
                    }

                    if (mOnClickListener != null) {
                        mOnClickListener.onClick(this);
                    }
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        mOnClickListener = l;
    }

    private OnCheckChangedListener onCheckChangedListener;

    public void setOnCheckChangedListener(OnCheckChangedListener listener){
        this.onCheckChangedListener = listener;
    }

    public interface OnCheckChangedListener {
        void onCheckChanged(boolean isCheck);
    }

    public interface OnStateChangedListener {
        void toggleToOn(STSwitchView view);

        void toggleToOff(STSwitchView view);
    }

    private OnStateChangedListener listener = new OnStateChangedListener() {
        @Override
        public void toggleToOn(STSwitchView view) {
            toggleSwitch(true);
        }

        @Override
        public void toggleToOff(STSwitchView view) {
            toggleSwitch(false);
        }
    };

    public void setOnStateChangedListener(OnStateChangedListener listener) {
        if (listener == null) Log.d(TAG, "setOnStateChangedListener: empty listener");
        this.listener = listener;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.isOpened = isChecked;
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.isChecked = ss.isOpened;
        this.state = this.isChecked ? STATE_SWITCH_ON : STATE_SWITCH_OFF;
        invalidate();
    }

    private static final class SavedState extends BaseSavedState {
        private boolean isOpened;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            isOpened = 1 == in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(isOpened ? 1 : 0);
        }

        // fixed by Night99 https://github.com/g19980115
        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}