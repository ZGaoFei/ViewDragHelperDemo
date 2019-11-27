package com.zgf.viewdraghelperdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zgf.viewdraghelperdemo.swipeback.SwipeBackFirstActivity;

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
    }
}
