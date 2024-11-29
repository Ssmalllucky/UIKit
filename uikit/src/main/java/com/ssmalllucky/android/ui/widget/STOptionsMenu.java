package com.ssmalllucky.android.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.adapter.helper.RecyclerViewBehaviours;
import com.ssmalllucky.android.ui.base.STPopupDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName STOptionsView
 * @Author shuaijialin
 * @Date 2024/3/22
 * @Description
 */
public class STOptionsMenu extends STPopupDialog {

    public static final String TAG = "STPopupOptions";

    private OptionsMenuAdapter adapter;

    private LinearLayoutManager manager;

    private int itemViewHeight = 0;

    public STOptionsMenu(Context context) {
        super(context);
        setContentView(LayoutInflater.from(mContext).inflate(R.layout.layout_options, (ViewGroup) getContext().getWindow().getDecorView(), false));
    }

    public void setAdapter() {
        adapter = new OptionsMenuAdapter(this,mContext, list,listener);
        if (mContentView == null || getContext() == null) {
            return;
        }
        long tempTime = System.currentTimeMillis();
        RecyclerView recyclerView = mContentView.findViewById(R.id.popupOptionsRecyclerView);
        recyclerView.setAdapter(adapter);
        manager = new LinearLayoutManager(getContext()) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                int childCount = getChildCount();
                for (int i = 0; i < childCount; i++) {
                    if (itemViewHeight != 0) {
                        return;
                    }
                    View child = getChildAt(i);
                    itemViewHeight = child.getHeight();
                }
            }
        };
        Log.d(TAG, "setAdapter: " + (System.currentTimeMillis() - tempTime) + " ms");
        tempTime = System.currentTimeMillis();
        RecyclerViewBehaviours.setStyle(mContext, recyclerView, manager, 0);
        Log.d(TAG, "setStyle: " + (System.currentTimeMillis() - tempTime) + " ms");
    }

    private final List<Item> list = new ArrayList<>();

    public void addItem(Item item) {
        list.add(item);
    }

    public void show(View view) {
        setAdapter();
        showAtLocation(view, Gravity.TOP | Gravity.START, 0, 0);
    }

    private OptionsMenuAdapter.OnItemClickListener listener;

    public OptionsMenuAdapter.OnItemClickListener getListener(){
        return listener;
    }

    public void setOnItemClickListener(OptionsMenuAdapter.OnItemClickListener listener){
        this.listener = listener;
    }

    public static class Item {

        int iconResId;
        String title;
        boolean enabled;

        public Item(int iconResId, String title, boolean enabled) {
            this.iconResId = iconResId;
            this.title = title;
            this.enabled = enabled;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIconResId() {
            return iconResId;
        }

        public void setIconResId(int iconResId) {
            this.iconResId = iconResId;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
}
