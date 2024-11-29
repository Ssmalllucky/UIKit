package com.ssmalllucky.android.uikit.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.gyf.immersionbar.ImmersionBar;
import com.ssmalllucky.android.ui.Interaction;
import com.ssmalllucky.android.ui.utils.DisplayUtils;
import com.ssmalllucky.android.ui.utils.KeyboardUtils;
import com.ssmalllucky.android.ui.widget.KeyboardIP;
import com.ssmalllucky.android.ui.widget.KeyboardPort;
import com.ssmalllucky.android.ui.widget.PopupSpinnerAdapter;
import com.ssmalllucky.android.ui.widget.STItem;
import com.ssmalllucky.android.ui.widget.STOptionsMenu;
import com.ssmalllucky.android.uikit.R;
import com.ssmalllucky.android.uikit.databinding.ActivityUiTestBinding;
import com.ssmalllucky.android.uikit.ui.adapter.STSlidingTabTitleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UITestActivity
 * @Author shuaijialin
 * @Date 2024/1/19
 * @Description
 */
public class UITestActivity extends AppCompatActivity {

    private ActivityUiTestBinding binding;

    private CardView toastBarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarColor(R.color.page_background).statusBarDarkFont(true).navigationBarColor(R.color.page_background).fitsSystemWindows(true).init();
        super.onCreate(savedInstanceState);
        binding = ActivityUiTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        View decorView = getWindow().getDecorView();
        ViewGroup contentView = decorView.findViewById(android.R.id.content);
        toastBarView = Interaction.makeToastBar(this);
        contentView.addView(toastBarView);


        binding.dmtbItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Interaction.showToastBar(UITestActivity.this, toastBarView, "用户名不能为空！", Interaction.TOAST_SUCCESS);
//                Interaction.showLoading(UITestActivity.this, null);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Interaction.showLoading(UITestActivity.this, "123456");
//                    }
//                }, 1000);
//                PopupSpinner popupDialog = new PopupSpinner(UITestActivity.this);
//                popupDialog.setContentView(LayoutInflater.from(UITestActivity.this).inflate(R.layout.layout_popupwindow, (ViewGroup) getWindow().getDecorView(), false));
//                popupDialog.showAtLocation(binding.dmtbItem.getTitleTextView(), Gravity.TOP | Gravity.START, 0, 0);
            }
        });

        binding.dialogItem.setOnClickListener((STItem.OnClickListener) v -> {
        });


        List<String> list = new ArrayList<>();
        list.add("01:大型汽车");
        list.add("02:小型汽车");
        list.add("03:使馆汽车");
        list.add("04:领馆汽车");
        list.add("05:境外汽车");
        list.add("06:外籍汽车");
        list.add("07:普通摩托车");
        list.add("08:轻便摩托车");
        list.add("09:使馆摩托车");
        list.add("10:领馆摩托车");
        list.add("11:境外摩托车");
        list.add("12:外籍摩托车");
