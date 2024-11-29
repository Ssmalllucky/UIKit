package com.ssmalllucky.android.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ssmalllucky.android.ui.R;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName STPickerItem
 * @Author shuaijialin
 * @Date 2024/3/14
 * @Description
 */
public class STPickerItem extends STContentItem {

    private List<String> list;

    public STPickerItem(@NonNull Context context) {
        super(context);
    }

    public STPickerItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public STPickerItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(@NonNull Context context, AttributeSet attrs) {
        super.init(context, attrs);
        setOnClickListener(v -> {
            if (list == null || list.isEmpty()) {
                return;
            }
            STPickerDialog timePickerView = new STPickerDialog(mContext, STPickerDialog.Type.CSYS, list);
            timePickerView.setCyclic(false);
            timePickerView.setCancelable(true);
            if (originSelectedArr != null) {
                timePickerView.setSelectedArr(originSelectedArr);
            } else {
                timePickerView.setSelectedArr((String[]) getTag(R.id.tag_stpickeritem_selected));
            }
            timePickerView.show();
            timePickerView.setOnOKListener(selected -> {
                setContentValue(fix(selected));
                setTag(R.id.tag_stpickeritem_selected, selected);
                Log.d("TAG", "getSelected: " + Arrays.asList(selected));
            });
        });
    }

    private String[] originSelectedArr;

    public void setSelectedArr(@NonNull String[] selectedArr) {
        if (selectedArr.length < 3) {
            this.originSelectedArr = new String[3];
            //将 selectedArr 数组从 0 位置至 selectedArr.length 位置，复制到 originSelectedArr 数组 0 位置到 selectedArr.length 位置。
            System.arraycopy(selectedArr, 0, originSelectedArr, 0, selectedArr.length);
        } else {
            this.originSelectedArr = selectedArr;
        }
        this.setContentValue(fix(selectedArr));
    }


    @NonNull
    private String fix(@NonNull String[] selected) {
        StringBuilder fixStr = new StringBuilder();
        for (int i = 0; i < selected.length; i++) {
            fixStr.append(selected[i]);
            if (i < selected.length - 1) {
                fixStr.append(",");
            }
        }
        return fixStr.toString();
    }

    public void setList(List<String> list) {
        this.list = list;
    }

}
