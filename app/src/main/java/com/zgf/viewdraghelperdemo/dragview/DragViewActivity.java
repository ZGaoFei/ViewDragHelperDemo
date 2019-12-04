package com.zgf.viewdraghelperdemo.dragview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zgf.viewdraghelperdemo.R;
import com.zgf.viewdraghelperdemo.swipedelete.NameEntity;

import java.util.ArrayList;
import java.util.List;

public class DragViewActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, DragViewActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_view);

        final DragViewLayout layout = findViewById(R.id.drag_view_layout);
        layout.setOnTypeListener(new DragViewLayout.OnTypepListener() {
            @Override
            public void onOpened() {
                Log.i("DragViewActivity", "DragViewLayout opened");
            }

            @Override
            public void onClosed() {
                Log.i("DragViewActivity", "DragViewLayout closed");
            }
        });
        TextView textView = findViewById(R.id.tv_close);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("DragViewActivity", "click close");
                layout.close();
            }
        });

        initRecyclerView();
        initUnderRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycle_view_drag_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<String> list = initData();
        DragViewAdapter adapter = new DragViewAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    private void initUnderRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.under_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<String> list = initData();
        DragViewAdapter adapter = new DragViewAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    private List<String> initData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("item: " + (i + 1));
        }
        return list;
    }
}
