package com.ssmalllucky.android.ui.widget;

import android.app.Activity;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.base.BaseKeyboard;

/**
 * 自定义键盘
 * Created by kuangch on 17/3/10.
 */
public class KeyboardIP extends BaseKeyboard {

    public KeyboardIP(Activity activity) {
        init(activity, R.xml.keyboard_ip);
    }
}