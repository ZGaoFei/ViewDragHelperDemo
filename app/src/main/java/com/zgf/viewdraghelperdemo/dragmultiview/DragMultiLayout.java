package com.zgf.viewdraghelperdemo.dragmultiview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;

/**
 * 默认最少包含两个子view
 */
public class DragMultiLayout extends FrameLayout {
    private ViewDragHelper mHelper;

    private View aboveView;
    private View underView;

    private int width;
    private int height;

    private int x;
    private int y;

    private boolean isLeftRemoved = false;
    private boolean isTopRemoved = false;

    private View dragView;
    // 默认已经包含了两个子view
    private int position = 1;

    private int max = 2;
    private AddViewListener mListener;

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return true;
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);

            if (isLeftRemoved || isTopRemoved) {
                position ++;
                if (position >= max) {
                    return;
                }
                if (mListener != null) {
                    View customerView = mListener.getView(position);
                    if (customerView != null) {
                        addView(customerView, 0);
                        mListener.addView(customerView, position);
                    } else {
                        addView(dragView, 0);
                        mListener.addView(dragView, position);
                    }
                } else {
                    addView(dragView, 0);
                    mListener.addView(dragView, position);
                }
            } else {
                mHelper.settleCapturedViewAt(x, y);
                invalidate();
            }
        }

        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            return mHelper.getTouchSlop();
        }

        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            return mHelper.getTouchSlop();
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            int currentLeft = Math.abs(left - x);
            if (currentLeft >= width * 1.5) {
                isLeftRemoved = true;
                dragView = child;
                removeView(child);
            } else  {
                isLeftRemoved = false;
            }
            return left;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            int currentTop = Math.abs(top - y);
            if (currentTop >= height * 1.5) {
                isTopRemoved = true;
                dragView = child;
                removeView(child);
            } else {
                isTopRemoved = false;
            }
            return top;
        }

        @Override
        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
            x = capturedChild.getLeft();
            y = capturedChild.getTop();
        }
    };

    public DragMultiLayout(Context context) {
        this(context, null);
    }

    public DragMultiLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragMultiLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mHelper = ViewDragHelper.create(this, callback);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        aboveView = getChildAt(0);
        underView = getChildAt(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = aboveView.getMeasuredWidth();
        height = aboveView.getMeasuredHeight();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mHelper != null && mHelper.continueSettling(true)) {
            invalidate();
        }
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setAddViewListener(AddViewListener listener) {
        mListener = listener;
    }

    public interface AddViewListener {
        void addView(View view, int position);

        View getView(int position);
    }
}
