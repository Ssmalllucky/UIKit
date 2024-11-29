package com.ssmalllucky.android.ui;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.ssmalllucky.android.ui.utils.DisplayUtils;
import com.ssmalllucky.android.ui.utils.ToastyUtils;
import com.ssmalllucky.android.ui.view.STActionView;

import java.util.List;

public class DialogMaker {

    public static final String DEFAULT_TITLE = "提示";
    public static final String DEFAULT_CANCEL_TEXT = "取消";
    public static final String DEFAULT_OK_TEXT = "确定";

    public static Dialog makePos(@NonNull FragmentActivity context, int level, String title, @NonNull String content, @Nullable String rightValue, @Nullable SimpleClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_simple_ok, null);

        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        final TextView titleTV = dialogView.findViewById(R.id.titleTV);
        final TextView contentTV = dialogView.findViewById(R.id.contentTV);
        final TextView okTV = dialogView.findViewById(R.id.okTV);

        int levelImage = getLevelImage(context, level);

        if (levelImage == 0) {
            throw new IllegalArgumentException("Invalid level image.");
        }

        titleTV.setText(TextUtils.isEmpty(title) ? DEFAULT_TITLE : title);
        if (TextUtils.isEmpty(content)) {
            throw new IllegalArgumentException("Dialog content can no be empty.");
        }

        contentTV.setText(content);
        okTV.setText(TextUtils.isEmpty(rightValue) ? DEFAULT_OK_TEXT : rightValue);

