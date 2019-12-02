package com.zgf.viewdraghelperdemo.dragmultiview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zgf.viewdraghelperdemo.R;

import java.util.ArrayList;
import java.util.List;

public class DragMultiTestActivity extends AppCompatActivity {

    private List<String> list;

    public static void start(Context context) {
        context.startActivity(new Intent(context, DragMultiTestActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_multi_test);

        initData();

        DragMultiLayout layout = findViewById(R.id.drag_multi_layout);
        layout.setMax(list.size());
        layout.setAddViewListener(new DragMultiLayout.AddViewListener() {
            @Override
            public void addView(View view, int position) {
                if (view instanceof TextView) {
                    TextView textView = (TextView) view;
                    textView.setText(list.get(position));
                } else {
                    TextView textView = view.findViewById(R.id.tv_item_drag);
                    textView.setText(list.get(position));
                }
            }

            @Override
            public View getView(int position) {
                if (position == 4) {
                    return getCustomerView(position);
                } else {
                    return null;
                }
            }
        });
    }

    private View getCustomerView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_drag_layout, null);
        TextView textView = view.findViewById(R.id.tv_item_drag);
        textView.setText(list.get(position));
        return view;
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("item: " + (i + 1));
        }
    }
}
