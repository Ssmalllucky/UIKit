package com.ssmalllucky.android.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
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
import com.ssmalllucky.android.ui.view.STEditText;
import com.ssmalllucky.android.ui.view.STSecondaryTextView;

/**
 * 带图标、标题、可输入内容的文本项组件
 *
 * @author shuaijialin
 */
public class SettingsIconEditTextItem extends LinearLayout {

    private Context context;
    private boolean isRequired;
    private boolean isTop;
    private boolean isBottom;
    private boolean hasNext;
    private String title;
    private String content;
    private String hint;
    private int iconResId;
    private ImageView requiredImageView;
    private STSecondaryTextView titleTextView;
    private ImageView iconImageView;
    private STEditText contentEditText;
    private ImageView imageView;
    private boolean clickable;

    public SettingsIconEditTextItem(@NonNull Context context) {
        super(context);
    }

    public SettingsIconEditTextItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SettingsIconEditTextItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        int padding = DisplayUtils.dip2px(context, 12f);
        setPadding(padding, 0, padding, 0);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingsIconEditTextItem);
        title = a.getString(R.styleable.SettingsIconEditTextItem_SettingsIconEditTextItem_title);
        content = a.getString(R.styleable.SettingsIconEditTextItem_SettingsIconEditTextItem_content);
        hint = a.getString(R.styleable.SettingsIconEditTextItem_SettingsIconEditTextItem_hint);
        isRequired = a.getBoolean(R.styleable.SettingsIconEditTextItem_SettingsIconEditTextItem_isRequired, false);
        isTop = a.getBoolean(R.styleable.SettingsIconEditTextItem_SettingsIconEditTextItem_isTop, false);
        isBottom = a.getBoolean(R.styleable.SettingsIconEditTextItem_SettingsIconEditTextItem_isBottom, false);
        hasNext = a.getBoolean(R.styleable.SettingsIconEditTextItem_SettingsIconEditTextItem_hasNext, true);
        iconResId = a.getResourceId(R.styleable.SettingsIconEditTextItem_SettingsIconEditTextItem_icon, 0);
        clickable = a.getBoolean(R.styleable.SettingsIconEditTextItem_SettingsIconEditTextItem_clickable,true);

        //0. Add requiredTV if true.
        if (isRequired) {
            requiredImageView = new ImageView(context);
            requiredImageView.setImageResource(R.drawable.ic_required);
            addView(requiredImageView);
        }

        //1. Add icon.
        if (iconResId != 0) {
            iconImageView = new ImageView(context);
            iconImageView.setImageResource(iconResId);
            addView(iconImageView);
        }

        //2. Add item title.
        titleTextView = new STSecondaryTextView(context);
        titleTextView.setText(title);
        titleTextView.setTextColor(ContextCompat.getColor(context, R.color.text_color_secondary_title));
        addView(titleTextView);

        //3. Add item content.
        contentEditText = new STEditText(context);
        contentEditText.setText(content);
        contentEditText.setHint(hint);
        contentEditText.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_edittext_background));
        contentEditText.setClickable(clickable);
        addView(contentEditText);

        //3. Add item arrow.
        if (hasNext) {
            imageView = new ImageView(context);
            imageView.setImageResource(R.drawable.ic_arrow_right);
            addView(imageView);
        }

        setStyle();
        layoutItem();
    }

    public void setContentBackground(int resId) {
        if (contentEditText != null) {
            contentEditText.setBackground(ContextCompat.getDrawable(context, resId));
        }
    }

    public void setTitle(String title) {
        if (titleTextView != null) titleTextView.setText(title);
    }

    public void setHint(String hint) {
        if (contentEditText != null) contentEditText.setHint(hint);
    }

    public void setContent(String content) {
        if (contentEditText != null) contentEditText.setText(content);
    }

    public String getTitle() {
        return titleTextView != null ? titleTextView.getText().toString().trim() : "";
    }

    public String getHint() {
        return contentEditText != null ? contentEditText.getHint().toString().trim() : "";
    }

    public String getContent() {
        return contentEditText != null ? contentEditText.getText().toString().trim() : "";
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void layoutItem() {
        int round = DisplayUtils.dip2px(context, 22f);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, round);

        if (isRequired) {
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            requiredImageView.setLayoutParams(layoutParams);
        }

        if (iconImageView != null) {
            layoutParams = new LayoutParams(round, round);
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            if (isRequired) {
                layoutParams.setMarginStart(DisplayUtils.dip2px(context, 4f));
            }
            iconImageView.setLayoutParams(layoutParams);
        }

        // Title layout params.
        int titleWidth = context.getResources().getDimensionPixelSize(R.dimen.settings_title_width);
        layoutParams = new LayoutParams(titleWidth, LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        int titleIconSpacing = 0;
        if (iconImageView != null) {
            titleIconSpacing = context.getResources().getDimensionPixelSize(R.dimen.settings_title_icon_spacing);
        } else if(isRequired){
            titleIconSpacing = context.getResources().getDimensionPixelSize(R.dimen.spacing_xs);
        }
        layoutParams.setMarginStart(titleIconSpacing);
        titleTextView.setGravity(Gravity.CENTER_VERTICAL);
        titleTextView.setLayoutParams(layoutParams);

        // Content layout params.
        layoutParams = new LayoutParams(0, DisplayUtils.dip2px(context, 38f));
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        layoutParams.weight = 1;
        int spacing = context.getResources().getDimensionPixelSize(R.dimen.spacing_xs);
        layoutParams.setMarginStart(spacing);
        contentEditText.setPaddingRelative(DisplayUtils.dip2px(context, 6), 0, 0, 0);
        contentEditText.setLayoutParams(layoutParams);

        if (imageView != null) {
            int imageViewSize = DisplayUtils.dip2px(context, 20f);
            LayoutParams imageViewParams = new LayoutParams(imageViewSize, imageViewSize);
            imageViewParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
            imageView.setLayoutParams(imageViewParams);
        }
    }
}
