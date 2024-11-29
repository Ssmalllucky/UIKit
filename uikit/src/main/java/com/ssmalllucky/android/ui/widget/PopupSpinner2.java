package com.ssmalllucky.android.ui.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ssmalllucky.android.ui.R;
import com.ssmalllucky.android.ui.adapter.helper.RecyclerViewBehaviours;
import com.ssmalllucky.android.ui.base.STPopupDialog;

import java.util.List;

/**
 * @ClassName PopupSpinner
 * @Author shuaijialin
 * @Date 2024/1/24
 * @Description
 */
public class PopupSpinner2 extends STPopupDialog {

    public static final String TAG = "PopupSpinner";

    private NestedScrollView nestedScrollView;
    private RecyclerView recyclerView;
    private PopupSpinnerAdapter2 adapter;

    public PopupSpinner2(Context context) {
        super(context);
    }

    private LinearLayoutManager manager;

    private int itemViewHeight = 0;

    public void setAdapter(PopupSpinnerAdapter2 adapter) {
        if (mContentView == null || getContext() == null) {
            return;
        }
        this.adapter = adapter;
        long tempTime = System.currentTimeMillis();
        nestedScrollView = mContentView.findViewById(R.id.nestedScrollView);
        recyclerView = mContentView.findViewById(R.id.popupRecyclerView);
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

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        recyclerView.post(() -> scrollByDistance(itemViewHeight * mSelection));
    }

    private int nestedScrollViewTop;

    public void scrollByDistance(int dy) {
        if (nestedScrollViewTop == 0) {
            nestedScrollViewTop = nestedScrollView.getHeight() / 2;
        }

        int distance = dy - nestedScrollViewTop;
        nestedScrollView.scrollTo(0, distance);
    }


    private int mSelection = -1;

    public int getSelectionByValue(String value) {
        if (TextUtils.isEmpty(value) || adapter == null) {
            return -1;
        } else {
            List<String> list = adapter.getList();
            if (list != null && !list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    if (value.equals(list.get(i))) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    public void setSelection(int selection) {
        if (selection == -1 || selection >= adapter.getItemCount()) {
            return;
        }
        this.mSelection = selection;
        adapter.setSelectedFlag(selection);
    }

    @Override
    public void setContentView(View contentView) {
        super.setContentView(contentView);
    }

    public void setOnItemSelectedListener(PopupSpinnerAdapter2.OnItemSelectedListener listener) {
        if (adapter != null) {
            adapter.setOnItemSelectedListener(listener);
        }
    }
}
