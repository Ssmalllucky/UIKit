package com.ssmalllucky.android.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.utils.DisplayUtils;
import com.ssmalllucky.android.ui.view.STPrimaryTextView;
import com.ssmalllucky.android.ui.view.STRadioButton;

import java.util.ArrayList;

/**
 * 带图标、下拉列表的组件
 *
 * @author shuaijialin
 */
public class UIIconTextRadioGroupItem extends ConstraintLayout {

    private Context context;
    private boolean isRequired;
    private boolean isTop;
    private boolean isBottom;
    private boolean isLayout;
    private boolean hasNext;
    private boolean showGroup;
    private int checkIndex;
    private ImageView requiredImageView;
    private ImageView iconImageView;
    private STPrimaryTextView titleTextView;
    private RadioGroup radioGroup;
    private ImageView arrowImageView;
    private int iconResId;
    private String title;

    private CharSequence[] entries;

    private ArrayList<String> arrayList;

    /**
     * Spinner数据
     */
    private ArrayAdapter<String> arrayAdapter;

    private AttributeSet attrs;

    public UIIconTextRadioGroupItem(@NonNull Context context) {
        super(context);
    }

    public UIIconTextRadioGroupItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public UIIconTextRadioGroupItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void setStyle() {
        if (isTop) {
            setBackground(ContextCompat.getDrawable(context, R.drawable.selector_layout_settings_item_top));
        } else if (isBottom) {
            setBackground(ContextCompat.getDrawable(context, R.drawable.selector_layout_settings_item_bottom));
        } else if (isLayout) {
            setBackground(ContextCompat.getDrawable(context, R.drawable.selector_layout_settings_item_layout));
        } else {
            setBackground(ContextCompat.getDrawable(context, R.drawable.selector_layout_settings));
        }
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        this.attrs = attrs;
        removeAllViews();

        ViewGroup.LayoutParams rootParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(rootParams);

        int padding = DisplayUtils.dip2px(context, 12f);
        setPadding(padding, 0, padding, 0);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.UIIconTextRadioGroupItem);
        title = a.getString(R.styleable.UIIconTextRadioGroupItem_UIIconTextRadioGroupItem_title);
        isRequired = a.getBoolean(R.styleable.UIIconTextRadioGroupItem_UIIconTextRadioGroupItem_isRequired, false);
        isTop = a.getBoolean(R.styleable.UIIconTextRadioGroupItem_UIIconTextRadioGroupItem_isTop, false);
        isBottom = a.getBoolean(R.styleable.UIIconTextRadioGroupItem_UIIconTextRadioGroupItem_isBottom, false);
        isLayout = a.getBoolean(R.styleable.UIIconTextRadioGroupItem_UIIconTextRadioGroupItem_isLayout, false);
        hasNext = a.getBoolean(R.styleable.UIIconTextRadioGroupItem_UIIconTextRadioGroupItem_hasNext, true);
        showGroup = a.getBoolean(R.styleable.UIIconTextRadioGroupItem_UIIconTextSpinnerItem_showGroup, true);
        iconResId = a.getResourceId(R.styleable.UIIconTextRadioGroupItem_UIIconTextRadioGroupItem_icon, 0);
        checkIndex = a.getInteger(R.styleable.UIIconTextRadioGroupItem_UIIconTextRadioGroupItem_checkIndex, 0);
        entries = a.getTextArray(R.styleable.UIIconTextRadioGroupItem_UIIconTextRadioGroupItem_entries);

        //1. Make icon layout.
        LayoutParams layoutParams;

        int round = DisplayUtils.dip2px(context, 22f);

        //0. Add requiredTV if true.
        if (isRequired) {
            requiredImageView = new ImageView(context);
            requiredImageView.setId(R.id.iconTextRadioGroupItemRequired);
            requiredImageView.setImageResource(R.drawable.ic_required);
            addView(requiredImageView);
        }

        //1. Add icon.
        if (iconResId != 0) {
            iconImageView = new ImageView(context);
            iconImageView.setId(R.id.iconTextRadioGroupItemIcon);
            iconImageView.setImageResource(iconResId);

            layoutParams = new LayoutParams(round, round);
            iconImageView.setLayoutParams(layoutParams);
            addView(iconImageView);
        }

        //2. Add item title.
        titleTextView = new STPrimaryTextView(context);
        titleTextView.setId(R.id.iconTextRadioGroupItemTitle);
        titleTextView.setText(title);
        titleTextView.setTextColor(ContextCompat.getColor(context, R.color.text_color_primary));
        int titleWidth = DisplayUtils.dip2px(context, 100f);
        layoutParams = new LayoutParams(titleWidth, LayoutParams.WRAP_CONTENT);
        layoutParams.setMarginStart(DisplayUtils.dip2px(context, 10f));
        titleTextView.setLayoutParams(layoutParams);
        addView(titleTextView);

