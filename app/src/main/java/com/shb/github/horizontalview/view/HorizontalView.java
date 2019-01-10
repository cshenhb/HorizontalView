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
    private float preValue;

    public float getStartRange() {
        return startRange;
    }

    public float getTotalRange() {
        return totalRange;
    }

    public void setRange(float startRange, float totalRange) {
        this.setStartRange(startRange);
        this.setTotalRange(totalRange);
        requestLayout();
    }

    public void setStartRange(float startRange) {
        this.startRange = startRange;
    }

    public void setTotalRange(float totalRange) {
        this.totalRange = totalRange;
    }

    private float startRange;
    private float totalRange = 1;

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

        BigDecimal bigDecimal1 = new BigDecimal(getStartRange());
        BigDecimal bigDecimal2 = new BigDecimal(getTotalRange());
        BigDecimal bigDecimal3 = new BigDecimal(total);


        preValue = bigDecimal1.divide(bigDecimal2, 3, BigDecimal.ROUND_HALF_EVEN).multiply(bigDecimal3).floatValue();

        Log.i(TAG, "onMeasure: pre: " + (getStartRange() / getTotalRange()));
        Log.i(TAG, "onMeasure: total: " + total + "   preValue: " + preValue + " startRange: " + getStartRange() + " totalRange: " + getTotalRange());

        int v = (int) (TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_PX, preValue, getContext().getResources().getDisplayMetrics()) + 0.5f);
        int v1 = (int) (TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getContext().getResources().getDisplayMetrics()) + 0.5f);
        setMeasuredDimension(v, v1);
    }

    public void startX(int x) {
        BigDecimal bigDecimal1 = new BigDecimal(x);
        BigDecimal bigDecimal2 = new BigDecimal(getTotalRange());

        startX = startX + (bigDecimal1.divide(bigDecimal2, 3, BigDecimal.ROUND_HALF_EVEN).floatValue() * (total));


        this.setTranslationX(startX);
    }


    public void bindRecyclerView(RecyclerView recyclerView) {
        if (recyclerView == null) {
            throw new NullPointerException("null");
        }
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager && ((LinearLayoutManager) recyclerView.getLayoutManager()).getOrientation() != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("必须是水平方向的LayoutManger");
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
                    int totalRange = recyclerView.computeHorizontalScrollRange();
                    setRange(startRange, totalRange);
                    Log.i(TAG, "onScrolled: starRange: " + startRange + " scrollRange: " + startRange);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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
