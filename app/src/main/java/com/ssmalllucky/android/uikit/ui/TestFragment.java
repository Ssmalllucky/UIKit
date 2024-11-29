package com.ssmalllucky.android.uikit.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ssmalllucky.android.uikit.R;

/**
 * @ClassName TestFragment
 * @Author shuaijialin
 * @Date 2024/3/4
 * @Description
 */
public class TestFragment extends Fragment {

    private Context context;

    public TestFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(context).inflate(com.ssmalllucky.android.uikit.R.layout.activity_version, container, false);
    }
}
