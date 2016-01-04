package com.test.contract;

import com.test.model.Product;

import java.util.List;

/**
 * Created by andrewkhristyan on 1/3/16.
 */
public class FeaturedContract {

    public interface Presenter extends BaseMvpPresenter<View> {

        void loadFeaturedItems();

    }

    public interface View extends BaseMvpView {

        void showFeaturedProducts(List<Product> featuredProducts);

    }


}
