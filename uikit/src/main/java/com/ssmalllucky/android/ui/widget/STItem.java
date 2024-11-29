package com.ssmalllucky.android.ui.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.TransitionDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.view.STPrimaryTextView;
import com.ssmalllucky.android.ui.view.STSecondaryTextView;

/**
 * <b>设置项默认Widget</b>
 * <p>
 * 修改自UIKit 1.0的STItem Widget。
 * <p>
 * 设置项的默认Widget，原则上所有关于设置项的组件均继承于此组件。
 * <p>
 * 包含子视图：
 * <p>
 * 1. 图标（可选）
 * <p>
 * 2. 标题文本（必选）
 * <p>
 * 3. 标题说明（可选）
 * <p>
 * 4. 向右箭头（可选）
 * <p>
 * 组件约定了默认背景色，且默认的子视图位置依赖。
 * <p>
 * 若非必要，请尽量不要修改此组件代码。
 *
 * @author shuaijialin
 * @version 1.1.0 调整了Widget的整体样式
 */
public class STItem extends LinearLayout {

    /**
     * 系统上下文
     */
    protected Context mContext;

    public static final int POSITION_TOP = 1;
    public static final int POSITION_MIDDLE = 2;
    public static final int POSITION_BOTTOM = 3;

    /**
     * 组件在父布局中所在的位置
     */
    protected int layoutPosition = POSITION_MIDDLE;

    /**
     * 是否有向右箭头图标
     */
    private boolean hasNext = true;

    /**
     * 是否为单独的布局
     */
    protected boolean isLayout = false;

    /**
     * 是否自由设置标题宽度
     */
    private boolean titleWrap = false;

    /**
     * 是否自由设置标题说明宽度，默认为true
     */
    private boolean descWrap = true;

    /**
     * 设置项图标资源
     */
    private int iconResId = 0;

    /**
     * 设置项标题
     */
    private String title;

    /**
     * 标题项字体大小
     */
    private float titleSize;

    /**
     * 设置项标题引述
     */
    private String desc;

    /**
     * 设置项图标对象
     */
    private ImageView iconImageView;

    /**
     * 标题文本对象
     */
    private STPrimaryTextView titleTextView;

    /**
     * 标题说明文本对象
     */
    private STSecondaryTextView descTextView;

    /**
     * 设置项标题和说明容器
     */
    protected LinearLayout titleContainer;

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

    protected int spacingXS;
    protected int spacingS;
    protected int spacingM;

    protected int titleWidth;

    private String activityTarget;

    private int defaultMarginTop;

    private boolean enabledTouch;

