package com.ssmalllucky.android.ui.base;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.fragment.app.FragmentActivity;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.utils.DisplayUtils;

/**
 * @ClassName STPopupDialog
 * @Author shuaijialin
 * @Date 2024/1/24
 * @Description
 */
public class STPopupDialog extends PopupWindow {
    protected Context mContext;
    private float mShowAlpha = 0.68f;
    private Drawable mBackgroundDrawable;
    private boolean isOutsideTouchable;

    public STPopupDialog(Context context) {
        super(context);
        this.mContext = context;
        initBasePopupWindow();
    }

    @Override
    public void setOutsideTouchable(boolean touchable) {
        super.setOutsideTouchable(touchable);
        if (touchable) {
            if (mBackgroundDrawable == null) {
                mBackgroundDrawable = new ColorDrawable(0x00000000);
            }
            super.setBackgroundDrawable(mBackgroundDrawable);
        } else {
            super.setBackgroundDrawable(null);
        }
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        mBackgroundDrawable = background;
        setOutsideTouchable(isOutsideTouchable);
    }

    /**
     * 初始化BasePopupWindow的一些信息 *
     */
    private void initBasePopupWindow() {
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        int originWidth = (int) (DisplayUtils.getPhoneWidth((FragmentActivity) mContext) * 0.6);
        setWidth(originWidth);
        setOutsideTouchable(false);
        setFocusable(true);
    }

    protected View mContentView;

    @Override
    public void setContentView(View contentView) {
        if (contentView != null) {
            mContentView = contentView;
            super.setContentView(contentView);
            addKeyListener(contentView);
        }
    }

    private int minWidth;
    private int maxWidth;
    private int maxHeight;

    private int windowWidth;

    private int windowHeight;

    private void calculatePopupWindowSize() {
        mContentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        windowWidth = mContentView.getMeasuredWidth();
        windowHeight = mContentView.getMeasuredHeight();
        minWidth = DisplayUtils.getPhoneWidth(getContext()) / 2 + DisplayUtils.dip2px(mContext, 24f);
        maxWidth = DisplayUtils.getPhoneWidth((FragmentActivity) mContentView.getContext()) / 10 * 9;
        maxHeight = DisplayUtils.getPhoneHeight((FragmentActivity) mContentView.getContext()) / 4 * 3;
        if (windowHeight > maxHeight || windowWidth > maxWidth) {
            int widthSpec;
            int heightSpec = View.MeasureSpec.UNSPECIFIED;
            if (windowHeight > maxHeight) {
                windowHeight = maxHeight;
                setHeight(windowHeight);
                heightSpec = View.MeasureSpec.EXACTLY;
            }
            if (windowWidth > maxWidth) {
                windowWidth = maxWidth;
            } else {
                if (windowWidth < minWidth) {
                    windowWidth = minWidth;
                }
            }
            setWidth(windowWidth);
            widthSpec = View.MeasureSpec.EXACTLY;
            mContentView.measure(widthSpec, heightSpec);
        } else {
            mContentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        }
    }

    public FragmentActivity getContext() {
        return (FragmentActivity) mContext;
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        int[] windowPos = calculatePopupWindowPos(parent, mContentView);
        int xOff = DisplayUtils.dip2px(getContext(), 12f); // 可以自己调整偏移
        windowPos[0] -= xOff;
        super.showAtLocation(parent, gravity, windowPos[0], windowPos[1]);
        showAnimator().start();
    }

    /**
     * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
     * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
     *
     * @param anchorView  呼出window的view
     * @param contentView window的内容布局
     * @return window显示的左上角的xOff, yOff坐标
     */
    private int[] calculatePopupWindowPos(final View anchorView, final View contentView) {

        if (anchorView == null || contentView == null) {
            return new int[2];
        }

        final int[] windowPos = new int[2];
        final int[] anchorLoc = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        final int screenHeight = DisplayUtils.getPhoneHeight((FragmentActivity) anchorView.getContext());
        final int screenWidth = DisplayUtils.getPhoneWidth((FragmentActivity) anchorView.getContext());
        // 计算contentView的高宽
        calculatePopupWindowSize();

        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = screenHeight - anchorLoc[1] - anchorHeight < windowHeight;
        windowPos[0] = screenWidth - windowWidth;
        if (isNeedShowUp) {
            setAnimationStyle(R.style.PopupAnimation_ToTop);
            windowPos[1] = anchorLoc[1] - windowHeight;
        } else {
            setAnimationStyle(R.style.PopupAnimation_ToBottom);
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }
        return windowPos;
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        showAnimator().start();
    }

    private int adjustYoff(View anchor, int yoff) {
        if (anchor == null) {
            return 0;
        }
        int[] location = new int[2];
        anchor.getLocationInWindow(location);
        int displayHeight = DisplayUtils.getPhoneHeight((FragmentActivity) anchor.getContext());
        int anchorHeight = anchor.getHeight();
        int availableHeight = displayHeight - location[1] - anchorHeight;
        int minWidowHeight = getHeight();
        if (availableHeight < minWidowHeight && minWidowHeight != -1) {
            yoff -= minWidowHeight;
        }
        return yoff;
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
        showAnimator().start();
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        int adjustYoff = adjustYoff(anchor, yoff);
        super.showAsDropDown(anchor, xoff, adjustYoff, gravity);
        showAnimator().start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        dismissAnimator().start();
    }

    /**
     * 窗口显示，窗口背景透明度渐变动画 *
     */
    private ValueAnimator showAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(1.0f, mShowAlpha);
        animator.addUpdateListener(animation -> {
            float alpha = (float) animation.getAnimatedValue();
            setWindowBackgroundAlpha(alpha);
        });
        animator.setDuration(200);
        return animator;
    }

    /**
     * 窗口隐藏，窗口背景透明度渐变动画 *
     */
    private ValueAnimator dismissAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(mShowAlpha, 1.0f);
        animator.addUpdateListener(animation -> {
            float alpha = (float) animation.getAnimatedValue();
            setWindowBackgroundAlpha(alpha);
        });
        animator.setDuration(200);
        return animator;
    }

    /**
     * 为窗体添加outside点击事件 *
     */
    private void addKeyListener(View contentView) {
        if (contentView != null) {
            contentView.setFocusable(true);
            contentView.setFocusableInTouchMode(true);
            contentView.setOnKeyListener((view, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                    return true;
                }
                return false;
            });
        }
    }

    /**
     * 控制窗口背景的不透明度 *
     */
    private void setWindowBackgroundAlpha(float alpha) {
        Window window = ((Activity) getContext()).getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = alpha;
        window.setAttributes(layoutParams);
    }
}