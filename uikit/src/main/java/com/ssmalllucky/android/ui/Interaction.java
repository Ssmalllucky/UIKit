package com.ssmalllucky.android.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ssmalllucky.android.ui.loading.LoadingIndicatorView;
import com.ssmalllucky.android.ui.utils.DisplayUtils;
import com.ssmalllucky.android.ui.utils.ToastUtils;
import com.ssmalllucky.android.ui.utils.ToastyUtils;
import com.ssmalllucky.android.ui.view.STPrimaryTextView;
import com.ssmalllucky.android.ui.view.STSecondaryTextView;

import java.util.List;

/**
 * @ClassName Interaction
 * @Author shuaijialin
 * @Date 2023/4/12
 * @Description 为APP端提供视觉交互能力。
 */
public class Interaction {

    private static ProgressDialog mProgressDialog;

    public static final int TOAST_INFO = 0x01;
    public static final int TOAST_SUCCESS = 0x02;
    public static final int TOAST_WARN = 0x03;
    public static final int TOAST_ERROR = 0x04;

    private static final int LOADING_PROGRESS = 0x05;
    private static final int LOADING_DIALOG = 0x06;

    private static final int LOADING_METHOD;

    static {
        LOADING_METHOD = LOADING_DIALOG;
    }

    /**
     * 展示加载框
     *
     * @param context Activity界面引用。
     */
    public static void showLoading(Context context) {
        showLoading(context, null);
    }

    public static void showLoading(Context context, @Nullable String message) {
        showLoading(context, message, true, true);
    }

    public static void showLoading(Context context, @Nullable String message, boolean cancelable, boolean canceledOnTouchOutside) {
        // 首先检查宿主Activity是否存在
        if (context instanceof Activity && !((Activity) context).isFinishing() && !((Activity) context).isDestroyed()) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.cancel();
                mProgressDialog = null;
            }

            mProgressDialog = new ProgressDialog(context, R.style.new_circle_progress);
            mProgressDialog.setCancelable(cancelable);
            mProgressDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
            View loadingLayout = LayoutInflater.from(context).inflate(R.layout.dialog_normal_loading, null);
            LoadingIndicatorView avi = loadingLayout.findViewById(R.id.avi);
            avi.setIndicator("BallSpinFadeLoaderIndicator");

            try {
                mProgressDialog.show();
                mProgressDialog.setContentView(loadingLayout);
            } catch (Exception var5) {
                Exception e = var5;
                System.out.println("=============" + e);
                mProgressDialog.dismiss();
            }

