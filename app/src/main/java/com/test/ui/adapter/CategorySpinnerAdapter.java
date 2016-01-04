package com.test.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.test.R;
import com.test.model.Category;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by andrewkhristyan on 12/30/15.
 */
public class CategorySpinnerAdapter extends BaseAdapter {

    private List<Category> mData;

    public CategorySpinnerAdapter(List<Category> objects) {
        mData = objects;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Category getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mData.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.bindData(getItem(position));
        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.text_view_category)
        TextView mTextViewCategory;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void bindData(Category category) {
            mTextViewCategory.setText(category.getPageTitle());
        }

    }
}
