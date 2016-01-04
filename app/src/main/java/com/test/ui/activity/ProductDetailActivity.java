package com.test.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.test.R;
import com.test.contract.ProductDetailContract;
import com.test.model.Product;
import com.test.model.ProductImage;
import com.test.presenter.ProductDetailPresenter;
import com.test.ui.adapter.ImagesPagerAdapter;
import com.test.util.CircularPageIndicator;
import com.test.util.FragmentPageTransformer;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by andrewkhristyan on 12/27/15.
 */
public class ProductDetailActivity extends BaseActivity implements ProductDetailContract.View {

    public static final String KEY_PRODUCT = "product";

    @Bind(R.id.view_pager_images)
    ViewPager mViewPagerImages;
    @Bind(R.id.text_view_title)
    TextView mTextViewTitle;
    @Bind(R.id.text_view_description)
    TextView mTextViewDescription;
    @Bind(R.id.text_view_price)
    TextView mTextViewPrice;
    @Bind(R.id.button_save_to_favorite)
    Button mButtonSave;
    @Bind(R.id.page_indicator)
    CircularPageIndicator mCircularPageIndicator;

    private ProductDetailContract.Presenter mPresenter;
    private Product mProduct;

    public static Intent newIntent(@NonNull Context context, @NonNull Product product) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra(KEY_PRODUCT, product);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        mPresenter = new ProductDetailPresenter();
        mPresenter.attachView(this);
        mProduct = getIntent().getParcelableExtra(KEY_PRODUCT);

        ButterKnife.bind(this);
        setupToolbar();
        bindData(mProduct);
    }

    private void bindData(@NonNull Product product) {
        mTextViewTitle.setText(product.getTitle());
        setupPager(product.getProductImages());

        mButtonSave.setVisibility(product.isSaved() ? ViewPager.GONE : ViewPager.VISIBLE);
        mTextViewDescription.setText(product.getDescription());
        mTextViewPrice.setText(product.getPrice() + " " + product.getCurrencyCode());
    }

    private void setupPager(@NonNull List<ProductImage> productImages) {
        ImagesPagerAdapter imagesPagerAdapter = new ImagesPagerAdapter();
        imagesPagerAdapter.addViews(productImages);
        mViewPagerImages.setPageTransformer(false, new FragmentPageTransformer());
        mViewPagerImages.setAdapter(imagesPagerAdapter);
        mCircularPageIndicator.setViewPager(mViewPagerImages);
    }

    @OnClick(R.id.button_save_to_favorite)
    void onClickSaveToFavorite() {
        mPresenter.saveToFavorite(mProduct);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
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

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onSavedToFavorites() {
        finish();
    }

    /**
     * Setup current toolbar
     */
    private void setupToolbar() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.str_product_details);
    }
}
