package com.test.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by andrewkhristyan on 1/3/16.
 */
public class ProductDBModel extends RealmObject {

    @PrimaryKey
    private String listingId;

    private String title;
    private String description;
    private String price;
    private String currencyCode;
    private RealmList<ProductDbImage> productImage;

    public void setProductImage(RealmList<ProductDbImage> productImage) {
        this.productImage = productImage;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getListingId() {
        return listingId;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public RealmList<ProductDbImage> getProductImage() {
        return productImage;
    }
}
