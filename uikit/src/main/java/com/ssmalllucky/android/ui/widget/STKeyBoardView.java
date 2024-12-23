package com.ssmalllucky.android.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.utils.DisplayUtils;

import java.util.List;

public class STKeyBoardView extends KeyboardView {
    private Context mContext;
    private Keyboard mKeyBoard;

    private int mTextSize;

    private int itemPadding;

    public STKeyBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mTextSize = context.getResources().getDimensionPixelSize(R.dimen.text_size_keyboardview);
        this.itemPadding = DisplayUtils.dip2px(context, 4f);
    }

    public STKeyBoardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        this.mTextSize = context.getResources().getDimensionPixelSize(R.dimen.text_size_keyboardview);
        this.itemPadding = DisplayUtils.dip2px(context, 4f);
    }

    /**
     * 重新画一些按键
     */
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mKeyBoard = this.getKeyboard();
        List<Key> keys = null;
        if (mKeyBoard != null) {
            keys = mKeyBoard.getKeys();
        }

        if (keys != null) {
            for (Key key : keys) {
                // 数字键盘的处理
                if (key.codes[0] == -4) {
                    drawKeyBackground(R.drawable.selector_action_r6, canvas, key);
                    drawText(canvas, key);
                } else if (key.codes[0] == -5) {
                    drawNormalBackground(canvas, key);
                    if (key.icon != null) {
                        key.icon.setBounds(key.x + (key.width - key.icon.getIntrinsicWidth()) / 2, key.y + (key.height - key.icon.getIntrinsicHeight()) / 2,
                                key.x + (key.width - key.icon.getIntrinsicWidth()) / 2 + key.icon.getIntrinsicWidth(), key.y + (key.height - key.icon.getIntrinsicHeight()) / 2 + key.icon.getIntrinsicHeight());
                        key.icon.draw(canvas);
                    }
                } else {
                    if (key.icon != null) {
                        if (key.label != null) {
                            key.icon.setBounds(key.x + itemPadding, key.y + itemPadding,
                                    key.x + key.width - itemPadding, key.y + key.height - itemPadding);
                            key.icon.draw(canvas);
                        }
                        drawNormalText(canvas, key);
                    }
                }
            }
        }
    }

    private void drawKeyBackground(int drawableId, Canvas canvas, Key key) {
        Drawable npd = ContextCompat.getDrawable(mContext, drawableId);
        int[] drawableState = key.getCurrentDrawableState();
        if (key.codes[0] != 0) {
            npd.setState(drawableState);
        }
        Drawable transparentDrawable = ContextCompat.getDrawable(mContext, R.color.CE4E4E4);
        transparentDrawable.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
        transparentDrawable.draw(canvas);

        npd.setBounds(key.x + itemPadding, key.y + itemPadding, key.x + key.width - itemPadding, key.y
                + key.height);
        npd.draw(canvas);
    }

    private void drawNormalBackground(Canvas canvas, Key key) {
        Drawable npd = ContextCompat.getDrawable(mContext, R.drawable.selector_keyboard_item);
        int[] drawableState = key.getCurrentDrawableState();
        if (key.codes[0] != 0) {
            npd.setState(drawableState);
        }

        Drawable transparentDrawable = ContextCompat.getDrawable(mContext, R.color.CE4E4E4);
        transparentDrawable.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
        transparentDrawable.draw(canvas);

        npd.setBounds(key.x + itemPadding, key.y + itemPadding, key.x + key.width - itemPadding, key.y
                + key.height);
        npd.draw(canvas);
    }

    private void drawNormalText(Canvas canvas, Key key) {
        Rect bounds = new Rect();
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#001226"));

        if (key.label != null) {
            String label = key.label.toString();
            paint.setTextSize(30f * DisplayUtils.getDensity(getContext()));
            paint.setTypeface(Typeface.DEFAULT);

//            if (label.length() > 1 && key.codes.length < 2) {
//                paint.setTypeface(Typeface.DEFAULT);
//            }
            paint.getTextBounds(key.label.toString(), 0, key.label.toString()
                    .length(), bounds);
            canvas.drawText(key.label.toString(), key.x + (key.width / 2),
                    (key.y + key.height / 2) + bounds.height() / 2, paint);
        }
    }

    private void drawText(Canvas canvas, Key key) {
        Rect bounds = new Rect();
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#FFFFFF"));

        if (key.label != null) {
            String label = key.label.toString();
            paint.setTextSize(26f * DisplayUtils.getDensity(getContext()));
            paint.setTypeface(Typeface.DEFAULT);

            if (label.length() > 1 && key.codes.length < 2) {
                paint.setTextSize(18f * DisplayUtils.getDensity(getContext()));
            }

            paint.getTextBounds(key.label.toString(), 0, key.label.toString()
                    .length(), bounds);
            canvas.drawText(key.label.toString(), key.x + (key.width / 2),
                    (key.y + key.height / 2) + bounds.height() / 2, paint);
        } else if (key.icon != null) {
            key.icon.setBounds(key.x + (key.width - key.icon.getIntrinsicWidth()) / 2, key.y + (key.height - key.icon.getIntrinsicHeight()) / 2,
                    key.x + (key.width - key.icon.getIntrinsicWidth()) / 2 + key.icon.getIntrinsicWidth(), key.y + (key.height - key.icon.getIntrinsicHeight()) / 2 + key.icon.getIntrinsicHeight());
            key.icon.draw(canvas);
        }
    }
}