    public STItem(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public STItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public STItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 加载ST默认参数
     *
     * @param context 系统上下文
     * @param attrs   属性接口
     */
    protected void init(@NonNull Context context, AttributeSet attrs) {
        this.mContext = context;
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
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.STItem);
            title = a.getString(R.styleable.STItem_STItem_title);
            titleSize = (a.getDimensionPixelSize(R.styleable.STItem_STItem_titleSize, context.getResources().getDimensionPixelSize(R.dimen.text_size_primary)));
            desc = a.getString(R.styleable.STItem_STItem_desc);
            layoutPosition = a.getInt(R.styleable.STItem_STItem_layoutPosition, POSITION_MIDDLE);
            hasNext = a.getBoolean(R.styleable.STItem_STItem_hasNext, true);
            iconResId = a.getResourceId(R.styleable.STItem_STItem_icon, 0);
            isLayout = a.getBoolean(R.styleable.STItem_STItem_isLayout, false);
            titleWrap = a.getBoolean(R.styleable.STItem_STItem_titleWrap, false);
            defaultMarginTop = a.getDimensionPixelSize(R.styleable.STItem_defaultMarginTop, context.getResources().getDimensionPixelSize(R.dimen.spacing_m));
            activityTarget = a.getString(R.styleable.STItem_activityTarget);
            enabledTouch = a.getBoolean(R.styleable.STItem_enabledTouch, true);
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
        titleContainer = new LinearLayout(context);
        titleContainer.setOrientation(VERTICAL);
        layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        if (iconResId != 0) {
            layoutParams.setMarginStart(spacingS);
        }
        titleContainer.setGravity(Gravity.START);
        titleContainer.setLayoutParams(layoutParams);
        addView(titleContainer);

        if (!TextUtils.isEmpty(title)) {
            titleTextView = new STPrimaryTextView(context);
            titleTextView.setText(title);
//            titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
            titleTextView.setTextColor(ContextCompat.getColor(context, R.color.text_color_primary));
            layoutParams = new LayoutParams(titleWrap ? LayoutParams.WRAP_CONTENT : titleWidth, LayoutParams.WRAP_CONTENT);
            titleTextView.setLayoutParams(layoutParams);
            titleContainer.addView(titleTextView);
        }

        if (!TextUtils.isEmpty(desc)) {
            descTextView = new STSecondaryTextView(context);
            descTextView.setText(desc);
            descTextView.setTextColor(ContextCompat.getColor(context, R.color.text_color_secondary_content));
            layoutParams = new LayoutParams(descWrap ? LayoutParams.WRAP_CONTENT : titleWidth, LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            descTextView.setLayoutParams(layoutParams);
            titleContainer.addView(descTextView);
        }

        // 3.添加内容容器
        contentContainer = new LinearLayout(context);
        contentContainer.setOrientation(HORIZONTAL);
        layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        contentContainer.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
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
            setBackground(ContextCompat.getDrawable(context, R.drawable.transition_settings_item_islayout));
        } else {
            setStyle();
        }


        setOnClickListener(v -> {
            if (!TextUtils.isEmpty(activityTarget)) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(mContext.getApplicationContext(), activityTarget));
                if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                    mContext.startActivity(intent);
                }
            }
        });
    }

    private int downX;
    private int downY;

    /**
     * 判断缩小动画有没有执行过
     */
    private boolean hasDoneAnimation;

    /**
     * 滑动最小距离
     */
    private static final int MIN_MOVE_DPI = 10;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!enabledTouch) {
                    return super.onTouchEvent(event);
                }
                /**
                 * 请求父类不要拦截我的事件,意思是让我来处理接下来的滑动或别的事件
                 */
                getParent().requestDisallowInterceptTouchEvent(true);
                downX = (int) event.getX();
                downY = (int) event.getY();
                hasDoneAnimation = false;
                post(this::onActionDown);
                return super.onTouchEvent(event);
            case MotionEvent.ACTION_MOVE:
                if (!enabledTouch) {
                    return super.onTouchEvent(event);
                }
                int moveX = (int) event.getX();
                int moveY = (int) event.getY();
                int moveDistanceX = Math.abs(moveX) - downX;
                int moveDistanceY = Math.abs(moveY) - downY;
                /**
                 * 这里是判断是向左还是向右滑动,然后用view的宽度计算出一个距离compareWidth,当滑动距离超出compareWidth时,需要执行返回动画.
                 */
                int compareWidth = moveDistanceX > 0 ? getWidth() - downX : downX;
                /**
                 * 第一个条件:判断向上或向下滑动距离大于滑动最小距离
                 * 第二个条件:判断向左或向右的滑动距离是否超出(compareWidth-最小距离)
                 * 第三个条件:判断有没有执行过返回动画并在执行过一次后置为true.
                 */
                if ((Math.abs(moveDistanceY) > dip2px(MIN_MOVE_DPI) || Math.abs(moveDistanceX) >= compareWidth - dip2px(MIN_MOVE_DPI)) && !hasDoneAnimation) {
                    /**
                     * 一 只要满足上述条件,就代表用户不是点击view,而是执行了滑动操作,这个时候我们就需要父类以及我们的最上层的控件来
                     * 拦截我们的事件,让最外层控件处理接下来的事件,比如scrollview的滑动.
                     * 二 因为我们执行了滑动操作,所以要执行view的返回动画
                     */
                    getParent().requestDisallowInterceptTouchEvent(false);
                    hasDoneAnimation = true;
                    post(this::onActionMoveOrUp);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!enabledTouch) {
                    return super.onTouchEvent(event);
                }
                /**
                 * 这里如果我们是单纯的点击事件就会执行
                 */
                if (!hasDoneAnimation) {
                    hasDoneAnimation = true;
                    post(() -> {
                        performClick();
                        onActionMoveOrUp();
                    });
                    return true;
                } else {
                    return false;
                }
            default:
                return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * Make dp to px
     *
     * @param dipValue dp you need to change
     * @return Get px according to your dp value.
     */
    public int dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private TransitionDrawable actionDownDrawable;

    private void onActionDown() {
        if (!(getBackground() instanceof TransitionDrawable)) {
            return;
        }
        actionDownDrawable = (TransitionDrawable) getBackground();
        actionDownDrawable.startTransition(200);
    }

    private void onActionMoveOrUp() {
        if (!(getBackground() instanceof TransitionDrawable)) {
            return;
        }
        TransitionDrawable transition = (TransitionDrawable) getBackground();
        transition.reverseTransition(200);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isLayout) {
            LayoutParams params = (LayoutParams) getLayoutParams();
            if (params == null) {
                params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            }
            params.setMargins(0, defaultMarginTop, 0, 0);
            setLayoutParams(params);
        }
    }

    /**
     * 设置背景色
     */
    protected void setStyle() {
        int drawableId;
        switch (layoutPosition) {
            case POSITION_TOP:
                drawableId = R.drawable.transition_settings_item_top;
                break;
            case POSITION_BOTTOM:
                drawableId = R.drawable.transition_settings_item_bottom;
                break;
            case POSITION_MIDDLE:
            default:
                drawableId = R.drawable.transition_settings_item;
                break;
        }
        setBackground(ContextCompat.getDrawable(mContext, drawableId));
    }

    protected void setErrorStyle() {
        int drawableId;
        switch (layoutPosition) {
            case POSITION_TOP:
                drawableId = R.drawable.transition_settings_item_top_error;
                break;
            case POSITION_BOTTOM:
                drawableId = R.drawable.transition_settings_item_bottom_error;
                break;
            case POSITION_MIDDLE:
            default:
                drawableId = R.drawable.transition_settings_item_error;
                break;
        }
        setBackground(ContextCompat.getDrawable(mContext, drawableId));
    }

    public STPrimaryTextView getTitleTextView() {
        return titleTextView;
    }

    private long lastClickTime;

    /**
     * 快速点击的时间间隔
     */
    private static final int TIME_DELAY = 500;

    /**
     * Judge is fast click event.
     *
     * @return Is fast click or not.
     */
    public boolean isFastClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD < TIME_DELAY) {
            return true;
        } else {
            lastClickTime = time;
            return false;
        }
    }

    public void setTitle(String title) {
        if (titleTextView != null) titleTextView.setText(title);
    }

    public String getTitle() {
        return title;
    }

    public void setErrorStatus(boolean isErrorStatus) {
        if (isErrorStatus) {
            setErrorStyle();
        } else {
            setStyle();
        }
    }
}