//        list.add("13:低速车");
//        list.add("14:拖拉机");
//        list.add("15:挂车");
//        list.add("16:教练汽车");
//        list.add("17:教练摩托车");
//        list.add("18:试验汽车");
//        list.add("19:试验摩托车");
//        list.add("20:临时入境汽车");
//        list.add("21:临时入境摩托车");
//        list.add("22:临时行驶车");
//        list.add("23:警用汽车");
//        list.add("24:警用摩托");
//        list.add("25:原农机号牌");
//        list.add("26:香港入出境车");
//        list.add("27:澳门入出境车");
//        list.add("31:武警号牌");
//        list.add("32:军队号牌");
//        list.add("41:无号牌");
//        list.add("42:假号牌");
//        list.add("43:挪用号牌");
//        list.add("51:大型新能源车");
//        list.add("52:小型新能源车");
//        list.add("99:其他号牌");
//        PopupSpinnerAdapter adapter = new PopupSpinnerAdapter(this, list);
//        binding.pdpxItem.setAdapter(adapter);
        binding.pdpxItem.setSelection(2);

        binding.pdpxItem.setOnItemSelectedListener(new PopupSpinnerAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(String item, int position) {
                Log.d("TAG", "onItemClick: " + item + " position: " + position);
            }
        });

        binding.diyViewTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        KeyboardUtils.bindEditTextEvent(new KeyboardIP(this), binding.iptestItem.getEditText());
        KeyboardUtils.bindEditTextEvent(new KeyboardPort(this), binding.iptestItem.getEditText());
        STSlidingTabTitleAdapter slidingTabTitleAdapter = new STSlidingTabTitleAdapter(getSupportFragmentManager(), getTabstripArrylist(), getTabFragmentArrylist());
        binding.stViewPager.setAdapter(slidingTabTitleAdapter);
        binding.stSlidingTab.setViewPager(binding.stViewPager, this);

        binding.loadingItem.setOnClickListener(v -> {
            Interaction.showLoading(this);
            new Handler(Looper.getMainLooper()).postDelayed(() -> Interaction.showLoading(UITestActivity.this), 300);
            new Handler(Looper.getMainLooper()).postDelayed(() -> Interaction.showLoading(UITestActivity.this), 500);
            new Handler(Looper.getMainLooper()).postDelayed(() -> Interaction.showLoading(UITestActivity.this), 400);
        });

        List<String> list1 = new ArrayList<>();
        list1.add("");
        list1.add("白");
        list1.add("黑");
        list1.add("红");
        list1.add("蓝");
        list1.add("紫");
        list1.add("青");
        list1.add("黄");
        list1.add("绿");
        list1.add("灰");
        binding.csysChooseItem.setList(list1);
        String[] arrays = "白青".split("");
        binding.csysChooseItem.setSelectedArr(arrays);

        binding.title.rightIV.setVisibility(View.VISIBLE);
        binding.title.rightIV.setImageResource(R.drawable.ic_options_menu);
        binding.title.rightIV.setOnClickListener(v -> {
            STOptionsMenu menu = new STOptionsMenu(this);
            menu.addItem(new STOptionsMenu.Item(0, "选项一", true));
            menu.addItem(new STOptionsMenu.Item(0, "选项二", true));
            menu.addItem(new STOptionsMenu.Item(0, "选项三", true));
            menu.show(binding.title.rightIV);
            menu.setOnItemClickListener((item, position) -> Log.d("TAG", "onItemClick: " + position));
        });

        ArrayList<String> list2 = new ArrayList<>();
        list2.add("Tom");
        list2.add("Jerry");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, list2);
        binding.textInputItem.setAdapter(adapter);
        binding.textInputItem.setInputType(EditorInfo.TYPE_TEXT_FLAG_CAP_WORDS);//数字键盘

        /////////////////////
        binding.qrCodeItem.setOnClickListener(v -> {
            ImageView imageView = new ImageView(UITestActivity.this);
            imageView.setImageResource(R.drawable.qrcode);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DisplayUtils.dip2px(this, 100f), DisplayUtils.dip2px(this, 100f));
            imageView.setLayoutParams(params);
            Interaction.showAnyDialog(UITestActivity.this, Interaction.TOAST_INFO, null, imageView, "关闭", null);
        });
    }

    public ArrayList<Map<String, String>> getTabstripArrylist() {
        Map<String, String> map1 = new HashMap();
        Map<String, String> map2 = new HashMap();
        Map<String, String> map3 = new HashMap();
        Map<String, String> map4 = new HashMap();
        map1.put("title", "登记信息");
        map2.put("title", "技术参数");
        map3.put("title", "公告信息");
        map4.put("title", "二维码");
        ArrayList<Map<String, String>> arrayList = new ArrayList<>();
        arrayList.add(map1);
        arrayList.add(map2);
        arrayList.add(map3);
        arrayList.add(map4);
        return arrayList;
    }

    public ArrayList<Fragment> getTabFragmentArrylist() {
        ArrayList<Fragment> fragmentslist = new ArrayList();
        fragmentslist.add(new TestFragment(this));
        fragmentslist.add(new TestFragment(this));
        fragmentslist.add(new TestFragment(this));
        fragmentslist.add(new TestFragment(this));
        return fragmentslist;
    }

    public void simpleDialog(View view) {
        Interaction.showPosDialog(this, Interaction.TOAST_ERROR, null, "发哈身份卡话费卡喝咖啡换卡后饭卡发哈", null);
    }

    public void warnDialogClick(View view) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        String replaced = "机动车外检登录成功#警示信息：当前录入的部分字段和部局字段不一致，请核对";
        String[] messageSplit = replaced.split("#");
        SpannableString spannableString = new SpannableString(messageSplit[0]);
        builder.append(spannableString);
        builder.append("\n");
        SpannableString warnSpannableString = new SpannableString(messageSplit[1]);
        warnSpannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, com.ssmalllucky.android.ui.R.color.errorColor)), 0, warnSpannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(warnSpannableString);
//        Interaction.showNegPosDialog(this, Interaction.TOAST_ERROR, null, builder, null);
    }
}
