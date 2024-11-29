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
public class UIIconTextEditTextItem extends LinearLayout {

    private Context context;
    private boolean isRequired;
    private boolean isTop;
    private boolean isBottom;
    private boolean hasNext;
    private String title;
    private String content;
    private String hint;
    private int iconResId;
    private int actionIconResId;
    private ImageView requiredImageView;
    private STSecondaryTextView titleTextView;
    private ImageView iconImageView;
    private STEditText contentEditText;
    private ImageView arrowImageView;
    private ImageView actionIconImageView;

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

    private boolean clickable;

    public UIIconTextEditTextItem(@NonNull Context context) {
        super(context);
    }

    public UIIconTextEditTextItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public UIIconTextEditTextItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        spacingXS = context.getResources().getDimensionPixelSize(R.dimen.spacing_xs);
        spacingS = context.getResources().getDimensionPixelSize(R.dimen.spacing_s);
        spacingM = context.getResources().getDimensionPixelSize(R.dimen.spacing_m);
        iconSize = context.getResources().getDimensionPixelSize(R.dimen.settings_icon_size);
        arrowSize = context.getResources().getDimensionPixelSize(R.dimen.settings_arrow_size);

        removeAllViews();
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

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
        actionIconResId = a.getResourceId(R.styleable.SettingsIconEditTextItem_SettingsIconEditTextItem_actionIcon,0);

        LinearLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //1. Add requiredIV if true.
        if (isRequired) {
            requiredImageView = new ImageView(context);
            requiredImageView.setImageResource(R.drawable.ic_required);
            addView(requiredImageView);
            requiredImageView.setLayoutParams(layoutParams);
        }

        //2. Add icon.
        if (iconResId != 0) {
            layoutParams = new LayoutParams(iconSize, iconSize);
            iconImageView = new ImageView(context);
            iconImageView.setImageResource(iconResId);
            if (isRequired) {
                layoutParams.setMarginStart(spacingXS);
            }
            iconImageView.setLayoutParams(layoutParams);
            addView(iconImageView);
        }

        //3. Add title.
        titleTextView = new STSecondaryTextView(context);
        titleTextView.setText(title);
        titleTextView.setTextColor(ContextCompat.getColor(context, R.color.text_color_secondary_title));
        // Title layout params.
        int titleWidth = context.getResources().getDimensionPixelSize(R.dimen.settings_title_width);
        layoutParams = new LayoutParams(titleWidth, LayoutParams.WRAP_CONTENT);
        if (iconImageView != null) {
            layoutParams.setMarginStart(spacingS);
        } else if (isRequired) {
            layoutParams.setMarginStart(spacingXS);
        }
        titleTextView.setLayoutParams(layoutParams);
        addView(titleTextView);

        //4. Add content.
        contentEditText = new STEditText(context);
        contentEditText.setText(content);
        contentEditText.setHint(hint);
        contentEditText.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_edittext_background));
        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.dip2px(context, 38f));
        layoutParams.weight = 1;
        layoutParams.setMarginStart(spacingXS);
        contentEditText.setPaddingRelative(spacingS, 0, 0, 0);
        contentEditText.setLayoutParams(layoutParams);
        contentEditText.setClickable(clickable);
        addView(contentEditText);

        if(actionIconResId != 0){
            actionIconImageView = new ImageView(context);
            actionIconImageView.setImageResource(actionIconResId);
            LayoutParams imageViewParams = new LayoutParams(iconSize, iconSize);
            imageViewParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
            imageViewParams.setMarginStart(spacingXS);
            actionIconImageView.setLayoutParams(imageViewParams);
            addView(actionIconImageView);
        }

        //5. Add arrow.
        if (hasNext) {
            arrowImageView = new ImageView(context);
            arrowImageView.setImageResource(R.drawable.ic_arrow_right);
            LayoutParams imageViewParams = new LayoutParams(arrowSize, arrowSize);
            imageViewParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
            arrowImageView.setLayoutParams(imageViewParams);
            addView(arrowImageView);
        }
        setStyle();
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
        if (contentEditText != null) {
            contentEditText.setBackground(ContextCompat.getDrawable(context, resId));
        }
    }

    public int getDefaultContentBackground() {
        return R.drawable.shape_edittext_background;
    }

    public int getErrorContentBackground() {
        return R.drawable.shape_layout_settings_item_error;
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

    public STEditText getContentEditText() {
        return contentEditText;
    }

    /**
     * 设置键盘显示类型
     * <p>
     * 仅对Content有效
     *
     * @param inputType 键盘显示类型
     */
    public void setInputType(int inputType) {
        if (getContentEditText() != null) {
            getContentEditText().setInputType(inputType);
        }
    }

    public void setActionOnClickListener(OnClickListener listener){
        if(actionIconImageView != null){
            actionIconImageView.setOnClickListener(listener);
        }
    }
}
