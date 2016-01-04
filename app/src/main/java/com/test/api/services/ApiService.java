package com.test.api.services;

import com.test.api.ApiSettings;
import com.test.model.BaseResponse;
import com.test.model.Category;
import com.test.model.Product;
import com.test.model.ProductImage;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface ApiService {

    @GET(ApiSettings.CATEGORIES)
    Call<BaseResponse<Category>> getCategories(
            @Query(ApiSettings.QUERY_API_KEY) String apikey);

    @GET(ApiSettings.SEARCH_LISTINGS)
    Call<BaseResponse<Product>> getSearchResults(
            @Query(ApiSettings.QUERY_API_KEY) String apiKey,
            @Query(ApiSettings.INCLUDES) String param,
            @Query(ApiSettings.CATEGORY_QUERY) String categoryQuery,
            @Query(ApiSettings.KEYWORDS_QUERY) String keyword,
            @Query(ApiSettings.PAGE) int page);

    @GET(ApiSettings.IMAGES_LISTINGS)
    Call<BaseResponse<ProductImage>> getProductImagesById(
            @Path(ApiSettings.LISTING_ID) String listingId,
            @Query(ApiSettings.QUERY_API_KEY) String apiKey);


}