package com.example.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;
import android.widget.TextView;

import java.util.jar.Attributes;

public class SlideLayout extends FrameLayout {

    private static final String TAG = "SlideLayout";
    private TextView contentView;
    private TextView menuView;
    private int viewHeight;
    private int contentWidth;
    private int menuWidth;

    private Scroller scroller;

    public SlideLayout(Context context, AttributeSet attrs){
        super(context, attrs);
        scroller = new Scroller(context);
    }

    //布局文件加载完成时调用
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.i(TAG, "onFinishInflate");
        contentView = (TextView)findViewById(R.id.content);
        menuView = (TextView)findViewById(R.id.menu);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure");
        viewHeight = menuView.getMeasuredHeight();
        contentWidth = contentView.getMeasuredWidth();
        menuWidth = menuView.getMeasuredWidth();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG, "onLayout");
        menuView.layout(contentWidth, 0, contentWidth + menuWidth, viewHeight);
    }

    private float startX;
    private float startY;

    private float downX;
    private float downY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = event.getX();
                float endY = event.getY();

                float distanceX = endX - startX;
                int toScrollX = (int)(getScrollX() - distanceX);
                if(toScrollX < 0){
                    Log.i(TAG, ""+ toScrollX);
                    toScrollX = 0;
                }
                if(toScrollX > menuWidth){
                    Log.i(TAG, ""+ toScrollX);
                    toScrollX = menuWidth;
                }
                System.out.println("toScroll-->"+toScrollX+"-->"+getScrollX());
                scrollTo(toScrollX, getScrollY());

                startX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }


}
