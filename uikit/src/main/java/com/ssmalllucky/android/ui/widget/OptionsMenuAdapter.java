package com.ssmalllucky.android.ui.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ssmalllucky.android.ui.R;

import java.util.List;

/**
 * @ClassName PopupWindowAdapter
 * @Author shuaijialin
 * @Date 2024/1/24
 * @Description
 */
public class OptionsMenuAdapter extends RecyclerView.Adapter<OptionsMenuAdapter.VH> {

    private STOptionsMenu optionsMenu;
    private final Context context;
    private final List<STOptionsMenu.Item> list;

    private OnItemClickListener listener;

    public OptionsMenuAdapter(Context context, List<STOptionsMenu.Item> list) {
        this.context = context;
        this.list = list;
    }

    public OptionsMenuAdapter(STOptionsMenu optionsMenu, Context context, List<STOptionsMenu.Item> list, OnItemClickListener listener) {
        this.optionsMenu = optionsMenu;
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_SINGLE) {
            view = LayoutInflater.from(context).inflate(R.layout.itemview_popup_options_single, parent, false);
        } else if (viewType == VIEW_TYPE_HEADER) {
            view = LayoutInflater.from(context).inflate(R.layout.itemview_popup_options_top, parent, false);
        } else if (viewType == VIEW_TYPE_FOOTER) {
            view = LayoutInflater.from(context).inflate(R.layout.itemview_popup_options_bottom, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.itemview_popup_options, parent, false);
        }
        return new VH(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (list == null) {
            return super.getItemViewType(position);
        }

        if (list.size() == 1) {
            return VIEW_TYPE_SINGLE;
        } else if (position == 0) {
            return VIEW_TYPE_HEADER;
        } else if (position == list.size() - 1) {
            return VIEW_TYPE_FOOTER;
        } else {
            return super.getItemViewType(position);
        }
    }

    public static final int VIEW_TYPE_HEADER = 1;
    public static final int VIEW_TYPE_FOOTER = 2;
    public static final int VIEW_TYPE_SINGLE = 3;

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        holder.textView.setTextColor(ContextCompat.getColor(context, R.color.text_color_primary));

        if (list.get(position).getIconResId() != 0) {
            holder.imageView.setVisibility(View.VISIBLE);
            holder.imageView.setImageResource(list.get(position).getIconResId());
        } else {
            holder.imageView.setVisibility(View.INVISIBLE);
        }

        holder.textView.setText(list.get(position).getTitle());

        holder.itemView.setOnClickListener(v -> {
            if (optionsMenu.getListener() != null)
                optionsMenu.dismiss();
            optionsMenu.getListener().onItemClick(list.get(holder.getAdapterPosition()), holder.getAdapterPosition());
        });
    }

    public void setOnItemSelectedListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class VH extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView imageView;

        public VH(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.searchImageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    public List<STOptionsMenu.Item> getList() {
        return list;
    }

    public interface OnItemClickListener {
        void onItemClick(STOptionsMenu.Item item, int position);
    }
}
