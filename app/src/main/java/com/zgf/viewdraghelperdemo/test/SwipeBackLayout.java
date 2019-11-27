package com.zgf.viewdraghelperdemo.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;

import com.zgf.viewdraghelperdemo.R;

public class SwipeBackLayout extends FrameLayout {

    private ViewDragHelper dragHelper;

    private Activity mActivity;
    private View mView;

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            // 默认不捕获view
            return false;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            // 拖动限制，大于左边界
            return Math.max(0, left);
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            // 上下不可拖动
            return 0;
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            // 大于屏幕一半向右移动，小于屏幕一半向左移动
            int left = releasedChild.getLeft();
            if (left > getWidth() / 2) {
                if (mActivity != null) {
                    mActivity.finish();
                    mActivity.overridePendingTransition(0, R.anim.right_out);
                }
            } else {
                dragHelper.settleCapturedViewAt(0, 0);
            }
            invalidate();
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            // 移动子view
            dragHelper.captureChildView(mView, pointerId);
        }
    };

    public SwipeBackLayout(Context context) {
        this(context, null);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        dragHelper = ViewDragHelper.create(this, callback);
        // 跟踪左边界拖动
        dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    public void attachActivity(Activity activity) {
        mActivity = activity;

        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View content = decorView.getChildAt(0);
        decorView.removeView(content);
        mView = content;
        addView(content);
        decorView.addView(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Touch Event 代理
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        // 子 View 需要更新状态
        if (dragHelper.continueSettling(true)) {
            invalidate();
        }
    }
}
