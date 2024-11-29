package com.ssmalllucky.android.ui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;

/**
 * 带图标、开关选项的设置文本项组件
 *
 * @author shuaijialin
 */
public class SettingsIconTextSwitchItem extends SettingsItem {

    private SwitchCompat switchCompat;

    // 创建轨道颜色
    private int[] trackColors;
    private int[][] trackStates;

    private boolean switchOnOrOff = false;

    public SettingsIconTextSwitchItem(@NonNull Context context) {
        super(context);
    }

    public SettingsIconTextSwitchItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SettingsIconTextSwitchItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingsIconTextSwitchItem);
        switchOnOrOff = a.getBoolean(R.styleable.SettingsIconTextSwitchItem_SettingsIconTextSwitchItem_switch_on, false);

        //Add switcher.
        trackStates = new int[2][];
        trackStates[0] = new int[]{android.R.attr.state_checked};
        trackStates[1] = new int[]{};

        trackColors = new int[2];
        trackColors[0] = ContextCompat.getColor(context, R.color.trackActiveColor);
        trackColors[1] = ContextCompat.getColor(context, R.color.disabledColor);
        switchCompat = new SwitchCompat(context);
        LayoutParams switcherParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        switcherParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
        switchCompat.setLayoutParams(switcherParams);
        switchCompat.setTrackTintList(new ColorStateList(trackStates, trackColors));
        switchCompat.setThumbTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.activeColor)));
        switchCompat.setChecked(switchOnOrOff);
        switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (mListener != null) mListener.onCheckChanged(isChecked);
        });
        addView(switchCompat);

        setOnClickListener(v -> {
            if (switchCompat != null) {
                switchCompat.setChecked(!switchCompat.isChecked());
            }
        });
    }

    private CheckChangeListener mListener;

    public void setChecked(boolean isChecked) {
        if (switchCompat != null) switchCompat.setChecked(isChecked);
    }

    public boolean isChecked() {
        return switchCompat != null && switchCompat.isChecked();
    }

    public void setOnCheckedChangeListener(CheckChangeListener listener) {
        this.mListener = listener;
    }

    public interface CheckChangeListener {
        void onCheckChanged(boolean isChecked);
    }
}
