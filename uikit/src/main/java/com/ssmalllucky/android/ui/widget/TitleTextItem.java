package com.ssmalllucky.android.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.utils.DisplayUtils;
import com.ssmalllucky.android.ui.view.STSecondaryTextView;

/**
 * 设置子项组件
 *
 * @author shuaijialin
 */
public class TitleTextItem extends LinearLayout {

    private Context context;
    private String title;

    private String text;

    private boolean isTop;
    private boolean isBottom;

    private STSecondaryTextView titleView;

    private STSecondaryTextView textView;

    public TitleTextItem(@NonNull Context context) {
        super(context);
    }

    public TitleTextItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TitleTextItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void setStyle() {
        if (isTop) {
            setBackground(ContextCompat.getDrawable(context, R.drawable.shape_title_input_top));
        } else if (isBottom) {
            setBackground(ContextCompat.getDrawable(context, R.drawable.shape_title_input_bottom));
        } else {
            setBackground(ContextCompat.getDrawable(context, R.drawable.shape_title_input));
        }
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        removeAllViews();

        setOrientation(HORIZONTAL);

        int padding = DisplayUtils.dip2px(context, 15f);
        setPadding(padding, 0, padding, 0);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleTextItem);
        title = a.getString(R.styleable.TitleTextItem_TitleTextItem_title);
        text = a.getString(R.styleable.TitleTextItem_TitleTextItem_text);
        isTop = a.getBoolean(R.styleable.TitleTextItem_TitleTextItem_isTop, false);
        isBottom = a.getBoolean(R.styleable.TitleTextItem_TitleTextItem_isBottom, false);

        titleView = new STSecondaryTextView(context);
        titleView.setText(title);
        titleView.setTextColor(ContextCompat.getColor(context, R.color.text_color_secondary_title));
        addView(titleView);

        textView = new STSecondaryTextView(context);
        textView.setText(text);
        textView.setTextColor(ContextCompat.getColor(context, R.color.text_color_secondary_content));
        addView(textView);

        setStyle();
        layoutItem();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setTitle(String title) {
        this.title = title;
        if (titleView != null) titleView.setText(title);
    }

    public void setText(String text) {
        this.text = text;
        if (textView != null) textView.setText(text);
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    private void layoutItem() {
        LayoutParams textParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textParams.gravity = Gravity.CENTER_VERTICAL;
        textParams.weight = 1;
        titleView.setGravity(Gravity.CENTER_VERTICAL);
        titleView.setLayoutParams(textParams);

        textParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textParams.gravity = Gravity.CENTER_VERTICAL;
        textParams.weight = 3;
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setLayoutParams(textParams);
    }
}
