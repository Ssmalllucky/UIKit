package com.ssmalllucky.android.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.utils.DisplayUtils;

/**
 * @ClassName STCheckBox
 * @Author shuaijialin
 * @Date 2023/9/4
 * @Description 复选框。继承自Android系统的CheckBox，按照海成安卓客户端UI规范，对字体大小、视图在不同状态时的颜色进行了封装。
 */
public class STCheckBox extends AppCompatCheckBox {

    int[] trackColors = new int[3];
    int[][] trackStates = new int[3][];

    private float textSize;

    private int activeColor;
    private int enabledColor;
    private int othersColor;

    public STCheckBox(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public STCheckBox(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public STCheckBox(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @SuppressLint("RestrictedApi")
    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.STCheckBox);
        float density = DisplayUtils.getDensity(context);
        textSize = (int) (a.getDimensionPixelSize(R.styleable.STCheckBox_STCheckBox_textSize, (context.getResources().getDimensionPixelSize(R.dimen.text_size_secondary_content))) / density);
        activeColor = a.getColor(R.styleable.STCheckBox_STCheckBox_activeColor, ContextCompat.getColor(context, R.color.activeColor));
        enabledColor = a.getColor(R.styleable.STCheckBox_STCheckBox_enabledColor, ContextCompat.getColor(context, R.color.text_color_secondary_content));
        othersColor = a.getColor(R.styleable.STCheckBox_STCheckBox_othersColor, ContextCompat.getColor(context, R.color.text_color_tertiary));

        if (!isEnabled()) {
            trackStates[0] = new int[]{};
            trackColors[0] = othersColor;
            setTextColor(othersColor);
            return;
        }

        trackStates[0] = new int[]{android.R.attr.state_checked};
        trackStates[1] = new int[]{android.R.attr.state_enabled};
        trackStates[2] = new int[]{};

        trackColors[0] = activeColor;
        trackColors[1] = enabledColor;
        trackColors[2] = othersColor;

        setTextColor(new ColorStateList(trackStates, trackColors));
        setButtonTintList(new ColorStateList(trackStates, trackColors));
        setOnCheckedChangeListener((buttonView, isChecked) -> setTextColor(isChecked ? activeColor : enabledColor));
    }

    private void setTextSize(int textSize) {
        this.textSize = textSize;
    }
}
