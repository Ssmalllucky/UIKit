package com.ssmalllucky.android.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.utils.DisplayUtils;

/**
 * 级别为二级的TextView，按照海成安卓客户端UI规范，对字体大小、字体颜色进行了封装。
 * <p>
 * 用于二级标题、二级按钮、常规的文本显示。
 *
 * @author shuaijialin
 */
public class STSecondaryTextView extends AppCompatTextView {

    private float textSize;
    private int textColor;

    public STSecondaryTextView(Context context) {
        super(context);
    }

    public STSecondaryTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public STSecondaryTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
//        setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/MiSans-Regular.ttf"));
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.STTextView);
        float density = DisplayUtils.getDensity(context);
        textSize = (int) (a.getDimensionPixelSize(R.styleable.STTextView_textSize, (context.getResources().getDimensionPixelSize(R.dimen.text_size_secondary_content))) / density);
        textColor = a.getColor(R.styleable.STTextView_textColor, ContextCompat.getColor(context, R.color.text_color_primary));
        setTextSize(textSize);
        setTextColor(textColor);

        if(!isEnabled()){
            setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_tertiary));
        }
    }

    public void setAppTextColor(@ColorRes int colorResId) {
        setTextColor(ContextCompat.getColor(getContext(), colorResId));
    }
}