        okTV.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onClicked();
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_dialog_background));
        dialog.show();
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            lp.width = (int) (DisplayUtils.getPhoneWidth(context) * 0.4);
        } else {
            lp.width = (int) (DisplayUtils.getPhoneWidth(context) * 0.8);
        }
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.dimAmount = 0.5f;
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }

    public static Dialog makeUpdatePos(@NonNull FragmentActivity context, String title, String version, @NonNull SpannableStringBuilder contentBuilder, @Nullable String rightValue, @Nullable SimpleClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_update, null);

        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        final TextView titleTV = dialogView.findViewById(R.id.titleTV);
        final TextView dialogVersionTV = dialogView.findViewById(R.id.dialogVersionTV);
        final TextView contentTV = dialogView.findViewById(R.id.contentTV);
        final TextView okTV = dialogView.findViewById(R.id.okTV);

        titleTV.setText(TextUtils.isEmpty(title) ? DEFAULT_TITLE : title);
        if (TextUtils.isEmpty(contentBuilder)) {
            throw new IllegalArgumentException("Dialog content can no be empty.");
        }

        dialogVersionTV.setText("版本号：" + version);

        contentTV.setText(contentBuilder);
        okTV.setText(TextUtils.isEmpty(rightValue) ? "我知道了" : rightValue);

        okTV.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onClicked();
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_dialog_background));
        dialog.show();
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            lp.width = (int) (DisplayUtils.getPhoneWidth(context) * 0.4);
        } else {
            lp.width = (int) (DisplayUtils.getPhoneWidth(context) * 0.8);
        }
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.dimAmount = 0.5f;
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }

    public static Dialog makePos(@NonNull FragmentActivity context, int level, String title, @NonNull Spanned spannedContent, @Nullable String rightValue, @Nullable SimpleClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_simple_ok, null);

        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        final TextView titleTV = dialogView.findViewById(R.id.titleTV);
        final TextView contentTV = dialogView.findViewById(R.id.contentTV);
        final TextView okTV = dialogView.findViewById(R.id.okTV);

        int levelImage = getLevelImage(context, level);

        if (levelImage == 0) {
            throw new IllegalArgumentException("Invalid level image.");
        }

        titleTV.setText(TextUtils.isEmpty(title) ? DEFAULT_TITLE : title);
        if (TextUtils.isEmpty(spannedContent)) {
            throw new IllegalArgumentException("Dialog content can no be empty.");
        }

        contentTV.setText(spannedContent);
        okTV.setText(TextUtils.isEmpty(rightValue) ? DEFAULT_OK_TEXT : rightValue);

        okTV.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onClicked();
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_dialog_background));
        dialog.show();
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            lp.width = (int) (DisplayUtils.getPhoneWidth(context) * 0.4);
        } else {
            lp.width = (int) (DisplayUtils.getPhoneWidth(context) * 0.8);
        }
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.dimAmount = 0.5f;
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }

    public static Dialog makeEnsurePos(@NonNull FragmentActivity context, int level, String title, @NonNull String content, @Nullable String rightValue, @Nullable SimpleClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_simple_ok, null);

        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        final TextView titleTV = dialogView.findViewById(R.id.titleTV);
        final TextView contentTV = dialogView.findViewById(R.id.contentTV);
        final TextView okTV = dialogView.findViewById(R.id.okTV);

        int levelImage = getLevelImage(context, level);

        if (levelImage == 0) {
            throw new IllegalArgumentException("Invalid level image.");
        }

        titleTV.setText(TextUtils.isEmpty(title) ? DEFAULT_TITLE : title);
        if (TextUtils.isEmpty(content)) {
            throw new IllegalArgumentException("Dialog content can no be empty.");
        }

        contentTV.setText(content);
        okTV.setText(TextUtils.isEmpty(rightValue) ? DEFAULT_OK_TEXT : rightValue);

        okTV.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onClicked();
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_dialog_background));
        dialog.show();
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (DisplayUtils.getPhoneWidth(context) * 0.8);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.dimAmount = 0.5f;
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }

    private static int getLevelImage(Context context, int level) {
        switch (level) {
            case Interaction.TOAST_SUCCESS:
                return R.drawable.ic_dialog_success;
            case Interaction.TOAST_INFO:
                return R.drawable.ic_dialog_info;
            case Interaction.TOAST_WARN:
                return R.drawable.ic_dialog_warn;
            case Interaction.TOAST_ERROR:
                return R.drawable.ic_dialog_error;
        }
        return 0;
    }

    public static int getLevelColor(Context context, int level) {
        switch (level) {
            case Interaction.TOAST_SUCCESS:
                return ToastyUtils.getColor(context, R.color.successColor);
            case Interaction.TOAST_INFO:
                return ToastyUtils.getColor(context, R.color.infoColor);
            case Interaction.TOAST_WARN:
                return ToastyUtils.getColor(context, R.color.warningColor);
            case Interaction.TOAST_ERROR:
                return ToastyUtils.getColor(context, R.color.errorColor);
        }
        return 0;
    }

    public static Dialog make(@NonNull FragmentActivity context, int level, String title, @NonNull String content, @Nullable String leftValue, @Nullable String rightValue, @Nullable OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_common, null);

        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        final TextView titleTV = dialogView.findViewById(R.id.titleTV);
        final TextView contentTV = dialogView.findViewById(R.id.contentTV);
        final TextView cancelTV = dialogView.findViewById(R.id.cancelTV);
        final TextView okTV = dialogView.findViewById(R.id.okTV);

        int levelImage = getLevelImage(context, level);

        if (levelImage == 0) {
            throw new IllegalArgumentException("Invalid level image.");
        }

        titleTV.setText(TextUtils.isEmpty(title) ? DEFAULT_TITLE : title);

        if (TextUtils.isEmpty(content)) {
            throw new IllegalArgumentException("Dialog content can no be empty.");
        }

        contentTV.setText(content);
        cancelTV.setText(TextUtils.isEmpty(leftValue) ? DEFAULT_CANCEL_TEXT : leftValue);
        okTV.setText(TextUtils.isEmpty(rightValue) ? DEFAULT_OK_TEXT : rightValue);

        cancelTV.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onCancelClicked();
        });

        okTV.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onOKClicked();
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_dialog_background));
        dialog.show();
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (DisplayUtils.getPhoneWidth(context) * 0.8);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.dimAmount = 0.5f;
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }

    public static Dialog makeNormalWidth(@NonNull FragmentActivity context, int level, String title, @NonNull String content, @Nullable String leftValue, @Nullable String rightValue, @Nullable OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_common, null);

        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        final TextView titleTV = dialogView.findViewById(R.id.titleTV);
        final TextView contentTV = dialogView.findViewById(R.id.contentTV);
        final TextView cancelTV = dialogView.findViewById(R.id.cancelTV);
        final TextView okTV = dialogView.findViewById(R.id.okTV);

        int levelImage = getLevelImage(context, level);

        if (levelImage == 0) {
            throw new IllegalArgumentException("Invalid level image.");
        }

        titleTV.setText(TextUtils.isEmpty(title) ? DEFAULT_TITLE : title);

        if (TextUtils.isEmpty(content)) {
            throw new IllegalArgumentException("Dialog content can no be empty.");
        }

        contentTV.setText(content);
        cancelTV.setText(TextUtils.isEmpty(leftValue) ? DEFAULT_CANCEL_TEXT : leftValue);
        okTV.setText(TextUtils.isEmpty(rightValue) ? DEFAULT_OK_TEXT : rightValue);

        cancelTV.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onCancelClicked();
        });

        okTV.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onOKClicked();
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_dialog_background));
        dialog.show();
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (DisplayUtils.getPhoneWidth(context) * 0.5);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.dimAmount = 0.5f;
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }

    public static Dialog makeRecyclerViewChild(@NonNull FragmentActivity context, String title, RecyclerView recyclerView, @Nullable String leftValue, @Nullable String rightValue, @Nullable OnRecyclerViewItemClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_recyclerview_child, null);

        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        final TextView titleTV = dialogView.findViewById(R.id.titleTV);

        final FrameLayout recyclerViewContainer = dialogView.findViewById(R.id.recyclerViewContainer);
        ConstraintLayout.LayoutParams containerParams = (ConstraintLayout.LayoutParams) recyclerViewContainer.getLayoutParams();
        containerParams.height = (int) (DisplayUtils.getPhoneHeight(context) * 0.5);
        recyclerViewContainer.setLayoutParams(containerParams);

        final TextView cancelTV = dialogView.findViewById(R.id.cancelTV);
        final TextView okTV = dialogView.findViewById(R.id.okTV);
        final ImageView addGroupIV = dialogView.findViewById(R.id.addGroupIV);

        if (recyclerView == null) {
            throw new IllegalArgumentException("Can not building a recyclerview with null.");
        }

        recyclerViewContainer.removeAllViews();
        recyclerViewContainer.addView(recyclerView);

        titleTV.setText(TextUtils.isEmpty(title) ? DEFAULT_TITLE : title);

        cancelTV.setText(TextUtils.isEmpty(leftValue) ? DEFAULT_CANCEL_TEXT : leftValue);
        okTV.setText(TextUtils.isEmpty(rightValue) ? DEFAULT_OK_TEXT : rightValue);

        addGroupIV.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onAddClicked();
        });

        cancelTV.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onCancelClicked();
        });

        okTV.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onOKClicked();
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_dialog_background));
        dialog.show();
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (DisplayUtils.getPhoneWidth(context) * 0.5);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.dimAmount = 0.5f;
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }

    public static Dialog makeLinearLayoutChild(@NonNull FragmentActivity context, String title, Spanned spannedContent, LinearLayout linearLayout, @Nullable String leftValue, @Nullable String rightValue, @Nullable OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_linearlayout_child, null);

        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        final TextView titleTV = dialogView.findViewById(R.id.titleTV);
        final TextView contentTV = dialogView.findViewById(R.id.contentTV);

        contentTV.setText(spannedContent);

        final FrameLayout linearLayoutContainer = dialogView.findViewById(R.id.linearLayoutContainer);
        ConstraintLayout.LayoutParams containerParams = (ConstraintLayout.LayoutParams) linearLayoutContainer.getLayoutParams();
        containerParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        containerParams.matchConstraintMaxHeight = (int) (DisplayUtils.getPhoneHeight(context) * 0.5);
        linearLayoutContainer.setLayoutParams(containerParams);

        final TextView cancelTV = dialogView.findViewById(R.id.cancelTV);
        final TextView okTV = dialogView.findViewById(R.id.okTV);

        if (linearLayout == null) {
            throw new IllegalArgumentException("Can not building a linearLayout with null.");
        }

        linearLayoutContainer.removeAllViews();
        linearLayoutContainer.addView(linearLayout);

        titleTV.setText(TextUtils.isEmpty(title) ? DEFAULT_TITLE : title);

        cancelTV.setText(TextUtils.isEmpty(leftValue) ? DEFAULT_CANCEL_TEXT : leftValue);
        okTV.setText(TextUtils.isEmpty(rightValue) ? DEFAULT_OK_TEXT : rightValue);

        cancelTV.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onCancelClicked();
        });

        okTV.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onOKClicked();
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_dialog_background));
        dialog.show();
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (DisplayUtils.getPhoneWidth(context) * 0.8);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.dimAmount = 0.5f;
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }

    public static Dialog makeAction3(@NonNull FragmentActivity context, String title, @NonNull String content, @Nullable String action1Content, @Nullable String action2Content, @NonNull String action3Content, @Nullable OnAction3ClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_common_action3, null);

        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        final TextView titleTV = dialogView.findViewById(R.id.titleTV);
        final TextView contentTV = dialogView.findViewById(R.id.contentTV);
        final TextView action1TV = dialogView.findViewById(R.id.action1TV);
        final TextView action2TV = dialogView.findViewById(R.id.action2TV);
        final TextView action3TV = dialogView.findViewById(R.id.action3TV);

        titleTV.setText(TextUtils.isEmpty(title) ? DEFAULT_TITLE : title);

        if (TextUtils.isEmpty(content)) {
            throw new IllegalArgumentException("Dialog content can no be empty.");
        }

        contentTV.setText(content);
        action1TV.setText(TextUtils.isEmpty(action1Content) ? DEFAULT_CANCEL_TEXT : action1Content);
        action2TV.setText(TextUtils.isEmpty(action2Content) ? DEFAULT_OK_TEXT : action2Content);
        action3TV.setText(TextUtils.isEmpty(action3Content) ? DEFAULT_OK_TEXT : action3Content);


        action1TV.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onAction1Clicked();
        });

        action2TV.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onAction2Clicked();
        });

        action3TV.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onAction3Clicked();
        });

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_dialog_background));
        dialog.show();
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (DisplayUtils.getPhoneWidth(context) * 0.8);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.dimAmount = 0.5f;
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }

    public static Dialog makeInput(@NonNull FragmentActivity context, int level, boolean maxContentHeight, String title, @Nullable String content, @Nullable String hint, @Nullable String leftValue, @Nullable String rightValue, @Nullable OnInputClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edittext, null);

        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        final TextView titleTV = dialogView.findViewById(R.id.titleTV);
        final EditText contentET = dialogView.findViewById(R.id.contentET);
        final TextView cancelTV = dialogView.findViewById(R.id.cancelTV);
        final TextView okTV = dialogView.findViewById(R.id.okTV);

        contentET.setText(content);
        contentET.setHint(hint);

        if (maxContentHeight) {
            ViewGroup.LayoutParams params = contentET.getLayoutParams();
            params.height = (int) (DisplayUtils.getPhoneHeight(context) * 0.5);
            contentET.setLayoutParams(params);
            contentET.setMaxLines(Integer.MAX_VALUE);
            contentET.setGravity(Gravity.TOP);
        } else {
            contentET.setMaxLines(2);
        }

        int levelImage = getLevelImage(context, level);

        if (levelImage == 0) {
            throw new IllegalArgumentException("Invalid level image.");
        }

        titleTV.setText(TextUtils.isEmpty(title) ? DEFAULT_TITLE : title);

        cancelTV.setText(TextUtils.isEmpty(leftValue) ? DEFAULT_CANCEL_TEXT : leftValue);
        okTV.setText(TextUtils.isEmpty(rightValue) ? DEFAULT_OK_TEXT : rightValue);


        cancelTV.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onCancelClicked();
        });

        okTV.setOnClickListener(v -> {
            if (TextUtils.isEmpty(contentET.getText().toString().trim())) {
                contentET.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_dialog_edittext_background_error));
                showContentErrorTranslation(contentET);
            } else {
                dialog.dismiss();
                if (listener != null) listener.onOKClicked(contentET.getText().toString().trim());
            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_dialog_background));
        dialog.show();
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (DisplayUtils.getPhoneWidth(context) * 0.8);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.dimAmount = 0.5f;
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }

    @Deprecated
    public static Dialog makeContent(@NonNull FragmentActivity context, int level, boolean maxContentHeight, String title, @Nullable String content, @Nullable String leftValue, @Nullable String rightValue, @Nullable OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_textview, null);

        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        final TextView titleTV = dialogView.findViewById(R.id.titleTV);
        final EditText contentTV = dialogView.findViewById(R.id.contentTV);
        final TextView cancelTV = dialogView.findViewById(R.id.cancelTV);
        final TextView okTV = dialogView.findViewById(R.id.okTV);

        contentTV.setText(content);

        if (maxContentHeight) {
            ViewGroup.LayoutParams params = contentTV.getLayoutParams();
            params.height = (int) (DisplayUtils.getPhoneHeight(context) * 0.5);
            contentTV.setLayoutParams(params);
            contentTV.setMaxLines(Integer.MAX_VALUE);
            contentTV.setGravity(Gravity.TOP);
        } else {
            contentTV.setMaxLines(2);
        }

        int levelImage = getLevelImage(context, level);

        if (levelImage == 0) {
            throw new IllegalArgumentException("Invalid level image.");
        }

        titleTV.setText(TextUtils.isEmpty(title) ? DEFAULT_TITLE : title);

        cancelTV.setText(TextUtils.isEmpty(leftValue) ? DEFAULT_CANCEL_TEXT : leftValue);
        okTV.setText(TextUtils.isEmpty(rightValue) ? DEFAULT_OK_TEXT : rightValue);


        cancelTV.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onCancelClicked();
        });

        okTV.setOnClickListener(v -> {
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_dialog_background));
        dialog.show();
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (DisplayUtils.getPhoneWidth(context) * 0.8);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.dimAmount = 0.5f;
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }

    private static void showContentErrorTranslation(EditText editText) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, -50f, 0f, 50f, 0f, -50f, 0f, 50f, 0f);
        valueAnimator.addUpdateListener(animation -> editText.setTranslationX((Float) animation.getAnimatedValue()));
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }

    public static class OptionsItem {
        private String menuTitle;
        private View.OnClickListener listener;

        public OptionsItem(String menuTitle, View.OnClickListener listener) {
            this.menuTitle = menuTitle;
            this.listener = listener;
        }

        public String getMenuTitle() {
            return menuTitle;
        }

        public void setMenuTitle(String menuTitle) {
            this.menuTitle = menuTitle;
        }

        public View.OnClickListener getListener() {
            return listener;
        }

        public void setListener(View.OnClickListener listener) {
            this.listener = listener;
        }
    }

    public static Dialog makeOptionsDialog(@NonNull FragmentActivity context, @NonNull List<OptionsItem> items) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_options_menu, null);

        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        LinearLayout container = dialogView.findViewById(R.id.container);
        container.removeAllViews();

        for (int i = 0; i < items.size(); i++) {
            TextView menuItem = getOptionsMenuView(context);
            menuItem.setText(items.get(i).getMenuTitle());
            final int tempI = i;
            menuItem.setOnClickListener(v -> {
                dialog.dismiss();
                items.get(tempI).getListener().onClick(v);
            });
            container.addView(menuItem);
        }

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_dialog_background));
        dialog.show();
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (DisplayUtils.getPhoneWidth(context) * 0.8);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.dimAmount = 0.5f;
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }

    private static TextView getOptionsMenuView(Context context) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setBackground(ContextCompat.getDrawable(context, R.drawable.selector_layout_normal));
        textView.setTextColor(ContextCompat.getColor(context, R.color.text_color_primary));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DisplayUtils.dip2px(context, 50f));
        textView.setLayoutParams(params);
        return textView;
    }

    private static boolean requireNotNull(String value) {
        return value != null;
    }

    public static Dialog makeFunctionGuidingDialog(@NonNull FragmentActivity context, String title, RecyclerView recyclerView, @Nullable String posText, long delay, @Nullable OnGuidingDialogClickListener listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_simple_guiding, null);

        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        final ConstraintLayout rootView = dialogView.findViewById(R.id.rootView);
        final TextView titleTV = dialogView.findViewById(R.id.titleTV);
        final FrameLayout descContainer = dialogView.findViewById(R.id.descContainer);
        final TextView okTV = dialogView.findViewById(R.id.okTV);

        titleTV.setText(title);

        descContainer.addView(recyclerView);

        if (!TextUtils.isEmpty(posText)) {
            okTV.setText(posText);
        }

        okTV.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onPosClicked();
        });

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_dialog_guiding_background));

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            dialog.show();
            dialog.getWindow().getAttributes().gravity = Gravity.BOTTOM;
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.windowAnimations = R.style.GuidingDialog;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = (int) (DisplayUtils.getPhoneHeight(context) * 0.9);
            lp.dimAmount = 0.5f;
            dialog.getWindow().setAttributes(lp);

            ViewGroup.LayoutParams params = rootView.getLayoutParams();
            params.height = (int) (DisplayUtils.getPhoneHeight(context) * 0.9);
            rootView.setLayoutParams(params);
        }, delay < 0 ? 0 : delay);
        return dialog;
    }

    public static Dialog makeKnowledgeDialog(@NonNull FragmentActivity context, String title1, String title2, String title3, RecyclerView recyclerView, @Nullable String posText, long delay, @Nullable OnGuidingDialogClickListener listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_knowledge, null);

        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        final ConstraintLayout rootView = dialogView.findViewById(R.id.rootView);
        final TextView title1TV = dialogView.findViewById(R.id.title1TV);
        final TextView title2TV = dialogView.findViewById(R.id.title2TV);
        final TextView title3TV = dialogView.findViewById(R.id.title3TV);
        final FrameLayout descContainer = dialogView.findViewById(R.id.descContainer);
        final STActionView okTV = dialogView.findViewById(R.id.okTV);

        title1TV.setText(title1);
        title2TV.setText(title2);
        title3TV.setText(title3);

        descContainer.addView(recyclerView);

        okTV.setText(posText);

        okTV.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onPosClicked();
        });

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_dialog_guiding_background));

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            dialog.show();
            dialog.getWindow().getAttributes().gravity = Gravity.BOTTOM;
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.windowAnimations = R.style.GuidingDialog;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = (int) (DisplayUtils.getPhoneHeight(context) * 0.9);
            lp.dimAmount = 0.5f;
            dialog.getWindow().setAttributes(lp);

            ViewGroup.LayoutParams params = rootView.getLayoutParams();
            params.height = (int) (DisplayUtils.getPhoneHeight(context) * 0.9);
            rootView.setLayoutParams(params);
        }, delay < 0 ? 0 : delay);
        return dialog;
    }

    public static Dialog makeBottomPos(@NonNull FragmentActivity context, int level, String title, @NonNull String content, @Nullable String rightValue, @Nullable SimpleClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_bottom_simple_ok, null);

        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        final TextView contentTV = dialogView.findViewById(R.id.contentTV);
        final TextView okTV = dialogView.findViewById(R.id.okTV);

        int levelImage = getLevelImage(context, level);

        if (levelImage == 0) {
            throw new IllegalArgumentException("Invalid level image.");
        }

        if (TextUtils.isEmpty(content)) {
            throw new IllegalArgumentException("Dialog content can no be empty.");
        }

        contentTV.setText(content);
        okTV.setText(TextUtils.isEmpty(rightValue) ? DEFAULT_OK_TEXT : rightValue);

        okTV.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onClicked();
        });
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_dialog_background));
        dialog.show();
        dialog.getWindow().getAttributes().gravity = Gravity.BOTTOM;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.dimAmount = 0.5f;
        lp.windowAnimations = R.style.GuidingDialog;
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }

    public static Dialog makeBottomOptions(@NonNull FragmentActivity context, @NonNull List<OptionsItem> items) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_options_menu, null);

        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        LinearLayout container = dialogView.findViewById(R.id.container);
        container.removeAllViews();

        for (int i = 0; i < items.size(); i++) {
            TextView menuItem = getOptionsMenuView(context);
            menuItem.setText(items.get(i).getMenuTitle());
            final int tempI = i;
            menuItem.setOnClickListener(v -> {
                dialog.dismiss();
                items.get(tempI).getListener().onClick(v);
            });
            container.addView(menuItem);
        }

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_dialog_background));
        dialog.show();
        dialog.getWindow().getAttributes().gravity = Gravity.BOTTOM;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.dimAmount = 0.5f;
        lp.windowAnimations = R.style.GuidingDialog;
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }


    public interface SimpleClickListener {

        void onClicked();
    }

    public interface OnClickListener {

        void onCancelClicked();

        void onOKClicked();
    }

    public interface OnRecyclerViewItemClickListener {

        void onCancelClicked();

        void onOKClicked();

        void onAddClicked();
    }


    public interface OnAction3ClickListener {

        void onAction1Clicked();

        void onAction2Clicked();

        void onAction3Clicked();
    }

    public interface OnGuidingDialogClickListener {
        void onPosClicked();
    }


    public interface OnInputClickListener {
        void onCancelClicked();

        void onOKClicked(String content);
    }

    /********************** For new UI ***************************************/
    public static final String DEFAULT_ANY_TEXT = "关闭";

    public static Dialog makeAny(@NonNull FragmentActivity context, int level, String title, View childView, @Nullable String rightValue, @Nullable SimpleClickListener listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_any, null);

        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        final TextView titleTV = dialogView.findViewById(R.id.titleTV);
        final LinearLayout linearLayout = dialogView.findViewById(R.id.linearLayout);
        final TextView okTV = dialogView.findViewById(R.id.okTV);

        titleTV.setText(TextUtils.isEmpty(title) ? DEFAULT_TITLE : title);

        if (childView != null) {
            linearLayout.removeAllViews();
            linearLayout.addView(childView);
        }

        okTV.setText(TextUtils.isEmpty(rightValue) ? DEFAULT_ANY_TEXT : rightValue);

        okTV.setOnClickListener(v -> {
            dialog.dismiss();
            if (listener != null) listener.onClicked();
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_dialog_background));
        dialog.show();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (DisplayUtils.getPhoneWidth(context) * 0.8);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        lp.windowAnimations = R.style.DialogAnim;
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }

    public static Dialog makeEmpty(@NonNull FragmentActivity context,String title, View childView, @Nullable SimpleClickListener listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_empty, null);

        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        final FrameLayout frameLayout = dialogView.findViewById(R.id.frameLayout);
        final TextView textView = dialogView.findViewById(R.id.titleTV);

        textView.setText(title);

        if (childView != null) {
            frameLayout.removeAllViews();
            frameLayout.addView(childView);
        }

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_dialog_background));
        dialog.show();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        lp.windowAnimations = R.style.DialogAnim;
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }
}
