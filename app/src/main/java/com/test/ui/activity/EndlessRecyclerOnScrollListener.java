package com.test.ui.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by andrewkhristyan on 12/27/15.
 */
public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {


    private int mPreviousTotal = 0;
    private boolean loading = true;

    private int mCurrentPage = 1;

    private GridLayoutManager mGridLayoutManager;

    public EndlessRecyclerOnScrollListener(GridLayoutManager gridLayoutManager) {
        this.mGridLayoutManager = gridLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = mGridLayoutManager.getItemCount();
        int firstVisibleItem = mGridLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > mPreviousTotal) {
                loading = false;
                mPreviousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount)
                <= firstVisibleItem) {
            mCurrentPage++;
            onLoadMore(mCurrentPage);
            loading = true;
        }
    }

    public abstract void onLoadMore(int currentPage);

}
