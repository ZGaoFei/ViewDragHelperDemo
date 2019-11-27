package com.zgf.viewdraghelperdemo.swipeback;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zgf.viewdraghelperdemo.R;

public class SwipeBackThreeActivity extends SwipeBaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, SwipeBackThreeActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_back_three);

//        findViewById(R.id.tv_swipe_three).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SwipeBackThreeActivity.start(SwipeBackThreeActivity.this);
//            }
//        });
    }
}
