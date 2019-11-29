package com.zgf.viewdraghelperdemo.swipedelete;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

/**
 * 侧滑删除控件
 *
 * 最少包括两个子view，前两个子view必须是ViewGroup
 * 第一个子view放置侧滑view
 * 第二个子view放置显示内容
 *
 * 支持两种滑动方式：DRAG_TYPE_FOLLOW，DRAG_TYPE_STILL
 *
 *  有待实现右侧滑动
 */
public class SwipeDeleteLayout extends FrameLayout {
    private static final String TAG = "SwipeDeleteLayout";

    public static final int DRAG_TYPE_FOLLOW = 0;
    public static final int DRAG_TYPE_STILL = 1;
    public static final int DRAG_EDGE_LEFT = 0;
    public static final int DRAG_EDGE_RIGHT = 1;

    private ViewDragHelper mDragHelper;
    private SwipeChangeListener mListener;

    private Status mStatus = Status.CLOSE;
    private int mDragType = DRAG_TYPE_FOLLOW;
    private int mDragEdge = DRAG_EDGE_LEFT;

    private View backView; //侧滑菜单
    private View frontView; //内容区域
    private int height; //自定义控件布局高
    private int width; //自定义控件布局宽
    private int range; //侧滑菜单可滑动范围

    private ViewDragHelper.Callback mCallBack = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            // 如果是跟着滑动，内容布局和侧滑布局都可以滑动
            if (mDragType == DRAG_TYPE_FOLLOW) {
                return true;
            } else { // 如果是侧滑静止显示，则侧滑布局不可滑动
                if (child == frontView) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        /**
         * 控制滑动的边界距离
         */
        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            if (child == frontView) {
                if (left > 0) {
                    return 0;
                } else if (left < -range) {
                    return -range; //边界不能超过-range
                }
            } else if (child == backView) {
                if (left > width) {
                    return width;
                } else if (left < width - range) {
                    return width - range;
                } else {

                }
            }
            return left;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return 0;
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            if (mDragType == DRAG_TYPE_FOLLOW) {
                if (changedView == frontView) {
                    backView.offsetLeftAndRight(dx); // 如果滑动的内容view，侧滑跟着动
                } else if (changedView == backView) {
                    frontView.offsetLeftAndRight(dx); // 如果滑动的侧滑view，内容view跟着动
                }
            }

            //事件派发
            dispatchSwipeEvent();
            //兼容低版本
            invalidate();
        }

        /**
         * 松开手指后，判断是打开还是关闭
         */
        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            if (xvel == 0 && frontView.getLeft() < -range * 0.5f) {
                open();
            } else if (xvel < 0) {
                open();
            } else {
                close();
            }
        }

        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            return mDragHelper.getTouchSlop();
        }

        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            return mDragHelper.getTouchSlop();
        }
    };

    /**
     * 根据当前状态判断回调事件
     */
    protected void dispatchSwipeEvent() {
        Status preStatus = mStatus;
        mStatus = updateStatus();

        if (mListener != null) {
            mListener.onDraging();
        }

        if (preStatus != mStatus && mListener != null) {
            if (mStatus == Status.CLOSE) {
                mListener.onClose();
            } else if (mStatus == Status.OPEN) {
                mListener.onOpen();
            } else if (mStatus == Status.DRAGING) {
                if (preStatus == Status.CLOSE) {
                    mListener.onStartOpen();
                } else if (preStatus == Status.OPEN) {
                    mListener.onStartClose();
                }
            }
        }
    }

    /**
     * 更改状态
     */
    private Status updateStatus() {
        int left = frontView.getLeft();
        if (left == 0) {
            return Status.CLOSE;
        } else if (left == -range) {
            return Status.OPEN;
        }
        return Status.DRAGING;
    }

    /**
     * 拖拽状态
     */
    public enum Status {
        OPEN, CLOSE, DRAGING
    }

    /**
     * 拖拽回调
     */
    public interface SwipeChangeListener {
        void onDraging();

        void onOpen();

        void onClose();

        void onStartOpen();

        void onStartClose();
    }

    public abstract static class SwipeChangeListenerAdapter implements SwipeChangeListener {

        @Override
        public void onDraging() {

        }

        @Override
        public void onOpen() {

        }

        @Override
        public void onClose() {

        }

        @Override
        public void onStartOpen() {

        }

        @Override
        public void onStartClose() {

        }
    }

    public SwipeDeleteLayout(Context context) {
        this(context, null);
    }

    public SwipeDeleteLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeDeleteLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mDragHelper = ViewDragHelper.create(this, mCallBack);
    }

    /**
     * 获取子view
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int childCount = getChildCount();
        if (childCount < 2) {
            throw new IllegalStateException("SwipeDeleteLayout must have at least two child view");
        }
        View childOne = getChildAt(0);
        View childTwo = getChildAt(1);
        if (!(childOne instanceof ViewGroup) || !(childTwo instanceof ViewGroup)) {
            throw new IllegalArgumentException("SwipeDeleteLayout have child view must is ViewGroup");
        }

        backView = childOne;
        frontView = childTwo;
    }

    /**
     * 获取子view的宽高
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = frontView.getMeasuredWidth();
        height = frontView.getMeasuredHeight();

        range = backView.getMeasuredWidth();

        Log.i(TAG, "width: " + width + " height: " + height + " range: " + range);
    }

    /**
     * 将子view放在指定的位置上
     * <p>
     * 让显示的布局盖住下面的操作按钮布局
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        layoutContent(mStatus == Status.OPEN);
    }

    private void layoutContent(boolean isOpen) {
        Rect frontRect = computerFrontViewRect(isOpen);
        frontView.layout(frontRect.left, frontRect.top, frontRect.right, frontRect.bottom);

        Rect backRect = computerBackViewRect(frontRect);
        backView.layout(backRect.left, backRect.top, backRect.right, backRect.bottom);
    }

    /**
     * 计算内容区域的位置
     */
    private Rect computerFrontViewRect(boolean isOpen) {
        int left = 0;
        if (isOpen) {
            left = -range;
        }
        return new Rect(left, 0, left + width, height);
    }

    /**
     * 计算侧滑区域的位置
     */
    private Rect computerBackViewRect(Rect frontRect) {
        int left = frontRect.right;
        if (mDragType == DRAG_TYPE_FOLLOW) {
            return new Rect(left, 0, left + range, height); // 将侧滑区域放到内容区域的右面
        } else {
            return new Rect(left - range, 0, left, height); // 将侧滑区域放到内容区域的右下面
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void setSwipeListener(SwipeChangeListener swipeListener) {
        this.mListener = swipeListener;
    }

    public void open() {
        open(true);
    }

    public void open(boolean isSmooth) {
        if (mStatus == Status.OPEN) {
            return;
        }
        int finalLeft = -range;
        if (isSmooth) {
            if (mDragHelper.smoothSlideViewTo(frontView, finalLeft, 0)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            layoutContent(true);
        }
    }

    public void close() {
        close(true);
    }

    public void close(boolean isSmooth) {
        if (mStatus == Status.CLOSE) {
            return;
        }
        int finalLeft = 0;
        if (isSmooth) {
            if (mDragHelper.smoothSlideViewTo(frontView, finalLeft, 0)) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            layoutContent(false);
        }
    }

    /**
     * 设置滑动的类型
     * <p>
     * 0：侧滑布局在内容的右边，跟着滑动出来
     * 1：侧滑布局在内容的下面，静止显示出来
     */
    public void setDragType(int dragType) {
        this.mDragType = dragType;
//        invalidate();
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    private void setDragEdge(int edge) {
        mDragEdge = edge;
    }
}
