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
 * 设置子项组件
 *
 * @author shuaijialin
 */
public class TitleContentTextItem extends LinearLayout {

    private Context context;
    private String text;
    private boolean isTop;
    private boolean isBottom;
    private boolean hasNext;
    private STSecondaryTextView titleTextView;
    private STSecondaryTextView contentTextView;
    private ImageView imageView;

    public TitleContentTextItem(@NonNull Context context) {
        super(context);
    }

    public TitleContentTextItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TitleContentTextItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleContentTextItem);
        text = a.getString(R.styleable.TitleContentTextItem_TitleContentTextItem_text);
        isTop = a.getBoolean(R.styleable.TitleContentTextItem_TitleContentTextItem_isTop, false);
        isBottom = a.getBoolean(R.styleable.TitleContentTextItem_TitleContentTextItem_isBottom, false);
        hasNext = a.getBoolean(R.styleable.TitleContentTextItem_TitleContentTextItem_hasNext, true);

        titleTextView = new STSecondaryTextView(context);
        titleTextView.setText(text);
        titleTextView.setTextColor(ContextCompat.getColor(context, R.color.text_color_secondary_title));
        addView(titleTextView);

        contentTextView = new STSecondaryTextView(context);
        contentTextView.setTextColor(ContextCompat.getColor(context, R.color.text_color_secondary_content));
        addView(contentTextView);

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
    }

    public void setContentText(String contentText) {
        if (contentTextView == null) {
            return;
        }
        contentTextView.setText(contentText);
    }

    private void layoutItem() {
        LayoutParams titleTextParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        titleTextParams.gravity = Gravity.CENTER_VERTICAL;
        titleTextParams.weight = 1;
        titleTextView.setLayoutParams(titleTextParams);

        LayoutParams contentTextParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        contentTextParams.gravity = Gravity.CENTER_VERTICAL;
        contentTextParams.weight = 1;
        contentTextView.setGravity(Gravity.END);
        contentTextView.setLayoutParams(contentTextParams);

        if (imageView != null) {
            int imageViewSize = DisplayUtils.dip2px(context, 20f);
            LayoutParams imageViewParams = new LayoutParams(imageViewSize, imageViewSize);
            imageViewParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
            imageView.setLayoutParams(imageViewParams);
        }
    }
}
