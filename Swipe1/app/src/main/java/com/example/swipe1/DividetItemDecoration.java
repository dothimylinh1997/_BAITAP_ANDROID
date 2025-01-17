package com.example.swipe1;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class DividetItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;
    private boolean mShowFirstDivider = false;
    private boolean mShowLastDivider = false;

    private DividetItemDecoration(Context context, AttributeSet attrs){
        final TypedArray a = context
                .obtainStyledAttributes(attrs, new int[android.R.attr.listDivider]);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    public DividetItemDecoration(Context context, AttributeSet attrs, boolean showFirstDivider,
                                 boolean showLastDivider) {
        this(context, attrs);
        mShowFirstDivider = showFirstDivider;
        mShowLastDivider = showLastDivider;
    }

    public DividetItemDecoration(Drawable divider) {
        mDivider = divider;
    }

    public DividetItemDecoration(Drawable divider, boolean showFirstDivider,
                                 boolean showLastDivider) {
        this(divider);
        mShowFirstDivider = showFirstDivider;
        mShowLastDivider = showLastDivider;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if(mDivider == null){
            return;
        }
        if(parent.getChildPosition(view)<1){
            return;
        }

        if(getOrientation(parent)== LinearLayout.VERTICAL){
            outRect.top = mDivider.getIntrinsicHeight();
        }else{
            outRect.top = mDivider.getIntrinsicWidth();
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if(mDivider==null){
            super.onDrawOver(c, parent, state);
            return;
        }
        int left=0, right=0, top=0, bottom=0, size;
        int orientation = getOrientation(parent);
        int childCount = parent.getChildCount();

        if(orientation == LinearLayoutManager.VERTICAL){
            size = mDivider.getIntrinsicHeight();
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
        }else{
            size = mDivider.getIntrinsicWidth();
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
        }

        for (int i = mShowFirstDivider ? 0 : 1; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            if (orientation == LinearLayoutManager.VERTICAL) {
                top = child.getTop() - params.topMargin;
                bottom = top + size;
            } else { //horizontal
                left = child.getLeft() - params.leftMargin;
                right = left + size;
            }
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }

        // divider terakhir
        if (mShowLastDivider && childCount > 0) {
            View child = parent.getChildAt(childCount - 1);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            if (orientation == LinearLayoutManager.VERTICAL) {
                top = child.getBottom() + params.bottomMargin;
                bottom = top + size;
            } else { // horizontal
                left = child.getRight() + params.rightMargin;
                right = left + size;
            }
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private int getOrientation(RecyclerView parent){
        if(parent.getLayoutManager() instanceof LinearLayoutManager){
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            return layoutManager.getOrientation();
        }else{
            throw new IllegalStateException("bla bla");
        }
    }
}