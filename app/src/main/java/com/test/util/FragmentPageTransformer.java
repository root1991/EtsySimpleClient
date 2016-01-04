package com.test.util;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by andrewkhristyan on 1/3/16.
 */
public class FragmentPageTransformer implements ViewPager.PageTransformer{

    @Override
    public void transformPage(View view, float position) {
        view.setTranslationX(view.getWidth() * -position);

        if(position <= -1.0F || position >= 1.0F) {
            view.setAlpha(0.0F);
        } else if( position == 0.0F ) {
            view.setAlpha(1.0F);
        } else {
            view.setAlpha(1.0F - Math.abs(position));
        }
    }
}
