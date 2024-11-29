package com.ssmalllucky.android.uikit.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/27.
 */

public class STSlidingTabTitleAdapter extends FragmentPagerAdapter {
    String ywlx;
    ArrayList<Map<String, String>> arrayList = new ArrayList();
    ArrayList<Fragment> fragmentsList = new ArrayList();

    public STSlidingTabTitleAdapter(FragmentManager fm, ArrayList<Map<String, String>> arrayList, ArrayList<Fragment> fragmentsList) {
        super(fm);
        this.arrayList = arrayList;
        this.fragmentsList = fragmentsList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return new Gson().toJson(arrayList.get(position));
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }

}
