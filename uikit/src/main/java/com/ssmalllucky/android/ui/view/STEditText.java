package com.ssmalllucky.android.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.utils.DisplayUtils;

import java.lang.reflect.Field;


public class STEditText extends androidx.appcompat.widget.AppCompatEditText {

    private float textSize;

    private Drawable background;

    public STEditText(Context context) {
        super(context);
        init(context, null);
    }

    public STEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public STEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.STEditText);
            setTextColor(context.getColor(R.color.text_color_primary));
            textSize = a.getDimensionPixelSize(R.styleable.STEditText_STEditText_textSize, (context.getResources().getDimensionPixelSize(R.dimen.text_size_secondary_content)));
            background = a.getDrawable(R.styleable.STEditText_STEditText_background);
        } else {
            textSize = (context.getResources().getDimensionPixelSize(R.dimen.text_size_secondary_content));
        }

        setBackground(background != null ? background : ContextCompat.getDrawable(context, R.drawable.shape_edittext_background));
        setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        setHintTextColor(context.getColor(R.color.text_hint_color));
        setPadding(DisplayUtils.dip2px(context, 6f), 0, 0, 0);

        setCopyDisabled();
    }

    private void setCopyDisabled() {
        setLongClickable(false);
        setTextIsSelectable(false);

        // call that method
        setCustomInsertionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
        setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // setInsertionDisabled when user touches the view
                setInsertionDisabled(STEditText.this);
            }
            return false;
        });

    }

    private void setInsertionDisabled(EditText editText) {
        try {
            Field editorField = TextView.class.getDeclaredField("mEditor");
            editorField.setAccessible(true);
            Object editorObject = editorField.get(editText);

            // if this view supports insertion handles
            Class editorClass = Class.forName("android.widget.Editor");
            Field mInsertionControllerEnabledField = editorClass.getDeclaredField("mInsertionControllerEnabled");
            mInsertionControllerEnabledField.setAccessible(true);
            mInsertionControllerEnabledField.set(editorObject, false);

            // if this view supports selection handles
            Field mSelectionControllerEnabledField = editorClass.getDeclaredField("mSelectionControllerEnabled");
            mSelectionControllerEnabledField.setAccessible(true);
            mSelectionControllerEnabledField.set(editorObject, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        return true;
    }

}
