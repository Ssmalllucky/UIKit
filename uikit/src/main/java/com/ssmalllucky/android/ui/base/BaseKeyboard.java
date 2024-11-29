package com.ssmalllucky.android.ui.base;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.widget.KeyModel;
import com.ssmalllucky.android.ui.widget.STKeyBoardView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @ClassName 自定义键盘基类
 * @Author shuaijialin
 * @Date 2024/1/24
 * @Description
 */
public class BaseKeyboard {
    private Activity mActivity;

    STKeyBoardView mKeyboardView;
    Keyboard Keyboard;
    EditText mEditText;
    int layoutResId;
    boolean mIfRandom;
    public boolean isAttached;

    public void init(Activity activity, int layoutResId) {
        this.mActivity = activity;
        this.layoutResId = layoutResId;
        this.isAttached = false;
        Keyboard = new Keyboard(mActivity, layoutResId);
        ViewGroup decorView = (ViewGroup) mActivity.getWindow().getDecorView();
        mKeyboardView = (STKeyBoardView) LayoutInflater.from(activity).inflate(R.layout.layout_keyboardview, (ViewGroup) decorView, false);
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mKeyboardView.setLayoutParams(params);
    }

    public void init(Activity activity, int layoutResId, boolean mIfRandom) {
        this.mActivity = activity;
        this.layoutResId = layoutResId;
        this.mIfRandom = mIfRandom;
        Keyboard = new Keyboard(mActivity, layoutResId);
        mKeyboardView = (STKeyBoardView) mActivity.findViewById(R.id.keyboard_view);
    }

    /**
     * edittext绑定自定义键盘
     *
     * @param editText 需要绑定自定义键盘的edittext
     */
    public void attachTo(EditText editText) {
        this.mEditText = editText;
        hideSystemSoftKeyboard(mActivity.getApplicationContext(), mEditText);
        showSoftKeyboard();
        this.isAttached = true;
    }

    public void removeAttach() {
        hideKeyboard();
        this.isAttached = false;
    }

    public void showSoftKeyboard() {
        if (Keyboard == null) {
            Keyboard = new Keyboard(mActivity, layoutResId);
        }
        ViewGroup decorView = (ViewGroup) mActivity.getWindow().getDecorView().findViewById(android.R.id.content);
        mKeyboardView = (STKeyBoardView) LayoutInflater.from(mActivity).inflate(R.layout.layout_keyboardview, (ViewGroup) decorView, false);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.BOTTOM;
        mKeyboardView.setLayoutParams(params);
        decorView.addView(mKeyboardView);

        if (mIfRandom) {
            randomKeyboardNumber();
        } else {
            mKeyboardView.setKeyboard(Keyboard);
        }
        mKeyboardView.setKeyboard(Keyboard);
        mKeyboardView.setEnabled(true);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setVisibility(View.VISIBLE);
        mKeyboardView.startAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.anim_st_keyboard_enter));
        mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);

    }

    private final KeyboardView.OnKeyboardActionListener mOnKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {

        }

        @Override
        public void onRelease(int primaryCode) {

        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = mEditText.getText();
            int start = mEditText.getSelectionStart();
            if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            } else if (primaryCode == Keyboard.KEYCODE_DONE) {// 隐藏键盘
                hideKeyboard();
                if (mOnOkClick != null) {
                    mOnOkClick.onOkClick();
                }
            } else {
                editable.insert(start, Character.toString((char) primaryCode));
            }
        }

        @Override
        public void onText(CharSequence text) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };


    /**
     * 隐藏系统键盘
     */
    public static void hideSystemSoftKeyboard(Context context, EditText editText) {
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= 11) {
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            editText.setInputType(InputType.TYPE_NULL);
        }
        //如果软键盘已经显示，则隐藏
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public interface OnOkClick {
        void onOkClick();
    }

    public OnOkClick mOnOkClick = null;

    public void setOnOkClick(OnOkClick onOkClick) {
        mOnOkClick = onOkClick;
    }

    public static final String WORDS = "0123456789";

    private boolean isNumber(String str) {
        return WORDS.contains(str) && !str.equals("");
    }

    private void randomKeyboardNumber() {
        List<Keyboard.Key> keyList = Keyboard.getKeys();
        // 查找出0-9的数字键
        List<Keyboard.Key> newkeyList = new ArrayList<Keyboard.Key>();
        for (int i = 0; i < keyList.size(); i++) {
            if (keyList.get(i).label != null
                    && isNumber(keyList.get(i).label.toString())) {
                newkeyList.add(keyList.get(i));
            }
        }
        // 数组长度
        int count = newkeyList.size();
        // 结果集
        List<KeyModel> resultList = new ArrayList<>();
        // 用一个LinkedList作为中介
        LinkedList<KeyModel> temp = new LinkedList<>();
        // 初始化temp
        for (int i = 0; i < count; i++) {
            temp.add(new KeyModel(48 + i, String.valueOf(i)));
        }
        // 取数
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            int num = rand.nextInt(count - i);
            resultList.add(new KeyModel(temp.get(num).getCode(),
                    temp.get(num).getLable()));
            temp.remove(num);
        }
        for (int i = 0; i < newkeyList.size(); i++) {
            newkeyList.get(i).label = resultList.get(i).getLable();
            newkeyList.get(i).codes[0] = resultList.get(i)
                    .getCode();
        }

        mKeyboardView.setKeyboard(Keyboard);
    }

    public void showKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            mKeyboardView.setVisibility(View.VISIBLE);
            mKeyboardView.startAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.anim_st_keyboard_enter));
        }
    }

    public void hideKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.anim_st_keyboard_exit);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mKeyboardView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mKeyboardView.startAnimation(animation);
        }
    }
}