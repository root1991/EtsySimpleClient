package com.test.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.R;
import com.test.contract.SearchResultsContract;
import com.test.model.Product;
import com.test.presenter.SearchResultsPresenter;
import com.test.ui.adapter.ProductsAdapter;
import com.test.util.SpaceItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by andrewkhristyan on 12/27/15.
 */
public class SearchResultsActivity extends BaseActivity implements SearchResultsContract.View, ProductsAdapter.ItemClickListener {

    private static final String KEY_CATEGORY_NAME = "category_name";
    private static final String KEY_QUERY = "key_query";

    private SearchResultsContract.Presenter mPresenter;
    private ProductsAdapter mProductsAdapter;

    @Bind(R.id.recycler_view_search_results)
    RecyclerView mRecyclerViewSearchResults;
    @Bind(R.id.text_view_empty)
    TextView mTextViewEmpty;
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeContainer;

    public static Intent newIntent(Context context, String categoryName, String query) {
        Intent intent = new Intent(context, SearchResultsActivity.class);
        intent.putExtra(KEY_CATEGORY_NAME, categoryName);
        intent.putExtra(KEY_QUERY, query);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        ButterKnife.bind(this);

        setupToolbar();

        mPresenter = new SearchResultsPresenter();
        mPresenter.attachView(this);
        mPresenter.loadSearchResults(
                getIntent().getStringExtra(KEY_CATEGORY_NAME),
                getIntent().getStringExtra(KEY_QUERY), 1);

        setupRecyclerView();

        mSwipeContainer.setColorSchemeResources(R.color.colorPrimary);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mProductsAdapter.clear();
                mPresenter.loadSearchResults(
                        getIntent().getStringExtra(KEY_CATEGORY_NAME),
                        getIntent().getStringExtra(KEY_QUERY), 1);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
        mSwipeContainer.setRefreshing(false);
    }

    @Override
    public void addSearchResultsItems(List<Product> products) {
        mProductsAdapter.addProducts(products, false);
        mTextViewEmpty.setVisibility(mProductsAdapter.isAdapterEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onItemClick(Product product) {
        startActivity(ProductDetailActivity.newIntent(this, product));
    }

    /**
     * Here we setup our recycler view
     * We should add item decoration for correct item dimens
     * also we should add endless scroll adapter
     */
    private void setupRecyclerView() {
        mProductsAdapter = new ProductsAdapter(R.layout.item_product_grid, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerViewSearchResults.addItemDecoration(new SpaceItemDecoration(2));
        mRecyclerViewSearchResults.setLayoutManager(gridLayoutManager);
        mRecyclerViewSearchResults.addOnScrollListener(new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                mPresenter.loadSearchResults(
                        getIntent().getStringExtra(KEY_CATEGORY_NAME),
                        getIntent().getStringExtra(KEY_QUERY), currentPage);
            }
        });
        mRecyclerViewSearchResults.setAdapter(mProductsAdapter);
    }

    /**
     * Setup current toolbar
     */
    private void setupToolbar() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.str_search_results);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
