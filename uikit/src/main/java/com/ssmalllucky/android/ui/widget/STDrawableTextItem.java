package com.ssmalllucky.android.ui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.utils.DisplayUtils;
import com.ssmalllucky.android.ui.view.STPrimaryTextView;

/**
 * @ClassName STDrawableTextItem
 * @Author shuaijialin
 * @Date 2024/10/22
 * @Description 带图标的TextView组件。
 */
public class STDrawableTextItem extends LinearLayout {

    private Context context;
    private int color;
    private int iconResId;
    private int iconSize;
    private float textSize;
    private String text;

    private ImageView imageView;
    private TextView textView;

    public STDrawableTextItem(Context context) {
        super(context);
    }

    public STDrawableTextItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public STDrawableTextItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        this.context = context;
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.STDrawableTextItem);
            iconSize = a.getDimensionPixelSize(R.styleable.STDrawableTextItem_STDrawableTextItem_iconSize, DisplayUtils.dip2px(context, 22f));
            text = a.getString(R.styleable.STDrawableTextItem_STDrawableTextItem_text);
            textSize = (a.getDimensionPixelSize(R.styleable.STDrawableTextItem_STDrawableTextItem_textSize, context.getResources().getDimensionPixelSize(R.dimen.text_size_primary)));
            iconResId = a.getResourceId(R.styleable.STDrawableTextItem_STDrawableTextItem_iconRes, 0);
            color = a.getColor(R.styleable.STDrawableTextItem_STDrawableTextItem_color, 0);
        }

        if (iconResId != 0) {
            imageView = new ImageView(context);
            LayoutParams params = new LayoutParams(iconSize, iconSize);
            imageView.setLayoutParams(params);
            imageView.setImageResource(iconResId);
            addView(imageView);
        }

        textView = new STPrimaryTextView(context);
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (iconResId != 0) {
            params.setMarginStart(DisplayUtils.dip2px(context, 6f));
        }
        textView.setLayoutParams(params);
        addView(textView);

        if (color != 0) {
            if (imageView != null) {
                ColorStateList colorStateList = getColorStateList();
                imageView.setBackgroundTintList(colorStateList);
            }
            textView.setTextColor(color);
        }
    }

    private @NonNull ColorStateList getColorStateList() {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_pressed},  // 按下状态
                new int[]{android.R.attr.state_focused},  // 聚焦状态
                new int[]{}  // 默认状态
        };

        int[] colors = new int[]{
                color,     // 按下状态颜色
                color,   // 聚焦状态颜色
                color    // 默认状态颜色
        };
        ColorStateList colorStateList = new ColorStateList(states, colors);
        return colorStateList;
    }

    public void setIcon(int iconResId) {
        this.iconResId = iconResId;
        if (iconResId != 0) {
            imageView = new ImageView(context);
            LayoutParams params = new LayoutParams(iconSize, iconSize);
            imageView.setLayoutParams(params);
            imageView.setImageResource(iconResId);
            addView(imageView, 0);
        }
    }

    public void setText(String text) {
        this.text = text;
        textView = new STPrimaryTextView(context);
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (iconResId != 0) {
            params.setMarginStart(DisplayUtils.dip2px(context, 6f));
        }
        textView.setLayoutParams(params);
        addView(textView);
    }

    public void setColor(int color) {
        this.color = color;
        if (color != 0) {
            if (imageView != null) {
                ColorStateList colorStateList = getColorStateList();
                imageView.setBackgroundTintList(colorStateList);
            }
            textView.setTextColor(color);
        }
    }
}
