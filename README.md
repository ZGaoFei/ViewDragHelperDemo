# ViewDragHelperDemo

> ViewDragHelper 的内部实现是通过类似Scroller机制的OverScroller来实现的


    这两个方法都是View内容实现的
    scrollTo(x, y)：根据当前View的【初始】位置滑动到(x, y)指定的位置
    scrollBy(x, y)：根据当前View的【当前】位置滑动到(x, y)指定的位置
    初始位置为view绘制的位置，当前位置为view上一次滑动后的位置
    x和y都是像素值，x为负值表示向右移动，为正值表示向左移动，y为负值表示向下移动，y为正值表示向上移动
    
    两个方法移动的都是【内容】
    即如果是ViewGroup移动的是内部的子view
    如果是View移动的是View的内容，如TextView移动的是显示的内容
    
    Scroller使用步骤：
    1、创建Scroller，
    new Scroller(context)
    2、调用startScroll()方法来初始化滚动数据并刷新界面
    mScroller.startScroll(x, y, dx, dy);
    invalidate();
    3、重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
    
    第二、三步，首先初始化Scroller的滚动数据，然后调用invalidate()，
    重写computeScroll()，并且在computeScroll()方法中还要调用invalidate()，
    是因为，computeScroll()方法在View中是一个空方法，它的调用是在draw(canvas)
    方法中调用的，invalidate()会触发draw(canvas)方法（draw(canvas)会调用onDraw()方法），
    这样draw(canvas)又会触发computeScroll()方法，这样view就会动起来。
    注意：在重写computeScroll()方法时，要加入mScroller.computeScrollOffset()判断是否结束，
    不然的话，会一直无限循环的执行的重绘和computeScroll()方法
    