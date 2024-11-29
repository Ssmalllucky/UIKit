package com.ssmalllucky.android.ui.utils;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ssmalllucky.android.ui.Toasty;

/**
 * Created by Administrator on 2017/3/28.
 */

public class ToastUtils {

    public static final String TAG = "ToastUtils";

    private static Toast toast;

    private static View view;

    private static ToastUtils INSTANCE = null;

    public static ToastUtils get() {
        if (INSTANCE == null) {
            INSTANCE = new ToastUtils();
        }
        return INSTANCE;
    }

    private ToastUtils() {
    }

    private static void getToast(Context context) {
        if (toast == null) {
            toast = new Toast(context);
        }
        if (view == null) {
            view = Toast.makeText(context, "", Toast.LENGTH_SHORT).getView();
        }
        toast.setView(view);
    }

    public static void showMessage(Context context, String msg) {
        showInfo(context, msg);
    }

    public static void showMessage(Context context, int resId) {
        showInfo(context, context.getString(resId));
    }

    public static void showLongToast(Context context, String msg) {
        showInfo(context, msg);
    }

    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

    public static void show(Context context, String text) {

    }

    public static void show(Context context, int resId) {
    }

    public static void showError(@NonNull Context context, String text) {
        Toasty.error(context, text).show();
    }

    public static void showError(@NonNull Context context, int messageResId) {
        Toasty.error(context, messageResId).show();
    }

    public static void showSuccess(@NonNull Context context, String text) {
        Toasty.success(context, text).show();
    }

    public static void showSuccess(@NonNull Context context, int messageResId) {
        Toasty.success(context, messageResId).show();
    }

    public static void showInfo(@NonNull Context context, String text) {
        Toasty.info(context, text).show();
    }

    public static void showInfo(@NonNull Context context, int messageResId) {
        Toasty.info(context, messageResId).show();
    }

    public static void showWarning(@NonNull Context context, String text) {
        Toasty.warning(context, text).show();
    }

    public static void showWarning(@NonNull Context context, int messageResId) {
        Toasty.warning(context, messageResId).show();
    }

    public static void showLong(Context context, String text) {
        Toast toast = Toasty.info(context, text);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void showLong(Context context, int resId) {
        Toast toast = Toasty.info(context, resId);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
}
