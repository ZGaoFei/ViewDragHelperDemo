package com.zgf.viewdraghelperdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zgf.viewdraghelperdemo.dragmultiview.DragMultiTestActivity;
import com.zgf.viewdraghelperdemo.swipeback.SwipeBackFirstActivity;
import com.zgf.viewdraghelperdemo.swipedelete.SwipeDeleteActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        TextView tvOne = findViewById(R.id.tv_one);
        tvOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestOneActivity.start(MainActivity.this);
            }
        });

        TextView tvTwo = findViewById(R.id.tv_two);
        tvTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwipeBackFirstActivity.start(MainActivity.this);
            }
        });

        TextView tvThree = findViewById(R.id.tv_three);
        tvThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwipeDeleteActivity.start(MainActivity.this);
            }
        });

        TextView tv_four = findViewById(R.id.tv_four);
        tv_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DragTestActivity.start(MainActivity.this);
            }
        });

        TextView tv_five = findViewById(R.id.tv_five);
        tv_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DragMultiTestActivity.start(MainActivity.this);
            }
        });
    }
}
