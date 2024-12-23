package com.ssmalllucky.android.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;

/**
 * @ClassName STExpandableTextView
 * @Author ssmalllucky
 * @Date 2024/12/16
 * @Description 折叠文本控件。
 * <p>
 * 目前仅支持竖向的视图显示，若检测到横向显示，将会抛出异常，请开发者谨慎使用。
 */
public class STExpandableTextView extends LinearLayout implements View.OnClickListener {

    private static final String TAG = STExpandableTextView.class.getSimpleName();

    /**
     * 默认显示的文本行数
     **/
    private static final int MAX_COLLAPSED_LINES = 4;

    /**
     * 默认动画时长
     **/
    private static final int DEFAULT_ANIM_DURATION = 300;

    /**
     * 默认渐变值
     **/
    private static final float DEFAULT_ANIM_ALPHA_START = 0.7f;

    protected TextView mTv;

    protected TextView mCollapse;

    private boolean mRelayout;

    private boolean mCollapsed = true;

    private int mCollapsedHeight;

    private int mTextHeightWithMaxLines;

    private int mMaxCollapsedLines;

    private int mMarginBetweenTxtAndBottom;

    private int mAnimationDuration;

    private float mAnimAlphaStart;

    private boolean mAnimating;

    private OnExpandStateChangeListener mListener;

    /**
     * 用于在 ListView 中使用时保存折叠状态
     **/
    private SparseBooleanArray mCollapsedStatus;
    private int mPosition;

    /**
     * 文本颜色
     */
    private int contentColor;

    /**
     * 展开/收起文本颜色
     */
    private int expandColor;


    public STExpandableTextView(Context context) {
        this(context, null);
    }

    public STExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public STExpandableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    @Override
    public void setOrientation(int orientation) {
        if (LinearLayout.HORIZONTAL == orientation) {
            throw new IllegalArgumentException("STExpandableTextView only supports Vertical Orientation.");
        }
        super.setOrientation(orientation);
    }

