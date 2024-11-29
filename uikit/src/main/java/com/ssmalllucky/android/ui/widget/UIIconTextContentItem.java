package com.ssmalllucky.android.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.utils.DisplayUtils;
import com.ssmalllucky.android.ui.view.STSecondaryTextView;

/**
 * 带图标、标题、内容文本的文本项组件
 *
 * @author shuaijialin
 */
public class UIIconTextContentItem extends LinearLayout {

    private Context context;
    private boolean isTop;
    private boolean isBottom;
    private boolean hasNext;
    private boolean isLayout;
    private String title;
    private boolean titleWrap;
    private int titleColor;
    private int titleSize;
    private String content;
    private int iconResId;
    private Drawable backgroundDrawable;
    private STSecondaryTextView titleTextView;
    private ImageView iconImageView;
    private STSecondaryTextView contentTextView;
    private STSecondaryTextView statusTextView;
    private ImageView arrowImageView;

    /**
     * 组件图标大小（长和宽保持一致）
     */
    private int iconSize = 0;

    /**
     * 组件向右图标大小（长和宽保持一致）
     */
    private int arrowSize = 0;

    private int spacingXS;
    private int spacingS;
    private int spacingM;

    private int titleWidth;

    private boolean contentWrap;

    private float contentSize;

    private String statusText;

    private float statusTextSize;

    private int statusTextColor;

    public UIIconTextContentItem(@NonNull Context context) {
        super(context);
    }

    public UIIconTextContentItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public UIIconTextContentItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        float density = DisplayUtils.getDensity(context);

