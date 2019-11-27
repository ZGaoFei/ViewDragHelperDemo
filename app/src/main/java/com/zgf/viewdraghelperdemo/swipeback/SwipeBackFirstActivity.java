package com.zgf.viewdraghelperdemo.swipeback;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zgf.viewdraghelperdemo.R;

public class SwipeBackFirstActivity extends SwipeBaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, SwipeBackFirstActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_back_first);

        findViewById(R.id.tv_swipe_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwipeBackTwoActivity.start(SwipeBackFirstActivity.this);
            }
        });
    }
}
