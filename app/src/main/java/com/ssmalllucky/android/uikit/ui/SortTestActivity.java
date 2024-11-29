package com.ssmalllucky.android.uikit.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ssmalllucky.android.ui.utils.DisplayUtils;
import com.ssmalllucky.android.ui.widget.STItem;
import com.ssmalllucky.android.uikit.R;
import com.ssmalllucky.android.uikit.databinding.ActivitySortTestBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @ClassName SortTestActivity
 * @Author shuaijialin
 * @Date 2024/3/13
 * @Description
 */
public class SortTestActivity extends AppCompatActivity {

    private ActivitySortTestBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySortTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addViews();
    }

    List<STItem> totalList;
    List<STItem> requiredList;
    List<STItem> optionalList;

    private void addViews() {
        long start = System.currentTimeMillis();

        if (totalList == null) {
            totalList = new ArrayList<>();
        }

        if (requiredList == null) {
            requiredList = new ArrayList<>();
        }

        if (optionalList == null) {
            optionalList = new ArrayList<>();
        }

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.dip2px(this, 66));
        for (int i = 0; i < 10; i++) {
            STItem stItem = new STItem(this);
            stItem.setTag(R.id.tag_index, i);
            stItem.setTag(R.id.required, i % 4 == 0);
            stItem.setTitle("数据项" + (i + 1));

            stItem.setLayoutParams(layoutParams);
            if (i % 4 == 0) {
                requiredList.add(stItem);
                binding.requiredContainer.addView(stItem);
            } else {
                optionalList.add(stItem);
                binding.optionalContainer.addView(stItem);
            }
            totalList.add(stItem);
        }
        Log.d("TAG", "addViews: " + (System.currentTimeMillis() - start) + " ms");

        for (int i = 0; i < totalList.size(); i++) {
            Log.d("TAG", "for: " + totalList.get(i).getTag(R.id.tag_index));
        }
    }

    public void onReSort(View view) {

        requiredList.clear();
        optionalList.clear();

        for (int i = 0; i < 3; i++) {
            int random = new Random().nextInt(10);
            Log.d("TAG", "onReSort: " + random);
            for (int i1 = 0; i1 < totalList.size(); i1++) {
                if ((int) totalList.get(i1).getTag(R.id.tag_index) == random) {
                    totalList.get(i1).setTag(R.id.required, true);
                    requiredList.add(totalList.get(i1));
                } else {
                    totalList.get(i1).setTag(R.id.required, false);
                    optionalList.add(totalList.get(i1));
                }
            }
        }
        totalList.clear();

        totalList.addAll(requiredList);
        totalList.addAll(optionalList);

        resetViews();
    }

    private void resetViews() {

        for (int i = 0; i < binding.requiredContainer.getChildCount(); i++) {
            STItem item = (STItem) binding.requiredContainer.getChildAt(i);
            if (item.getParent() != null) {
                ViewGroup viewGroup = (ViewGroup) item.getParent();
                viewGroup.removeView(item); // <- fix
            }
        }

        for (int i = 0; i < binding.optionalContainer.getChildCount(); i++) {
            STItem item = (STItem) binding.optionalContainer.getChildAt(i);
            if (item.getParent() != null) {
                ViewGroup viewGroup = (ViewGroup) item.getParent();
                viewGroup.removeView(item); // <- fix
            }
        }


//        for (int i = 0; i < requiredList.size(); i++) {
//            STItem stItem = new STItem(this);
//            stItem.setTag(R.id.tag_index, requiredList.get(i).getTag(R.id.tag_index));
//            stItem.setTag(R.id.required, requiredList.get(i).getTag(R.id.required));
//            stItem.setTitle((String)requiredList.get(i).getTag(R.id.tag_title));
//            binding.requiredContainer.addView(stItem);
//        }
//
//        for (int i = 0; i < optionalList.size(); i++) {
//            STItem stItem = new STItem(this);
//            stItem.setTag(R.id.tag_index, optionalList.get(i).getTag(R.id.tag_index));
//            stItem.setTag(R.id.required, optionalList.get(i).getTag(R.id.required));
//            stItem.setTitle((String)optionalList.get(i).getTag(R.id.tag_title));
//            binding.optionalContainer.addView(stItem);
//        }
    }
}
