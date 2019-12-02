package com.zgf.viewdraghelperdemo.swipedelete;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zgf.viewdraghelperdemo.R;

import java.util.List;

/**
 * 把所有操作逻辑全都放在了adapter中处理了
 *
 * 功能：
 * 删除
 * 打开一个关闭其他的所有打开的（有且只有一个处于打开状态）
 * 打开或者关闭状态保存（打开或者关闭状态在滑出屏幕后再滑回来状态保留）
 * 滑动时是否关闭已打开的item（有待确认）
 */
public class SwipeDeleteAdapter extends RecyclerView.Adapter<SwipeDeleteAdapter.SwipeDeleteHolder> {

    private Context context;
    private List<NameEntity> list;
    private RecyclerView recyclerView;

    public SwipeDeleteAdapter(Context context, List<NameEntity> list, RecyclerView recyclerView) {
        this.context = context;
        this.list = list;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public SwipeDeleteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_swipe_delete, parent, false);
        return new SwipeDeleteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SwipeDeleteHolder holder, int position) {
        NameEntity entity = list.get(position);
        String name = entity.getName();
        holder.textView.setText(name);

        if (entity.getIsOpen() == 0) {
            holder.deleteLayout.setStatus(SwipeDeleteLayout.Status.CLOSE);
        } else {
            holder.deleteLayout.setStatus(SwipeDeleteLayout.Status.OPEN);
        }
        setViewListener(holder, position);
    }

    private void setViewListener(final SwipeDeleteHolder holder, final int position) {
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show("delete item: " + position);

                deleteItem(position);
            }
        });

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show("click item: " + position);

                holder.deleteLayout.close();
            }
        });

        holder.deleteLayout.setSwipeListener(new SwipeDeleteLayout.SwipeChangeListenerAdapter() {

            @Override
            public void onClose() {
                super.onClose();
                list.get(position).setIsOpen(0);
            }

            @Override
            public void onStartOpen() {
                super.onStartOpen();
                closeOther();
            }

            @Override
            public void onOpen() {
                super.onOpen();
                list.get(position).setIsOpen(1);
            }
        });
    }

    private void deleteItem(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }

    private void closeOther() {
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int first = manager.findFirstVisibleItemPosition();
        int last = manager.findLastVisibleItemPosition();

        for (int i = first; i <= last; i++) {
            int isOpen = list.get(i).getIsOpen();
            if (isOpen == 1) {
                View child = recyclerView.getChildAt(i - first);
                SwipeDeleteHolder holder = (SwipeDeleteHolder) recyclerView.getChildViewHolder(child);
                holder.deleteLayout.close();
            }
        }

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setIsOpen(0);
        }
    }

    private void show(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class SwipeDeleteHolder extends RecyclerView.ViewHolder {
        private SwipeDeleteLayout deleteLayout;
        private TextView tvDelete;
        private TextView textView;

        public SwipeDeleteHolder(@NonNull View itemView) {
            super(itemView);

            deleteLayout = itemView.findViewById(R.id.item_swipe_delete_layout);
//            deleteLayout.setDragType(SwipeDeleteLayout.DRAG_TYPE_STILL);
            tvDelete = itemView.findViewById(R.id.tv_item_delete);
            textView = itemView.findViewById(R.id.tv_name_str);
        }
    }
}
