package com.test.ui.adapter;

import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.test.R;
import com.test.model.ProductImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrewkhristyan on 1/3/16.
 */
public class ImagesPagerAdapter extends PagerAdapter {

    private List<ProductImage> mProductImages = new ArrayList<>();

    public void addViews(List<ProductImage> productImages) {
        mProductImages.clear();
        mProductImages.addAll(productImages);
        notifyDataSetChanged();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) LayoutInflater.from(container.getContext()).inflate(R.layout.item_image_product, container, false);
        simpleDraweeView.setImageURI(Uri.parse(mProductImages.get(position).getImageUrl()));
        container.addView(simpleDraweeView);
        return simpleDraweeView;
    }

    @Override
    public int getCount() {
        return mProductImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
