package com.ssmalllucky.android.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.utils.DisplayUtils;
import com.ssmalllucky.android.ui.view.STSecondaryTextView;

/**
 * 显示设置文本项组件
 *
 * @author shuaijialin
 */
public class SettingsTextItem extends LinearLayout {

    private Context context;

    private String text;
    private boolean isTop;
    private boolean isBottom;
    private boolean hasNext;
    private STSecondaryTextView textView;
    private ImageView imageView;

    public SettingsTextItem(@NonNull Context context) {
        super(context);
    }

    public SettingsTextItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SettingsTextItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void setStyle() {
        if (isTop) {
            setBackground(ContextCompat.getDrawable(context, R.drawable.selector_layout_settings_item_top));
        } else if (isBottom) {
            setBackground(ContextCompat.getDrawable(context, R.drawable.selector_layout_settings_item_bottom));
        } else {
            setBackground(ContextCompat.getDrawable(context, R.drawable.selector_layout_settings));
        }
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        removeAllViews();

        setOrientation(HORIZONTAL);

        int padding = DisplayUtils.dip2px(context, 15f);
        setPadding(padding, 0, padding, 0);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingsTextItem);
        text = a.getString(R.styleable.SettingsTextItem_text);
        isTop = a.getBoolean(R.styleable.SettingsTextItem_isTop, false);
        isBottom = a.getBoolean(R.styleable.SettingsTextItem_isBottom, false);
        hasNext = a.getBoolean(R.styleable.SettingsTextItem_hasNext, true);

        textView = new STSecondaryTextView(context);
        textView.setText(text);
        textView.setTextColor(ContextCompat.getColor(context, R.color.text_color_secondary_title));
        addView(textView);

        if (hasNext) {
            imageView = new ImageView(context);
            imageView.setImageResource(R.drawable.ic_arrow_right);
            addView(imageView);
        }

        setStyle();
        layoutItem();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(LayoutParams.MATCH_PARENT, 500);
    }

    private void layoutItem() {
        LayoutParams textParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        textParams.gravity = Gravity.CENTER_VERTICAL;
        textParams.weight = 1;
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setLayoutParams(textParams);

        if (imageView != null) {
            int imageViewSize = DisplayUtils.dip2px(context, 20f);
            LayoutParams imageViewParams = new LayoutParams(imageViewSize, imageViewSize);
            imageViewParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
            imageView.setLayoutParams(imageViewParams);
        }
    }
}
