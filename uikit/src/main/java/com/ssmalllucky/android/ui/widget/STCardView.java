package com.ssmalllucky.android.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.view.STSecondaryTextView;

/**
 * @ClassName STCardView
 * @Author shuaijialin
 * @Date 2024/5/24
 * @Description 卡片视图，主要用于图标和文本的上下组合。
 */
public class STCardView extends CardView {

    private Context mContext;

    private LinearLayout container;

    protected int spacingXS;
    protected int spacingS;
    protected int spacingM;
    protected int spacingL;

    private Drawable icon;

    private int color;

    private int titleColor;

    private String title;

    private int titleSize;

    private String desc;

    private int descSize;

    private int defaultColor;

    private int pressedColor;

    private STSecondaryTextView titleTextView;

    private STSecondaryTextView descTextView;

    private ImageView imageView;

    private int iconSize;

    private boolean isSimpleBackground;


    public STCardView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public STCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public STCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    protected void init(@NonNull Context context, AttributeSet attrs) {
        this.mContext = context;
        spacingXS = context.getResources().getDimensionPixelSize(R.dimen.spacing_xs);
        spacingS = context.getResources().getDimensionPixelSize(R.dimen.spacing_s);
        spacingM = context.getResources().getDimensionPixelSize(R.dimen.spacing_m);
        spacingL = context.getResources().getDimensionPixelSize(R.dimen.spacing_l);

        container = new LinearLayout(context);
        container.removeAllViews();

        container.setPadding(0, spacingL, 0, spacingL);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setGravity(Gravity.CENTER);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.STCardView);
            color = a.getColor(R.styleable.STCardView_STCardView_color, ContextCompat.getColor(context, R.color.white));
            icon = a.getDrawable(R.styleable.STCardView_STCardView_icon);
            title = a.getString(R.styleable.STCardView_STCardView_title);
            iconSize = a.getDimensionPixelSize(R.styleable.STCardView_STCardView_iconSize, context.getResources().getDimensionPixelSize(R.dimen.st_card_view_icon_size));
            titleColor = a.getDimensionPixelSize(R.styleable.STCardView_STCardView_titleColor, ContextCompat.getColor(context, R.color.text_color_primary));
            desc = a.getString(R.styleable.STCardView_STCardView_desc);
            titleSize = a.getDimensionPixelSize(R.styleable.STCardView_STCardView_titleSize, context.getResources().getDimensionPixelSize(R.dimen.text_size_secondary_title));
            descSize = context.getResources().getDimensionPixelSize(R.dimen.text_size_tertiary);
            isSimpleBackground = a.getBoolean(R.styleable.STCardView_STCardView_simpleBackground, false);
        }

        //设置默认状态下的透明度
        defaultColor = ColorUtils.setAlphaComponent(color, 26);

        //设置点击状态下的透明度
        pressedColor = ColorUtils.setAlphaComponent(color, 102);

        LinearLayout.LayoutParams layoutParams;

        layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        container.setLayoutParams(layoutParams);

        if (!isSimpleBackground) {
            setDrawableBackgrounds(container, pressedColor, defaultColor);
        }

        addIconImageView();

        if (!TextUtils.isEmpty(title)) {
            addTitleTextView();
        }

        if (!TextUtils.isEmpty(desc)) {
            addDescTextView();
        }

        addView(container);
    }

    private void addTitleTextView() {
        LinearLayout.LayoutParams layoutParams;
        if (titleTextView == null) {
            titleTextView = new STSecondaryTextView(mContext);
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(spacingXS, spacingM, spacingXS, 0);
            titleTextView.setLayoutParams(layoutParams);
            titleTextView.setGravity(Gravity.CENTER);
            container.addView(titleTextView);
        }
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
        titleTextView.setText(title);
        titleTextView.setTextColor(color);
    }

    private void addDescTextView() {
        LinearLayout.LayoutParams layoutParams;
        if (descTextView == null) {
            descTextView = new STSecondaryTextView(mContext);
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(spacingXS, spacingM, spacingXS, 0);
            descTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, descSize);
            descTextView.setLayoutParams(layoutParams);
            descTextView.setGravity(Gravity.CENTER);
            container.addView(descTextView);
        }
        descTextView.setText(desc);
        descTextView.setTextColor(color);
    }

    private void addIconImageView() {
        if (icon != null) {
            LinearLayout.LayoutParams layoutParams;
            if (imageView == null) {
                imageView = new ImageView(mContext);
                layoutParams = new LinearLayout.LayoutParams(iconSize, iconSize);
                imageView.setLayoutParams(layoutParams);
                container.addView(imageView);
            }
            imageView.setImageDrawable(icon);
        }
    }

    /**
     * 设置button各种状态的颜色。
     *
     * @param pressDrawableId  按下去的颜色
     * @param normalDrawableId 默认状态下颜色
     */
    private void setDrawableBackgrounds(View view, @DrawableRes int pressDrawableId,
                                        @DrawableRes int normalDrawableId) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        Drawable buttonPress = new ColorDrawable(pressDrawableId);
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, buttonPress);

        stateListDrawable.addState(new int[]{android.R.attr.state_focused}, buttonPress);

        Drawable normalDrawable = new ColorDrawable(normalDrawableId);
        stateListDrawable.addState(new int[]{android.R.attr.state_enabled}, normalDrawable);

        stateListDrawable.addState(new int[]{}, normalDrawable);
        view.setBackground(stateListDrawable);
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            this.title = title;
            addTitleTextView();
        }
    }

    public void setIcon(int resId) {
        icon = ContextCompat.getDrawable(mContext, resId);
        addIconImageView();
    }

    public void setColor(int colorResId) {
        color = ContextCompat.getColor(mContext, colorResId);
        //设置默认状态下的透明度
        defaultColor = ColorUtils.setAlphaComponent(color, 26);
        //设置点击状态下的透明度
        pressedColor = ColorUtils.setAlphaComponent(color, 102);
        if (container != null) {
            setDrawableBackgrounds(container, pressedColor, defaultColor);
        }

        if (icon != null) {
            PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);
            icon.setColorFilter(colorFilter);
            imageView.setImageDrawable(icon);
        }
    }

    public void setParseColor(int colorParsed) {
        color = colorParsed;
        //设置默认状态下的透明度
        defaultColor = ColorUtils.setAlphaComponent(color, 26);
        //设置点击状态下的透明度
        pressedColor = ColorUtils.setAlphaComponent(color, 102);
        if (container != null) {
            setDrawableBackgrounds(container, pressedColor, defaultColor);
        }

        if (icon != null) {
            PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);
            icon.setColorFilter(colorFilter);
            imageView.setImageDrawable(icon);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).setEnabled(enabled);
        }
    }
}
