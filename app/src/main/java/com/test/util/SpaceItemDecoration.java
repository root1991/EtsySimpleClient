package com.test.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by andrewkhristyan on 11/27/15.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private static final int SPACING = 5;

    private int spanCount;

    public SpaceItemDecoration(int spanCount) {
        this.spanCount = spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int column = position % spanCount;

        outRect.left = column * SPACING / spanCount; // column * ((1f / spanCount) * spacing)
        outRect.right = SPACING - (column + 1) * SPACING / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
        if (position >= spanCount) {
            outRect.top = SPACING; // item top
        }
    }
}