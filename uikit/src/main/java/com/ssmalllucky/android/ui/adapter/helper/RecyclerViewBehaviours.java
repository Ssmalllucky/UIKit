package com.ssmalllucky.android.ui.adapter.helper;

import android.content.Context;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ssmalllucky.android.ui.R;

public class RecyclerViewBehaviours {

    public static void setStyle(Context context, RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager, int offset) {

        if (context == null) {
            return;
        }

        LinearDividerItemDecoration decoration = new LinearDividerItemDecoration(context, LinearDividerItemDecoration.VERTICAL);
        decoration.setDrawable(ContextCompat.getColor(context, R.color.transparent), offset);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setLayoutManager(layoutManager);
        ViewGroup.LayoutParams params;
        if (recyclerView.getLayoutParams() != null) {
            params = recyclerView.getLayoutParams();
        } else {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        recyclerView.setLayoutParams(params);
    }

    public static void setGridStyle(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager, int spanCount, int offsetX, int offsetY) {
        recyclerView.setLayoutManager(layoutManager);
        GridSpaceItemDecoration decoration = new GridSpaceItemDecoration(spanCount, offsetX, offsetY);
        for (int i = 0; i < recyclerView.getItemDecorationCount(); i++) {
            recyclerView.removeItemDecorationAt(i);
        }
        recyclerView.addItemDecoration(decoration);
    }

    public static void setGridStyle2(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager, int spanCount, int offsetX, int offsetY) {
        recyclerView.setLayoutManager(layoutManager);
        int itemDecorationCount = recyclerView.getItemDecorationCount();
        for (int i = 0; i < itemDecorationCount; i++) {
            recyclerView.removeItemDecorationAt(i);
        }
        GridSpaceItemDecoration2 decoration = new GridSpaceItemDecoration2(spanCount, offsetX, offsetY);
        recyclerView.addItemDecoration(decoration);
    }
}
