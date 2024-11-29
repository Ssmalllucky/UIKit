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
public class PopupSpinnerAdapter2 extends RecyclerView.Adapter<PopupSpinnerAdapter2.VH> {

    private final Context context;
    private final List<String> list;

    private int selectedFlag = -1;

    private STSpinnerItem2 spinnerItem;

    private OnItemSelectedListener listener;

    public PopupSpinnerAdapter2(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public PopupSpinnerAdapter2(Context context, List<String> list, OnItemSelectedListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemview_popupwindow, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        if (selectedFlag == holder.getAdapterPosition()) {
            holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.selector_popspinner_item_selected));
            holder.textView.setTextColor(ContextCompat.getColor(context, R.color.activeColor));
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.textView.setTextColor(ContextCompat.getColor(context, R.color.text_color_primary));
            holder.imageView.setVisibility(View.INVISIBLE);
        }

        holder.textView.setText(list.get(position));

        holder.itemView.setOnClickListener(v -> {
            if (selectedFlag != holder.getAdapterPosition()) {
                int temp = selectedFlag;
                notifyItemChanged(temp, false);
                selectedFlag = holder.getAdapterPosition();
                notifyItemChanged(selectedFlag, false);
                this.spinnerItem.updateSelection(selectedFlag);
                this.spinnerItem.setContentValue(list.get(holder.getAdapterPosition()));
            } else {
                this.spinnerItem.dismissPopSpinner();
            }
            if (listener != null)
                listener.onItemSelected(spinnerItem.getContentValue(), holder.getAdapterPosition());
        });
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    public void setSpinnerItem(STSpinnerItem2 spinnerItem) {
        this.spinnerItem = spinnerItem;
    }

    public void setSelectedFlag(int selectedFlag) {
        this.selectedFlag = selectedFlag;
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

            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.searchImageView);
        }
    }

    public List<String> getList() {
        return list;
    }

    public interface OnItemSelectedListener {
        void onItemSelected(String item, int position);
    }
}