        //3. Add Spinner.
        if (showGroup) {
            radioGroup = new RadioGroup(context);
            radioGroup.setOrientation(LinearLayout.HORIZONTAL);
            radioGroup.setId(R.id.iconTextRadioGroupItemRadioGroup);
            addView(radioGroup);

            if (entries != null) {
                for (CharSequence entry : entries) {
                    addRadioButton(String.valueOf(entry));
                }
            }

            //3. Make item radioGroup layout.
            layoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT);
            layoutParams.horizontalWeight = 1;
            radioGroup.setLayoutParams(layoutParams);
        }

        //3. Add item arrow.
        arrowImageView = new ImageView(context);
        arrowImageView.setId(R.id.iconTextRadioGroupItemArrow);
        arrowImageView.setImageResource(R.drawable.ic_arrow_right);
        addView(arrowImageView);
        if (!hasNext) {
            arrowImageView.setVisibility(View.GONE);
        }

        //4. Make arrow icon layout.
        if (arrowImageView != null) {
            int imageViewSize = DisplayUtils.dip2px(context, 20f);
            layoutParams = new LayoutParams(imageViewSize, imageViewSize);
            arrowImageView.setLayoutParams(layoutParams);
        }
        setStyle();
        layoutItem();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 添加RadioButton
     *
     * @param content
     */
    public void addRadioButton(String content) {
        STRadioButton radioButton = new STRadioButton(context);
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        radioButton.setText(content);
        radioButton.setLayoutParams(params);
        if (radioGroup != null) {
            radioGroup.addView(radioButton);
        }
    }

    private void layoutItem() {
        ConstraintSet set;

        //0. Add required constraintSet.
        if (isRequired) {
            set = new ConstraintSet();
            set.clone(this);
            set.centerVertically(R.id.iconTextRadioGroupItemRequired, ConstraintSet.PARENT_ID);
            set.connect(R.id.iconTextRadioGroupItemRequired, ConstraintSet.START, getId(), ConstraintSet.START);
            set.applyTo(this);
        }

        //1. Add icon constraintSet.
        if (iconImageView != null) {
            set = new ConstraintSet();
            set.clone(this);
            set.centerVertically(R.id.iconTextRadioGroupItemIcon, ConstraintSet.PARENT_ID);
            if (isRequired) {
                set.connect(R.id.iconTextRadioGroupItemIcon, ConstraintSet.START, R.id.iconTextRadioGroupItemRequired, ConstraintSet.END);
            } else {
                set.connect(R.id.iconTextRadioGroupItemIcon, ConstraintSet.START, getId(), ConstraintSet.START);
            }
            set.applyTo(this);
        }

        //2. Add title constraintSet.
        int marginParent = DisplayUtils.dip2px(context, 4f);
        set = new ConstraintSet();
        set.clone(this);
        set.centerVertically(R.id.iconTextRadioGroupItemTitle, ConstraintSet.PARENT_ID);
        if (iconImageView != null) {
            set.connect(R.id.iconTextRadioGroupItemTitle, ConstraintSet.START, R.id.iconTextRadioGroupItemIcon, ConstraintSet.END);
        } else if (isRequired) {
            set.connect(R.id.iconTextRadioGroupItemTitle, ConstraintSet.START, R.id.iconTextRadioGroupItemRequired, ConstraintSet.END);
        }
        set.setMargin(R.id.iconTextRadioGroupItemTitle, ConstraintSet.START, marginParent);
        set.applyTo(this);

        //3. Add radioGroup constraintSet.
        set = new ConstraintSet();
        set.clone(this);
        marginParent = DisplayUtils.dip2px(context, 4f);
        set.centerVertically(R.id.iconTextRadioGroupItemRadioGroup, ConstraintSet.PARENT_ID);
        set.connect(R.id.iconTextRadioGroupItemRadioGroup, ConstraintSet.START, R.id.iconTextRadioGroupItemTitle, ConstraintSet.END);
        set.connect(R.id.iconTextRadioGroupItemRadioGroup, ConstraintSet.END, R.id.iconTextRadioGroupItemArrow, ConstraintSet.START);
        set.setMargin(R.id.iconTextRadioGroupItemRadioGroup, ConstraintSet.START, marginParent);
        if (hasNext) {
            set.setMargin(R.id.iconTextRadioGroupItemRadioGroup, ConstraintSet.END, marginParent);
        }
        set.setMargin(R.id.iconTextRadioGroupItemRadioGroup, ConstraintSet.TOP, marginParent);
        set.setMargin(R.id.iconTextRadioGroupItemRadioGroup, ConstraintSet.BOTTOM, marginParent);
        set.applyTo(this);

        //4. Add arrow constraintSet.
        set = new ConstraintSet();
        set.clone(this);
        set.centerVertically(R.id.iconTextRadioGroupItemArrow, ConstraintSet.PARENT_ID);
        set.connect(R.id.iconTextRadioGroupItemArrow, ConstraintSet.END, getId(), ConstraintSet.END);
        set.applyTo(this);
    }

    public void setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener listener) {
        if (radioGroup != null) {
            radioGroup.setOnCheckedChangeListener(listener);
        }
    }

    public RadioGroup getRadioGroup() {
        return radioGroup;
    }

    public void setCheckIndex(int index) {
        if (radioGroup != null) {
            if (index < radioGroup.getChildCount()) {
                ((RadioButton) radioGroup.getChildAt(index)).setChecked(true);
            }
        }
    }

    public String getTitle() {
        return titleTextView != null ? titleTextView.getText().toString().trim() : "";
    }

    public int getCheckIndex() {
        if (radioGroup != null) {
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                if (((RadioButton) radioGroup.getChildAt(i)).isChecked()) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void setContentStatus(int status) {
        switch (status) {
            case 0:
                setContentBackground(getErrorContentBackground());
                break;
            case 1:
                setContentBackground(getDefaultContentBackground());
                break;
        }
    }

    public void setContentBackground(int resId) {
        if (radioGroup != null) {
            radioGroup.setBackground(ContextCompat.getDrawable(context, resId));
        }
    }

    public int getDefaultContentBackground() {
        return R.color.white;
    }

    public int getErrorContentBackground() {
        return R.drawable.shape_page_background_error;
    }


}
