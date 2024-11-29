package com.ssmalllucky.android.ui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.view.STPrimaryTextView;

/**
 * 带图标、开关选项的设置文本项组件
 *
 * @author shuaijialin
 */
public class STSwitchItem extends STItem {

    private SwitchCompat switchCompat;

    public static final int OFF = 1;
    public static final int ON = 2;


    // 创建轨道颜色
    private int[] trackColors;

    private int[] normalColors;

    private int[][] trackStates;

    private int status = OFF;

    private boolean isEnable = true;

    private CharSequence[] entries;

    private STPrimaryTextView entryTextView;

    public STSwitchItem(@NonNull Context context) {
        super(context);
    }

    public STSwitchItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public STSwitchItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(@NonNull Context context, AttributeSet attrs) {
        super.init(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.STSwitchItem);
        status = a.getInt(R.styleable.STSwitchItem_STSwitchItem_status, OFF);
        isEnable = a.getBoolean(R.styleable.STSwitchItem_isenable, true);
        entries = a.getTextArray(R.styleable.STSwitchItem_STSwitchItem_entries);

        setEnabled(isEnable);

        if (entries != null && entries.length == 2) {
            entryTextView = new STPrimaryTextView(context);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMarginEnd(spacingS);
            contentContainer.addView(entryTextView);
        }

        switchCompat = new SwitchCompat(context);
        switchCompat.setEnabled(isEnable);

        //将Switcher的状态区分为未激活和激活，分开控制样式
        if (!isEnable) {
            trackStates = new int[1][];
            trackStates[0] = new int[]{};

            trackColors = new int[1];
            trackColors[0] = ContextCompat.getColor(context, R.color.disabledColor);

            normalColors = new int[1];
            normalColors[0] = ContextCompat.getColor(context, R.color.disabledColorNormal);

        } else {
            trackStates = new int[3][];
            trackStates[0] = new int[]{android.R.attr.state_checked};
            trackStates[1] = new int[]{android.R.attr.state_enabled};
            trackStates[2] = new int[]{};

            trackColors = new int[3];
            trackColors[0] = ContextCompat.getColor(context, R.color.trackActiveColor);
            trackColors[1] = ContextCompat.getColor(context, R.color.activeColorBackground);
            trackColors[2] = ContextCompat.getColor(context, R.color.disabledColor);

            normalColors = new int[3];
            normalColors[0] = ContextCompat.getColor(context, R.color.activeColor);
            normalColors[1] = ContextCompat.getColor(context, R.color.activeColor);
            normalColors[2] = ContextCompat.getColor(context, R.color.disabledColor);

            switchCompat = new SwitchCompat(context);
            LayoutParams switcherParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            switcherParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
            switchCompat.setLayoutParams(switcherParams);
        }
        switchCompat.setTrackTintList(new ColorStateList(trackStates, trackColors));
        switchCompat.setThumbTintList(new ColorStateList(trackStates, normalColors));

        switchCompat.setChecked(status == ON);
        switchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (mListener != null) mListener.onCheckChanged(isChecked);
        });
        contentContainer.addView(switchCompat);

        setEntryTextView();

        setOnClickListener(v -> {
            if (switchCompat != null) {
                switchCompat.setChecked(!switchCompat.isChecked());
                setEntryTextView();
            }
        });
    }

    private void setEntryTextView(){
        if (entryTextView != null) {
            entryTextView.setText(switchCompat.isChecked() ? entries[0] : entries[1]);
        }
    }

    private CheckChangeListener mListener;

    public void setChecked(boolean isChecked) {
        if (switchCompat != null) switchCompat.setChecked(isChecked);
        setEntryTextView();
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