            if (!TextUtils.isEmpty(message)) {
                TextView loadingMessageTV = loadingLayout.findViewById(R.id.loadingMessageTV);
                loadingMessageTV.setVisibility(View.VISIBLE);
                loadingMessageTV.setText(message);
            }

        }
    }

    public static void dismissLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
            mProgressDialog = null;
        }
    }

    private static ValueAnimator showAnimator;
    private static ValueAnimator exitAnimator;

    public static void showFloatingToast(FragmentActivity context, CardView view, String message, int level) {

        if (context == null || context.isFinishing() || context.isDestroyed()) {
            return;
        }

        if (showAnimator != null && showAnimator.isStarted()) {
            showAnimator.removeAllListeners();
            showAnimator.cancel();
            showAnimator = null;
        }

        if (exitAnimator != null && exitAnimator.isStarted()) {
            exitAnimator.removeAllListeners();
            exitAnimator.cancel();
            exitAnimator = null;
            onFloatingAnimEnd(view);
        }

        STSecondaryTextView floatTextView = (STSecondaryTextView) (view.getChildAt(0));

        view.setVisibility(View.VISIBLE);
        floatTextView.setText(message);
        floatTextView.setTextColor(DialogMaker.getLevelColor(context, level));
        view.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
        showAnimator = ValueAnimator.ofInt(-150, 100);
        showAnimator.setDuration(300);
        showAnimator.setInterpolator(new DecelerateInterpolator());
        showAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 动画执行完毕后，需要将动画对象置为空，否则会导致跟动画相关的view无法释放，
                // 造成内存泄漏。
                showAnimator = null;
                dismissFloat(view);
            }
        });
        showAnimator.addUpdateListener(animation -> view.setTranslationY((Integer) animation.getAnimatedValue()));
        showAnimator.start();
    }

    protected static void dismissFloat(CardView view) {

        if (exitAnimator != null && exitAnimator.isRunning()) {
            exitAnimator.removeAllListeners();
            exitAnimator.cancel();
            exitAnimator = null;
        }

        exitAnimator = ValueAnimator.ofInt(100, -150);
        exitAnimator.setDuration(300);
        exitAnimator.setInterpolator(new DecelerateInterpolator());
        exitAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 动画执行完毕后，需要将动画对象置为空，否则会导致跟动画相关的view无法释放，
                // 造成内存泄漏。
                exitAnimator = null;
                onFloatingAnimEnd(view);
            }
        });
        exitAnimator.addUpdateListener(animation -> view.setTranslationY((Integer) animation.getAnimatedValue()));
        exitAnimator.setStartDelay(1200);
        exitAnimator.start();
    }

    private static void onFloatingAnimEnd(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    public static void showAnyDialog(FragmentActivity context, int level, String title, View childView, String posText, DialogMaker.SimpleClickListener listener) {
        DialogMaker.makeAny(context, level, title, childView, posText, listener);
    }

    public static void showToast(@NonNull Context context, @NonNull int resId, int level) {
        switch (level) {
            case TOAST_INFO:
                showInfoToast(context, resId);
                break;
            case TOAST_SUCCESS:
                showSuccessToast(context, resId);
                break;
            case TOAST_WARN:
                showWarnToast(context, resId);
                break;
            case TOAST_ERROR:
                showErrorToast(context, resId);
                break;
        }
    }

    public static void showToast(@NonNull Context context, @NonNull String msg, int level) {
        switch (level) {
            case TOAST_INFO:
                showInfoToast(context, msg);
                break;
            case TOAST_SUCCESS:
                showSuccessToast(context, msg);
                break;
            case TOAST_WARN:
                showWarnToast(context, msg);
                break;
            case TOAST_ERROR:
                showErrorToast(context, msg);
                break;
        }
    }

    public static void showSuccessToast(@NonNull Context context, String text) {
        ToastUtils.showSuccess(context, text);
    }

    public static void showSuccessToast(@NonNull Context context, int textId) {
        ToastUtils.showSuccess(context, textId);
    }

    public static void showInfoToast(@NonNull Context context, String text) {
        ToastUtils.showInfo(context, text);
    }

    public static void showWarnToast(@NonNull Context context, String text) {
        ToastUtils.showWarning(context, text);
    }

    public static void showWarnToast(@NonNull Context context, int textId) {
        ToastUtils.showWarning(context, textId);
    }

    public static void showInfoToast(@NonNull Context context, int textId) {
        ToastUtils.showInfo(context, textId);
    }

    public static void showErrorToast(@NonNull Context context, String text) {
        ToastUtils.showError(context, text);
    }

    public static void showErrorToast(@NonNull Context context, int textId) {
        ToastUtils.showError(context, textId);
    }

    public static void showPosDialog(FragmentActivity context, int level, String title, String content, DialogMaker.SimpleClickListener listener) {
        showPosDialog(context, level, title, content, null, listener);
    }

    public static void showUpdateDialog(FragmentActivity context, String title, String version, SpannableStringBuilder contentBuilder, DialogMaker.SimpleClickListener listener) {
        showUpdateDialog(context, title, version, contentBuilder, null, listener);
    }

    public static void showUpdateDialog(FragmentActivity context, String title, String version, SpannableStringBuilder contentBuilder, String posText, DialogMaker.SimpleClickListener listener) {
        DialogMaker.makeUpdatePos(context, title, version, contentBuilder, posText, listener);
    }

    public static void showPosDialog(FragmentActivity context, int level, String title, String content, String posText, DialogMaker.SimpleClickListener listener) {
        DialogMaker.makePos(context, level, title, content, posText, listener);
    }

    public static void showPosDialog(FragmentActivity context, int level, String title, Spanned spannedContent, String posText, DialogMaker.SimpleClickListener listener) {
        DialogMaker.makePos(context, level, title, spannedContent, posText, listener);
    }

    public static void showOldNegPosDialog(FragmentActivity context, int level, String title, String content, String negText, String posText, DialogMaker.OnClickListener listener) {
        DialogMaker.make(context, level, title, content, negText, posText, listener);
    }

    public static void showOldNegPosDialogWithContext(@NonNull FragmentActivity context, int level, String title, String content, DialogMaker.OnClickListener listener) {
        showOldNegPosDialogWithContext(context, level, title, content, null, null, listener);
    }

    public static void showOldNegPosDialogWithContext(@NonNull FragmentActivity context, int level, String title, String content, String negText, String posText, DialogMaker.OnClickListener listener) {
        DialogMaker.make(context, level, title, content, negText, posText, listener);
    }

    public static void showOldNegPosDialog(FragmentActivity activity, int level, String title, String content, DialogMaker.OnClickListener listener) {
        showOldNegPosDialog(activity, level, title, content, null, null, listener);
    }

    public static void showNegPosDialog(FragmentActivity activity, int level, String title, String content, String negText, String posText, DialogMaker.OnClickListener listener) {
        DialogMaker.make(activity, level, title, content, negText, posText, listener);
    }

    public static void showNegPosNormalDialog(FragmentActivity activity, int level, String title, String content, String negText, String posText, DialogMaker.OnClickListener listener) {
        DialogMaker.makeNormalWidth(activity, level, title, content, negText, posText, listener);
    }

    public static void showNegPosDialog(FragmentActivity activity, int level, String title, String content, DialogMaker.OnClickListener listener) {
        showNegPosDialog(activity, level, title, content, null, null, listener);
    }

    public static void showWarnOptionsDialog(FragmentActivity activity, int level, String title, Spanned spannedContent, LinearLayout linearLayoutChild, DialogMaker.OnClickListener listener) {
        showWarnOptionsDialog(activity, level, title, spannedContent, linearLayoutChild, null, null, listener);
    }

    public static void showWarnOptionsDialog(FragmentActivity activity, int level, String title, Spanned spannedContent, LinearLayout linearLayoutChild, String negText, String posText, DialogMaker.OnClickListener listener) {
        DialogMaker.makeLinearLayoutChild(activity, title, spannedContent, linearLayoutChild, negText, posText, listener);
    }


    public static void showInputDialog(FragmentActivity activity, int level, boolean maxContentHeight, String title, String content, String hint, String negText, String posText, DialogMaker.OnInputClickListener listener) {
        DialogMaker.makeInput(activity, level, maxContentHeight, title, content, hint, negText, posText, listener);
    }

    public static void showOptionsDialog(FragmentActivity activity, List<DialogMaker.OptionsItem> list) {
        DialogMaker.makeOptionsDialog(activity, list);
    }

    public static void showAction3Dialog(FragmentActivity activity, String title, String content, @NonNull String action1Content, @NonNull String action2Content, @NonNull String action3Content, DialogMaker.OnAction3ClickListener listener) {
        DialogMaker.makeAction3(activity, title, content, action1Content, action2Content, action3Content, listener);
    }


    /**
     * 功能引导弹窗。
     * 一般用于首次进入某个功能界面。
     */
    public static void showFunctionGuidingDialog(@NonNull FragmentActivity activity, @NonNull String title, @NonNull RecyclerView recyclerView, @Nullable String posText, long delay, DialogMaker.OnGuidingDialogClickListener listener) {
        DialogMaker.makeFunctionGuidingDialog(activity, title, recyclerView, posText, delay, listener);
    }

    public static void showKnowledgeDialog(@NonNull FragmentActivity activity, @NonNull String title1, @NonNull String title2, @NonNull String title3, @NonNull RecyclerView recyclerView, @Nullable String posText, long delay, DialogMaker.OnGuidingDialogClickListener listener) {
        DialogMaker.makeKnowledgeDialog(activity, title1, title2, title3, recyclerView, posText, delay, listener);
    }

    public static void showBottomPosDialog(FragmentActivity context, int level, String title, String content, String posText, DialogMaker.SimpleClickListener listener) {
        DialogMaker.makeBottomPos(context, level, title, content, posText, listener);
    }

    public static void showBottomOptionsDialog(FragmentActivity activity, List<DialogMaker.OptionsItem> list) {
        DialogMaker.makeBottomOptions(activity, list);
    }

    public static int getLevelColor(Context context, int level) {
        switch (level) {
            case 1:
                return ToastyUtils.getColor(context, R.color.infoColor);
            case 2:
                return ToastyUtils.getColor(context, R.color.successColor);
            case 3:
                return ToastyUtils.getColor(context, R.color.warningColor);
            case 4:
                return ToastyUtils.getColor(context, R.color.errorColor);
            default:
                return 0;
        }
    }

    public static void showToastBar(FragmentActivity context, @Nullable final CardView view, String message, int level) {
        if (context != null && !context.isFinishing() && !context.isDestroyed()) {
            if (view == null) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            } else {
                if (showAnimator != null && showAnimator.isStarted()) {
                    showAnimator.removeAllListeners();
                    showAnimator.cancel();
                    showAnimator = null;
                }

                if (exitAnimator != null && exitAnimator.isStarted()) {
                    exitAnimator.removeAllListeners();
                    exitAnimator.cancel();
                    exitAnimator = null;
                    onFloatingAnimEnd(view);
                }

                TextView childTextView = (TextView) view.getChildAt(0);
                view.setVisibility(View.VISIBLE);
                childTextView.setText(message);
                view.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
                showAnimator = ValueAnimator.ofInt(-150, 100);
                showAnimator.setDuration(300L);
                showAnimator.setInterpolator(new DecelerateInterpolator());
                showAnimator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        Interaction.dismissFloat(view);
                        showAnimator = null;
                    }
                });
                showAnimator.addUpdateListener((animation) -> {
                    view.setTranslationY((float) (Integer) animation.getAnimatedValue());
                });
                showAnimator.start();
            }
        }
    }

    /**
     * 初始化顶部提示条
     *
     * @param context 不能为空
     * @return
     */
    public static CardView makeToastBar(@NonNull FragmentActivity context) {

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.removeAllViews();
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER);
        FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        frameParams.gravity = Gravity.CENTER;
        linearLayout.setLayoutParams(frameParams);
        int lPadding = context.getResources().getDimensionPixelSize(R.dimen.spacing_m);
        int rPadding = context.getResources().getDimensionPixelSize(R.dimen.spacing_l);
        int TBPadding = context.getResources().getDimensionPixelSize(R.dimen.spacing_s);
        linearLayout.setPadding(lPadding, TBPadding, rPadding, TBPadding);

        ImageView imageView = new ImageView(context);
        int toastBarImageViewSize = DisplayUtils.dip2px(context, 22f);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(toastBarImageViewSize, toastBarImageViewSize));
        linearLayout.addView(imageView);

        //设置弹窗文本
        STPrimaryTextView floatTextView = new STPrimaryTextView(context);
        floatTextView.setGravity(Gravity.CENTER);
        float density = DisplayUtils.getDensity(context);
        float textSize = context.getResources().getDimensionPixelSize(R.dimen.text_size_secondary_title) / density;
        floatTextView.setTextSize(textSize);
        FrameLayout.LayoutParams tvParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvParams.gravity = Gravity.CENTER;
        tvParams.setMarginStart(DisplayUtils.dip2px(context, 6f));
        floatTextView.setLayoutParams(tvParams);
        floatTextView.getPaint().setFakeBoldText(true);
        linearLayout.addView(floatTextView);

        //设置弹窗卡片
        CardView toastBarView = new CardView(context);
        toastBarView.addView(linearLayout);
        toastBarView.setCardElevation(context.getResources().getDimensionPixelSize(R.dimen.spacing_s));
        toastBarView.setMaxCardElevation(context.getResources().getDimensionPixelSize(R.dimen.spacing_s));


        int screenWidth = DisplayUtils.getPhoneWidth(context);
//        int floatingHeightBased = (int) (getResources().getDimensionPixelSize(R.dimen.view_floating_height));
        int floatingHeightBased = DisplayUtils.dip2px(context, 44f);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams((int) (screenWidth * 0.6), FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        int paddingLRValue = DisplayUtils.dip2px(context, 20f);
        toastBarView.setMinimumHeight(floatingHeightBased);
        toastBarView.setPadding(paddingLRValue, 0, paddingLRValue, 0);
        toastBarView.setRadius((float) floatingHeightBased / 2);
        toastBarView.setLayoutParams(params);
        toastBarView.setVisibility(View.INVISIBLE);
        return toastBarView;
    }
}
