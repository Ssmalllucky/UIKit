package com.ssmalllucky.android.ui;

import android.graphics.Color;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;


public class XPopup {
    private XPopup() {
    }

    /**
     * 全局弹窗的设置
     **/
    private static int primaryColor = Color.parseColor("#121212");
    private static int animationDuration = 300;
    private static int statusBarBgColor = Color.parseColor("#55000000");
    private static int navigationBarColor = 0;
    private static int shadowBgColor = Color.parseColor("#7F000000");
    public static int isLightStatusBar = 0; //大于0为true，小于0为false
    public static int isLightNavigationBar = 0; //大于0为true，小于0为false

    /**
     * 设置全局的背景阴影颜色
     *
     * @param color
     */
    public static void setShadowBgColor(int color) {
        shadowBgColor = color;
    }

    public static int getShadowBgColor() {
        return shadowBgColor;
    }

    /**
     * 设置全局的状态栏背景颜色
     *
     * @param color
     */
    public static void setStatusBarBgColor(int color) {
        statusBarBgColor = color;
    }

    public static int getStatusBarBgColor() {
        return statusBarBgColor;
    }

    /**
     * 设置全局的导航栏栏背景颜色
     *
     * @param color
     */
    public static void setNavigationBarColor(int color) {
        navigationBarColor = color;
    }

    public static int getNavigationBarColor() {
        return navigationBarColor;
    }

    /**
     * 设置主色调
     *
     * @param color
     */
    public static void setPrimaryColor(int color) {
        primaryColor = color;
    }

    public static int getPrimaryColor() {
        return primaryColor;
    }

    /**
     * 统一设置是否是亮色状态栏
     *
     * @param isLight
     */
    public static void setIsLightStatusBar(boolean isLight) {
        isLightStatusBar = isLight ? 1 : -1;
    }

    /**
     * 统一设置是否是亮色导航栏
     *
     * @param isLight
     */
    public static void setIsLightNavigationBar(boolean isLight) {
        isLightNavigationBar = isLight ? 1 : -1;
    }

    /**
     * 设置全局动画时长
     *
     * @param duration
     */
    public static void setAnimationDuration(int duration) {
        if (duration >= 0) {
            animationDuration = duration;
        }
    }

    public static int getAnimationDuration() {
        return animationDuration;
    }

    /**
     * 在长按弹出弹窗后，能保证下层View不能滑动
     *
     * @param v
     */
    public static PointF longClickPoint = null;

    public static void fixLongClick(View v) {
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    longClickPoint = new PointF(event.getRawX(), event.getRawY());
                }
                if ("xpopup".equals(v.getTag()) && event.getAction() == MotionEvent.ACTION_MOVE) {
                    //长按发送，阻断父View拦截
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //长按结束，恢复阻断
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                    v.setTag(null);
                }
                return false;
            }
        });
        v.setTag("xpopup");
    }
}
