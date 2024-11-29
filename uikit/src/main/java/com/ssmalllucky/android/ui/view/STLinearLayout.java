package com.ssmalllucky.android.ui.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.ssmalllucky.android.ui.R;

/**
 * @ClassName STLinearLayout
 * @Author shuaijialin
 * @Date 2024/3/21
 * @Description
 */
public class STLinearLayout extends LinearLayout {
    public STLinearLayout(Context context) {
        super(context);
        init(context);
    }

    public STLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public STLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public STLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context) {
        int spacingM = context.getResources().getDimensionPixelSize(R.dimen.spacing_m);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setSize(spacingM, 1);
        setDividerDrawable(drawable);
        setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
    }
}
