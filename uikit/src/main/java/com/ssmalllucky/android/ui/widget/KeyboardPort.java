package com.ssmalllucky.android.ui.widget;

import android.app.Activity;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.base.BaseKeyboard;

/**
 * 自定义键盘
 * Created by kuangch on 17/3/10.
 */
public class KeyboardPort extends BaseKeyboard {

    public KeyboardPort(Activity activity) {
        init(activity, R.xml.keyboard_port);
    }
}