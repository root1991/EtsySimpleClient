package com.test.presenter;

import com.test.App;
import com.test.R;
import com.test.contract.ProductDetailContract;
import com.test.model.Product;
import com.test.model.ProductDBModel;
import com.test.model.ProductDbImage;
import com.test.model.ProductImage;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by andrewkhristyan on 1/3/16.
 */
public class ProductDetailPresenter implements ProductDetailContract.Presenter {

    private ProductDetailContract.View mView;

    @Override
    public void saveToFavorite(Product product) {
        ProductDBModel productDBModel = new ProductDBModel();

        productDBModel.setListingId(product.getListingId());
        productDBModel.setProductImage(createDbImages(product.getProductImages()));
        productDBModel.setTitle(product.getTitle());
        productDBModel.setDescription(product.getDescription());
        productDBModel.setPrice(product.getPrice());
        productDBModel.setCurrencyCode(product.getCurrencyCode());

        App.getDataManager().storeProduct(productDBModel);
        mView.onSavedToFavorites();
    }

    private RealmList<ProductDbImage> createDbImages(List<ProductImage> productImages) {
        RealmList<ProductDbImage> productDbImages = new RealmList<>();
        for (ProductImage productImage : productImages) {
            ProductDbImage productDbImage = new ProductDbImage();
            productDbImage.setImageUrl(productImage.getImageUrl());
            productDbImages.add(productDbImage);
        }
        return productDbImages;
    }

    @Override
    public void attachView(ProductDetailContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
