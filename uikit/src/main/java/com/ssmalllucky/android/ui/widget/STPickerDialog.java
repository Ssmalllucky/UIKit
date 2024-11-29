package com.ssmalllucky.android.ui.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.base.BasePickerView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间选择器
 * Created by Sai on 15/11/22.
 */
public class STPickerDialog extends BasePickerView implements View.OnClickListener {
    public enum Type {
        ALL, YEAR_MONTH_DAY, HOURS_MINS, MONTH_DAY_HOUR_MIN, YEAR_MONTH, CSYS
    }// 四种选择模式，年月日时分，年月日，时分，月日时分

    WheelTime wheelTime;
    WheelView wheelView;
    private View okIV, cancelIV;
    private TextView tvTitle;
    private static final String TAG_SUBMIT = "ok";
    private static final String TAG_CANCEL = "cancel";
    private OnTimeSelectListener timeSelectListener;

    private OnOKListener onOKListener;

    public STPickerDialog(Context context, Type type, List<String> list) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.layout_pickerview_csys, contentContainer);
        final View timepickerview = findViewById(R.id.pickerContainer);
        wheelView = new WheelView(timepickerview, type);
        wheelView.setList(list);
        wheelView.setPicker(false, false, false);
        // -----确定和取消按钮
        okIV = findViewById(R.id.okIV);
        okIV.setTag(TAG_SUBMIT);
        cancelIV = findViewById(R.id.cancelIV);
        cancelIV.setTag(TAG_CANCEL);
        okIV.setOnClickListener(this);
        cancelIV.setOnClickListener(this);
        //顶部标题
        tvTitle = (TextView) findViewById(R.id.tvTitle);
    }

    @Override
    public void show() {
        if (wheelView != null) wheelView.setPicker(false, false, false);
        super.show();
    }

    public void setSelectedArr(String[] selectedArr) {
        wheelView.setSelectedArr(selectedArr);
    }

    public STPickerDialog(Context context, Type type) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.layout_pickerview_time, contentContainer);
        // ----时间转轮
        final View timepickerview = findViewById(R.id.pickerContainer);
        wheelTime = new WheelTime(timepickerview, type);
        //默认选中当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        wheelTime.setPicker(year, month, day, hours, minute, second);

        // -----确定和取消按钮
        okIV = findViewById(R.id.okIV);
        okIV.setTag(TAG_SUBMIT);
        cancelIV = findViewById(R.id.cancelIV);
        cancelIV.setTag(TAG_CANCEL);
        okIV.setOnClickListener(this);
        cancelIV.setOnClickListener(this);
        //顶部标题
        tvTitle = (TextView) findViewById(R.id.tvTitle);
    }

    /**
     * 设置可以选择的时间范围
     * 要在setTime之前调用才有效果
     *
     * @param startYear 开始年份
     * @param endYear   结束年份
     */
    public void setRange(int startYear, int endYear) {
        wheelTime.setStartYear(startYear);
        wheelTime.setEndYear(endYear);
    }

    /**
     * 设置选中时间
     *
     * @param date 时间
     */
    public void setTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date == null)
            calendar.setTimeInMillis(System.currentTimeMillis());
        else
            calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        wheelTime.setPicker(year, month, day, hours, minute, second);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic 是否循环
     */
    public void setCyclic(boolean cyclic) {
        if (wheelTime != null) {
            wheelTime.setCyclic(cyclic);
        } else if (wheelView != null) {
            wheelView.setCyclic(cyclic);
        }
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
        } else {
            if (timeSelectListener != null) {
                try {
                    Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
                    timeSelectListener.onTimeSelect(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (onOKListener != null) {
                onOKListener.getSelected(wheelView.getSelectedArr());
            }
            dismiss();
        }
    }

    public String[] getSelectedArr() {
        return wheelView.getSelectedArr();
    }

    public interface OnTimeSelectListener {
        void onTimeSelect(Date date);
    }

    public void setOnTimeSelectListener(OnTimeSelectListener timeSelectListener) {
        this.timeSelectListener = timeSelectListener;
    }

    public void setOnOKListener(OnOKListener onOKListener) {
        this.onOKListener = onOKListener;
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public interface OnOKListener {
        void getSelected(String[] selected);
    }
}
