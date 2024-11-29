package com.ssmalllucky.android.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
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
 * 带图标的设置文本项组件
 *
 * @author shuaijialin
 */
public class SettingsIconTitleItem extends LinearLayout {

    private Context context;
    private int layoutPosition;
    private boolean hasNext;
    private int iconResId;
    private String title;
    private ImageView iconImageView;
    private STSecondaryTextView textView;
    private ImageView arrowImageView;

    public static final int POSITION_TOP = 1;
    public static final int POSITION_MIDDLE = 2;
    public static final int POSITION_BOTTOM = 3;

    public SettingsIconTitleItem(@NonNull Context context) {
        super(context);
    }

    public SettingsIconTitleItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SettingsIconTitleItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void setStyle() {
        Drawable backgroundDrawable;
        switch (layoutPosition) {
            case POSITION_TOP:
                backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.selector_layout_settings_item_top);
                break;
            case POSITION_BOTTOM:
                backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.selector_layout_settings_item_bottom);
                break;
            case POSITION_MIDDLE:
            default:
                backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.selector_layout_settings);
                break;
        }
        setBackground(backgroundDrawable);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        removeAllViews();

        setOrientation(HORIZONTAL);

        int padding = DisplayUtils.dip2px(context, 15f);
        setPadding(padding, 0, padding, 0);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingsIconTitleItem);
        title = a.getString(R.styleable.SettingsIconTitleItem_SettingsIconTitleItem_title);
        layoutPosition = a.getInt(R.styleable.SettingsIconTitleItem_SettingsIconTitleItem_layoutPosition, POSITION_MIDDLE);
        hasNext = a.getBoolean(R.styleable.SettingsIconTitleItem_SettingsIconTitleItem_hasNext, true);
        iconResId = a.getResourceId(R.styleable.SettingsIconTitleItem_SettingsIconTitleItem_icon, R.drawable.ic_settings_icon_default);

        //1. Add icon.
        iconImageView = new ImageView(context);
        if (iconResId == 0) {
            iconResId = R.drawable.ic_settings_icon_default;
        }
        iconImageView.setImageResource(iconResId);
        addView(iconImageView);

        //2. Add item title.
        textView = new STSecondaryTextView(context);
        textView.setText(title);
        textView.setTextColor(ContextCompat.getColor(context, R.color.text_color_secondary_title));
        addView(textView);


        //3. Add item arrow.
        if (hasNext) {
            arrowImageView = new ImageView(context);
            arrowImageView.setImageResource(R.drawable.ic_arrow_right);
            addView(arrowImageView);
        }

        setStyle();
        layoutItem();
    }

    private void layoutItem() {
        int round = DisplayUtils.dip2px(context, 22f);
        LayoutParams leftParams = new LayoutParams(round, round);
        leftParams.gravity = Gravity.CENTER_VERTICAL;
        iconImageView.setLayoutParams(leftParams);

        leftParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        leftParams.gravity = Gravity.CENTER_VERTICAL;
        leftParams.weight = 1;
        leftParams.setMarginStart(DisplayUtils.dip2px(context, 10f));
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setLayoutParams(leftParams);

        if (arrowImageView != null) {
            int imageViewSize = DisplayUtils.dip2px(context, 20f);
            LayoutParams imageViewParams = new LayoutParams(imageViewSize, imageViewSize);
            imageViewParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
            arrowImageView.setLayoutParams(imageViewParams);
        }
    }
}
