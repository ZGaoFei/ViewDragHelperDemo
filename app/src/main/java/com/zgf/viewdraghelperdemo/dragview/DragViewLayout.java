package com.zgf.viewdraghelperdemo.dragview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 简单的折叠和展开的例子
 */
public class DragViewLayout extends FrameLayout {
    private static final String TAG = "DragViewLayout";

    private ViewDragHelper helper;

    private View underView;
    private View aboveView;
    private RecyclerView recyclerView;

    private int underViewHeight;
    private int distanceY;

    private OnTypepListener listener;

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return child == aboveView;
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);

            int top = releasedChild.getTop();
            Log.i(TAG, "getTop: " + top);
            if (top > 500) {
                helper.settleCapturedViewAt(0, underViewHeight);
                if (listener != null) {
                    listener.onOpened();
                }
            } else {
                helper.settleCapturedViewAt(0, 0);
                if (listener != null) {
                    listener.onClosed();
                }
            }
            invalidate();
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return 0;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            if (top < 0) {
                top = 0;
            }
            return top;
        }

        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            return helper.getTouchSlop();
        }

        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            return helper.getTouchSlop();
        }
    };

    public DragViewLayout(Context context) {
        this(context, null);
    }

    public DragViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        helper = ViewDragHelper.create(this, callback);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        underView = getChildAt(0);
        aboveView = getChildAt(1);

        /**
         * 这里已知recycleview在布局中的位置
         */
        recyclerView = (RecyclerView) ((ViewGroup)aboveView).getChildAt(0);
        serScrollListener();
    }

    private void serScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                distanceY += dy;
                Log.i(TAG, "distanceY: " + distanceY);
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        underViewHeight = underView.getMeasuredHeight();
        Log.i(TAG, "underViewHeight: " + underViewHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return helper.shouldInterceptTouchEvent(ev);
    }

    /**
     * 解决布局中包含recyclerview导致的滑动冲突
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (distanceY > 0) {
            return recyclerView.onTouchEvent(event);
        } else {
            helper.processTouchEvent(event);
            return true;
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (helper != null && helper.continueSettling(true)) {
            invalidate();
        }
    }

    public void close() {
        helper.smoothSlideViewTo(aboveView, 0, 0);
        invalidate();

        if (this.listener != null) {
            listener.onClosed();
        }
    }

    public void setOnTypeListener(OnTypepListener listener) {
        this.listener = listener;
    }

    public interface OnTypepListener {
        void onOpened();

        void onClosed();
    }
}
