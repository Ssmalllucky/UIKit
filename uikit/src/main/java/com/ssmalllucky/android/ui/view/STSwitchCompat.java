package com.ssmalllucky.android.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.utils.DisplayUtils;

/**
 * @ClassName STSwitchCompat
 * @Author shuaijialin
 * @Date 2023/8/23
 * @Description 开关按钮。继承自Android系统的SwitchCompat，按照海成安卓客户端UI规范，对字体大小、视图在不同状态时的颜色进行了封装。
 */
public class STSwitchCompat extends SwitchCompat {

    int[] trackColors = new int[2];
    int[][] trackStates = new int[2][];

    private float textSize;

    private int activeColor;
    private int trackActiveColor;
    private int enabledColor;
    private int othersColor;


    public STSwitchCompat(@NonNull Context context) {
        super(context);
    }

    public STSwitchCompat(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public STSwitchCompat(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @SuppressLint("RestrictedApi")
    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.STSwitchCompat);
        float density = DisplayUtils.getDensity(context);
        textSize = (int) (a.getDimensionPixelSize(R.styleable.STSwitchCompat_STSwitchCompat_textSize, (context.getResources().getDimensionPixelSize(R.dimen.text_size_secondary_content))) / density);
        activeColor = a.getColor(R.styleable.STSwitchCompat_STSwitchCompat_activeColor, ContextCompat.getColor(context, R.color.trackActiveColor));
        enabledColor = a.getColor(R.styleable.STSwitchCompat_STSwitchCompat_enabledColor, ContextCompat.getColor(context, R.color.text_color_secondary_content));
        trackActiveColor = a.getColor(R.styleable.STSwitchCompat_STSwitchCompat_trackActiveColor, ContextCompat.getColor(context, R.color.trackActiveColor));
        othersColor = a.getColor(R.styleable.STSwitchCompat_STSwitchCompat_othersColor, context.getColor(R.color.disabledColor));

        if (!isEnabled()) {
            trackStates[0] = new int[]{};
            trackColors[0] = othersColor;
            setTextColor(othersColor);
            return;
        }

        trackStates[0] = new int[]{android.R.attr.state_checked};
        trackStates[1] = new int[]{android.R.attr.state_enabled};

        trackColors[0] = activeColor;
        trackColors[1] = othersColor;

        setTrackTintList(new ColorStateList(trackStates, trackColors));
        setThumbTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.activeColor)));
        setOnCheckedChangeListener((buttonView, isChecked) -> setTextColor(isChecked ? activeColor : enabledColor));
    }
}
