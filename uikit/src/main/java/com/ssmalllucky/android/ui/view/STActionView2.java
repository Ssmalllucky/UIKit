package com.ssmalllucky.android.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
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
 * @ClassName STActionView
 * @Author shuaijialin
 * @Date 2024/12/9
 * @Description 执行操作的按钮（推荐使用此自定义View，替换原有的 {@link STActionView}）。
 * <p>
 * 通常放在界面的下方，用于进行下一步/提交操作等。
 */
public class STActionView2 extends LinearLayout {

    public static final String TAG = "STActionView2";

    private Context mContext;

    private boolean showShader = true;

    private String text;
    private float textSize;
    private STPrimaryTextView textView;
    private boolean enabled;

    private long lastClickTime = 0L; // 记录上次点击时间
    private static final long CLICK_INTERVAL = 1000; // 防止重复点击的时间间隔，单位为毫秒

    public STActionView2(Context context) {
        super(context);
        init(context, null);
    }

    public STActionView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public STActionView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;

        setBackground(ContextCompat.getDrawable(context, R.drawable.selector_action_view2));

        removeAllViews();
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);

        if (attrs != null) {
            float density = DisplayUtils.getDensity(context);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.STActionView2);
            showShader = a.getBoolean(R.styleable.STActionView2_STActionView2_showShader, true);
            text = a.getString(R.styleable.STActionView2_STActionView2_text);
            textSize = a.getDimensionPixelSize(R.styleable.STActionView2_STActionView2_textSize, (int) (context.getResources().getDimensionPixelSize(R.dimen.text_size_primary) / density));
            enabled = a.getBoolean(R.styleable.STActionView2_STActionView2_enabled, true);
        }

        textView = new STPrimaryTextView(context);
        textView.setText(text);
        textView.setTextSize(textSize);
        textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        addView(textView);

        updateShader();

        if (!enabled) {
            setEnabled(false);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        updateShader();
        super.setEnabled(enabled);
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

    private void updateShader() {
        if (Build.VERSION.SDK_INT >= 28 && showShader) {
            setTranslationZ(enabled ? DisplayUtils.dip2px(mContext, 10f) : 0);
            setElevation(enabled ? DisplayUtils.dip2px(mContext, 10f) : 0);
            setOutlineSpotShadowColor(ContextCompat.getColor(mContext, enabled ? R.color.activeColorPressed : R.color.transparent));
            setOutlineAmbientShadowColor(ContextCompat.getColor(mContext, enabled ? R.color.activeColorPressed : R.color.transparent));
        }
    }

    public void setText(String text) {
        this.text = text;
        if (textView != null) textView.setText(text);
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
