package com.test.presenter;

import com.test.App;
import com.test.api.task.ApiTask;
import com.test.contract.SearchResultsContract;
import com.test.model.BaseResponse;
import com.test.model.Product;

import retrofit.Call;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by andrewkhristyan on 12/27/15.
 */
public class SearchResultsPresenter implements SearchResultsContract.Presenter {

    private SearchResultsContract.View mView;
    private Call<BaseResponse<Product>> mSearchResultsCall;

    @Override
    public void loadSearchResults(String category, String keyword, int page) {
        mView.showProgress();

        mSearchResultsCall = App.getApiManager().getSearchResults(category, keyword, page);
        mSearchResultsCall.enqueue(new ApiTask<BaseResponse<Product>>() {
            @Override
            protected void onSuccess(BaseResponse<Product> response) {
                mView.hideProgress();
                mView.addSearchResultsItems(response.getResults());
            }

            @Override
            protected void onError() {
                if (mView != null) {
                    mView.hideProgress();
                }
            }
        });
    }


    @Override
    public void attachView(SearchResultsContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
        Observable.just(mSearchResultsCall)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<Call<BaseResponse<Product>>>() {
                    @Override
                    public void call(Call<BaseResponse<Product>> baseResponseCall) {
                        baseResponseCall.cancel();
                    }
                });
    }
}
