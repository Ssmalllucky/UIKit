package com.ssmalllucky.android.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.utils.DisplayUtils;

/**
 * @ClassName STActionView
 * @Author shuaijialin
 * @Date 2024/3/27
 * @Description 执行操作的按钮。
 * <p>
 * 通常放在界面的下方，用于进行下一步/提交操作等。
 */
public class STActionView extends LinearLayout {

    private Context mContext;

    private boolean showShader = true;

    private String text;

    private STPrimaryTextView textView;

    public STActionView(Context context) {
        super(context);
        init(context, null);
    }

    public STActionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public STActionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;

        setBackground(ContextCompat.getDrawable(context, R.drawable.selector_dialog_ok));

        removeAllViews();
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.STActionView);
            showShader = a.getBoolean(R.styleable.STActionView_STActionView_showShader, true);
            text = a.getString(R.styleable.STActionView_STActionView_text);
        }

        if (Build.VERSION.SDK_INT >= 28 && showShader) {
            setTranslationZ(DisplayUtils.dip2px(context, 10f));
            setElevation(DisplayUtils.dip2px(context, 10f));
            setOutlineSpotShadowColor(ContextCompat.getColor(context, R.color.activeColorPressed));
            setOutlineAmbientShadowColor(ContextCompat.getColor(context, R.color.activeColorPressed));
        }

        textView = new STPrimaryTextView(context);
        textView.setText(text);
        textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        addView(textView);
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
