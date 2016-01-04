package com.test.api.task;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import timber.log.Timber;

/**
 * Base Api Task created for performing error handling in one place
 */
public abstract class ApiTask<E> implements Callback<E> {

    @Override
    public void onResponse(Response<E> response, Retrofit retrofit) {
        Timber.d("success", response.code());
        if (response.isSuccess()) {
            onSuccess(response.body());
        }
    }

    /**
     * Callback for all child classes of {@link ApiTask}
     *
     * @param response which contains Object of type defined in child
     */
    protected abstract void onSuccess(E response);

    /**
     * Callback for all child classes of {@link ApiTask}*
     */
    protected abstract void onError();

    /**
     * Invoked when a network or unexpected exception occurred during the HTTP request.
     * Can be Override in child class for additional message throwing
     */
    @Override
    public void onFailure(Throwable t) {
        onError();
    }

}