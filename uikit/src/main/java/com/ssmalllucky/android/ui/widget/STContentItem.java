package com.ssmalllucky.android.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.utils.DisplayUtils;
import com.ssmalllucky.android.ui.view.STSecondaryTextView;

/**
 * @ClassName STContentItem
 * @Author shuaijialin
 * @Date 2024/5/24
 * @Description 带图标、状态描述的文本项组件。
 */
public class STContentItem extends STItem {

    protected STSecondaryTextView contentTextView;
    private int maxLines = 1;

    protected String contentValue;

    private int contentColor;

    private String hint;

    private Drawable contentBackground;

    public STContentItem(@NonNull Context context) {
        super(context);
    }

    public STContentItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public STContentItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(@NonNull Context context, AttributeSet attrs) {
        super.init(context, attrs);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.STContentItem);
            contentValue = a.getString(R.styleable.STContentItem_STContentItem_contentText);
            maxLines = a.getInteger(R.styleable.STContentItem_STContentItem_maxLines, 1);
            contentColor = a.getColor(R.styleable.STContentItem_STContentItem_contentColor, 0);
            contentBackground = a.getDrawable(R.styleable.STContentItem_STContentItem_contentBackground);
            hint = a.getString(R.styleable.STContentItem_STContentItem_hint);
        }

        contentTextView = new STSecondaryTextView(context);
        contentTextView.setText(contentValue);
        contentTextView.setHintTextColor(context.getColor(R.color.text_color_tertiary));
        contentTextView.setHint(hint);
        contentTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.text_size_secondary_title));

        contentTextView.setMaxLines(maxLines);
        contentTextView.setSingleLine(maxLines <= 1);
        contentTextView.setEllipsize(TextUtils.TruncateAt.END);
        contentTextView.setTextColor(contentColor == 0 ? ContextCompat.getColor(context, R.color.text_color_secondary_content) : contentColor);
        LayoutParams statusParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        contentTextView.setGravity(Gravity.END);
        contentTextView.setEllipsize(TextUtils.TruncateAt.END);
        contentTextView.setLayoutParams(statusParams);
        if (contentBackground != null) {
            int lrPadding = DisplayUtils.dip2px(context, 12f);
            int tbPadding = DisplayUtils.dip2px(context, 6f);
            contentTextView.setPadding(lrPadding, tbPadding, lrPadding, tbPadding);
            contentTextView.setBackground(contentBackground);
        }
        if (contentContainer != null) {
            contentContainer.addView(contentTextView);
        }
    }

    /**
     * 设置项状态
     *
     * @param value 状态文本
     */
    public void setContentValue(String value) {
        if (contentTextView != null) contentTextView.setText(value);
    }

    public String getContentValue() {
        return contentTextView.getText().toString().trim();
    }

    public TextView getContentTextView() {
        return contentTextView;
    }

    public void setStatus(String contentValue, int status) {
        int textColor = 0;
        int textBackground = 0;
        switch (status) {
            case SUCCESS:
                textColor = R.color.successColor;
                textBackground = R.drawable.shape_content_success;
                break;
            case WARN:
                textColor = R.color.warningColor;
                textBackground = R.drawable.shape_content_warn;
                break;
            case ERROR:
                textColor = R.color.errorColor;
                textBackground = R.drawable.shape_content_error;
                break;
        }
        if (contentTextView != null) {
            contentTextView.setTextColor(ContextCompat.getColor(mContext, textColor));
            contentTextView.setBackground(ContextCompat.getDrawable(mContext, textBackground));
        }
    }

    public static final int SUCCESS = 0x01;
    public static final int WARN = 0x02;
    public static final int ERROR = 0x03;
}
