package com.test.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by andrewkhristyan on 12/30/15.
 */
public class Product implements Parcelable {

    @SerializedName("listing_id")
    private String listingId;

    @SerializedName("title")
    private String title;

    @SerializedName("Images")
    private List<ProductImage> productImages;

    @SerializedName("description")
    private String description;

    @SerializedName("price")
    private String price;

    @SerializedName("currency_code")
    private String currencyCode;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    private transient boolean isSaved;

    public boolean isSaved() {
        return isSaved;
    }

    public void setIsSaved(boolean isSaved) {
        this.isSaved = isSaved;
    }

    protected Product(Parcel in) {
        listingId = in.readString();
        title = in.readString();
        description = in.readString();
        price = in.readString();
        currencyCode = in.readString();
        isSaved = in.readInt() != 0;
        productImages = in.createTypedArrayList(ProductImage.CREATOR);
    }

    public Product(String listingId, String title, String description, String price, String currencyCode) {
        this.listingId = listingId;
        this.title = title;
        this.description = description;
        this.price = price;
        this.currencyCode = currencyCode;
    }

    public void setProductImages(RealmList<ProductDbImage> productImages) {
        this.productImages = new ArrayList<>();
        for (ProductDbImage productDbImage : productImages) {
            ProductImage productImage = new ProductImage(productDbImage.getImageUrl());
            this.productImages.add(productImage);
        }
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public String getListingId() {
        return listingId;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(listingId);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(price);
        dest.writeString(currencyCode);
        dest.writeInt(isSaved ? 1 : 0);
        dest.writeTypedList(productImages);
    }
}
