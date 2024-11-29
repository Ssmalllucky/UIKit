package com.ssmalllucky.android.ui.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;

public class ScaledConstraintLayout extends ConstraintLayout {

    public static final String TAG = "ScaledLayout";
    private Context context;

    /**
     * 动画持续时长
     */
    private static final int DURATION = 100;
    /**
     * 快速点击的时间间隔
     */
    private static final int TIME_DELAY = 500;
    /**
     * 滑动最小距离
     */
    private static final int MIN_MOVE_DPI = 10;
    /**
     * 缩放的scale
     */
    private static final float SMALL_SCALE = 0.95f;
    /**
     * 初始scale
     */
    private static final float ONE_SCALE = 1f;
    /**
     * 判断缩小动画有没有执行过
     */
    private boolean hasDoneAnimation;
    /**
     * 点击事件监听
     */
    private OnClickListener listener;
    /**
     * 开始动画
     */
    private AnimatorSet beginAnimatorSet;
    /**
     * scale返回动画
     */
    private AnimatorSet backAnimatorSet;
    private int downX;
    private int downY;
    private long lastClickTime;

    private boolean scaleAnimEnabled = true;

    public ScaledConstraintLayout(@NonNull Context context) {
        super(context);
    }

    public ScaledConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAnimation();
    }

    public ScaledConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAnimation();
    }

    /**
     * 手指按下时的背景
     */
    private int touchDownBackgroundRes;

    /**
     * 手指移动时的背景
     */
    private int touchMoveBackgroundRes;

    /**
     * 手指抬起时的背景
     */
    private int touchUpBackgroundRes;

    public void setScaleAnimEnabled(boolean scaleAnimEnabled) {
        this.scaleAnimEnabled = scaleAnimEnabled;
    }

    public void setTouchDownBackground(int touchBackgroundRes) {
        this.touchDownBackgroundRes = touchBackgroundRes;
    }

    public void setTouchMoveBackground(int touchMoveBackgroundRes) {
        this.touchMoveBackgroundRes = touchMoveBackgroundRes;
    }

    public void setTouchUpBackground(int touchUpBackgroundRes) {
        this.touchUpBackgroundRes = touchUpBackgroundRes;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //是否快速点击，是则拦截事件
                if (isFastClick()) {
                    return true;
                }

                if(!scaleAnimEnabled){
                    return false;
                }

                //请求父类不要拦截子类事件
                getParent().requestDisallowInterceptTouchEvent(true);
                downX = (int) event.getX();
                downY = (int) event.getY();
                hasDoneAnimation = false;
                post(() -> {
                    setBackground(ContextCompat.getDrawable(getContext(), touchDownBackgroundRes));
                    beginAnimatorSet.start();
                });
                break;
            case MotionEvent.ACTION_MOVE:

                if(!scaleAnimEnabled){
                    return false;
                }

                int moveX = (int) event.getX();
                int moveY = (int) event.getY();
                int moveDistanceX = Math.abs(moveX) - downX;
                int moveDistanceY = Math.abs(moveY) - downY;

                //这里是判断是向左还是向右滑动,然后用view的宽度计算出一个距离compareWidth,当滑动距离超出compareWidth时,需要执行返回动画.
                int compareWidth = moveDistanceX > 0 ? getWidth() - downX : downX;

                //缩放效果的执行条件：
                //第一个条件:判断向上或向下滑动距离大于滑动最小距离
                //第二个条件:判断向左或向右的滑动距离是否超出(compareWidth-最小距离)
                //第三个条件:判断有没有执行过返回动画并在执行过一次后置为true.
                if ((Math.abs(moveDistanceY) > dip2px(MIN_MOVE_DPI) || Math.abs(moveDistanceX) >= compareWidth - dip2px(MIN_MOVE_DPI)) && !hasDoneAnimation) {
                    //只要满足上述条件,就代表用户不是点击view,而是执行了滑动操作,这个时候我们就需要父类以及我们的最上层的控件来
                    //拦截我们的事件,让最外层控件处理接下来的事件,比如scrollview的滑动.
                    //且因为我们执行了滑动操作,所以要执行view的返回动画
                    setBackground(ContextCompat.getDrawable(context, touchMoveBackgroundRes));
                    getParent().requestDisallowInterceptTouchEvent(false);
                    hasDoneAnimation = true;
                    post(() -> backAnimatorSet.start());
                }
                break;
            case MotionEvent.ACTION_UP:

                if(!scaleAnimEnabled){
                    return false;
                }

                if (!hasDoneAnimation) {
                    hasDoneAnimation = true;
                    post(() -> {
                        setBackground(ContextCompat.getDrawable(getContext(), touchUpBackgroundRes));
                        backAnimatorSet.start();
                    });
                    post(() -> {
                        if (listener != null) {
                            listener.onClick(ScaledConstraintLayout.this);
                        }
                    });
                }
                break;
        }
        return true;
    }

    /**
     * 初始化，并为视图添加X方向和Y方向的缩放动画属性。
     */
    private void initAnimation() {
        ObjectAnimator beginXAnimation = ObjectAnimator.ofFloat(this, "scaleX", ONE_SCALE, SMALL_SCALE).setDuration(DURATION);
        beginXAnimation.setInterpolator(new LinearInterpolator());
        ObjectAnimator beginYAnimation = ObjectAnimator.ofFloat(this, "scaleY", ONE_SCALE, SMALL_SCALE).setDuration(DURATION);
        beginYAnimation.setInterpolator(new LinearInterpolator());
        ObjectAnimator backXAnimation = ObjectAnimator.ofFloat(this, "scaleX", SMALL_SCALE, ONE_SCALE).setDuration(DURATION);
        backXAnimation.setInterpolator(new LinearInterpolator());
        ObjectAnimator backYAnimation = ObjectAnimator.ofFloat(this, "scaleY", SMALL_SCALE, ONE_SCALE).setDuration(DURATION);
        backYAnimation.setInterpolator(new LinearInterpolator());
        beginAnimatorSet = new AnimatorSet();
        beginAnimatorSet.play(beginXAnimation).with(beginYAnimation);
        backAnimatorSet = new AnimatorSet();
        backAnimatorSet.play(backXAnimation).with(backYAnimation);
    }

    /**
     * 监听事件设置
     * @param l
     */
    public void setOnClickListener(OnClickListener l) {
        listener = l;
    }

    public interface OnClickListener {
        void onClick(View v);
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
}
