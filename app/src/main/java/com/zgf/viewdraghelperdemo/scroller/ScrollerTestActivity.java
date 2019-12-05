package com.zgf.viewdraghelperdemo.scroller;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zgf.viewdraghelperdemo.R;

public class ScrollerTestActivity extends AppCompatActivity {
    private LinearLayout linearLayout;

    public static void start(Context context) {
        context.startActivity(new Intent(context, ScrollerTestActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller_test);

        initView();
    }

    private void initView() {
        linearLayout = findViewById(R.id.linear_layout);
        final TextView textOne = findViewById(R.id.tv_one);
        final TextView textTwo = findViewById(R.id.tv_two);

        textOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                linearLayout.scrollTo(-100, -100);

//                textOne.scrollTo(-10, -10);

                startAnimation();
            }
        });

        textTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.scrollTo(-200, -200);

//                textTwo.scrollBy(-10, -10);
            }
        });
    }

    private void startAnimation() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 400);
        animator.setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                linearLayout.scrollTo(-value, -value);
            }
        });
        animator.start();
    }
}
