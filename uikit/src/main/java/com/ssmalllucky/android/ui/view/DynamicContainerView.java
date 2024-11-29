package com.ssmalllucky.android.ui.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.utils.DisplayUtils;

import java.util.ArrayList;

/**
 * Created by shuaijialin on 2023/7/3.
 * <p>
 * 仿iOS灵动岛样式的组件。
 */
public class DynamicContainerView extends View {

    private Context context;

    /**
     * 按钮的状态
     */
    public interface Status {
        // 展开状态
        int EXPAND = 1;
        // 普通状态
        int NORMAL = 0;
    }

    /**
     * 动画时长
     */
    private final int mDuration = 500;

    /**
     * 目前的状态
     */
    private int mCurrentStatus = Status.NORMAL;

    /**
     * 是否正在动画中
     */
    private boolean isAnim;

    /**
     * 初始圆角的大小
     */
    private float mOriginRadius;

    private float maxRadius;

    /**
     * 画笔
     */
    private Paint mPaint;

    private Paint mTitleTextPaint;
    private Paint mContentTextPaint;

    /**
     * 绘制的形状区域
     */
    private RectF mRectF;

    /**
     * 当前的Padding
     */
    private int mCurrentPadding;

    private int childSpacing;

    private int toHeight = 0;

    public DynamicContainerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        // 把默认背景去掉
        setBackgroundColor(0);

        setTranslationZ(20f);
        setElevation(20f);

        //设置标题文本样式
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置抗锯齿
        mTitleTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTitleTextPaint.setColor(ContextCompat.getColor(context, R.color.errorColor));
        mTitleTextPaint.setFakeBoldText(true);
        float titleTextSize = context.getResources().getDimensionPixelSize(R.dimen.text_size_secondary_title);
        mTitleTextPaint.setTextSize(titleTextSize);

        //设置内容文本样式
        mContentTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mContentTextPaint.setColor(ContextCompat.getColor(context, R.color.errorColor));
        mContentTextPaint.setFakeBoldText(true);
        float contentTextSize = context.getResources().getDimensionPixelSize(R.dimen.text_size_secondary_content);
        mContentTextPaint.setTextSize(contentTextSize);

        //定义内容文本上下间距
        float density = DisplayUtils.getDensity(context);
        childSpacing = (int) (context.getResources().getDimensionPixelSize(R.dimen.text_size_secondary_content) / density);

        //设置防止抖动
        mPaint.setDither(true);
        setPadding(childSpacing, childSpacing, childSpacing, childSpacing);

        setOnClickListener(v -> {
            if (!isAnim) {
                switchStatus();
            }
        });

