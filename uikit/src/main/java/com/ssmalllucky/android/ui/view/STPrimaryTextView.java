package com.ssmalllucky.android.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.utils.DisplayUtils;

/**
 * 级别为一级的TextView，按照海成安卓客户端UI规范，对字体大小、字体颜色进行了封装。
 * <p>
 * 用于一级标题、一级按钮、重要的文本显示。
 *
 * @author shuaijialin
 */
public class STPrimaryTextView extends AppCompatTextView {

    /**
     * 字体大小
     */
    private float textSize;

    /**
     * 字体颜色
     */
    private int textColor;

    public STPrimaryTextView(Context context) {
        super(context);
    }

    public STPrimaryTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public STPrimaryTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.STTextView);

        //根据不同设备密度，获取最准确的字体大小
        float density = DisplayUtils.getDensity(context);
        textSize = (int) (a.getDimensionPixelSize(R.styleable.STTextView_textSize, (context.getResources().getDimensionPixelSize(R.dimen.text_size_primary))) / density);
        textColor = a.getColor(R.styleable.STTextView_textColor, ContextCompat.getColor(context, R.color.text_color_primary));

        setTextSize(textSize);
        setTextColor(textColor);
    }

    public void setAppTextColor(@ColorRes int colorResId) {
        setTextColor(ContextCompat.getColor(getContext(), colorResId));
    }
}
