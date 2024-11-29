package com.ssmalllucky.android.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.utils.DisplayUtils;

/**
 * @ClassName STSwitchCompat
 * @Author shuaijialin
 * @Date 2023/8/23
 * @Description 开关按钮。继承自Android系统的SwitchCompat，按照海成安卓客户端UI规范，对字体大小、视图在不同状态时的颜色进行了封装。
 */
public class STRadioButton extends AppCompatRadioButton {

    int[] trackColors = new int[3];
    int[][] trackStates = new int[3][];

    private float textSize;

    private int activeColor;
    private int enabledColor;
    private int othersColor;


    public STRadioButton(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public STRadioButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public STRadioButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @SuppressLint({"RestrictedApi"})
    private void init(Context context, AttributeSet attrs) {
        float density = DisplayUtils.getDensity(context);
        if (attrs == null) {
            this.textSize = context.getResources().getDimensionPixelSize(R.dimen.text_size_secondary_content) / density;
            this.activeColor = ContextCompat.getColor(context, R.color.activeColor);
            this.enabledColor = ContextCompat.getColor(context, R.color.text_color_secondary_content);
            this.othersColor = ContextCompat.getColor(context, R.color.text_color_tertiary);
        } else {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.STRadioButton);
            this.textSize = (float) ((int) ((float) a.getDimensionPixelSize(R.styleable.STRadioButton_STRadioButton_textSize, context.getResources().getDimensionPixelSize(R.dimen.text_size_secondary_content)) / density));
            this.activeColor = a.getColor(R.styleable.STRadioButton_STRadioButton_activeColor, ContextCompat.getColor(context, R.color.activeColor));
            this.enabledColor = a.getColor(R.styleable.STRadioButton_STRadioButton_enabledColor, ContextCompat.getColor(context, R.color.text_color_secondary_content));
            this.othersColor = a.getColor(R.styleable.STRadioButton_STRadioButton_othersColor, ContextCompat.getColor(context, R.color.text_color_tertiary));
        }
        if (!this.isEnabled()) {
            this.trackStates[0] = new int[0];
            this.trackColors[0] = this.othersColor;
            this.setTextColor(this.othersColor);
        } else {
            trackStates[0] = new int[]{android.R.attr.state_checked};
            trackStates[1] = new int[]{android.R.attr.state_enabled};
            trackStates[2] = new int[]{};
            this.trackColors[0] = this.activeColor;
            this.trackColors[1] = this.enabledColor;
            this.trackColors[2] = this.othersColor;
            this.setTextColor(new ColorStateList(this.trackStates, this.trackColors));
            this.setButtonTintList(new ColorStateList(this.trackStates, this.trackColors));
            this.setOnCheckedChangeListener((buttonView, isChecked) -> {
                this.setTextColor(isChecked ? this.activeColor : this.enabledColor);
            });
        }
    }
}
