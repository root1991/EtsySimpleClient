package com.test.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by andrewkhristyan on 12/27/15.
 */
public class BaseResponse<T> {

    @SerializedName("count")
    private int mCount;

    @SerializedName("results")
    private List<T> mCategories;

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }

    public List<T> getResults() {
        return mCategories;
    }

    public void setCategories(List<T> categories) {
        mCategories = categories;
    }
}
