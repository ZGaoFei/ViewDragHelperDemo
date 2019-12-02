package com.zgf.viewdraghelperdemo.test;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;


public class DragTestLayout extends FrameLayout implements View.OnTouchListener {
    private int mStatusBarHeight;

    // 初始位置
    private float x;
    private float y;
    // 子view的左上margin值
    private int leftMargin;
    private int topMargin;
    // 结束位置
    private float endX;
    private float endY;

    private View childView;
    private float viewWidth;
    private float viewHeight;

    public DragTestLayout(Context context) {
        this(context, null);
    }

    public DragTestLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragTestLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        setOnTouchListener(this);

        mStatusBarHeight = getStatusBarHeight(getContext());
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        childView = getChildAt(0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        viewWidth = childView.getMeasuredWidth();
        viewHeight = childView.getMeasuredHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
        leftMargin = lp.leftMargin;
        topMargin = lp.topMargin;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getViewDefaultLocation();
                break;
            case MotionEvent.ACTION_MOVE:
                updateChildView(event.getRawX(), event.getRawY());
                break;
            case MotionEvent.ACTION_UP:
                endX = event.getRawX();
                endY = event.getRawY();

                smoothScrollToDefault();
                break;
        }
        return true;
    }

    /**
     * 获取子view的初始位置坐标
     */
    private void getViewDefaultLocation() {
        x = childView.getLeft();
        y = childView.getTop();
        Log.i("DragTestLayout", "X: " + x + " Y: " + y);
    }

    /**
     * 松开手指后，自动回到原始位置
     */
    private void smoothScrollToDefault() {
//        startAnimator();

        startAnimation();
    }

    /**
     * 滑动过程中，更新子view的位置
     */
    private void updateChildView(float x, float y) {
        childView.setTranslationX(x - viewWidth / 2);
        childView.setTranslationY(y - mStatusBarHeight - viewHeight / 2);
    }

    /**
     * 根据子view的最终位置和初始位置，计算动画执行的时间
     */
    private int computerDuration() {
        int distanceX = (int) (endX - x);
        int distanceY = (int) (endY - y);
        Log.i("DragTestLayout", "endX: " + endX + " endY: " + endY);
        Log.i("DragTestLayout", "distanceX: " + distanceX + " distanceY: " + distanceY);
        int offSet = (int) Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        Log.i("DragTestLayout", "offSet: " + offSet);
        int duration = offSet / 2; // 暂定像素就是时间
        return duration;
    }

    /**
     * 执行动画
     */
    private void startAnimator() {
        int duration = computerDuration();

        ValueAnimator animator = ValueAnimator.ofInt(100, 0);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();

                setTranslation(value);
            }
        });
        animator.start();
    }

    private void setTranslation(int value) {
        int currentX = (int) ((endX - x) * value / 100 + x);
        int currentY = (int) ((endY - y) * value / 100 + y);

        Log.i("DragTestLayout", "currentX: " + currentX + " currentY: " + currentY);

        childView.setTranslationX(currentX - leftMargin);
        childView.setTranslationY(currentY - topMargin);
    }

    /**
     * 另一种动画方式
     *
     * 跟上面的一致，都是通过setTranslationX()
     * 和setTranslationY()得分方式实现
     */
    private void startAnimation() {
        int duration = computerDuration();
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(childView, "translationX", endX - viewWidth / 2, x - leftMargin);
        animatorX.setDuration(duration);
        animatorX.setInterpolator(new LinearInterpolator());
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(childView, "translationY", endY - mStatusBarHeight - viewHeight / 2, y - topMargin);
        animatorY.setDuration(duration);
        animatorY.setInterpolator(new LinearInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorX).with(animatorY);
        animatorSet.start();
    }

}
