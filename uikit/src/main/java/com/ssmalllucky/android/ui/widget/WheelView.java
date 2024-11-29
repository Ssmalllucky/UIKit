package com.ssmalllucky.android.ui.widget;

import android.text.TextUtils;
import android.view.View;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.adapter.StringWheelAdapter;
import com.ssmalllucky.android.ui.view.wheel.STWheelView;

import java.util.List;


public class WheelView {

    public static final String TAG = "WheelView";

    private View view;
    private STWheelView csys1;
    private STWheelView csys2;

    private STWheelView csys3;

    private STPickerDialog.Type type;

    private List<String> list;

    private String[] selectedArr;

    public WheelView(View view) {
        super();
        this.view = view;
        type = STPickerDialog.Type.ALL;
        setView(view);
    }

    public WheelView(View view, STPickerDialog.Type type) {
        super();
        this.view = view;
        this.type = type;
        setView(view);
    }

    public void setList(List<String> list) {
        this.list = list;
        this.selectedArr = new String[3];
    }

    public void setSelectedArr(String[] selectedArr) {
        this.selectedArr = selectedArr;
        if (this.selectedArr == null) {
            this.selectedArr = new String[3];
        }
    }

    public void setPicker(boolean disableFirst, boolean disableSecond, boolean disableThird) {
        if (list == null || list.isEmpty()) {
            return;
        }

        csys1 = view.findViewById(R.id.csys1);
        csys1.setAdapter(new StringWheelAdapter(list));
        selectedArr[0] = TextUtils.isEmpty(selectedArr[0]) ? list.get(0) : selectedArr[0];

        if (TextUtils.isEmpty(selectedArr[0])) {
            csys1.setCurrentItem(0);
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(selectedArr[0])) {
                    csys1.setCurrentItem(i);
                    break;
                }
            }
        }

        csys1.setViewDisabled(disableFirst);

        csys2 = view.findViewById(R.id.csys2);
        csys2.setAdapter(new StringWheelAdapter(list));
        csys2.setCurrentItem(0);
        selectedArr[1] = TextUtils.isEmpty(selectedArr[1]) ? list.get(0) : selectedArr[1];

        if (TextUtils.isEmpty(selectedArr[1])) {
            csys2.setCurrentItem(0);
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(selectedArr[1])) {
                    csys2.setCurrentItem(i);
                    break;
                }
            }
        }

        csys2.setViewDisabled(disableSecond);

        csys3 = view.findViewById(R.id.csys3);
        csys3.setAdapter(new StringWheelAdapter(list));
        selectedArr[2] = TextUtils.isEmpty(selectedArr[2]) ? list.get(0) : selectedArr[2];

        if (TextUtils.isEmpty(selectedArr[2])) {
            csys3.setCurrentItem(0);
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(selectedArr[2])) {
                    csys3.setCurrentItem(i);
                    break;
                }
            }
        }

        csys3.setViewDisabled(disableThird);

        STWheelView.OnItemSelectedListener csys1Listener = index -> selectedArr[0] = list.get(index);

        STWheelView.OnItemSelectedListener csys2Listener = index -> selectedArr[1] = list.get(index);

        STWheelView.OnItemSelectedListener csys3Listener = index -> selectedArr[2] = list.get(index);

        csys1.setOnItemSelectedListener(csys1Listener);
        csys2.setOnItemSelectedListener(csys2Listener);
        csys3.setOnItemSelectedListener(csys3Listener);
    }

    public String[] getSelectedArr() {
        return selectedArr;
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        csys1.setCyclic(cyclic);
        csys2.setCyclic(cyclic);
        csys3.setCyclic(cyclic);
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
