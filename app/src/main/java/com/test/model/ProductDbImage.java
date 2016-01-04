package com.test.model;

import io.realm.RealmObject;

/**
 * Created by andrewkhristyan on 1/3/16.
 */
public class ProductDbImage extends RealmObject {

    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
