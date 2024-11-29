package com.ssmalllucky.android.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.utils.DisplayUtils;
import com.ssmalllucky.android.ui.view.STSecondaryTextView;
import com.ssmalllucky.android.ui.view.STSpinnerCompat;

/**
 * 带图标、下拉列表的组件
 *
 * @author shuaijialin
 */
public class UIIconTextSpinnerItem extends ConstraintLayout {

    private Context context;
    private boolean isRequired;
    private boolean isTop;
    private boolean isBottom;
    private boolean isLayout;
    private boolean hasNext;
    private boolean showSpinner;
    private ImageView requiredImageView;
    private ImageView iconImageView;
    private STSecondaryTextView titleTextView;
    private STSpinnerCompat spinnerContainer;
    private ImageView arrowImageView;
    private int iconResId;
    private String title;

    /**
     * Spinner数据
     */
    private ArrayAdapter<String> arrayAdapter;

    public UIIconTextSpinnerItem(@NonNull Context context) {
        super(context);
    }

    public UIIconTextSpinnerItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public UIIconTextSpinnerItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        int minHeight = DisplayUtils.dip2px(context, 50f);
        int spacingXS = context.getResources().getDimensionPixelSize(R.dimen.spacing_xs);
        int spacingS = context.getResources().getDimensionPixelSize(R.dimen.spacing_s);
        int spacingM = context.getResources().getDimensionPixelSize(R.dimen.spacing_m);

        setMinHeight(minHeight);
        removeAllViews();

        ViewGroup.LayoutParams rootParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(rootParams);

