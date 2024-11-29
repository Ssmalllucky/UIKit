package com.ssmalllucky.android.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.utils.DisplayUtils;

/**
 * @ClassName STAssistView
 * @Author shuaijialin
 * @Date 2024/5/21
 * @Description 执行操作的按钮。
 * <p>
 * 通常放在界面的下方，配合 {@link STActionView} 使用。
 */
public class STAssistView extends LinearLayout {

    private Context mContext;

    private boolean showShader = false;
    private String text;
    private STPrimaryTextView textView;
    private Drawable background;
    private int textColor;

    public STAssistView(Context context) {
        super(context);
        init(context, null);
    }

    public STAssistView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public STAssistView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.STAssistView);
            showShader = a.getBoolean(R.styleable.STAssistView_STAssistView_showShader, true);
            text = a.getString(R.styleable.STAssistView_STAssistView_text);
            background = a.getDrawable(R.styleable.STAssistView_STAssistView_background);
            textColor = a.getColor(R.styleable.STAssistView_STAssistView_textColor, ContextCompat.getColor(context, R.color.text_color_primary));
        }

        if (background == null) {
            setBackground(ContextCompat.getDrawable(context, R.drawable.selector_assist));
        } else {
            setBackground(background);
        }

        if (Build.VERSION.SDK_INT >= 28 && showShader) {
            setTranslationZ(DisplayUtils.dip2px(context, 10f));
            setElevation(DisplayUtils.dip2px(context, 10f));
            setOutlineSpotShadowColor(ContextCompat.getColor(context, R.color.CEAEAEA));
            setOutlineAmbientShadowColor(ContextCompat.getColor(context, R.color.CEAEAEA));
        }

        textView = new STPrimaryTextView(context);
        textView.setText(text);
        textView.setTextColor(textColor);
        addView(textView);
    }

    public void setText(String text) {
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
