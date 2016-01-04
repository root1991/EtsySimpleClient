package com.test.presenter;

import com.test.App;
import com.test.api.task.ApiTask;
import com.test.contract.CategoryContract;
import com.test.model.Category;
import com.test.model.BaseResponse;

import java.util.List;

import retrofit.Call;

public class CategoryPresenter implements CategoryContract.Presenter {

    private CategoryContract.View mView;

    private Call<BaseResponse<Category>> mCategoriesCall;

    @Override
    public void attachView(CategoryContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        if (mCategoriesCall != null) {
            mCategoriesCall.cancel();
        }
    }

    @Override
    public void initCategoriesList(boolean loadIfEmpty) {
        List<Category> storedCategories = App.getDataManager().getCategories();
        if (loadIfEmpty && storedCategories.isEmpty()) {
            fetchCategories();
        } else {
            mView.showCategories(storedCategories);
        }
    }

    @Override
    public void fetchCategories() {
        mView.showProgress();

        mCategoriesCall = App.getApiManager().getCategories();
        mCategoriesCall.enqueue(new ApiTask<BaseResponse<Category>>() {
            @Override
            protected void onSuccess(BaseResponse response) {
                mView.hideProgress();
                onCategoriesLoaded(response.getResults());
                mView.showCategories(response.getResults());
            }

            @Override
            protected void onError() {
                mView.hideProgress();
                mView.showErrorMessage();
            }
        });
    }

    private void onCategoriesLoaded(List<Category> loadedRepositories) {
        App.getDataManager().storeCategories(loadedRepositories);
        initCategoriesList(false);
    }

}
