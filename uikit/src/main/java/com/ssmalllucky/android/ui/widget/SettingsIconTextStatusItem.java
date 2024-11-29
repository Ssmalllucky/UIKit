package com.ssmalllucky.android.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.view.STSecondaryTextView;

/**
 * 带图标、状态描述的设置文本项组件
 *
 * @author shuaijialin
 */
public class SettingsIconTextStatusItem extends SettingsItem {

    private String statusText;
    private STSecondaryTextView statusTextView;
    private int maxLines = 1;

    private int statusTextColor;

    public SettingsIconTextStatusItem(@NonNull Context context) {
        super(context);
    }

    public SettingsIconTextStatusItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SettingsIconTextStatusItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(@NonNull Context context, AttributeSet attrs) {
        super.init(context, attrs);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingsIconTextStatusItem);
            statusText = a.getString(R.styleable.SettingsIconTextStatusItem_SettingsIconTextStatusItem_status_text);
            maxLines = a.getInteger(R.styleable.SettingsIconTextStatusItem_SettingsIconTextStatusItem_maxLines, 1);
            statusTextColor = a.getColor(R.styleable.SettingsIconTextStatusItem_SettingsIconTextStatusItem_status_text_color, 0);
        }

        statusTextView = new STSecondaryTextView(context);
        statusTextView.setText(statusText);

        statusTextView.setMaxLines(maxLines);
        statusTextView.setSingleLine(maxLines <= 1);
        statusTextView.setEllipsize(TextUtils.TruncateAt.END);
        statusTextView.setTextColor(statusTextColor == 0 ? ContextCompat.getColor(context, R.color.text_color_secondary_content) : statusTextColor);
        LayoutParams statusParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        statusTextView.setGravity(Gravity.END);
        statusTextView.setEllipsize(TextUtils.TruncateAt.END);
        statusTextView.setLayoutParams(statusParams);
        if (contentContainer != null) {
            contentContainer.addView(statusTextView);
        }
    }

    /**
     * 设置项状态
     *
     * @param value 状态文本
     */
    public void setStatusText(String value) {
        if (statusTextView != null) statusTextView.setText(value);
    }

    public TextView getStatusTextView() {
        return statusTextView;
    }
}
