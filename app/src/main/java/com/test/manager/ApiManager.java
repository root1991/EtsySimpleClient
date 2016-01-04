package com.test.manager;

import android.content.Context;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.test.api.ApiSettings;
import com.test.api.services.ApiService;
import com.test.interfaces.Manager;
import com.test.model.BaseResponse;
import com.test.model.Category;
import com.test.model.Product;

import java.io.IOException;

import io.realm.RealmObject;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class ApiManager implements Manager {

    private ApiService mApiService;
    private Retrofit mRetrofit;

    @Override
    public void init(Context context) {
        initRetrofit();
        initServices();
    }

    private void initRetrofit() {
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new Interceptor() {
            @Override
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(interceptor);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(ApiSettings.SERVER)
                .addConverterFactory(createGsonConverter())
                .client(client)
                .build();
    }

    private GsonConverterFactory createGsonConverter() {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getDeclaringClass().equals(RealmObject.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        });
        return GsonConverterFactory.create(builder.create());
    }

    private void initServices() {
        mApiService = mRetrofit.create(ApiService.class);
    }

    public Call<BaseResponse<Category>> getCategories() {
        return mApiService.getCategories(ApiSettings.ETSY_API_KEY);
    }

    public Call<BaseResponse<Product>> getSearchResults(String category, String keyword, int page) {
        return mApiService.getSearchResults(ApiSettings.ETSY_API_KEY, ApiSettings.IMAGES, category, keyword, page);
    }

    @Override
    public void clear() {

    }

}
