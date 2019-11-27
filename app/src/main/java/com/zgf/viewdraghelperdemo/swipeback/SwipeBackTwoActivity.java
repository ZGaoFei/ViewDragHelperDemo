package com.zgf.viewdraghelperdemo.swipeback;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zgf.viewdraghelperdemo.R;

public class SwipeBackTwoActivity extends SwipeBaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, SwipeBackTwoActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_back_two);

        findViewById(R.id.tv_swipe_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwipeBackThreeActivity.start(SwipeBackTwoActivity.this);
            }
        });
    }
}
