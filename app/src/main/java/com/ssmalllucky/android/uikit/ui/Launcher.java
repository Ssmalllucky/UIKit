package com.ssmalllucky.android.uikit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.gyf.immersionbar.ImmersionBar;
import com.ssmalllucky.android.ui.adapter.helper.RecyclerViewBehaviours;
import com.ssmalllucky.android.ui.utils.DisplayUtils;
import com.ssmalllucky.android.uikit.R;
import com.ssmalllucky.android.uikit.databinding.ActivityLauncherBinding;
import com.ssmalllucky.android.uikit.ui.adapter.LauncherAdapter;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class Launcher extends AppCompatActivity {

    private ActivityLauncherBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarColor(R.color.white).statusBarDarkFont(true).navigationBarColor(R.color.white).fitsSystemWindows(true).init();
        super.onCreate(savedInstanceState);
        binding = ActivityLauncherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                binding.recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                binding.recyclerView.setAdapter(new LauncherAdapter(Launcher.this, binding.recyclerView.getWidth(), 2));
                RecyclerViewBehaviours.setGridStyle(binding.recyclerView, new GridLayoutManager(Launcher.this, 2), 2, DisplayUtils.dip2px(Launcher.this, 12f), DisplayUtils.dip2px(Launcher.this, 12f));
            }
        });
    }

    public void onViewTestClick(View view) {
        startActivity(new Intent(this, ViewTestUI.class));
    }

    public void onUITestClick(View view) {
        startActivity(new Intent(this, UITestActivity.class));
    }

    public void onLoadLargeItemClick(View view) {
        startActivity(new Intent(this, SortTestActivity.class));
    }

    public void onLargeObjectToMapClick(View view) {
    }

    public static Map<String, Object> getObjectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        Class<?> clazz = obj.getClass();
        System.out.println(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(obj);
            if (value == null) {
                value = "";
            }
            map.put(fieldName, value);
        }
        return map;
    }
}