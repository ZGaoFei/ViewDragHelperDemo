package com.zgf.viewdraghelperdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class DragTestActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, DragTestActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_test);
    }
}
