package com.ssmalllucky.android.ui.widget;

import android.content.Context;
import android.view.View;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.adapter.NumericWheelAdapter;
import com.ssmalllucky.android.ui.view.wheel.STWheelView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class WheelTime {
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private View view;
    private STWheelView wv_year;
    private STWheelView wv_month;
    private STWheelView wv_day;
    private STWheelView wv_hours;
    private STWheelView wv_mins;

    private STWheelView wv_sec;

    private STPickerDialog.Type type;
    public static final int DEFULT_START_YEAR = 1990;
    public static final int DEFULT_END_YEAR = 2100;
    private int startYear = DEFULT_START_YEAR;
    private int endYear = DEFULT_END_YEAR;


    public WheelTime(View view) {
        super();
        this.view = view;
        type = STPickerDialog.Type.ALL;
        setView(view);
    }

    public WheelTime(View view, STPickerDialog.Type type) {
        super();
        this.view = view;
        this.type = type;
        setView(view);
    }

    public void setPicker(int year, int month, int day) {
        this.setPicker(year, month, day, 0, 0,0);
    }

    public void setPicker(int year, int month, int day, int h, int m,int s) {
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};

        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);

        Context context = view.getContext();
        // 年
        wv_year = view.findViewById(R.id.year);
        wv_year.setAdapter(new NumericWheelAdapter(startYear, endYear));// 设置"年"的显示数据
        wv_year.setLabel(context.getString(R.string.pickerview_year));// 添加文字
        wv_year.setCurrentItem(year - startYear);// 初始化时显示的数据

        // 月
        wv_month = view.findViewById(R.id.month);
        wv_month.setAdapter(new NumericWheelAdapter(1, 12));
        wv_month.setLabel(context.getString(R.string.pickerview_month));
        wv_month.setCurrentItem(month);

        wv_day = view.findViewById(R.id.day);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (list_big.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 31));
        } else if (list_little.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 30));
        } else {
            // 闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                wv_day.setAdapter(new NumericWheelAdapter(1, 29));
            else
                wv_day.setAdapter(new NumericWheelAdapter(1, 28));
        }
        wv_day.setLabel(context.getString(R.string.pickerview_day));
        wv_day.setCurrentItem(day - 1);


        wv_hours = view.findViewById(R.id.hour);
        wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
        wv_hours.setLabel(context.getString(R.string.pickerview_hours));// 添加文字
        wv_hours.setCurrentItem(h);

        wv_mins = view.findViewById(R.id.min);
        wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
        wv_mins.setLabel(context.getString(R.string.pickerview_minutes));// 添加文字
        wv_mins.setCurrentItem(m);

        wv_sec = view.findViewById(R.id.sec);
        wv_sec.setAdapter(new NumericWheelAdapter(0, 59));
        wv_sec.setLabel(context.getString(R.string.pickerview_seconds));// 添加文字
        wv_sec.setCurrentItem(s);

        // 添加"年"监听
        STWheelView.OnItemSelectedListener wheelListener_year = index -> {
            int year_num = index + startYear;
            // 判断大小月及是否闰年,用来确定"日"的数据
            int maxItem;
            if (list_big
                    .contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
                wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                maxItem = 31;
            } else if (list_little.contains(String.valueOf(wv_month
                    .getCurrentItem() + 1))) {
                wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                maxItem = 30;
            } else {
                if ((year_num % 4 == 0 && year_num % 100 != 0)
                        || year_num % 400 == 0) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                    maxItem = 29;
                } else {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                    maxItem = 28;
                }
            }
            if (wv_day.getCurrentItem() > maxItem - 1) {
                wv_day.setCurrentItem(maxItem - 1);
            }
        };
        // 添加"月"监听
        STWheelView.OnItemSelectedListener wheelListener_month = index -> {
            int month_num = index + 1;
            int maxItem;
            // 判断大小月及是否闰年,用来确定"日"的数据
            if (list_big.contains(String.valueOf(month_num))) {
                wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                maxItem = 31;
            } else if (list_little.contains(String.valueOf(month_num))) {
                wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                maxItem = 30;
            } else {
                if (((wv_year.getCurrentItem() + startYear) % 4 == 0 && (wv_year
                        .getCurrentItem() + startYear) % 100 != 0)
                        || (wv_year.getCurrentItem() + startYear) % 400 == 0) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                    maxItem = 29;
                } else {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                    maxItem = 28;
                }
            }
            if (wv_day.getCurrentItem() > maxItem - 1) {
                wv_day.setCurrentItem(maxItem - 1);
            }

        };
        wv_year.setOnItemSelectedListener(wheelListener_year);
        wv_month.setOnItemSelectedListener(wheelListener_month);

        // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
        float textSize = 6;
        switch (type) {
            case ALL:
                textSize = textSize * 2.5f;
                break;
            case YEAR_MONTH_DAY:
                textSize = textSize * 4;
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                wv_sec.setVisibility(View.GONE);
                break;
            case HOURS_MINS:
                textSize = textSize * 4;
                wv_year.setVisibility(View.GONE);
                wv_month.setVisibility(View.GONE);
                wv_day.setVisibility(View.GONE);
                break;
            case MONTH_DAY_HOUR_MIN:
                textSize = textSize * 3;
                wv_year.setVisibility(View.GONE);
                wv_sec.setVisibility(View.GONE);
                break;
            case YEAR_MONTH:
                textSize = textSize * 4;
                wv_day.setVisibility(View.GONE);
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                wv_sec.setVisibility(View.GONE);
        }
        wv_day.setTextSize(textSize);
        wv_month.setTextSize(textSize);
        wv_year.setTextSize(textSize);
        wv_hours.setTextSize(textSize);
        wv_mins.setTextSize(textSize);
        wv_sec.setTextSize(textSize);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wv_year.setCyclic(cyclic);
        wv_month.setCyclic(cyclic);
        wv_day.setCyclic(cyclic);
        wv_hours.setCyclic(cyclic);
        wv_mins.setCyclic(cyclic);
    }

    public String getTime() {
        StringBuffer sb = new StringBuffer();
        sb.append((wv_year.getCurrentItem() + startYear)).append("-")
                .append((wv_month.getCurrentItem() + 1)).append("-")
                .append((wv_day.getCurrentItem() + 1)).append(" ")
                .append(wv_hours.getCurrentItem()).append(":")
                .append(wv_mins.getCurrentItem()).append(":")
                .append(wv_sec.getCurrentItem());
        return sb.toString();
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }
}
