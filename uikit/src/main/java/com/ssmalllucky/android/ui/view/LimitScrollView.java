package com.ssmalllucky.android.ui.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.ScrollView;

/**
 * @ClassName LimitScrollView
 * @Author shuaijialin
 * @Date 2023/10/24
 * @Description
 */
public class LimitScrollView extends ScrollView {

    private Context mContext;

    public LimitScrollView(Context context) {
        this(context, null);
    }

    public LimitScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LimitScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            Display display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
            DisplayMetrics d = new DisplayMetrics();
            display.getMetrics(d);
            // 设置控件最大高度不能超过屏幕高度的三分之二
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(d.heightPixels / 3 * 2, MeasureSpec.AT_MOST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 重新计算控件的宽高
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
