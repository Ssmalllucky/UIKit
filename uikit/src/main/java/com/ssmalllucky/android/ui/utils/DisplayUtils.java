package com.ssmalllucky.android.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import androidx.fragment.app.FragmentActivity;

public class DisplayUtils {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 35.     * 将px值转换为sp值，保证文字大小不变
     * 36.     *
     * 37.     * @param pxValue
     * 38.     * @param fontScale
     * 39.     *            （DisplayMetrics类中属性scaledDensity）
     * 40.     * @return
     * 41.
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 48.     * 将sp值转换为px值，保证文字大小不变
     * 49.     *
     * 50.     * @param spValue
     * 51.     * @param fontScale
     * 52.     *            （DisplayMetrics类中属性scaledDensity）
     * 53.     * @return
     * 54.
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int getPhoneWidth(FragmentActivity activity) {

        if (activity == null || activity.isDestroyed()) {
            return 0;
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getPhoneHeight(FragmentActivity activity) {

        if (activity == null || activity.isDestroyed()) {
            return 0;
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }


    private static boolean checkContext(Context context) {
        return context != null;
    }

    /**
     * @param context
     * @return
     */
    public static float getDensity(Context context) {
        if (!checkContext(context)) {
            return 0;
        }
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.density;
    }
}
