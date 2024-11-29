package com.ssmalllucky.android.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.utils.DisplayUtils;

/**
 * 级别为三级的TextView，按照海成安卓客户端UI规范，对字体大小、字体颜色进行了封装。
 * <p>
 * 用于提示类文本的显示
 *
 * @author shuaijialin
 */
public class STTertiaryTextView extends AppCompatTextView {

    private float textSize;
    private int textColor;

    public STTertiaryTextView(Context context) {
        super(context);
    }

    public STTertiaryTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public STTertiaryTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.STTertiaryTextView);
        float density = DisplayUtils.getDensity(context);
        textSize = (int) (a.getDimensionPixelSize(R.styleable.STTertiaryTextView_STTertiaryTextView_textSize, (context.getResources().getDimensionPixelSize(R.dimen.text_size_tertiary))) / density);
        textColor = a.getColor(R.styleable.STTertiaryTextView_STTertiaryTextView_textColor, ContextCompat.getColor(context, R.color.text_color_tertiary));

        setTextSize(textSize);
        setTextColor(textColor);
    }

    public void setAppTextColor(@ColorRes int colorResId) {
        setTextColor(ContextCompat.getColor(getContext(), colorResId));
    }
}
