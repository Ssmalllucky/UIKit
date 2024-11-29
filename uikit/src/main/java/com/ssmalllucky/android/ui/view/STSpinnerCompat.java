package com.ssmalllucky.android.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;

import com.ssmalllucky.android.ui.utils.DisplayUtils;

/**
 * @ClassName STSpinnerCompat
 * @Author shuaijialin
 * @Date 2023/9/7
 * @Description
 */
public class STSpinnerCompat extends LinearLayout {

    private AppCompatSpinner spinner;

    public STSpinnerCompat(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public STSpinnerCompat(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public STSpinnerCompat(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        spinner = new AppCompatSpinner(context, Spinner.MODE_DIALOG);
        int margin = DisplayUtils.dip2px(context, 10f);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        spinner.setPadding(0, 0, margin, 0);
        spinner.setLayoutParams(layoutParams);
        addView(spinner);
    }

    public Spinner getSpinner() {
        return spinner;
    }

    public void setAdapter(ArrayAdapter<String> arrayAdapter) {
        if (spinner != null) spinner.setAdapter(arrayAdapter);
    }

    public void setSelection(int selection){
        if(spinner != null) spinner.setSelection(selection);
    }

    public Object getSelectedItem(){
        if(spinner != null) {
            return spinner.getSelectedItem();
        } else {
            return "";
        }
    }

    public void setArrayAdapter(ArrayAdapter<String> adapter) {
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener){
        if(spinner != null){
            spinner.setOnItemSelectedListener(listener);
        }
    }
}
