package com.zgf.viewdraghelperdemo.dragview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zgf.viewdraghelperdemo.R;

import java.util.List;

public class DragViewAdapter extends RecyclerView.Adapter<DragViewAdapter.DragViewHolder> {
    private List<String> list;
    private Context context;

    public DragViewAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DragViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_drag_view, parent, false);
        return new DragViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DragViewHolder holder, int position) {
        holder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class DragViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public DragViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_drag);
        }
    }
}
