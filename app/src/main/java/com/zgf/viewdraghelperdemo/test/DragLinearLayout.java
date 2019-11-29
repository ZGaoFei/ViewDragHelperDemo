package com.zgf.viewdraghelperdemo.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;

public class DragLinearLayout extends LinearLayout {
    private static final String TAG = "DragLinearLayout";

    private ViewDragHelper dragHelper;

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        int left;
        int top;

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            Log.e(TAG, "onViewDragStateChanged state: " + state);
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            Log.e(TAG, "onViewPositionChanged view: " + changedView.getId() + " left: " + left + " top: " + top + " dx: " + dx + " dy: " + dy);
        }

        @Override
        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
            Log.e(TAG, "onViewCaptured activePointerId: " + activePointerId);

            left = capturedChild.getLeft();
            top = capturedChild.getTop();
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            Log.e(TAG, "onViewReleased releasedChild: " + releasedChild.getId() + " xvel: " + xvel + " yvel: " + yvel);

            // 回弹
            dragHelper.settleCapturedViewAt(left, top);
            invalidate();
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
            Log.e(TAG, "onEdgeTouched edgeFlags: " + edgeFlags + " pointerId: " + pointerId);
        }

        @Override
        public boolean onEdgeLock(int edgeFlags) {
            Log.e(TAG, "onEdgeLock edgeFlags: " + edgeFlags);
            return super.onEdgeLock(edgeFlags);
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            super.onEdgeDragStarted(edgeFlags, pointerId);
            Log.e(TAG, "onEdgeDragStarted edgeFlags: " + edgeFlags + " pointerId: " + pointerId);
        }

        @Override
        public int getOrderedChildIndex(int index) {
            Log.e(TAG, "getOrderedChildIndex index: " + index);
            return super.getOrderedChildIndex(index);
        }

        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            Log.e(TAG, "getViewHorizontalDragRange child: " + child.getId());
            return dragHelper.getTouchSlop(); // 添加拖拽阈值
        }

        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            Log.e(TAG, "getViewVerticalDragRange child: " + child.getId());
            return dragHelper.getTouchSlop(); // 添加拖拽阈值
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            Log.e(TAG, "clampViewPositionHorizontal child: " + child.getId() + " left: " + left + " dx: " + dx);
            return left;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            Log.e(TAG, "clampViewPositionVertical child: " + child.getId() + " top: " + top + " dy: " + dy);
            return top;
        }

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            Log.e(TAG, "tryCaptureView child: " + child.getId() + " pointerId: " + pointerId);

            // 设置tag来区分抓取哪个view
            String tag = (String) child.getTag();
            Log.e(TAG, "tag: " + tag);
            if(tag.equals("one")) {
                return false;
            }
            return true;
        }

    };

    public DragLinearLayout(Context context) {
        this(context, null);
    }

    public DragLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        dragHelper = ViewDragHelper.create(this, callback);

        /**
         * sensitivity来控制灵敏度，越大越灵敏
         * 灵敏度为在布局上划过多少距离后布局才跟着动
         * 默认值为 1.0f
         */
//        dragHelper = ViewDragHelper.create(this, 0.1f, callback);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (dragHelper != null && dragHelper.continueSettling(true)) {
            invalidate();
        }
    }
}
