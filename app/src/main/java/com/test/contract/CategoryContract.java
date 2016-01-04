package com.test.contract;

import android.support.annotation.NonNull;

import com.test.model.Category;

import java.util.List;

public class CategoryContract {

    public interface Presenter extends BaseMvpPresenter<View> {

        void initCategoriesList(boolean loadIfEmpty);

        void fetchCategories();

    }

    public interface View extends BaseMvpView {

        void showCategories(List<Category> categoryList);

        void showProgress();

        void hideProgress();

        void showErrorMessage();

    }

}
