package com.ssmalllucky.android.uikit.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ssmalllucky.android.ui.utils.DisplayUtils;
import com.ssmalllucky.android.uikit.databinding.ActivityViewTestBinding;

/**
 * @ClassName ViewTestUI
 * @Author shuaijialin
 * @Date 2024/3/22
 * @Description
 */
public class ViewTestUI extends AppCompatActivity {

    public static final String TAG = "ViewTestUI";

    private ActivityViewTestBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        binding.switchView.setChecked(true);

        binding.switchView.setOnCheckChangedListener(isCheck -> Log.d(TAG, "onCheckChanged: " + isCheck));

        binding.testCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
            }
        });

//        binding.dynamicTVWithWidth.adjustTextSize(DisplayUtils.dip2px(this,10),12);
    }
}
