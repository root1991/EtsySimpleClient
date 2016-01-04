package com.test.ui.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.test.App;
import com.test.R;
import com.test.model.Product;
import com.test.util.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by andrewkhristyan on 1/2/16.
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.SearchResultsViewHolder> implements ItemTouchHelperAdapter {

    private final ItemClickListener mItemClickListener;
    private int mLayoutId;

    private List<Product> mSearchResults = new ArrayList<>();

    public ProductsAdapter(int layoutId, @Nullable ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        mLayoutId = layoutId;
    }

    public void addProducts(List<Product> repositoryList, boolean needUpdate) {
        if (needUpdate) {
            mSearchResults.clear();
        }
        mSearchResults.addAll(repositoryList);
        notifyDataSetChanged();
    }

    public boolean isAdapterEmpty() {
        return mSearchResults.isEmpty();
    }

    public void addProduct(Product product) {
        mSearchResults.add(product);
        notifyDataSetChanged();
    }

    @Override
    public SearchResultsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(mLayoutId, parent, false);
        return new SearchResultsViewHolder(itemView, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(SearchResultsViewHolder holder, int position) {
        Product product = mSearchResults.get(position);
        holder.bindData(product);
    }

    @Override
    public int getItemCount() {
        return mSearchResults.size();
    }

    @Override
    public void onItemDismiss(int position) {
        App.getDataManager().deleteFeaturedProduct(mSearchResults.get(position));
        mSearchResults.remove(position);
        notifyDataSetChanged();
    }

    public void clear() {
        mSearchResults.clear();
    }

    public static class SearchResultsViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.text_view_title)
        TextView titleTextView;
        @Bind(R.id.image_view_product)
        SimpleDraweeView productImageView;

        private final ItemClickListener mItemClickListener;

        private Product mProduct;
        private int imageSize;

        public SearchResultsViewHolder(@NonNull View itemView, @Nullable ItemClickListener itemClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mItemClickListener = itemClickListener;
            imageSize = itemView.getResources().getDimensionPixelSize(R.dimen.image_size);
        }

        public void bindData(Product product) {
            mProduct = product;
            if (!product.getProductImages().isEmpty()) {
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(product.getProductImages().get(0).getImageUrl()))
                        .setResizeOptions(new ResizeOptions(imageSize, imageSize))
                        .build();
                PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .build();
                productImageView.setAspectRatio(1.0f);
                productImageView.setController(controller);
            }
            titleTextView.setText(product.getTitle());
        }

        @OnClick(R.id.layout_content)
        void onClickListItem() {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(mProduct);
            }
        }
    }

    public interface ItemClickListener {
        void onItemClick(Product product);
    }

}