        textRect = new Rect();
    }

    private Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private boolean ensureHandlerNotNull() {
        return handler != null;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        mOriginRadius = height / 2;
        maxRadius = height / 3;
    }

    private String tipsTitle = "";

    private ArrayList<TipsItem> tipsList;

    public void setTipsTitle(String tipsTitle) {
        this.tipsTitle = tipsTitle;
    }

    public void setTipsList(ArrayList<TipsItem> tipsList) {
        if(tipsList == null || tipsList.size() == 0){
            return;
        }
        this.tipsList = tipsList;
        mContentTextPaint.getTextBounds(tipsList.get(0).getField(), 0, tipsList.get(0).getField().length(), textRect);
        int spacing = DisplayUtils.dip2px(context,6f);
        toHeight = (textRect.height() + spacing) * tipsList.size();
        invalidate();
    }

    private Rect textRect;

    @Override
    protected void onDraw(Canvas canvas) {
        if (mRectF == null) {
            mRectF = new RectF();
        }
        mRectF.set(0, 0, getWidth(), getHeight() + mCurrentPadding);
        // 开始画后面的背景
        mPaint.setColor(Color.WHITE);
        canvas.clipRect(mRectF);
        canvas.drawRoundRect(mRectF, mOriginRadius, mOriginRadius, mPaint);
        mTitleTextPaint.setTextAlign(Paint.Align.LEFT);
        String finalTipsTitle = tipsTitle + ((mCurrentStatus == Status.EXPAND) ? "，点击收起" : "，点击展开");
        mTitleTextPaint.getTextBounds(finalTipsTitle, 0, finalTipsTitle.length(), textRect);
        //如果想让文字跟着一起滚动，可以加上实时变化的mCurrentPadding
//        canvas.drawText(tipsTitle, getWidth() / 2 - textRect.width() / 2, getBaseLine(mTitleTextPaint, (getHeight()) / 2  + 20), mTitleTextPaint);
        canvas.drawText(finalTipsTitle, getWidth() / 2 - textRect.width() / 2, getBaseLine(mTitleTextPaint, getHeight() / 2), mTitleTextPaint);

        int baselineY = 0;

        if (tipsList != null && tipsList.size() > 0) {
            for (int i = 0; i < tipsList.size(); i++) {
                TipsItem item = tipsList.get(i);
                String child = item.getField();
                mContentTextPaint.getTextBounds(child, 0, child.length(), textRect);
                if (i == 0) {
                    baselineY = getHeight() + textRect.height() + childSpacing;
                } else {
                    baselineY += textRect.height() + childSpacing;
                }
                canvas.drawText(child, getWidth() / 2 - textRect.width() / 2, getBaseLine(mContentTextPaint, baselineY), mContentTextPaint);
            }
        }
        super.onDraw(canvas);
    }

    private int getBaseLine(Paint paint, int centerY) {
        return centerY - (paint.getFontMetricsInt().bottom + paint.getFontMetricsInt().top) / 2;
    }

    private int count = 1;

    /**
     * 设置按钮状态
     */
    public void setStatus(int newStatus) {
        if (newStatus != mCurrentStatus && !isAnim) {
            switch (newStatus) {
                case Status.NORMAL:
                    startAnimSet((int) maxRadius, (int) mOriginRadius, 200, 0);
                    break;
                case Status.EXPAND:
                    startAnimSet((int) mOriginRadius, (int) maxRadius, 0, 200);
                    break;
            }
            mCurrentStatus = newStatus;
        }
    }

    public void switchStatus() {
        switch (mCurrentStatus) {
            case Status.NORMAL:
                startAnimSet((int) mOriginRadius, (int) maxRadius, 0, getHeight() + toHeight);
                mCurrentStatus = Status.EXPAND;
                break;
            case Status.EXPAND:
                startAnimSet((int) maxRadius, (int) mOriginRadius,getHeight() +  toHeight, 0);
                mCurrentStatus = Status.NORMAL;
                break;
        }
    }

    /**
     * 获取当前状态
     */
    public int getStatus() {
        return mCurrentStatus;
    }

    /**
     * 开启动画效果
     */
    private void startAnimSet(int fromRadius, int roRadius, int fromPadding, int toPadding) {
        isAnim = true;
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.playTogether(getRadiusAnim(fromRadius, roRadius), getShapeAnim(fromPadding, toPadding));
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if (ensureHandlerNotNull()) {
                    handler.sendEmptyMessage(mCurrentStatus);
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                isAnim = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.start();
    }

    /**
     * 颜色动画
     */
    @Deprecated
    private ValueAnimator getColorAnim(int fromColor, int toColor) {
        ValueAnimator colorAnim = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, toColor);
        colorAnim.setDuration(mDuration);
        colorAnim.addUpdateListener(valueAnimator -> {
            invalidate();
        });
        return colorAnim;
    }

    /**
     * 圆角动画
     */
    private ValueAnimator getRadiusAnim(int fromRadius, int toRadius) {
        ValueAnimator radiusAnim = ValueAnimator.ofFloat(fromRadius, toRadius);
        radiusAnim.setDuration(mDuration);
        radiusAnim.addUpdateListener(valueAnimator -> {
            mOriginRadius = (float) valueAnimator.getAnimatedValue();
            invalidate();
        });
        return radiusAnim;
    }

    /**
     * 形状动画
     */
    private ValueAnimator getShapeAnim(int fromPadding, int toPadding) {
        ValueAnimator shapeAnim = ValueAnimator.ofInt(fromPadding, toPadding);
        shapeAnim.setDuration(mDuration);
        shapeAnim.addUpdateListener(valueAnimator -> mCurrentPadding = (int) valueAnimator.getAnimatedValue());
        return shapeAnim;
    }

    public static class TipsItem {
        String field;
        float fieldY;

        public TipsItem(String field, float fieldY) {
            this.field = field;
            this.fieldY = fieldY;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public float getFieldY() {
            return fieldY;
        }

        public void setFieldY(float fieldY) {
            this.fieldY = fieldY;
        }
    }
}
