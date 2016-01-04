package com.test.presenter;

import com.test.App;
import com.test.contract.FeaturedContract;
import com.test.model.Product;
import com.test.model.ProductDBModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrewkhristyan on 1/3/16.
 */
public class FeaturedPresenter implements FeaturedContract.Presenter {

    private FeaturedContract.View mView;

    @Override
    public void loadFeaturedItems() {
        List<Product> products = new ArrayList<>();
        for (ProductDBModel productDBModel : App.getDataManager().getFeaturedProducts()) {
            Product product = new Product(productDBModel.getListingId(), productDBModel.getTitle(),
                    productDBModel.getDescription(), productDBModel.getPrice(), productDBModel.getCurrencyCode());
            product.setProductImages(productDBModel.getProductImage());
            product.setIsSaved(true);
            products.add(product);
        }
        mView.showFeaturedProducts(products);
    }

    @Override
    public void attachView(FeaturedContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
