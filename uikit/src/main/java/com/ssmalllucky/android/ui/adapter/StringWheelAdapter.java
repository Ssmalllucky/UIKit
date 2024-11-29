package com.ssmalllucky.android.ui.adapter;

import java.util.List;

/**
 * @ClassName StringWheelAdapter
 * @Author shuaijialin
 * @Date 2024/3/14
 * @Description
 */
public class StringWheelAdapter implements WheelAdapter{

    private List<String> list;

    public StringWheelAdapter(List<String> list){
        this.list = list;
    }

    @Override
    public int getItemsCount() {
        return list.size();
    }

    @Override
    public Object getItem(int index) {
        return list.get(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }
}
