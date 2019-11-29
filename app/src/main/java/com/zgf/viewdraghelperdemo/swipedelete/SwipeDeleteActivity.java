package com.zgf.viewdraghelperdemo.swipedelete;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zgf.viewdraghelperdemo.R;
import com.zgf.viewdraghelperdemo.test.DragLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class SwipeDeleteActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private SwipeDeleteLayout layout;

    public static void start(Context context) {
        context.startActivity(new Intent(context, SwipeDeleteActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_delete);

        initSwipeDeleteView();
        initView();
    }

    private void initSwipeDeleteView() {
        layout = findViewById(R.id.swipe_delete_layout);
        layout.setDragType(SwipeDeleteLayout.DRAG_TYPE_FOLLOW);
        layout.setSwipeListener(new SwipeDeleteLayout.SwipeChangeListener() {
            @Override
            public void onDraging() {
                Log.i("SwipeDeleteActivity", "onDraging");
            }

            @Override
            public void onOpen() {
                Log.i("SwipeDeleteActivity", "onOpen");
            }

            @Override
            public void onClose() {
                Log.i("SwipeDeleteActivity", "onClose");
            }

            @Override
            public void onStartOpen() {
                Log.i("SwipeDeleteActivity", "onStartOpen");
            }

            @Override
            public void onStartClose() {
                Log.i("SwipeDeleteActivity", "onStartClose");
            }
        });

        TextView tvDelete = findViewById(R.id. tv_delete);
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show("delete");

                layout.close();
            }
        });
    }

    private void initView() {
        recyclerView = findViewById(R.id.swipe_back_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SwipeDeleteAdapter adapter = new SwipeDeleteAdapter(this, initData(), recyclerView);
        recyclerView.setAdapter(adapter);
    }

    private List<NameEntity> initData() {
        List<NameEntity> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            NameEntity nameEntity = new NameEntity("小明" + (i + 1) + "号", 0);
            list.add(nameEntity);
        }
        return list;
    }

    private void show(String message) {
        Toast.makeText(SwipeDeleteActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
