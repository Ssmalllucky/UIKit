package com.ssmalllucky.android.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.view.STEditText2;
import com.ssmalllucky.android.ui.view.STPrimaryTextView;

/**
 * 带图标、标题、可输入内容的文本项组件
 *
 * @author shuaijialin
 */
public class STEditTextItem2 extends STItem2 {

    private String content;
    private String hint;
    private STEditText2 contentEditText;

    private STPrimaryTextView unitTextView;

    private int inputType;

    private String unit;

    private String digits;

    public STEditTextItem2(@NonNull Context context) {
        super(context);
    }

    public STEditTextItem2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public STEditTextItem2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        super.init(context, attrs);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.STEditTextItem2);
            content = a.getString(R.styleable.STEditTextItem2_STEditTextItem2_content);
            hint = a.getString(R.styleable.STEditTextItem2_STEditTextItem2_hint);
            inputType = a.getInteger(R.styleable.STEditTextItem2_STEditTextItem2_inputType, InputType.TYPE_CLASS_TEXT);
            unit = a.getString(R.styleable.STEditTextItem2_STEditTextItem2_unit);
            digits = a.getString(R.styleable.STEditTextItem2_STEditTextItem2_digits);
        }

        //Add item content.
        contentEditText = new STEditText2(context);
        contentEditText.setText(content);
        contentEditText.setHint(hint);
        contentEditText.setInputType(inputType);
        if(!TextUtils.isEmpty(digits)){
            contentEditText.setKeyListener(DigitsKeyListener.getInstance(digits));
        }
        LayoutParams params;

        if (!TextUtils.isEmpty(unit) || !isLayout) {
            params = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, spacingXS, 0, spacingXS);
            params.gravity = Gravity.CENTER_VERTICAL;
            params.weight = 1;
        } else {
            contentEditText.setGravity(Gravity.END);
            params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_VERTICAL;
        }

        contentEditText.setLayoutParams(params);
        contentEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.text_size_secondary_title));
        contentContainer.addView(contentEditText);

        if (!TextUtils.isEmpty(unit)) {
            unitTextView = new STPrimaryTextView(context);
            unitTextView.setText(unit);
            LayoutParams unitParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            unitParams.setMarginStart(spacingS);
            unitTextView.setLayoutParams(unitParams);
            contentContainer.addView(unitTextView);
        }

        if (isLayout) {
            setBackground(ContextCompat.getDrawable(context, R.drawable.shape_settings_edittext_item_layout));
        } else {
            setStyle();
        }
    }

    public void showSoftInput(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.postDelayed(() -> {
            InputMethodManager manager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.showSoftInput(editText, 0);
        }, 100);
    }

    public EditText getEditText() {
        return contentEditText;
    }

    @Override
    protected void setStyle() {
        int drawableId;
        if (isLayout) {
            drawableId = R.drawable.shape_settings_edittext_item_layout;
        } else {
            if (contentEditText != null)
                contentEditText.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_edittext_background2));

            switch (layoutPosition) {
                case POSITION_TOP:
                    drawableId = R.drawable.shape_settings_edittext_item_top;
                    break;
                case POSITION_BOTTOM:
                    drawableId = R.drawable.shape_settings_edittext_item_bottom;
                    break;
                case POSITION_MIDDLE:
                default:
                    drawableId = R.drawable.shape_settings_edittext_item;
                    break;
            }
        }
        setBackground(ContextCompat.getDrawable(mContext, drawableId));
    }

    public void setHint(String hint) {
        if (contentEditText != null) contentEditText.setHint(hint);
    }

    public void setContent(String content) {
        if (contentEditText != null) contentEditText.setText(content);
    }

    public String getHint() {
        return contentEditText != null ? contentEditText.getHint().toString().trim() : "";
    }

    public String getContent() {
        return contentEditText != null ? contentEditText.getText().toString().trim() : "";
    }
}
