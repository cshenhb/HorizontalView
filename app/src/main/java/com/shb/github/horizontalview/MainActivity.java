package com.shb.github.horizontalview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.shb.github.horizontalview.view.HorizontalView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private int starRange = 0;
    private int scrollRange;
    private HorizontalView mHorizontalView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.hRecyclerView);
        mHorizontalView = (HorizontalView) findViewById(R.id.horView);


        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        MyAdapter adapter = new MyAdapter();
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                try {
                    starRange = mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1).getRight();
                    scrollRange = mRecyclerView.computeHorizontalScrollRange();
                    mHorizontalView.setRange(starRange, scrollRange);
                    mHorizontalView.setTotalRange(scrollRange);
                    Log.i(TAG, "onScrolled: starRange: " + starRange + " scrollRange: " + scrollRange);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                //
                // boolean b = recyclerView.canScrollHorizontally(0);

                //Log.i(TAG, "onScrolled: " + (!b ? "向左滑动" : "向右滑动"));

                //int range = recyclerView.computeHorizontalScrollRange();
                //Log.i(TAG, "onScrolled: dx: " + dx);
                Log.i(TAG, "onScrolled: " + recyclerView.canScrollHorizontally(0));

                /*if (recyclerView.canScrollHorizontally(0)) {
                }*/
                mHorizontalView.startX(dx);
                //Log.i(TAG, "onScrolled: range: "+range);


                //Log.i(TAG, "onScrolled: firstVisibleItemPosition: " + firstVisibleItemPosition + " lastVisibleItemPosition: " + lastVisibleItemPosition);
                //Log.i(TAG, "onScrolled: child==null: " + (childAt == null));

            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
        });

    }


    private class MyAdapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mTv.setText("测试数据..." + position);
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.text1);
        }

    }
}
