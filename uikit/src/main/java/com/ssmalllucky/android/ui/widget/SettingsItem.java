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
import com.ssmalllucky.android.ui.view.STSecondaryTextView;

/**
 * <b>设置父组件</b>
 * <p>
 * 设置项的默认视图组件，原则上所有关于设置项的组件均继承于此组件。
 * <p>
 * 默认子视图：
 * <p>
 * 1. 图标
 * <p>
 * 2. 标题文本
 * <p>
 * 3. 向右箭头
 * <p>
 * 组件约定了默认背景色，且默认的子视图位置依赖。
 * <p>
 * 若非必要，请尽量不要修改此组件代码。
 *
 * @author shuaijialin
 * @version 1.0.0 构造了此组件
 */
public class SettingsItem extends LinearLayout {

    /**
     * 系统上下文
     */
    private Context context;

    public static final int POSITION_TOP = 1;
    public static final int POSITION_MIDDLE = 2;
    public static final int POSITION_BOTTOM = 3;

    /**
     * 组件在父布局中所在的位置
     */
    private int layoutPosition = POSITION_MIDDLE;

    /**
     * 是否有向右箭头图标
     */
    private boolean hasNext = true;

    /**
     * 是否为单独的布局
     */
    private boolean isLayout = false;

    /**
     * 是否自由设置标题宽度
     */
    private boolean titleWrap = false;

    /**
     * 设置项图标资源
     */
    private int iconResId = 0;

    /**
     * 设置项标题
     */
    private String title;

    /**
     * 设置项图标对象
     */
    private ImageView iconImageView;

    /**
     * 标题文本对象
     */
    private STSecondaryTextView titleTextView;

    /**
     * 设置项中间内容容器
     */
    protected LinearLayout contentContainer;

    /**
     * 向右图标对象
     */
    private ImageView arrowImageView;

    /**
     * 设置项图标大小（长和宽保持一致）
     */
    private int iconSize = 0;

    /**
     * 设置项向右图标大小（长和宽保持一致）
     */
    private int arrowSize = 0;

    private int spacingXS;
    private int spacingS;
    private int spacingM;

    private int titleWidth;

    public SettingsItem(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public SettingsItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SettingsItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 加载Settings默认参数
     *
     * @param context 系统上下文
     * @param attrs   属性接口
     */
    protected void init(@NonNull Context context, AttributeSet attrs) {
        this.context = context;
        iconSize = context.getResources().getDimensionPixelSize(R.dimen.settings_icon_size);
        arrowSize = context.getResources().getDimensionPixelSize(R.dimen.settings_arrow_size);
        spacingXS = context.getResources().getDimensionPixelSize(R.dimen.spacing_xs);
        spacingS = context.getResources().getDimensionPixelSize(R.dimen.spacing_s);
        spacingM = context.getResources().getDimensionPixelSize(R.dimen.spacing_m);
        titleWidth = context.getResources().getDimensionPixelSize(R.dimen.settings_title_width);

        removeAllViews();
        setOrientation(HORIZONTAL);
        setPadding(spacingM, 0, spacingM, 0);
        setGravity(Gravity.CENTER_VERTICAL);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SettingsItem);
            title = a.getString(R.styleable.SettingsItem_SettingsItem_title);
            layoutPosition = a.getInt(R.styleable.SettingsItem_SettingsItem_layoutPosition, POSITION_MIDDLE);
            hasNext = a.getBoolean(R.styleable.SettingsItem_SettingsItem_hasNext, true);
            iconResId = a.getResourceId(R.styleable.SettingsItem_SettingsItem_icon, 0);
            isLayout = a.getBoolean(R.styleable.SettingsItem_SettingsItem_isLayout, false);
            titleWrap = a.getBoolean(R.styleable.SettingsItem_SettingsItem_titleWrap, false);
        }

        LayoutParams layoutParams;

        // 1. 添加设置项图标
        if (iconResId != 0) {
            layoutParams = new LayoutParams(iconSize, iconSize);
            iconImageView = new ImageView(context);
            iconImageView.setImageResource(iconResId);
            iconImageView.setLayoutParams(layoutParams);
            addView(iconImageView);
        }

        // 2. 添加设置项标题
        titleTextView = new STSecondaryTextView(context);
        titleTextView.setText(title);
        titleTextView.setTextColor(ContextCompat.getColor(context, R.color.text_color_secondary_title));
        layoutParams = new LayoutParams(titleWrap ? LayoutParams.WRAP_CONTENT : titleWidth, LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        if (iconResId != 0) {
            layoutParams.setMarginStart(spacingS);
        }
        titleTextView.setLayoutParams(layoutParams);
        addView(titleTextView);

        // 3.添加内容容器
        contentContainer = new LinearLayout(context);
        contentContainer.setOrientation(HORIZONTAL);
        layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        contentContainer.setGravity(Gravity.END);
        contentContainer.setLayoutParams(layoutParams);
        addView(contentContainer);

        // 4. 添加设置项向右图标
        if (hasNext) {
            arrowImageView = new ImageView(context);
            arrowImageView.setImageResource(R.drawable.ic_arrow_right);
            layoutParams = new LayoutParams(arrowSize, arrowSize);
            arrowImageView.setLayoutParams(layoutParams);
            addView(arrowImageView);
        }

        // 判断布局属性，若是layout属性（即为单独的布局），则单独采用背景，其他情况下，根据
        // layoutPosition值采用布局
        if (isLayout) {
            setBackground(ContextCompat.getDrawable(context, R.drawable.selector_layout_settings_item_layout));
        } else {
            setStyle();
        }
    }

    /**
     * 设置背景色
     */
    protected void setStyle() {
        int drawableId;
        switch (layoutPosition) {
            case POSITION_TOP:
                drawableId = R.drawable.selector_layout_settings_item_top;
                break;
            case POSITION_BOTTOM:
                drawableId = R.drawable.selector_layout_settings_item_bottom;
                break;
            case POSITION_MIDDLE:
            default:
                drawableId = R.drawable.selector_layout_settings;
                break;
        }
        setBackground(ContextCompat.getDrawable(context, drawableId));
    }
}
