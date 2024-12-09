package com.ssmalllucky.android.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.utils.DisplayUtils;

/**
 * @ClassName STAssistView
 * @Author shuaijialin
 * @Date 2024/12/9
 * @Description 执行辅助操作的按钮，一般配合操作按钮{@link STActionView2}使用，推荐
 * 使用此按钮，替换原有的 {@link STAssistView}。
 */
public class STAssistView2 extends LinearLayout {

    public static final String TAG = "STAssistView2";

    private Context mContext;

    private boolean showShader = false;
    private String text;
    private STPrimaryTextView textView;
    private Drawable background;
    private float textSize;
    private int textColor;
    private boolean enabled;

    private long lastClickTime = 0L; // 记录上次点击时间
    private static final long CLICK_INTERVAL = 1000; // 防止重复点击的时间间隔，单位为毫秒

    public STAssistView2(Context context) {
        super(context);
        init(context, null);
    }

    public STAssistView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public STAssistView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;

        setClipChildren(false);
        setClipToPadding(false);
        removeAllViews();
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);

        if (attrs != null) {
            float density = DisplayUtils.getDensity(context);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.STAssistView2);
            showShader = a.getBoolean(R.styleable.STAssistView2_STAssistView2_showShader, true);
            text = a.getString(R.styleable.STAssistView2_STAssistView2_text);
            background = a.getDrawable(R.styleable.STAssistView2_STAssistView2_background);
            textColor = a.getColor(R.styleable.STAssistView2_STAssistView2_textColor, ContextCompat.getColor(context, R.color.text_color_primary));
            textSize = a.getDimensionPixelSize(R.styleable.STAssistView2_STAssistView2_textSize, (int) (context.getResources().getDimensionPixelSize(R.dimen.text_size_primary) / density));
            enabled = a.getBoolean(R.styleable.STAssistView2_STAssistView2_enabled, true);
        }

        if (background == null) {
            setBackground(ContextCompat.getDrawable(context, R.drawable.selector_assist_view2));
        } else {
            setBackground(background);
        }

        textView = new STPrimaryTextView(context);
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setTextColor(textColor);
        addView(textView);

        updateShader();

        if (!enabled) {
            setEnabled(false);
        }
    }

    public void setText(String text) {
        if (textView != null) textView.setText(text);
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        updateShader();
        super.setEnabled(enabled);
    }

    private void updateShader() {
        if (Build.VERSION.SDK_INT >= 28 && showShader) {
            setTranslationZ(DisplayUtils.dip2px(mContext, 10f));
            setElevation(DisplayUtils.dip2px(mContext, 10f));
            setOutlineSpotShadowColor(ContextCompat.getColor(mContext, R.color.CEAEAEA));
            setOutlineAmbientShadowColor(ContextCompat.getColor(mContext, R.color.CEAEAEA));
        }
    }

    @Override
    public boolean performClick() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > CLICK_INTERVAL) {
            lastClickTime = currentTime;
            return super.performClick();
        } else {
            // 如果时间间隔小于 1 秒，则不执行点击事件
            Log.d(TAG, "重复点击,不执行点击事件");
            return false;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int designHeight = mContext.getResources().getDimensionPixelSize(R.dimen.action_height);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int height;

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(designHeight, heightSize);
        } else {
            height = designHeight;
        }

        setMeasuredDimension(widthMeasureSpec, height);
    }
}
