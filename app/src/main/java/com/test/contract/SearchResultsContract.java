package com.test.contract;

import com.test.model.Product;

import java.util.List;

/**
 * Created by andrewkhristyan on 12/30/15.
 */
public class SearchResultsContract {

    public interface Presenter extends BaseMvpPresenter<View>{

        void loadSearchResults(String category, String keyword, int page);

    }

    public interface View extends BaseMvpView {

        void showProgress();

        void hideProgress();

        void addSearchResultsItems(List<Product> product);

    }
}
