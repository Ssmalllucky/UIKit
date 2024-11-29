package com.ssmalllucky.android.uikit.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ssmalllucky.android.core.utils.ResourceUtils;
import com.ssmalllucky.android.ui.widget.STCardView;
import com.ssmalllucky.android.uikit.R;
import com.ssmalllucky.android.uikit.entity.ModuleItem;
import com.ssmalllucky.android.uikit.ui.ImagesHandlingUI;
import com.ssmalllucky.android.uikit.ui.UITestActivity;
import com.ssmalllucky.android.uikit.ui.ViewTestUI;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName LauncherAdapter
 * @Author shuaijialin
 * @Date 2024/10/15
 * @Description
 */
public class LauncherAdapter extends RecyclerView.Adapter<LauncherAdapter.VH> {

    private Context context;

    private int recyclerViewWidth;

    private int spanCount;

    private static List<ModuleItem> list;

    static {
        list = new ArrayList<>();
        list.add(new ModuleItem("按钮", "ic_module_button"));
        list.add(new ModuleItem("功能组件", "123"));
        list.add(new ModuleItem("图片处理", "ic_module_images", "#00BBFF"));
        list.add(new ModuleItem("新版照片类型选择", "", "#9F6DB1"));
    }

    public LauncherAdapter(Context context, int recyclerViewWidth, int spanCount) {
        this.context = context;
        this.recyclerViewWidth = recyclerViewWidth;
        this.spanCount = spanCount;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemview_launcher, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        // 计算每个子项的宽度
//        int itemWidth = recyclerViewWidth / spanCount;

        // 设置子项的宽高
//        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
////        layoutParams.width = (int) (itemWidth * 0.8);
//        layoutParams.height = (int) (layoutParams.width * 1.2);
//        holder.itemView.setLayoutParams(layoutParams);
//
//        ViewGroup.LayoutParams cardLayoutParams = holder.cardView.getLayoutParams();
//        cardLayoutParams.width = layoutParams.width;
//        cardLayoutParams.height = layoutParams.height;
//        holder.cardView.setLayoutParams(cardLayoutParams);

        ModuleItem item = list.get(position);
        holder.cardView.setIcon(ResourceUtils.getResIdByDrawableName(context, item.getIcon()));
        if (!TextUtils.isEmpty(item.getColorString())) {
            holder.cardView.setParseColor(Color.parseColor(item.getColorString()));
        } else {
            holder.cardView.setColor(com.ssmalllucky.android.ui.R.color.theme_color_primary);
        }
        holder.cardView.setTitle(item.getTitle());

        holder.itemView.setOnClickListener(v -> {
            if (position == 0) {
                context.startActivity(new Intent(context, ViewTestUI.class));
            } else if (position == 1) {
                context.startActivity(new Intent(context, UITestActivity.class));
            } else if (position == 2) {
                context.startActivity(new Intent(context, ImagesHandlingUI.class));
            } else if (position == 3) {
                context.startActivity(new Intent(context, ImagesHandlingUI.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends RecyclerView.ViewHolder {

        private STCardView cardView;

        public VH(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
