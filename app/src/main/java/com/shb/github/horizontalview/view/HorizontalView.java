package com.shb.github.horizontalview.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;

import java.math.BigDecimal;

public class HorizontalView extends View {
    private static final String TAG = "HorizontalView";
    private float startX;
    private float total;
    //初始值
    private float preValue;
    //百分比
    private float percentage;

    public float getStartRange() {
        return startRange;
    }

    public float getScrollRange() {
        return scrollRange;
    }

    public void setRange(float startRange, float totalRange) {
        this.setStartRange(startRange);
        this.setScrollRange(totalRange);
        requestLayout();
    }

    public void setStartRange(float startRange) {
        this.startRange = startRange;
    }

    public void setScrollRange(float totalRange) {
        this.scrollRange = totalRange;
    }

    private float startRange;
    private float scrollRange;

    public HorizontalView(Context context) {
        this(context, null);
    }

    public HorizontalView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setBackgroundColor(Color.RED);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //100dp
        total = TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getContext().getResources().getDisplayMetrics());

        total = TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_PX, total, getContext().getResources().getDisplayMetrics());



/*
        BigDecimal bigDecimal1 = new BigDecimal(getStartRange());
        BigDecimal bigDecimal2 = new BigDecimal(getTotalRange());
        BigDecimal bigDecimal3 = new BigDecimal(total);


        preValue = bigDecimal1.divide(bigDecimal2, 3, BigDecimal.ROUND_HALF_EVEN).multiply(bigDecimal3).floatValue();

        Log.i(TAG, "onMeasure: pre: " + (getStartRange() / getTotalRange()));
        Log.i(TAG, "onMeasure: total: " + total + "   preValue: " + preValue + " startRange: " + getStartRange() + " totalRange: " + getTotalRange());*/

        preValue = percentage * total;
        Log.i(TAG, "onMeasure: preValue: " + preValue);
        int v = (int) (TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_PX, preValue, getContext().getResources().getDisplayMetrics()) + 0.5f);
        int v1 = (int) (TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getContext().getResources().getDisplayMetrics()) + 0.5f);
        setMeasuredDimension(v, v1);
    }

    public void startX(int x) {


        // 滑动的距离/总的
        float pre = div(x, scrollRange + startRange, 3);
        // pre = x(滑动的距离)/total;

        float nextX = pre * (total);

        startX = startX + nextX;

        if (startX < 0) {
            startX = 0;
        }

        if (startX > total - preValue) {
            startX = total - preValue;
        }


        this.setTranslationX(startX);
    }


    public void bindRecyclerView(RecyclerView recyclerView) {
        if (recyclerView == null) {
            throw new NullPointerException("null");
        }
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager && ((LinearLayoutManager) recyclerView.getLayoutManager()).getOrientation() != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("必须是水平方向的LayoutManger");
        }

        if (recyclerView.getAdapter() == null) {
            throw new NullPointerException("null");
        }

        initListener(recyclerView);
        initArags(recyclerView);


    }

    private void initArags(final RecyclerView recyclerView) {
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                try {
                    int startRange = recyclerView.getChildAt(recyclerView.getChildCount() - 1).getRight();
                    int scrollRange = recyclerView.computeHorizontalScrollRange() - startRange;
                    setRange(startRange, scrollRange);
                    percentage = div(startRange, startRange + scrollRange, 3);
                    Log.i(TAG, "onScrolled: starRange: " + startRange + " scrollRange: " + scrollRange + "  percentage: " + percentage + "  " + recyclerView.getWidth());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public static float div(float v1, float v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        if (v2 <= 0) {
            return 0;
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_EVEN).floatValue();
    }


    private void initListener(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                startX(dx);
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }


}
