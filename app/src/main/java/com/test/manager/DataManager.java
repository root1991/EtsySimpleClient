package com.test.manager;

import android.content.Context;

import com.test.Constant;
import com.test.interfaces.Manager;
import com.test.model.Category;
import com.test.model.Product;
import com.test.model.ProductDBModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmMigrationNeededException;

public class DataManager implements Manager {

    private Realm mRealm;

    @Override
    public void init(Context context) {
        mRealm = getRealmInstance(context);
    }

    private Realm getRealmInstance(Context context) {
        try {
            return Realm.getDefaultInstance();
        } catch (RealmMigrationNeededException exception) {
            Realm.deleteRealm(new RealmConfiguration.Builder(context)
                    .name(Constant.Realm.STORAGE_MAIN).build());
            return Realm.getDefaultInstance();
        }
    }

    public List<Category> getCategories() {
        return mRealm.where(Category.class).findAll();
    }

    public List<ProductDBModel> getFeaturedProducts() {
        return mRealm.where(ProductDBModel.class).findAll();
    }

    public void storeCategories(final List<Category> repositories) {
        try {
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(repositories);
            mRealm.commitTransaction();
        } catch (RealmException e) {
            mRealm.cancelTransaction();
        }
    }

    public void storeProduct(ProductDBModel product) {
        try {
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(product);
            mRealm.commitTransaction();
        } catch (RealmException e) {
            mRealm.cancelTransaction();
        }
    }

    public void deleteFeaturedProduct(Product product) {
        try {
            mRealm.beginTransaction();
            mRealm.where(ProductDBModel.class).equalTo("listingId", product.getListingId()).findAll().remove(0);
            mRealm.commitTransaction();
        } catch (RealmException e) {
            mRealm.cancelTransaction();
        }
    }

    public void removeCategories() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.clear(Category.class);
            }
        });
    }

    @Override
    public void clear() {
        if (mRealm != null && !mRealm.isClosed()) {
            mRealm.close();
        }
    }

}