    @Override
    public void onClick(View view) {
        if (mCollapse.getVisibility() != View.VISIBLE) {
            return;
        }

        mCollapsed = !mCollapsed;
        mCollapse.setText(mCollapsed ? "查看全文" : "收起");

        if (mCollapsedStatus != null) {
            mCollapsedStatus.put(mPosition, mCollapsed);
        }

        // 标记动画正在进行中
        mAnimating = true;

        Animation animation;
        if (mCollapsed) {
            animation = new ExpandCollapseAnimation(this, getHeight(), mCollapsedHeight);
        } else {
            animation = new ExpandCollapseAnimation(this, getHeight(), getHeight() +
                    mTextHeightWithMaxLines - mTv.getHeight());
        }

        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                applyAlphaAnimation(mTv, mAnimAlphaStart);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 在此处清除动画以避免重复调用applyTransformation()
                clearAnimation();
                // 清除动画标志
                mAnimating = false;

                if (mListener != null) {
                    mListener.onExpandStateChanged(mTv, !mCollapsed);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        clearAnimation();
        startAnimation(animation);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 在动画进行过程中，拦截所有对子元素的触摸事件，以防止在动画过程中发生额外的点击
        return mAnimating;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViews();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!mRelayout || getVisibility() == View.GONE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        mRelayout = false;

        mCollapse.setVisibility(View.GONE);
        mTv.setMaxLines(Integer.MAX_VALUE);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mTv.getLineCount() <= mMaxCollapsedLines) {
            return;
        }

        // 保存文本高度和最大行数
        mTextHeightWithMaxLines = getRealTextViewHeight(mTv);

        // 不适合折叠模式。根据需要折叠文本视图。
        if (mCollapsed) {
            mTv.setMaxLines(mMaxCollapsedLines);
        }
        mCollapse.setVisibility(View.VISIBLE);

        // 使用新设置重新测量
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mCollapsed) {
            // 获取 TextView 底部和 ViewGroup 底部之间的边距
            mTv.post(() -> mMarginBetweenTxtAndBottom = getHeight() - mTv.getHeight());
            // 保存此 ViewGroup 的折叠高度
            mCollapsedHeight = getMeasuredHeight();
        }
    }

    public void setOnExpandStateChangeListener(@Nullable OnExpandStateChangeListener listener) {
        mListener = listener;
    }

    public void setText(@Nullable CharSequence text) {
        mRelayout = true;
        mTv.setText(text);
        setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
    }

    public void setText(@Nullable CharSequence text, @NonNull SparseBooleanArray collapsedStatus, int position) {
        mCollapsedStatus = collapsedStatus;
        mPosition = position;
        boolean isCollapsed = collapsedStatus.get(position, true);
        clearAnimation();
        mCollapsed = isCollapsed;
        if (mTv != null) mTv.setEllipsize(TextUtils.TruncateAt.END);
        mCollapse.setText(mCollapsed ? "查看全文" : "收起");
        setText(text);
        getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        requestLayout();
    }

    @Nullable
    public CharSequence getText() {
        if (mTv == null) {
            return "";
        }
        return mTv.getText();
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.STExpandableTextView);
        mMaxCollapsedLines = typedArray.getInt(R.styleable.STExpandableTextView_maxCollapsedLines, MAX_COLLAPSED_LINES);
        mAnimationDuration = typedArray.getInt(R.styleable.STExpandableTextView_animDuration, DEFAULT_ANIM_DURATION);
        mAnimAlphaStart = typedArray.getFloat(R.styleable.STExpandableTextView_animAlphaStart, DEFAULT_ANIM_ALPHA_START);
        contentColor = typedArray.getColor(R.styleable.STExpandableTextView_contentColor, ContextCompat.getColor(context, R.color.text_color_primary));
        expandColor = typedArray.getColor(R.styleable.STExpandableTextView_expandColor, ContextCompat.getColor(context, R.color.activeColor));

        typedArray.recycle();

        setOrientation(LinearLayout.VERTICAL);

        setVisibility(GONE);
    }

    private void findViews() {
        mTv = findViewById(R.id.expandable_text);
        mTv.setTextColor(contentColor);
        mTv.setEllipsize(TextUtils.TruncateAt.END);
        mTv.setOnClickListener(this);
        mCollapse = findViewById(R.id.expand_collapse);
        mCollapse.setTextColor(expandColor);
        if (mTv != null) mTv.setEllipsize(TextUtils.TruncateAt.END);
        mCollapse.setText(mCollapsed ? "查看全文" : "收起");
        mCollapse.setOnClickListener(this);
    }

    private static void applyAlphaAnimation(View view, float alpha) {
        view.setAlpha(alpha);
    }

    private static int getRealTextViewHeight(@NonNull TextView textView) {
        int textHeight = textView.getLayout().getLineTop(textView.getLineCount());
        int padding = textView.getCompoundPaddingTop() + textView.getCompoundPaddingBottom();
        return textHeight + padding;
    }

    class ExpandCollapseAnimation extends Animation {
        private final View mTargetView;
        private final int mStartHeight;
        private final int mEndHeight;

        public ExpandCollapseAnimation(View view, int startHeight, int endHeight) {
            mTargetView = view;
            mStartHeight = startHeight;
            mEndHeight = endHeight;
            setDuration(mAnimationDuration);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final int newHeight = (int) ((mEndHeight - mStartHeight) * interpolatedTime + mStartHeight);
            mTv.setMaxHeight(newHeight - mMarginBetweenTxtAndBottom);
            if (Float.compare(mAnimAlphaStart, 1.0f) != 0) {
                applyAlphaAnimation(mTv, mAnimAlphaStart + interpolatedTime * (1.0f - mAnimAlphaStart));
            }
            mTargetView.getLayoutParams().height = newHeight;
            mTargetView.requestLayout();
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }

    public interface OnExpandStateChangeListener {
        /**
         * 当展开/收起状态触发完毕后，此方法被调用
         */
        void onExpandStateChanged(TextView textView, boolean isExpanded);
    }
}
