package com.test.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.R;
import com.test.contract.FeaturedContract;
import com.test.model.Product;
import com.test.presenter.FeaturedPresenter;
import com.test.ui.activity.ProductDetailActivity;
import com.test.ui.adapter.ProductsAdapter;
import com.test.ui.adapter.SimpleDividerItemDecoration;
import com.test.util.SimpleItemTouchHelperCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by andrewkhristyan on 12/27/15.
 */
public class FeaturedFragment extends BaseFragment implements FeaturedContract.View, ProductsAdapter.ItemClickListener {

    @Bind(R.id.recycler_view_featured)
    RecyclerView mRecyclerViewFeatured;
    @Bind(R.id.text_view_empty)
    TextView mTextViewEmpty;

    private ProductsAdapter mAdapter;
    private FeaturedContract.Presenter mPresenter;
    private ItemTouchHelper mItemTouchHelper;

    public static FeaturedFragment newInstance() {
        Bundle args = new Bundle();
        FeaturedFragment fragment = new FeaturedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_featured, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new FeaturedPresenter();
        mPresenter.attachView(this);

        setupRecyclerView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadFeaturedItems();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mPresenter.detachView();
    }

    @Override
    public void showFeaturedProducts(List<Product> featuredProducts) {
        mTextViewEmpty.setVisibility(featuredProducts.isEmpty() ? View.VISIBLE : View.GONE);
        mAdapter.addProducts(featuredProducts, true);
    }

    @Override
    public void onItemClick(Product product) {
        startActivity(ProductDetailActivity.newIntent(getContext(), product));
    }

    private void setupRecyclerView() {
        mAdapter = new ProductsAdapter(R.layout.item_featured, this);
        mRecyclerViewFeatured.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewFeatured.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewFeatured.addItemDecoration(new SimpleDividerItemDecoration(getContext()));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerViewFeatured);

        mRecyclerViewFeatured.setAdapter(mAdapter);
    }
}
