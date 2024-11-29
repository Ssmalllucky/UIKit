package com.ssmalllucky.android.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.utils.DisplayUtils;

import java.util.LinkedList;
import java.util.List;


/**
 * 带图标、下拉列表的设置文本项组件
 *
 * @author shuaijialin
 */
public class STSpinnerItem2 extends STContentItem2 {

    /**
     * 被选中的位置索引
     */
    private int mSelectedPosition = -1;

    private PopupSpinner2 popupSpinner;

    private CharSequence[] entries;

    public STSpinnerItem2(@NonNull Context context) {
        super(context);
    }

    public STSpinnerItem2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public STSpinnerItem2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(@NonNull Context context, AttributeSet attrs) {
        super.init(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.STSpinnerItem);
        entries = a.getTextArray(R.styleable.STSpinnerItem_STSpinnerItem_entries);

        //加载列表数据
        if (entries != null && entries.length != 0) {
            List<String> list = new LinkedList<>();
            for (CharSequence entry : entries) {
                list.add(String.valueOf(entry));
            }
            //生成数据适配器
            mAdapter = new PopupSpinnerAdapter2(context, list);
            setAdapter(mAdapter);
        }

        LayoutParams contentParams = (LayoutParams) contentTextView.getLayoutParams();
        contentParams.weight = 1;
        contentTextView.setLayoutParams(contentParams);

        //右侧图标
        ImageView spinnerIV = new ImageView(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMarginStart(DisplayUtils.dip2px(context, 4f));
        spinnerIV.setLayoutParams(params);
        spinnerIV.setImageResource(R.drawable.ic_spinner_indicator);
        contentContainer.addView(spinnerIV);

        setOnClickListener(v -> {
            if (getAdapter() == null || getAdapter().getItemCount() == 0) {
                return;
            }
            popupSpinner = new PopupSpinner2(mContext);
            popupSpinner.setContentView(LayoutInflater.from(mContext).inflate(R.layout.layout_popupwindow, (ViewGroup) ((Activity) getContext()).getWindow().getDecorView(), false));
            popupSpinner.setAdapter(mAdapter);
            // 区别于Android原生的Spinner组件，只能用setSelection设置选中
            // 此组件提供selection和defaultValue两种默认值设置方式
            // 默认值，表示未设置选中，但这种情况下，用户可能设置了defaultValue，所以还需要做一步处理
            if (mSelectedPosition == -1) {
                int selection = popupSpinner.getSelectionByValue(contentValue);
                //根据查找结果设置选中
                mSelectedPosition = selection == -1 ? 0 : selection;
            }
            setSelection(mSelectedPosition);
            popupSpinner.setSelection(mSelectedPosition);
            setContentValue(mAdapter.getList().get(mSelectedPosition));
            popupSpinner.showAtLocation(getTitleTextView(), Gravity.TOP | Gravity.START, 0, 0);
            popupSpinner.setOnItemSelectedListener(listener);
        });
    }

    private PopupSpinnerAdapter2 mAdapter;

    private PopupSpinnerAdapter2 getAdapter() {
        return mAdapter;
    }

    public void setAdapter(PopupSpinnerAdapter2 adapter) {
        this.mAdapter = adapter;
        this.mAdapter.setSpinnerItem(this);
        setSelection(0);
        if (mAdapter.getList() != null && mAdapter.getList().size() > 0) {
            setContentValue(mAdapter.getList().get(0));
        }
    }

    /**
     * 设置Spinner值
     *
     * @param value
     */
    public void setDefaultValue(String value) {
        if (getAdapter() == null) {
            return;
        }

        if (TextUtils.isEmpty(value)) {
            return;
        }

        List<String> list = getAdapter().getList();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains(value)) {
                setContentValue(list.get(i));
                setSelection(i);
                break;
            }
        }
    }

    /**
     * 设置Spinner索引
     *
     * @param selection 被选中的索引
     */
    public void setSelection(int selection) {
        if (getAdapter() == null) {
            return;
        }

        if (selection < 0) {
            this.mSelectedPosition = 0;
        } else this.mSelectedPosition = Math.min(selection, getAdapter().getList().size() - 1);

        setContentValue(getAdapter().getList().get(mSelectedPosition));

        if (popupSpinner != null) {
            popupSpinner.setSelection(mSelectedPosition);
        }
    }

    public void updateSelection(int selection) {
        this.mSelectedPosition = selection;
        popupSpinner.dismiss();
    }

    public void dismissPopSpinner() {
        popupSpinner.dismiss();
    }

    public int getSelection() {
        return mSelectedPosition;
    }

    /**
     * 设置项状态
     *
     * @param value 状态文本
     */
    public void setContentValue(String value) {
        if (contentTextView != null) {
            this.contentValue = value;
            contentTextView.setText(value);
        }
    }

    public void setContentValueByDmz(String dmz) {

        if (mAdapter == null || mAdapter.getList() == null) {
            return;
        }

        List<String> list = mAdapter.getList();
        String contentValue = "";
        for (int i = 0; i < list.size(); i++) {
            String itemStr = list.get(i);
            if (itemStr.split(":")[0].equals(dmz)) {
                contentValue = list.get(i);
            }
        }

        if (contentTextView != null) {
            this.contentValue = contentValue;
            contentTextView.setText(contentValue);
        }
    }

    public void setContentValueByDmsm1(String dmsm1) {

        if (mAdapter == null || mAdapter.getList() == null) {
            return;
        }

        List<String> list = mAdapter.getList();
        String contentValue = "";
        for (int i = 0; i < list.size(); i++) {
            String itemStr = list.get(i);
            if (itemStr.split(":")[1].equals(dmsm1)) {
                contentValue = list.get(i);
            }
        }

        if (contentTextView != null) {
            this.contentValue = contentValue;
            contentTextView.setText(contentValue);
        }
    }

    /**
     * 获取选中的内容
     *
     * @return
     */
    public String getContentValue() {
        return contentValue;
    }

    public String getDmz() {
        return TextUtils.isEmpty(contentValue) ? contentValue : contentValue.split(":")[0];
    }

    public String getDmsm() {
        return TextUtils.isEmpty(contentValue) ? contentValue : contentValue.split(":")[1];
    }

    public TextView getContentTextView() {
        return contentTextView;
    }

    private PopupSpinnerAdapter2.OnItemSelectedListener listener;

    public void setOnItemSelectedListener(PopupSpinnerAdapter2.OnItemSelectedListener listener) {
        this.listener = listener;
    }
}
