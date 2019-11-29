package com.zgf.viewdraghelperdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.customview.widget.ViewDragHelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TestOneActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, TestOneActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_one);

        TextView tvOne = findViewById(R.id.tv_drag_one);
        tvOne.setTag("one");
        tvOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("click one text");
            }
        });

        TextView tvTwo = findViewById(R.id.tv_drag_two);
        tvTwo.setTag("two");
        tvTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("click two text");
            }
        });
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
