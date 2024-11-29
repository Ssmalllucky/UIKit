package com.ssmalllucky.android.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.utils.DisplayUtils;
import com.ssmalllucky.android.ui.view.STEditText;
import com.ssmalllucky.android.ui.view.STSecondaryTextView;

/**
 * 设置子项组件
 *
 * @author shuaijialin
 */
public class TitleInputItem extends LinearLayout {

    private Context context;
    private String title;

    private String inputText;

    private String inputHint;

    private boolean isTop;
    private boolean isBottom;
    private STSecondaryTextView textView;
    private STEditText inputEditText;

    private int inputType;

    public TitleInputItem(@NonNull Context context) {
        super(context);
    }

    public TitleInputItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TitleInputItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void setStyle() {
        if (isTop) {
            setBackground(ContextCompat.getDrawable(context, R.drawable.shape_title_input_top));
        } else if (isBottom) {
            setBackground(ContextCompat.getDrawable(context, R.drawable.shape_title_input_bottom));
        } else {
            setBackground(ContextCompat.getDrawable(context, R.drawable.shape_title_input));
        }

        inputEditText.setBackground(ContextCompat.getDrawable(context,R.color.transparent));
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        removeAllViews();

        setOrientation(HORIZONTAL);

        int padding = DisplayUtils.dip2px(context, 15f);
        setPadding(padding, 0, padding, 0);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleInputItem);
        title = a.getString(R.styleable.TitleInputItem_TitleInputItem_title);
        inputText = a.getString(R.styleable.TitleInputItem_TitleInputItem_inputText);
        inputHint = a.getString(R.styleable.TitleInputItem_TitleInputItem_inputHint);
        isTop = a.getBoolean(R.styleable.TitleInputItem_TitleInputItem_isTop, false);
        isBottom = a.getBoolean(R.styleable.TitleInputItem_TitleInputItem_isBottom, false);
        inputType = a.getInt(R.styleable.TitleInputItem_android_inputType, 0);
        textView = new STSecondaryTextView(context);
        textView.setText(title);
        textView.setTextColor(ContextCompat.getColor(context, R.color.text_color_secondary_title));
        addView(textView);

        inputEditText = new STEditText(context);
        inputEditText.setHint(inputHint);
        inputEditText.setText(inputText);
        inputEditText.setInputType(inputType);
        addView(inputEditText);

        setStyle();
        layoutItem();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setTitle(String title) {
        this.title = title;
        if (textView != null) textView.setText(title);
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
        if (inputEditText != null) inputEditText.setText(inputText);
    }

    public void setInputHint(String inputHint) {
        this.inputHint = inputHint;
        if (inputEditText != null) inputEditText.setHint(inputHint);
    }

    public String getTitle() {
        return textView != null ? textView.getText().toString().trim() : title;
    }

    public String getInputText() {
        return inputEditText != null ? inputEditText.getText().toString().trim() : inputText;
    }

    public String getInputHint() {
        return inputHint;
    }

    private void layoutItem() {
        LayoutParams textParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textParams.gravity = Gravity.CENTER_VERTICAL;
        textParams.weight = 1;
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setLayoutParams(textParams);

        textParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textParams.gravity = Gravity.CENTER_VERTICAL;
        textParams.weight = 3;
        inputEditText.setGravity(Gravity.CENTER_VERTICAL);
        inputEditText.setLayoutParams(textParams);
    }
}