        int padding = DisplayUtils.dip2px(context, 12f);
        setPadding(padding, 0, padding, 0);
        setGravity(Gravity.CENTER_VERTICAL);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.UIIconTextContentItem);
        backgroundDrawable = a.getDrawable(R.styleable.UIIconTextContentItem_UIIconTextContentItem_background);
        title = a.getString(R.styleable.UIIconTextContentItem_UIIconTextContentItem_title);
        titleWrap = a.getBoolean(R.styleable.UIIconTextContentItem_UIIconTextContentItem_titleWrap, true);
        titleColor = a.getColor(R.styleable.UIIconTextContentItem_UIIconTextContentItem_titleColor, 0);
        titleSize = (int) (a.getDimensionPixelSize(R.styleable.UIIconTextContentItem_UIIconTextContentItem_titleSize, (context.getResources().getDimensionPixelSize(R.dimen.text_size_secondary_title))) / density);
        content = a.getString(R.styleable.UIIconTextContentItem_UIIconTextContentItem_content);
        isTop = a.getBoolean(R.styleable.UIIconTextContentItem_UIIconTextContentItem_isTop, false);
        isBottom = a.getBoolean(R.styleable.UIIconTextContentItem_UIIconTextContentItem_isBottom, false);
        isLayout = a.getBoolean(R.styleable.UIIconTextContentItem_UIIconTextContentItem_isLayout, false);
        hasNext = a.getBoolean(R.styleable.UIIconTextContentItem_UIIconTextContentItem_hasNext, false);
        iconResId = a.getResourceId(R.styleable.UIIconTextContentItem_UIIconTextContentItem_icon, 0);
        contentWrap = a.getBoolean(R.styleable.UIIconTextContentItem_UIIconTextContentItem_contentWrap, false);
        contentSize = (int) (a.getDimensionPixelSize(R.styleable.UIIconTextContentItem_UIIconTextContentItem_contentSize, (context.getResources().getDimensionPixelSize(R.dimen.text_size_secondary_content))) / density);
        statusText = a.getString(R.styleable.UIIconTextContentItem_UIIconTextContentItem_statusText);
        statusTextColor = a.getColor(R.styleable.UIIconTextContentItem_UIIconTextContentItem_statusTextColor, 0);
        statusTextSize = (int) (a.getDimensionPixelSize(R.styleable.UIIconTextContentItem_UIIconTextContentItem_statusTextSize, (context.getResources().getDimensionPixelSize(R.dimen.text_size_secondary_content))) / density);

        spacingXS = context.getResources().getDimensionPixelSize(R.dimen.spacing_xs);
        spacingS = context.getResources().getDimensionPixelSize(R.dimen.spacing_s);
        spacingM = context.getResources().getDimensionPixelSize(R.dimen.spacing_m);
        iconSize = context.getResources().getDimensionPixelSize(R.dimen.settings_icon_size);
        arrowSize = context.getResources().getDimensionPixelSize(R.dimen.settings_arrow_size);
        titleWidth = context.getResources().getDimensionPixelSize(R.dimen.settings_title_width);

        LayoutParams layoutParams;

        //1. Add icon.
        if (iconResId != 0) {
            iconImageView = new ImageView(context);
            iconImageView.setImageResource(iconResId);
            addView(iconImageView);
        }

        //2. Add item title.
        titleTextView = new STSecondaryTextView(context);
        titleTextView.setText(title);
        titleTextView.setTextSize(titleSize);
        titleTextView.setTextColor(titleColor != 0 ? titleColor : ContextCompat.getColor(context, R.color.text_color_secondary_title));
        layoutParams = new LayoutParams(titleWrap ? ViewGroup.LayoutParams.WRAP_CONTENT : titleWidth, LayoutParams.WRAP_CONTENT);
        if (iconImageView != null) {
            layoutParams.setMarginStart(spacingM);
        }
        titleTextView.setGravity(Gravity.CENTER_VERTICAL);
        titleTextView.setLayoutParams(layoutParams);
        addView(titleTextView);

        //3. Add item content.
        contentTextView = new STSecondaryTextView(context);
        contentTextView.setText(content);
        contentTextView.setTextSize(contentSize);
        contentTextView.setTextColor(ContextCompat.getColor(context, R.color.text_color_secondary_content));
        if (contentWrap) {
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
        }
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        layoutParams.setMarginStart(spacingXS);
        layoutParams.setMarginEnd(spacingXS);
        contentTextView.setGravity(Gravity.END);
        contentTextView.setLayoutParams(layoutParams);
        addView(contentTextView);

        //4. Add statusText
        if (!TextUtils.isEmpty(statusText)) {
            layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
            statusTextView = new STSecondaryTextView(context);
            statusTextView.setText(statusText);
            statusTextView.setTextSize(statusTextSize);
            statusTextView.setTextColor(statusTextColor == 0 ? ContextCompat.getColor(context, R.color.text_color_secondary_content) : statusTextColor);
            statusTextView.setLayoutParams(layoutParams);
            addView(statusTextView);
        }

        //5. Add item arrow.
        if (hasNext) {
            arrowImageView = new ImageView(context);
            arrowImageView.setImageResource(R.drawable.ic_arrow_right);
            layoutParams = new LayoutParams(arrowSize, arrowSize);
            layoutParams.gravity = Gravity.CENTER_VERTICAL|Gravity.END;
            arrowImageView.setLayoutParams(layoutParams);
            addView(arrowImageView);
        }

        setStatusTextStatus(Status.UNDONE);

        // 判断布局属性，若是layout属性（即为单独的布局），则单独采用背景，其他情况下，根据
        // layoutPosition值采用布局
        if (isLayout) {
            if (backgroundDrawable != null) {
                setBackground(backgroundDrawable);
            } else {
                setBackground(ContextCompat.getDrawable(context, R.drawable.selector_layout_settings_item_layout));
            }
        } else {
            setStyle();
        }
    }

    public void setTitle(String title) {
        if (titleTextView != null) titleTextView.setText(title);
    }

    public void setContent(String content) {
        if (contentTextView != null) contentTextView.setText(content);
    }

    public void setStatusText(String statusText) {
        if (statusTextView != null) statusTextView.setText(statusText);
    }

    public void setStatusTextColor(int statusTextColor) {
        if (statusTextView != null) statusTextView.setTextColor(statusTextColor);
    }

    public String getTitle() {
        return titleTextView != null ? titleTextView.getText().toString().trim() : "";
    }

    public String getContent() {
        return contentTextView != null ? contentTextView.getText().toString().trim() : "";
    }

    public void setStatusTextStatus(Status status) {
        if (statusTextView == null) {
            return;
        }
        int textColorResId = 0;
        switch (status) {
            case UNDONE:
                textColorResId = R.color.text_color_secondary_content;
                break;
            case DONE:
                textColorResId = R.color.theme_color_primary;
                break;
            case ERROR:
                textColorResId = R.color.errorColor;
                break;
        }
        if (textColorResId == 0) {
            textColorResId = R.color.text_color_secondary_content;
        }
        statusTextView.setTextColor(ContextCompat.getColor(context, textColorResId));
    }

    public enum Status {
        UNDONE,
        DONE,
        ERROR
    }

    public void setContentStatus(int status) {
        switch (status) {
            case 0:
                setContentBackground(getErrorContentBackground());
                break;
            case 1:
                setContentBackground(getDefaultContentBackground());
                break;
        }
    }

    public void setContentBackground(int resId) {
        if (contentTextView != null) {
            contentTextView.setBackground(ContextCompat.getDrawable(context, resId));
        }
    }

    public int getDefaultContentBackground() {
        return R.drawable.shape_page_background;
    }

    public int getErrorContentBackground() {
        return R.drawable.shape_page_background_error;
    }
}