        int padding = context.getResources().getDimensionPixelSize(R.dimen.page_layout_margin);
        setPadding(padding, 0, padding, 0);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.UIIconTextSpinnerItem);
        title = a.getString(R.styleable.UIIconTextSpinnerItem_UIIconTextSpinnerItem_title);
        isRequired = a.getBoolean(R.styleable.UIIconTextSpinnerItem_UIIconTextSpinnerItem_isRequired, false);
        isTop = a.getBoolean(R.styleable.UIIconTextSpinnerItem_UIIconTextSpinnerItem_isTop, false);
        isBottom = a.getBoolean(R.styleable.UIIconTextSpinnerItem_UIIconTextSpinnerItem_isBottom, false);
        isLayout = a.getBoolean(R.styleable.UIIconTextSpinnerItem_UIIconTextSpinnerItem_isLayout, false);
        hasNext = a.getBoolean(R.styleable.UIIconTextSpinnerItem_UIIconTextSpinnerItem_hasNext, true);
        showSpinner = a.getBoolean(R.styleable.UIIconTextSpinnerItem_UIIconTextSpinnerItem_showSpinner, true);
        iconResId = a.getResourceId(R.styleable.UIIconTextSpinnerItem_UIIconTextSpinnerItem_icon, 0);

        //0. Add requiredTV if true.
        if (isRequired) {
            requiredImageView = new ImageView(context);
            requiredImageView.setId(R.id.iconTextSpinnerItemRequired);
            requiredImageView.setImageResource(R.drawable.ic_required);
            addView(requiredImageView);
        }

        // All View layoutParams
        ConstraintLayout.LayoutParams layoutParams;

        //1. Add icon.
        int iconSize = DisplayUtils.dip2px(context, 22f);
        if (iconResId != 0) {
            iconImageView = new ImageView(context);
            iconImageView.setId(R.id.iconTextSpinnerItemIcon);
            iconImageView.setImageResource(iconResId);

            layoutParams = new ConstraintLayout.LayoutParams(iconSize, iconSize);
            iconImageView.setLayoutParams(layoutParams);
            addView(iconImageView);
        }

        //2. Add item title.
        titleTextView = new STSecondaryTextView(context);
        titleTextView.setId(R.id.iconTextSpinnerItemTitle);
        titleTextView.setText(title);
        titleTextView.setTextColor(ContextCompat.getColor(context, R.color.text_color_secondary_title));

        int titleWidth = context.getResources().getDimensionPixelSize(R.dimen.settings_title_width);
        layoutParams = new ConstraintLayout.LayoutParams(titleWidth, LayoutParams.WRAP_CONTENT);
        if (iconImageView != null) {
            layoutParams.setMarginStart(spacingS);
        }
        titleTextView.setLayoutParams(layoutParams);
        addView(titleTextView);

        //3. Add Spinner.
        if (showSpinner) {
            spinnerContainer = new STSpinnerCompat(context);
            spinnerContainer.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_page_background));
            spinnerContainer.setId(R.id.iconTextSpinnerItemSpinner);
            addView(spinnerContainer);

            //3. Make item spinner layout.
            int spinnerHeight = context.getResources().getDimensionPixelSize(R.dimen.view_spinner_height);
            layoutParams = new LayoutParams(0, spinnerHeight);
            layoutParams.horizontalWeight = 2;
            spinnerContainer.setLayoutParams(layoutParams);
        }

        //4. Add arrow icon.
        arrowImageView = new ImageView(context);
        arrowImageView.setId(R.id.iconTextSpinnerItemArrow);
        arrowImageView.setImageResource(R.drawable.ic_arrow_right);
        if (arrowImageView != null) {
            int arrowSize = context.getResources().getDimensionPixelSize(R.dimen.settings_arrow_size);
            layoutParams = new LayoutParams(arrowSize, arrowSize);
            arrowImageView.setLayoutParams(layoutParams);
        }
        addView(arrowImageView);

        if (!hasNext) {
            arrowImageView.setVisibility(View.GONE);
        }

        setStyle();
        layoutItem();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    private void layoutItem() {
        int spacingXS = context.getResources().getDimensionPixelSize(R.dimen.spacing_xs);
        ConstraintSet set;

        //0. Add required constraintSet.
        if (isRequired) {
            set = new ConstraintSet();
            set.clone(this);
            set.centerVertically(R.id.iconTextSpinnerItemRequired, ConstraintSet.PARENT_ID);
            set.connect(R.id.iconTextSpinnerItemRequired, ConstraintSet.START, getId(), ConstraintSet.START);
            set.applyTo(this);
        }

        //1. Add icon constraintSet.
        if (iconImageView != null) {
            set = new ConstraintSet();
            set.clone(this);
            set.centerVertically(R.id.iconTextSpinnerItemIcon, ConstraintSet.PARENT_ID);
            if (isRequired) {
                set.connect(R.id.iconTextSpinnerItemIcon, ConstraintSet.START, R.id.iconTextSpinnerItemRequired, ConstraintSet.END);
            } else {
                set.connect(R.id.iconTextSpinnerItemIcon, ConstraintSet.START, getId(), ConstraintSet.START);
            }
            set.applyTo(this);
        }

        //2. Add title constraintSet.
        set = new ConstraintSet();
        set.clone(this);
        set.centerVertically(R.id.iconTextSpinnerItemTitle, ConstraintSet.PARENT_ID);
        if (iconImageView != null) {
            set.connect(R.id.iconTextSpinnerItemTitle, ConstraintSet.START, R.id.iconTextSpinnerItemIcon, ConstraintSet.END);
        } else if (isRequired) {
            set.connect(R.id.iconTextSpinnerItemTitle, ConstraintSet.START, R.id.iconTextSpinnerItemRequired, ConstraintSet.END);
        } else {
            set.connect(R.id.iconTextSpinnerItemTitle, ConstraintSet.START, getId(), ConstraintSet.START);
        }
        set.applyTo(this);

        //3. Add spinner constraintSet.
        if (showSpinner) {
            set = new ConstraintSet();
            set.clone(this);
            set.centerVertically(R.id.iconTextSpinnerItemSpinner, ConstraintSet.PARENT_ID);
            set.connect(R.id.iconTextSpinnerItemSpinner, ConstraintSet.START, R.id.iconTextSpinnerItemTitle, ConstraintSet.END);
            set.connect(R.id.iconTextSpinnerItemSpinner, ConstraintSet.END, R.id.iconTextSpinnerItemArrow, ConstraintSet.START);
            set.setMargin(R.id.iconTextSpinnerItemSpinner, ConstraintSet.START, spacingXS);
            if (hasNext) {
                set.setMargin(R.id.iconTextSpinnerItemSpinner, ConstraintSet.END, spacingXS);
            }
            set.applyTo(this);
        }

        //4. Add arrow constraintSet.
        set = new ConstraintSet();
        set.clone(this);
        set.centerVertically(R.id.iconTextSpinnerItemArrow, ConstraintSet.PARENT_ID);
        set.connect(R.id.iconTextSpinnerItemArrow, ConstraintSet.END, getId(), ConstraintSet.END);
        set.applyTo(this);
    }

    public Spinner getSpinner() {
        return spinnerContainer.getSpinner();
    }

    public Object getSelectedItem() {
        if (spinnerContainer.getSpinner() != null) {
            Object selectedItem = spinnerContainer.getSpinner().getSelectedItem();
            return selectedItem == null ? "" : selectedItem.toString();
        }
        return "";
    }

    public void setAdapter(ArrayAdapter<String> adapter) {
        if (spinnerContainer.getSpinner() != null) {
            spinnerContainer.setArrayAdapter(adapter);
        }
    }

    public void setArrayAdapter(ArrayAdapter<String> adapter) {
        if (spinnerContainer != null) {
            spinnerContainer.setArrayAdapter(adapter);
        }
    }

    public String getTitle() {
        return titleTextView != null ? titleTextView.getText().toString().trim() : "";
    }

    public void setSelection(int selection) {
        if (spinnerContainer != null) {
            spinnerContainer.setSelection(selection);
        }
    }

    public void setSelectionByDmz(String dmz) {
        if (spinnerContainer != null && getSpinner() != null) {
            try {
                SpinnerAdapter spinnerAdapter = spinnerContainer.getSpinner().getAdapter();
                for (int i = 0; i < spinnerAdapter.getCount(); i++) {
                    String item = (String) spinnerAdapter.getItem(i);
                    String[] itemSplit = item.split(":");
                    if (itemSplit.length > 1 && itemSplit[0].equals(dmz)) {
                        getSpinner().setSelection(i);
                        break;
                    } else if (item.equals(dmz)) {
                        getSpinner().setSelection(i);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setSelectionByValue(String value) {
        if (spinnerContainer != null && getSpinner() != null) {
            try {
                SpinnerAdapter spinnerAdapter = spinnerContainer.getSpinner().getAdapter();
                for (int i = 0; i < spinnerAdapter.getCount(); i++) {
                    String item = (String) spinnerAdapter.getItem(i);
                    if (item.equals(value)) {
                        getSpinner().setSelection(i);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
        if (spinnerContainer != null) {
            spinnerContainer.getSpinner().setOnItemSelectedListener(listener);
        }
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
        if (spinnerContainer != null) {
            spinnerContainer.setBackground(ContextCompat.getDrawable(context, resId));
        }
    }

    public int getDefaultContentBackground() {
        return R.drawable.shape_page_background;
    }

    public int getErrorContentBackground() {
        return R.drawable.shape_page_background_error;
    }
}
