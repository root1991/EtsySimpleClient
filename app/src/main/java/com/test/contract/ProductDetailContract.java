package com.test.contract;

import com.test.model.Product;

/**
 * Created by andrewkhristyan on 1/3/16.
 */
public class ProductDetailContract {

    public interface Presenter extends BaseMvpPresenter<View> {

        void saveToFavorite(Product product);

    }

    public interface View extends BaseMvpView {

        void onSavedToFavorites();

    }


}
