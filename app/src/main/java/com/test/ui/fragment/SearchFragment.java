package com.test.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.test.App;
import com.test.R;
import com.test.contract.CategoryContract;
import com.test.model.Category;
import com.test.presenter.CategoryPresenter;
import com.test.ui.activity.SearchResultsActivity;
import com.test.ui.adapter.CategorySpinnerAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by andrewkhristyan on 12/27/15.
 */
public class SearchFragment extends BaseFragment implements CategoryContract.View {

    private CategoryContract.Presenter mPresenter;
    private CategorySpinnerAdapter mSpinnerAdapterCategories;

    @Bind(R.id.spinner_categories)
    Spinner mSpinnerCategories;
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;
    @Bind(R.id.edit_text_search)
    EditText mEditTextSearch;

    private View mRootView;

    public static SearchFragment newInstance() {

        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, mRootView);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mPresenter = new CategoryPresenter();
        mPresenter.attachView(this);

        mPresenter.initCategoriesList(true);
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mPresenter.detachView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.button_submit)
    void onClickSubmit() {
        Category category = mSpinnerAdapterCategories.getItem(mSpinnerCategories.getSelectedItemPosition());
        startActivity(SearchResultsActivity.newIntent(getContext(), category.getCategoryName(), mEditTextSearch.getText().toString().trim()));
    }

    @Override
    public void showCategories(List<Category> categoryList) {
        mProgressBar.setVisibility(View.GONE);
        mSpinnerAdapterCategories = new CategorySpinnerAdapter(App.getDataManager().getCategories());
        mSpinnerCategories.setAdapter(mSpinnerAdapterCategories);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMessage() {
        Snackbar.make(mRootView, R.string.str_error_message, Snackbar.LENGTH_SHORT).show();
    }
}
