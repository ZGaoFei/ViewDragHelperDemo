package com.zgf.viewdraghelperdemo.swipeback;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zgf.viewdraghelperdemo.test.SwipeBackLayout;

/**
 * 侧滑退出效果
 *
 * 要显示下面的activity，因此要设置activity背景色透明
 */
public class SwipeBaseActivity extends AppCompatActivity {
    private SwipeBackLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = new SwipeBackLayout(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        layout.attachActivity(this);
    }

}
